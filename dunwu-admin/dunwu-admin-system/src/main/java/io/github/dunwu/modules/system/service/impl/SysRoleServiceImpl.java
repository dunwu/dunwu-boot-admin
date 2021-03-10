package io.github.dunwu.modules.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.dunwu.data.mybatis.ServiceImpl;
import io.github.dunwu.data.redis.RedisHelper;
import io.github.dunwu.modules.security.exception.AuthException;
import io.github.dunwu.modules.security.service.SecurityUtil;
import io.github.dunwu.modules.system.dao.*;
import io.github.dunwu.modules.system.entity.*;
import io.github.dunwu.modules.system.entity.dto.SysDeptDto;
import io.github.dunwu.modules.system.entity.dto.SysMenuDto;
import io.github.dunwu.modules.system.entity.dto.SysRoleDto;
import io.github.dunwu.modules.system.entity.dto.SysUserDto;
import io.github.dunwu.modules.system.service.SysRoleService;
import io.github.dunwu.modules.system.service.SysUserService;
import io.github.dunwu.tool.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统角色信息 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "role")
public class SysRoleServiceImpl extends ServiceImpl implements SysRoleService {

    private final SysUserService userService;

    private final SysRoleDao roleDao;
    private final SysDeptDao deptDao;
    private final SysMenuDao menuDao;
    private final SysUserRoleDao userRoleDao;
    private final SysJobRoleDao jobRoleDao;
    private final SysRoleMenuDao roleMenuDao;
    private final SysRoleDeptDao roleDeptDao;

    private final RedisHelper redisHelper;

    @Override
    public boolean save(SysRole entity) {
        checkRoleLevel(entity.getLevel());
        if (entity.getEnabled() == null) {
            entity.setEnabled(true);
        }
        return roleDao.save(entity);
    }

    @Override
    public boolean updateById(SysRole entity) {
        checkRoleLevel(entity.getLevel());
        return roleDao.updateById(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        SysRole sysRole = roleDao.getById(id);
        if (sysRole == null) { return true; }
        checkRoleLevel(sysRole.getLevel());
        return roleDao.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByIds(Collection<Serializable> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return true;
        }

        return roleDao.removeByIds(ids);
    }

    @Override
    public Page<SysRoleDto> pojoPageByQuery(Object query, Pageable pageable) {
        return roleDao.pojoPageByQuery(query, pageable, this::doToDto);
    }

    @Override
    public List<SysRoleDto> pojoListByQuery(Object query) {
        return roleDao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    @Cacheable(key = "'id:' + #p0")
    public SysRoleDto pojoById(Serializable id) {
        return roleDao.pojoById(id, this::doToDto);
    }

    @Override
    public SysRoleDto pojoByQuery(Object query) {
        return roleDao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(Object query) {
        return roleDao.countByQuery(query);
    }

    @Override
    public void exportByIds(Collection<Serializable> ids, HttpServletResponse response) throws IOException {
        List<SysRoleDto> list = roleDao.pojoListByIds(ids, SysRoleDto.class);
        roleDao.exportDtoList(list, response);
    }

    @Override
    public void exportPageData(Object query, Pageable pageable, HttpServletResponse response) throws IOException {
        Page<SysRoleDto> page = roleDao.pojoPageByQuery(query, pageable, SysRoleDto.class);
        roleDao.exportDtoList(page.getContent(), response);
    }

    @Override
    public List<SysRoleDto> pojoListByUserId(Long userId) {
        List<SysRoleDto> sysRoleVos = new ArrayList<>();
        SysUserRole userRole = new SysUserRole();
        userRole.setUserId(userId);

        List<SysUserRole> usersRoles = userRoleDao.list(userRole);
        if (CollectionUtil.isEmpty(usersRoles)) {
            return sysRoleVos;
        }

        Set<Long> roleIds = usersRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toSet());
        if (CollectionUtil.isEmpty(roleIds)) {
            return sysRoleVos;
        }

        return roleDao.pojoListByIds(roleIds, this::doToDto);
    }

    @Override
    public List<SysRoleDto> pojoListByJobId(Long jobId) {
        List<SysRoleDto> dtoList = new ArrayList<>();
        List<SysJobRole> list = jobRoleDao.list(new SysJobRole().setJobId(jobId));
        if (CollectionUtil.isEmpty(list)) {
            return dtoList;
        }
        Set<Long> roleIds = list.stream().map(SysJobRole::getRoleId).collect(Collectors.toSet());
        if (CollectionUtil.isEmpty(roleIds)) {
            return dtoList;
        }
        return roleDao.pojoListByIds(roleIds, SysRoleDto.class);
    }

    @Override
    public List<SysRoleDto> pojoListByMenuIds(Collection<Long> menuIds) {
        LambdaQueryWrapper<SysRoleMenu> wrapper =
            new QueryWrapper<SysRoleMenu>().lambda().in(SysRoleMenu::getMenuId, menuIds);
        List<SysRoleMenu> roleMenus = roleMenuDao.list(wrapper);
        List<Long> roleIds = roleMenus.stream()
                                      .filter(Objects::nonNull)
                                      .map(SysRoleMenu::getRoleId)
                                      .collect(Collectors.toList());
        return roleDao.pojoListByIds(roleIds, SysRoleDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateMenusByRoleId(Long roleId, List<SysMenuDto> menus) {

        SysRole entity = roleDao.getById(roleId);
        checkRoleLevel(entity.getLevel());

        List<SysRoleMenu> roleMenus = roleMenuDao.list(new SysRoleMenu().setRoleId(roleId));
        Set<Long> oldIds = roleMenus.stream().map(SysRoleMenu::getId).collect(Collectors.toSet());
        Set<Long> newIds = menus.stream().map(SysMenuDto::getId).collect(Collectors.toSet());
        boolean isEquals = Arrays.equals(oldIds.toArray(new Long[0]), newIds.toArray(new Long[0]));
        if (isEquals) {
            return true;
        }

        roleMenuDao.removeByIds(oldIds);
        Set<SysRoleMenu> roleMenuSet = newIds.stream().map(menuId -> new SysRoleMenu(roleId, menuId))
                                             .collect(Collectors.toSet());
        return roleMenuDao.saveBatch(roleMenuSet);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRolesByJobId(Long jobId, Collection<Long> roleIds) {
        Set<Long> oldRoleIds = new HashSet<>();
        List<SysJobRole> oldJobRoles = jobRoleDao.list(new SysJobRole().setJobId(jobId));
        if (CollectionUtil.isNotEmpty(oldJobRoles)) {
            oldRoleIds.addAll(oldJobRoles.stream().map(SysJobRole::getId).collect(Collectors.toSet()));
        }

        boolean isEquals = Arrays.equals(oldRoleIds.toArray(new Long[0]), roleIds.toArray(new Long[0]));
        if (isEquals) {
            return true;
        }

        jobRoleDao.removeByIds(oldRoleIds);
        Set<SysJobRole> jobRoleSet = roleIds.stream().map(roleId -> new SysJobRole(jobId, roleId))
                                            .collect(Collectors.toSet());
        return jobRoleDao.saveBatch(jobRoleSet);
    }

    private SysRoleDto doToDto(SysRole obj) {
        SysRoleDto role = BeanUtil.toBean(obj, SysRoleDto.class);
        fillDeptInfo(role);
        fillMenuInfo(role);
        return role;
    }

    private void fillDeptInfo(SysRoleDto role) {
        List<SysDeptDto> sysDeptVos = new ArrayList<>();
        QueryWrapper<SysRoleDept> roleDeptQueryWrapper = Wrappers.query(new SysRoleDept().setRoleId(role.getId()));
        List<SysRoleDept> sysRoleDepts = roleDeptDao.list(roleDeptQueryWrapper);
        if (CollectionUtil.isNotEmpty(sysRoleDepts)) {
            Set<Long> deptIds = sysRoleDepts.stream().map(SysRoleDept::getDeptId).collect(Collectors.toSet());
            List<SysDept> sysDepts = deptDao.listByIds(deptIds);
            if (CollectionUtil.isNotEmpty(sysDepts)) {
                sysDeptVos.addAll(BeanUtil.toBeanList(sysDepts, SysDeptDto.class));
            }
        }
        role.setDepts(sysDeptVos);
    }

    private void fillMenuInfo(SysRoleDto role) {
        List<SysMenuDto> sysMenuDtos = new ArrayList<>();
        QueryWrapper<SysRoleMenu> roleMenuQueryWrapper = Wrappers.query(new SysRoleMenu().setRoleId(role.getId()));
        List<SysRoleMenu> sysUserRoles = roleMenuDao.list(roleMenuQueryWrapper);
        if (CollectionUtil.isNotEmpty(sysUserRoles)) {
            Set<Long> menuIds = sysUserRoles.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toSet());
            List<SysMenu> sysMenus = menuDao.listByIds(menuIds);
            if (CollectionUtil.isNotEmpty(sysMenus)) {
                sysMenuDtos.addAll(BeanUtil.toBeanList(sysMenus, SysMenuDto.class));
            }
        }
        role.setMenus(sysMenuDtos);
    }

    /**
     * 获取用户的角色级别
     */
    @Override
    public void checkRoleLevel(Integer level) throws AuthException {
        Integer roleLevel = getRoleLevel();
        if (level != null) {
            if (level < roleLevel) {
                throw new AuthException("权限不足，你的角色级别：" + roleLevel + "，低于操作的角色级别：" + level);
            }
        }
    }

    @Override
    public Integer getRoleLevel() {
        SysUserDto userDto = userService.pojoByUsername(SecurityUtil.getCurrentUsername());
        if (userDto == null) {
            return null;
        }
        List<SysRoleDto> roles = pojoListByUserId(userDto.getId());
        if (CollectionUtil.isEmpty(roles)) {
            return null;
        }
        List<Integer> levels = roles.stream().map(SysRoleDto::getLevel).collect(Collectors.toList());
        if (CollectionUtil.isEmpty(levels)) {
            return null;
        }
        return Collections.min(levels);
    }

    @Override
    @Cacheable(key = "'auth:' + #p0.id")
    public List<GrantedAuthority> mapToGrantedAuthorities(SysUserDto user) {
        Set<String> permissions = new HashSet<>();
        // 如果是管理员直接返回
        if (user.getIsAdmin()) {
            permissions.add("admin");
            return permissions.stream().map(SimpleGrantedAuthority::new)
                              .collect(Collectors.toList());
        }

        List<SysRoleDto> roles = pojoListByUserId(user.getId());
        permissions = roles.stream().flatMap(role -> role.getMenus().stream())
                           .filter(menu -> StrUtil.isNotBlank(menu.getPermission()))
                           .map(SysMenuDto::getPermission).collect(Collectors.toSet());
        return permissions.stream().map(SimpleGrantedAuthority::new)
                          .collect(Collectors.toList());
    }

}
