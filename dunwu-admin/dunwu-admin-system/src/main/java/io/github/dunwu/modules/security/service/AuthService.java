package io.github.dunwu.modules.security.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.wf.captcha.*;
import com.wf.captcha.base.Captcha;
import io.github.dunwu.config.RsaProperties;
import io.github.dunwu.data.core.DataException;
import io.github.dunwu.data.core.Result;
import io.github.dunwu.data.redis.RedisHelper;
import io.github.dunwu.data.util.PageUtil;
import io.github.dunwu.modules.security.config.DunwuWebSecurityProperties;
import io.github.dunwu.modules.security.entity.dto.JwtUserDto;
import io.github.dunwu.modules.security.entity.dto.LoginCodeDto;
import io.github.dunwu.modules.security.entity.dto.OnlineUserDto;
import io.github.dunwu.modules.system.entity.SysUser;
import io.github.dunwu.modules.system.entity.dto.SysUserDto;
import io.github.dunwu.modules.system.entity.query.SysUserQuery;
import io.github.dunwu.modules.system.entity.vo.UserPassVo;
import io.github.dunwu.modules.system.service.SysDeptService;
import io.github.dunwu.modules.system.service.SysRoleService;
import io.github.dunwu.modules.system.service.SysUserService;
import io.github.dunwu.modules.system.service.VerifyService;
import io.github.dunwu.tool.exception.BadConfigurationException;
import io.github.dunwu.util.*;
import io.github.dunwu.util.enums.CodeEnum;
import io.github.dunwu.web.util.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Zheng Jie
 * @date 2019???10???26???21:56:27
 */
@Slf4j
@Service("userDetailsService")
public class AuthService implements UserDetailsService {

    static final Map<String, JwtUserDto> userDtoCache = new ConcurrentHashMap<>();

    private final RedisHelper redisHelper;
    private final SysUserService userService;
    private final SysDeptService deptService;
    private final SysRoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final VerifyService verifyService;
    private final DunwuWebSecurityProperties securityProperties;

    public AuthService(RedisHelper redisHelper, SysUserService userService,
        SysDeptService deptService, SysRoleService roleService,
        PasswordEncoder passwordEncoder, VerifyService verifyService,
        DunwuWebSecurityProperties securityProperties) {
        this.redisHelper = redisHelper;
        this.userService = userService;
        this.deptService = deptService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.verifyService = verifyService;
        this.securityProperties = securityProperties;
    }

    /**
     * ????????????????????????
     *
     * @param jwtUserDto /
     * @param token      /
     * @param request    /
     */
    public void save(JwtUserDto jwtUserDto, String token, HttpServletRequest request) {
        String dept = jwtUserDto.getUser().getDept().getName();
        ServletUtil.RequestIdentityInfo requestIdentityInfo = ServletUtil.getRequestIdentityInfo(request);
        String ip = requestIdentityInfo.getIp();
        String browser = requestIdentityInfo.getBrowser();
        String address = requestIdentityInfo.getLocation();
        OnlineUserDto onlineUserDto = null;
        try {
            onlineUserDto = new OnlineUserDto(jwtUserDto.getUsername(), jwtUserDto.getUser().getNickname(), dept,
                browser, ip, address, EncryptUtils.desEncrypt(token), new Date());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        redisHelper.set(securityProperties.getJwt().getOnlineKey() + token, onlineUserDto,
            securityProperties.getJwt().getTokenValidityInSeconds() / 1000);
    }

    /**
     * ??????????????????
     *
     * @param filter   /
     * @param pageable /
     * @return /
     */
    public Map<String, Object> getAll(String filter, Pageable pageable) {
        List<OnlineUserDto> onlineUserDtos = getAll(filter);
        return PageUtil.toMap(
            PageUtil.toList(pageable.getPageNumber(), pageable.getPageSize(), onlineUserDtos),
            onlineUserDtos.size()
        );
    }

    /**
     * ??????????????????????????????
     *
     * @param filter /
     * @return /
     */
    public List<OnlineUserDto> getAll(String filter) {
        List<String> keys = redisHelper.scan(securityProperties.getJwt().getOnlineKey() + "*");
        Collections.reverse(keys);
        List<OnlineUserDto> onlineUserDtos = new ArrayList<>();
        for (String key : keys) {
            OnlineUserDto onlineUserDto = (OnlineUserDto) redisHelper.get(key);
            if (StrUtil.isNotBlank(filter)) {
                if (onlineUserDto.toString().contains(filter)) {
                    onlineUserDtos.add(onlineUserDto);
                }
            } else {
                onlineUserDtos.add(onlineUserDto);
            }
        }
        onlineUserDtos.sort((o1, o2) -> o2.getLoginTime().compareTo(o1.getLoginTime()));
        return onlineUserDtos;
    }

    /**
     * ????????????
     *
     * @param key /
     */
    public void kickOut(String key) {
        key = securityProperties.getJwt().getOnlineKey() + key;
        redisHelper.del(key);
    }

    /**
     * ????????????
     *
     * @param token /
     */
    public void logout(String token) {
        String key = securityProperties.getJwt().getOnlineKey() + token;
        redisHelper.del(key);
    }

    /**
     * ??????
     *
     * @param all      /
     * @param response /
     * @throws IOException /
     */
    public void download(List<OnlineUserDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (OnlineUserDto user : all) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("?????????", user.getUserName());
            map.put("??????", user.getDept());
            map.put("??????IP", user.getIp());
            map.put("????????????", user.getAddress());
            map.put("?????????", user.getBrowser());
            map.put("????????????", user.getLoginTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    /**
     * ????????????
     *
     * @param key /
     * @return /
     */
    public OnlineUserDto getOne(String key) {
        return (OnlineUserDto) redisHelper.get(key);
    }

    /**
     * ???????????????????????????????????????????????????????????????
     *
     * @param userName ?????????
     */
    public void checkLoginOnUser(String userName, String igoreToken) {
        List<OnlineUserDto> onlineUserDtos = getAll(userName);
        if (onlineUserDtos == null || onlineUserDtos.isEmpty()) {
            return;
        }
        for (OnlineUserDto onlineUserDto : onlineUserDtos) {
            if (onlineUserDto.getUserName().equals(userName)) {
                try {
                    String token = EncryptUtils.desDecrypt(onlineUserDto.getKey());
                    if (StrUtil.isNotBlank(igoreToken) && !igoreToken.equals(token)) {
                        this.kickOut(token);
                    } else if (StrUtil.isBlank(igoreToken)) {
                        this.kickOut(token);
                    }
                } catch (Exception e) {
                    log.error("checkUser is error", e);
                }
            }
        }
    }

    /**
     * ???????????????????????????
     *
     * @param username /
     */
    @Async
    public void kickOutForUsername(String username) throws Exception {
        List<OnlineUserDto> onlineUsers = getAll(username);
        for (OnlineUserDto onlineUser : onlineUsers) {
            if (onlineUser.getUserName().equals(username)) {
                String token = EncryptUtils.desDecrypt(onlineUser.getKey());
                kickOut(token);
            }
        }
    }

    /**
     * ???????????????????????????
     *
     * @return /
     */
    public Captcha getCaptcha() {
        return switchCaptcha(securityProperties.getLoginCode());
    }

    /**
     * ?????????????????????????????????
     *
     * @param loginCode ?????????????????????
     * @return /
     */
    private Captcha switchCaptcha(LoginCodeDto loginCode) {
        Captcha captcha;
        synchronized (this) {
            switch (loginCode.getCodeType()) {
                case arithmetic:
                    // ???????????? https://gitee.com/whvse/EasyCaptcha
                    captcha = new ArithmeticCaptcha(loginCode.getWidth(), loginCode.getHeight());
                    // ?????????????????????????????????
                    captcha.setLen(loginCode.getLength());
                    break;
                case chinese:
                    captcha = new ChineseCaptcha(loginCode.getWidth(), loginCode.getHeight());
                    captcha.setLen(loginCode.getLength());
                    break;
                case chinese_gif:
                    captcha = new ChineseGifCaptcha(loginCode.getWidth(), loginCode.getHeight());
                    captcha.setLen(loginCode.getLength());
                    break;
                case gif:
                    captcha = new GifCaptcha(loginCode.getWidth(), loginCode.getHeight());
                    captcha.setLen(loginCode.getLength());
                    break;
                case spec:
                    captcha = new SpecCaptcha(loginCode.getWidth(), loginCode.getHeight());
                    captcha.setLen(loginCode.getLength());
                    break;
                default:
                    throw new BadConfigurationException("???????????????????????????????????????????????? LoginCodeEnum ");
            }
        }
        if (StrUtil.isNotBlank(loginCode.getFontName())) {
            captcha.setFont(new Font(loginCode.getFontName(), Font.PLAIN, loginCode.getFontSize()));
        }
        return captcha;
    }

    public void setEnableCache(boolean enableCache) {
        securityProperties.setCacheEnable(enableCache);
    }

    @Override
    public JwtUserDto loadUserByUsername(String username) {
        boolean searchDb = true;
        JwtUserDto jwtUserDto = null;
        if (securityProperties.isCacheEnable() && userDtoCache.containsKey(username)) {
            jwtUserDto = userDtoCache.get(username);
            searchDb = false;
        }
        if (searchDb) {
            SysUserDto user;
            user = userService.pojoByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("");
            } else {
                if (!user.getEnabled()) {
                    throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "??????????????????");
                }

                Set<Long> deptIds = deptService.getChildrenDeptIds(user.getDeptId());
                jwtUserDto = new JwtUserDto(user, deptIds, roleService.mapToGrantedAuthorities(user));
                userDtoCache.put(username, jwtUserDto);
            }
        }
        return jwtUserDto;
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateCenter(SysUser entity) {
        SysUserDto user = userService.pojoById(entity.getId());
        SysUserQuery query = new SysUserQuery();
        query.setPhone(entity.getPhone());
        SysUserDto user1 = userService.pojoByQuery(query);
        if (user1 != null && !user.getId().equals(user1.getId())) {
            throw new DataException(StrUtil.format("????????? phone = {} ?????????", entity.getPhone()));
        }
        SysUser sysUser = BeanUtil.toBean(user, SysUser.class);
        sysUser.setNickname(entity.getNickname());
        sysUser.setPhone(entity.getPhone());
        sysUser.setGender(entity.getGender());
        userService.updateById(sysUser);
        // ????????????
        delCaches(user.getId(), user.getUsername());
    }

    @Transactional(rollbackFor = Exception.class)
    public void updatePass(UserPassVo passVo) throws Exception {
        String oldPass = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, passVo.getOldPass());
        String newPass = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, passVo.getNewPass());
        SysUserDto sysUserDto = userService.pojoByUsername(SecurityUtils.getCurrentUsername());
        if (!passwordEncoder.matches(oldPass, sysUserDto.getPassword())) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "??????????????????????????????");
        }
        if (passwordEncoder.matches(newPass, sysUserDto.getPassword())) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "?????????????????????????????????");
        }
        SysUser user = new SysUser();
        user.setId(sysUserDto.getId());
        user.setPassword(passwordEncoder.encode(newPass));
        userService.updateById(user);
        flushCache(sysUserDto.getUsername());
    }

    public Result updateEmail(String code, SysUser entity) throws Exception {
        String password = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, entity.getPassword());
        SysUserDto userDto = userService.pojoByUsername(SecurityUtils.getCurrentUsername());
        if (!passwordEncoder.matches(password, userDto.getPassword())) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "????????????");
        }
        verifyService.validated(CodeEnum.EMAIL_RESET_EMAIL_CODE.getKey() + entity.getEmail(), code);
        SysUser user = new SysUser();
        user.setId(entity.getId());
        user.setPassword(passwordEncoder.encode(entity.getPassword()));
        userService.updateById(user);
        return Result.ok();
    }

    /**
     * ????????????
     *
     * @param id /
     */
    public void delCaches(Long id, String username) {
        redisHelper.del(CacheKey.USER_ID + id);
        flushCache(username);
    }

    /**
     * ?????? ????????? ??????????????????
     *
     * @param username /
     */
    private void flushCache(String username) {
        cleanUserCache(username);
    }

    /**
     * ??????????????????????????????<br> ?????????????????????
     *
     * @param userName /
     */
    public void cleanUserCache(String userName) {
        if (StrUtil.isNotEmpty(userName)) {
            userDtoCache.remove(userName);
        }
    }

    /**
     * ?????????????????????????????????<br> ?????????????????????????????????????????????????????????????????????
     */
    public void cleanAll() {
        userDtoCache.clear();
    }

}
