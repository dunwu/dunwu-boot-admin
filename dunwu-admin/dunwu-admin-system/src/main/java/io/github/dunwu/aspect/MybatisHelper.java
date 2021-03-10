package io.github.dunwu.aspect;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import io.github.dunwu.modules.system.entity.dto.SysUserDto;
import io.github.dunwu.modules.system.service.SysUserService;
import io.github.dunwu.tool.util.ClassUtil;
import io.github.dunwu.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-14
 */
@Slf4j
@Component
public class MybatisHelper {

    private final SysUserService userService;

    public MybatisHelper(SysUserService userService) {
        this.userService = userService;
    }

    /**
     * 将数据库实体部分约定参数进行转换
     */
    public void convertEntity(Object results) throws IllegalAccessException {

        if (results instanceof List<?>) {
            List<?> list = (List<?>) results;
            if (CollectionUtil.isNotEmpty(list)) {
                for (Object entity : list) {
                    convertEntityInSingle(entity);
                }
            }
            return;
        }

        if (results instanceof Page<?>) {
            Page<?> page = (Page<?>) results;
            if (CollectionUtil.isNotEmpty(page.getContent())) {
                List<?> list = page.getContent();
                for (Object entity : list) {
                    convertEntityInSingle(entity);
                }
            }
            return;
        }

        convertEntityInSingle(results);
    }

    /**
     * 如果传入的实体含有 createBy, updateBy 字段，自动其 createBy, updateBy 由 ID 转为用户名
     */
    public void convertEntityInSingle(Object obj) throws IllegalAccessException {
        if (log.isTraceEnabled()) {
            log.trace("[MybatisCrudAspect] 修改返回值前：{}", obj);
        }

        Field[] fields = ClassUtil.getAllFields(obj.getClass());
        for (Field f : fields) {
            if (f.getName().equals("createBy") || f.getName().equals("updateBy")) {
                f.setAccessible(true);
                Object value = f.get(obj);
                // 如果应答结果实体中 createBy、updateBy 为 String 类型，则将 ID 转换为用户名
                if (f.getType() == String.class) {
                    if (value != null) {
                        SysUserDto sysUser = userService.pojoById((Serializable) value);
                        if (sysUser == null) {
                            f.set(obj, null);
                        } else {
                            f.set(obj, sysUser.getUsername());
                        }
                    }
                }
            }
        }

        if (log.isTraceEnabled()) {
            log.trace("[MybatisCrudAspect] 修改返回值后：{}", obj);
        }
    }

    /**
     * 为数据库实体补全创建者、更新者信息
     */
    public void completeUserToEntity(Object obj, String methodName) throws IllegalAccessException {

        if (obj instanceof Wrapper) {
            return;
        }

        if (obj instanceof Collection) {
            Collection<?> list = (Collection<?>) obj;
            for (Object o : list) {
                completeUserToEntityInSingle(o, methodName);
            }
            return;
        }

        completeUserToEntityInSingle(obj, methodName);
    }

    private void completeUserToEntityInSingle(Object obj, String methodName) throws IllegalAccessException {
        Field[] fields = ClassUtil.getAllFields(obj.getClass());
        for (Field f : fields) {
            f.setAccessible(true);

            if (f.getName().equals("createBy") && methodName.startsWith("save")) {
                f.set(obj, SecurityUtils.getCurrentUsername());
            } else if (f.getName().equals("updateBy") && methodName.startsWith("update")) {
                f.set(obj, SecurityUtils.getCurrentUsername());
            }

            f.setAccessible(false);
        }
    }

}
