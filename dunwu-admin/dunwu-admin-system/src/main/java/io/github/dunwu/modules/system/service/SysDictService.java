package io.github.dunwu.modules.system.service;

import io.github.dunwu.data.core.annotation.QueryField;
import io.github.dunwu.data.mybatis.IService;
import io.github.dunwu.modules.system.entity.SysDict;
import io.github.dunwu.modules.system.entity.dto.SysDictDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统数据字典 Service 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
public interface SysDictService extends IService {

    /**
     * 添加一条 {@link SysDict} 记录
     *
     * @param entity {@link SysDict} 数据实体
     * @return true / false
     */
    boolean save(SysDictDto entity);

    /**
     * 根据 ID 更新一条 {@link SysDict} 记录
     *
     * @param entity {@link SysDict} 数据实体
     * @return true / false
     */
    boolean updateById(SysDictDto entity);

    /**
     * 根据 ID 删除一条 {@link SysDict} 记录
     *
     * @param id {@link SysDict} 主键
     * @return true / false
     */
    boolean removeById(Serializable id);

    /**
     * 根据 ID 列表批量删除 {@link SysDict} 记录
     *
     * @param ids {@link SysDict} 主键列表
     * @return true / false
     */
    boolean removeByIds(Collection<Serializable> ids);

    /**
     * 根据 query 和 pageable 分页查询 {@link SysDictDto}
     *
     * @param query    查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @return {@link Page <SysDictDto>}
     */
    Page<SysDictDto> pojoPageByQuery(Object query, Pageable pageable);

    /**
     * 根据 query 查询 {@link SysDictDto} 列表
     *
     * @param query 查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<SysDictDto>}
     */
    List<SysDictDto> pojoListByQuery(Object query);

    /**
     * 根据 id 查询 {@link SysDictDto}
     *
     * @param id {@link SysDict} 主键
     * @return {@link List<SysDictDto>}
     */
    SysDictDto pojoById(Serializable id);

    /**
     * 根据 query 查询 {@link SysDictDto}
     *
     * @param query 查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<SysDictDto>}
     */
    SysDictDto pojoByQuery(Object query);

    /**
     * 根据 query 查询满足条件的记录数
     *
     * @param query 查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Integer}
     */
    Integer countByQuery(Object query);

    /**
     * 根据 id 列表查询 {@link SysDictDto} 列表，并导出 excel 表单
     *
     * @param ids      id 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportByIds(Collection<Serializable> ids, HttpServletResponse response) throws IOException;

    /**
     * 根据 query 和 pageable 查询 {@link SysDictDto} 列表，并导出 excel 表单
     *
     * @param query    查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @param response {@link HttpServletResponse} 实体
     */
    void exportPage(Object query, Pageable pageable, HttpServletResponse response) throws IOException;

}
