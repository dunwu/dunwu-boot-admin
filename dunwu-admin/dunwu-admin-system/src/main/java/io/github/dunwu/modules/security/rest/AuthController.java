package io.github.dunwu.modules.security.rest;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.wf.captcha.base.Captcha;
import io.github.dunwu.annotation.rest.AnonymousGetMapping;
import io.github.dunwu.annotation.rest.AnonymousPostMapping;
import io.github.dunwu.config.RsaProperties;
import io.github.dunwu.data.core.Result;
import io.github.dunwu.data.redis.RedisHelper;
import io.github.dunwu.data.validator.annotation.EditCheck;
import io.github.dunwu.modules.monitor.annotation.AppLog;
import io.github.dunwu.modules.security.config.DunwuWebSecurityProperties;
import io.github.dunwu.modules.security.entity.constant.LoginCodeEnum;
import io.github.dunwu.modules.security.entity.dto.AuthUserDto;
import io.github.dunwu.modules.security.entity.dto.JwtUserDto;
import io.github.dunwu.modules.security.security.TokenProvider;
import io.github.dunwu.modules.security.service.AuthService;
import io.github.dunwu.modules.system.entity.SysUser;
import io.github.dunwu.modules.system.entity.vo.UserPassVo;
import io.github.dunwu.util.RsaUtils;
import io.github.dunwu.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Zheng Jie
 * @date 2018-11-23 授权、根据token获取用户详细信息
 */
@Slf4j
@RestController
@RequestMapping("auth")
@Api(tags = "系统：系统授权接口")
public class AuthController {

    private final DunwuWebSecurityProperties properties;
    private final RedisHelper redisHelper;
    private final AuthService authService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthController(DunwuWebSecurityProperties properties, RedisHelper redisHelper,
        AuthService authService, TokenProvider tokenProvider,
        AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.properties = properties;
        this.redisHelper = redisHelper;
        this.authService = authService;
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @ApiOperation("登录授权")
    @AnonymousPostMapping(value = "/login")
    public Result login(@Validated @RequestBody AuthUserDto authUser, HttpServletRequest request)
        throws Exception {
        // 密码解密
        String password = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, authUser.getPassword());
        // 查询验证码
        String code = (String) redisHelper.get(authUser.getUuid());
        // 清除验证码
        redisHelper.del(authUser.getUuid());
        if (StrUtil.isBlank(code)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "验证码不存在或已过期");
        }
        if (StrUtil.isBlank(authUser.getCode()) || !authUser.getCode().equalsIgnoreCase(code)) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "验证码错误");
        }
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(authUser.getUsername(), password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 生成令牌
        String token = tokenProvider.createToken(authentication);
        final JwtUserDto jwtUserDto = (JwtUserDto) authentication.getPrincipal();
        // 保存在线信息
        authService.save(jwtUserDto, token, request);
        // 返回 token 与 用户信息
        Map<String, Object> authInfo = new HashMap<String, Object>(2) {{
            put("token", properties.getJwt().getTokenStartWith() + token);
            put("user", jwtUserDto);
        }};
        if (properties.isSingleLogin()) {
            //踢掉之前已经登录的token
            authService.checkLoginOnUser(authUser.getUsername(), token);
        }
        return Result.ok(authInfo);
    }

    @ApiOperation("获取用户信息")
    @GetMapping(value = "/info")
    public Result getUserInfo() {
        return Result.ok(SecurityUtils.getCurrentUser());
    }

    @ApiOperation("获取验证码")
    @AnonymousGetMapping(value = "/code")
    public Result getCode() {
        // 获取运算的结果
        Captcha captcha = authService.getCaptcha();
        String uuid = properties.getJwt().getCodeKey() + IdUtil.simpleUUID();
        //当验证码类型为 arithmetic时且长度 >= 2 时，captcha.text()的结果有几率为浮点型
        String captchaValue = captcha.text();
        if (captcha.getCharType() - 1 == LoginCodeEnum.arithmetic.ordinal() && captchaValue.contains(".")) {
            captchaValue = captchaValue.split("\\.")[0];
        }
        // 保存
        redisHelper.set(uuid, captchaValue, properties.getLoginCode().getExpiration(), TimeUnit.MINUTES);
        // 验证码信息
        Map<String, Object> imgResult = new HashMap<String, Object>(2) {{
            put("img", captcha.toBase64());
            put("uuid", uuid);
        }};
        return Result.ok(imgResult);
    }

    @ApiOperation("退出登录")
    @AnonymousPostMapping(value = "/logout")
    public Result logout(HttpServletRequest request) {
        authService.logout(tokenProvider.getToken(request));
        return Result.ok();
    }

    @AppLog("修改用户：个人中心")
    @ApiOperation("修改用户：个人中心")
    @PostMapping("edit/center")
    public Result center(@Validated(EditCheck.class) @RequestBody SysUser entity) {
        if (!entity.getId().equals(SecurityUtils.getCurrentUserId())) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "不能修改他人资料");
        }
        authService.updateCenter(entity);
        return Result.ok();
    }

    @ApiOperation("修改用户密码")
    @PostMapping("edit/password")
    public Result updatePass(@RequestBody UserPassVo entity) throws Exception {
        authService.updatePass(entity);
        return Result.ok();
    }

    @AppLog("修改用户邮箱")
    @ApiOperation("修改用户邮箱")
    @PostMapping(value = "edit/email/{code}")
    public Result updateEmail(@PathVariable String code,
        @Validated(EditCheck.class) @RequestBody SysUser entity) throws Exception {
        authService.updateEmail(code, entity);
        return Result.ok();
    }

}
