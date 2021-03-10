package io.github.dunwu.modules.security.config;

import io.github.dunwu.modules.security.entity.dto.JwtDto;
import io.github.dunwu.modules.security.entity.dto.LoginCodeDto;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * web 安全配置
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-08
 */
@Data
@ToString
public class DunwuWebSecurityProperties {

    /** 安全开关 */
    private boolean enabled = false;
    /** 是否允许跨域开关 */
    private boolean corsEnabled = false;
    /** 用户登录信息缓存 */
    private boolean cacheEnable;
    /** 是否开启单点登录 */
    private boolean singleLogin = true;
    /** 校验码有效期（时间单位：分钟） */
    private Long codeExpireTime = 10L;
    /** RSA 私钥 */
    private String rsaPrivateKey;
    /** 跨域的路径 */
    private String corsPath = "/**";
    /** 是否开启 XSS 开关 */
    private boolean xssEnabled = true;
    /** 跳过 XSS 防御的路径 */
    private String xssExcludePath = "/favicon.ico,/img/*,/js/*,/css/*";
    /** 认证令牌 */
    private String authTokenKey = "token";
    /** 所有请求白名单 */
    private List<String> allRequestWhiteList;
    /** Get 请求白名单 */
    private List<String> getRequestWhiteList;
    /** JWT */
    private final JwtDto jwt = new JwtDto();
    /** 登陆码 */
    private final LoginCodeDto loginCode = new LoginCodeDto();

}
