package io.github.dunwu.modules.system.service;

import io.github.dunwu.data.core.annotation.QueryField;
import io.github.dunwu.data.mybatis.IService;
import io.github.dunwu.modules.system.entity.SysUser;
import io.github.dunwu.modules.system.entity.dto.SysUserDto;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统用户信息 Service 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-25
 */
public interface SysUserService extends IService {

    /**
     * 添加一条 {@link SysUser} 记录
     *
     * @param entity {@link SysUser} 数据实体
     * @return true / false
     */
    boolean save(SysUser entity);

    /**
     * 根据 ID 更新一条 {@link SysUser} 记录
     *
     * @param entity {@link SysUser} 数据实体
     * @return true / false
     */
    boolean updateById(SysUser entity);

    /**
     * 根据 ID 删除一条 {@link SysUser} 记录
     *
     * @param id {@link SysUser} 主键
     * @return true / false
     */
    boolean removeById(Serializable id);

    /**
     * 根据 ID 列表批量删除 {@link SysUser} 记录
     *
     * @param ids {@link SysUser} 主键列表
     * @return true / false
     */
    boolean removeByIds(Collection<Serializable> ids);

    /**
     * 根据 query 和 pageable 分页查询 {@link SysUserDto}
     *
     * @param query    查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @return {@link Page <SysUserDto>}
     */
    Page<SysUserDto> pojoPageByQuery(Object query, Pageable pageable);

    /**
     * 根据 query 查询 {@link SysUserDto} 列表
     *
     * @param query 查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<SysUserDto>}
     */
    List<SysUserDto> pojoListByQuery(Object query);

    /**
     * 根据 id 查询 {@link SysUserDto}
     *
     * @param id {@link SysUser} 主键
     * @return {@link List<SysUserDto>}
     */
    SysUserDto pojoById(Serializable id);

    /**
     * 根据 query 查询 {@link SysUserDto}
     *
     * @param query 查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<SysUserDto>}
     */
    SysUserDto pojoByQuery(Object query);

    /**
     * 根据 query 查询满足条件的记录数
     *
     * @param query 查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Integer}
     */
    Integer countByQuery(Object query);

    /**
     * 根据 id 列表查询 {@link SysUserDto} 列表，并导出 excel 表单
     *
     * @param ids      id 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportList(Collection<Serializable> ids, HttpServletResponse response) throws IOException;

    /**
     * 根据 query 和 pageable 查询 {@link SysUserDto} 列表，并导出 excel 表单
     *
     * @param query    查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @param response {@link HttpServletResponse} 实体
     */
    void exportPage(Object query, Pageable pageable, HttpServletResponse response) throws IOException;

    // ==========================================================================

    Long saveUserRelatedRecords(SysUserDto dto);

    boolean updateUserRelatedRecords(SysUserDto dto);

    SysUserDto pojoByUsername(String username);

    /**
     * 根据菜单查询用户
     *
     * @param menuId 菜单ID
     * @return /
     */
    List<SysUser> findByMenuId(Long menuId);

    /**
     * 根据角色查询用户
     *
     * @param roleId /
     * @return /
     */
    @Select("SELECT u.* FROM sys_user u, sys_users_roles r WHERE u.user_id = r.user_id AND r.role_id = #{roleId}")
    List<SysUser> findByRoleId(Long roleId);

    /**
     * 根据角色查询
     *
     * @param roleIds /
     * @return /
     */
    @Select("SELECT count(1) FROM sys_user u, sys_users_roles r WHERE u.user_id = r.user_id AND r.role_id in #{roleIds}")
    int countByRoles(Set<Long> roleIds);

}
