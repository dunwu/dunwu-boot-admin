package io.github.dunwu.modules.system.service;

import io.github.dunwu.data.core.annotation.QueryField;
import io.github.dunwu.data.mybatis.IService;
import io.github.dunwu.modules.system.entity.SysMenu;
import io.github.dunwu.modules.system.entity.dto.SysMenuDto;
import io.github.dunwu.modules.system.entity.vo.MenuVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统菜单信息 Service 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
public interface SysMenuService extends IService {

    /**
     * 添加一条 {@link SysMenu} 记录
     *
     * @param entity {@link SysMenu} 数据实体
     * @return true / false
     */
    boolean save(SysMenu entity);

    /**
     * 根据 ID 更新一条 {@link SysMenu} 记录
     *
     * @param entity {@link SysMenu} 数据实体
     * @return true / false
     */
    boolean updateById(SysMenu entity);

    /**
     * 根据 ID 删除一条 {@link SysMenu} 记录
     *
     * @param id {@link SysMenu} 主键
     * @return true / false
     */
    boolean removeById(Serializable id);

    /**
     * 根据 ID 列表批量删除 {@link SysMenu} 记录
     *
     * @param ids {@link SysMenu} 主键列表
     * @return true / false
     */
    boolean removeByIds(Collection<Serializable> ids);

    /**
     * 根据 query 和 pageable 分页查询 {@link SysMenuDto}
     *
     * @param query    查询条件，根据 query 中的 {@link io.github.dunwu.data.core.annotation.QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @return {@link Page <SysMenuDto>}
     */
    Page<SysMenuDto> pojoPageByQuery(Object query, Pageable pageable);

    /**
     * 根据 query 查询 {@link SysMenuDto} 列表
     *
     * @param query 查询条件，根据 query 中的 {@link io.github.dunwu.data.core.annotation.QueryField} 注解自动组装查询条件
     * @return {@link List<SysMenuDto>}
     */
    List<SysMenuDto> pojoListByQuery(Object query);

    /**
     * 根据 id 查询 {@link SysMenuDto}
     *
     * @param id {@link SysMenu} 主键
     * @return {@link List<SysMenuDto>}
     */
    SysMenuDto pojoById(Serializable id);

    /**
     * 根据 query 查询 {@link SysMenuDto}
     *
     * @param query 查询条件，根据 query 中的 {@link io.github.dunwu.data.core.annotation.QueryField} 注解自动组装查询条件
     * @return {@link List<SysMenuDto>}
     */
    SysMenuDto pojoByQuery(Object query);

    /**
     * 根据 query 查询满足条件的记录数
     *
     * @param query 查询条件，根据 query 中的 {@link io.github.dunwu.data.core.annotation.QueryField} 注解自动组装查询条件
     * @return {@link Integer}
     */
    Integer countByQuery(Object query);

    /**
     * 根据 id 列表查询 {@link SysMenuDto} 列表，并导出 excel 表单
     *
     * @param ids      id 列表
     * @param response {@link HttpServletResponse} 实体
     * @throws IOException /
     */
    void exportList(Collection<Serializable> ids, HttpServletResponse response) throws IOException;

    /**
     * 根据 query 和 pageable 查询 {@link SysMenuDto} 列表，并导出 excel 表单
     *
     * @param query    查询条件，根据 query 中的 {@link io.github.dunwu.data.core.annotation.QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @param response {@link HttpServletResponse} 实体
     * @throws IOException /
     */
    void exportPage(Object query, Pageable pageable, HttpServletResponse response) throws IOException;

    /**
     * 根据 query 和 pageable 查询 {@link SysMenuDto} 树形列表
     *
     * @param query 查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @return /
     */
    List<SysMenuDto> treeList(Object query);

    List<SysMenuDto> treeListByIds(Collection<Serializable> list);

    List<SysMenuDto> pojoTreeListByRoleIds(Collection<Long> roleIds);

    List<MenuVo> buildMenuListForCurrentUser();

    List<Long> childrenIds(Long id);

}
