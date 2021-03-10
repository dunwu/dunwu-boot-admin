package io.github.dunwu.modules.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import io.github.dunwu.modules.system.entity.dto.SysDeptDto;
import io.github.dunwu.modules.system.entity.dto.SysRoleDto;
import io.github.dunwu.modules.system.entity.dto.SysUserDto;
import io.github.dunwu.modules.system.service.DataService;
import io.github.dunwu.modules.system.service.SysDeptService;
import io.github.dunwu.modules.system.service.SysRoleService;
import io.github.dunwu.util.enums.DataScopeEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Zheng Jie
 * @website https://el-admin.vip
 * @description 数据权限服务实现
 * @date 2020-05-07
 **/
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "data")
public class DataServiceImpl implements DataService {

    private final SysRoleService roleService;
    private final SysDeptService deptService;

    /**
     * 用户角色改变时需清理缓存
     *
     * @param user /
     * @return /
     */
    @Override
    @Cacheable(key = "'user:' + #p0.id")
    public List<Long> getDeptIds(SysUserDto user) {
        // 用于存储部门id
        Set<Long> deptIds = new HashSet<>();
        // 查询用户角色
        List<SysRoleDto> roles = roleService.pojoListByUserId(user.getId());
        // 获取对应的部门ID
        for (SysRoleDto role : roles) {
            DataScopeEnum dataScopeEnum = DataScopeEnum.find(role.getDataScope());
            switch (Objects.requireNonNull(dataScopeEnum)) {
                case THIS_LEVEL:
                    deptIds.add(user.getDept().getId());
                    break;
                case CUSTOMIZE:
                    deptIds.addAll(getCustomize(deptIds, role));
                    break;
                default:
                    return new ArrayList<>(deptIds);
            }
        }
        return new ArrayList<>(deptIds);
    }

    /**
     * 获取自定义的数据权限
     *
     * @param deptIds 部门ID
     * @param role    角色
     * @return 数据权限ID
     */
    public Set<Long> getCustomize(Set<Long> deptIds, SysRoleDto role) {
        List<SysDeptDto> sysDeptDtos = deptService.pojoListByRoleId(role.getId());
        for (SysDeptDto dept : sysDeptDtos) {
            deptIds.add(dept.getId());
            Set<Long> childrenDeptIds = deptService.getChildrenDeptIds(dept.getId());
            if (CollectionUtil.isNotEmpty(childrenDeptIds)) {
                deptIds.addAll(childrenDeptIds);
            }
        }
        return deptIds;
    }

}
