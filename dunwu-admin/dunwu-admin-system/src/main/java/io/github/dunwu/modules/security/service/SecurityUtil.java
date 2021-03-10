package io.github.dunwu.modules.security.service;

import cn.hutool.json.JSONObject;
import io.github.dunwu.modules.security.exception.AuthException;
import io.github.dunwu.web.util.SpringUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Spring Security 工具类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-08
 */
public class SecurityUtil {

    /**
     * 获取当前用户身份信息
     */
    public static UserDetails getCurrentUser() throws AuthException {
        UserDetails userDetails = getDefaultUserDetails();
        UserDetailsService userDetailsService = SpringUtil.getBean(UserDetailsService.class);
        return userDetailsService.loadUserByUsername(userDetails.getUsername());
    }

    /**
     * 获取当前用户名称
     */
    public static String getCurrentUsername() {
        UserDetails userDetails = getDefaultUserDetails();
        return userDetails.getUsername();
    }

    /**
     * 获取系统用户ID
     *
     * @return 系统用户ID
     */
    public static Long getCurrentUserId() {
        UserDetails userDetails = getCurrentUser();
        return new JSONObject(new JSONObject(userDetails).get("user")).get("id", Long.class);
    }

    private static UserDetails getDefaultUserDetails() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new AuthException("当前登录状态过期");
        }
        return (UserDetails) authentication.getPrincipal();
    }

}
