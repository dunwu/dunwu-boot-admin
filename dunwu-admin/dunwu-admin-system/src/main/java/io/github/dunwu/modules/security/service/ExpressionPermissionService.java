package io.github.dunwu.modules.security.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限表达式处理服务
 * <p>
 * 会拦截被  @PreAuthorize("@exp.check('xxx')") 修饰的方法，只有 check 方法返回 true，才有权限执行方法
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-08
 */
@Service(value = "exp")
public class ExpressionPermissionService {

    public boolean check(String... permissions) {
        // 获取当前用户的所有权限
        List<String> userPerms = SecurityUtil.getCurrentUser()
            .getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());
        // 判断当前用户的所有权限是否包含接口上定义的权限
        return userPerms.contains("admin") || Arrays.stream(permissions).anyMatch(userPerms::contains);
    }

}
