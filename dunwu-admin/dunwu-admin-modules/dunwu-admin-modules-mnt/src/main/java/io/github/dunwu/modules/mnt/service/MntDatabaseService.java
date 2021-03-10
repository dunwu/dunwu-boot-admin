package io.github.dunwu.modules.mnt.service;

import io.github.dunwu.data.core.annotation.QueryField;
import io.github.dunwu.data.mybatis.IService;
import io.github.dunwu.modules.mnt.entity.MntDatabase;
import io.github.dunwu.modules.mnt.entity.dto.MntDatabaseDto;
import io.github.dunwu.modules.mnt.entity.query.MntDatabaseQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * Service 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-07
 */
public interface MntDatabaseService extends IService {

    /**
     * 添加一条 {@link MntDatabase} 记录
     *
     * @param entity {@link MntDatabase} 数据实体
     * @return true / false
     */
    boolean save(MntDatabase entity);

    /**
     * 批量添加 {@link MntDatabase} 记录
     *
     * @param list {@link MntDatabase} 数据实体列表
     * @return true / false
     */
    boolean saveBatch(Collection<MntDatabase> list);

    /**
     * 根据 ID 更新一条 {@link MntDatabase} 记录
     *
     * @param entity {@link MntDatabase} 数据实体
     * @return true / false
     */
    boolean updateById(MntDatabase entity);

    /**
     * 根据 ID 批量更新 {@link MntDatabase} 记录
     *
     * @param list {@link MntDatabase} 数据实体列表
     * @return true / false
     */
    boolean updateBatchById(Collection<MntDatabase> list);

    /**
     * 根据 ID 删除一条 {@link MntDatabase} 记录
     *
     * @param id {@link MntDatabase} 主键
     * @return true / false
     */
    boolean removeById(Serializable id);

    /**
     * 根据 ID 列表批量删除 {@link MntDatabase} 记录
     *
     * @param ids {@link MntDatabase} 主键列表
     * @return true / false
     */
    boolean removeByIds(Collection<Serializable> ids);

    /**
     * 根据 query 和 pageable 分页查询 {@link MntDatabaseDto}
     *
     * @param query    查询条件，根据 MntDatabaseQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @return {@link Page<MntDatabaseDto>}
     */
    Page<MntDatabaseDto> pojoPageByQuery(MntDatabaseQuery query, Pageable pageable);

    /**
     * 根据 query 查询 {@link MntDatabaseDto} 列表
     *
     * @param query 查询条件，根据 MntDatabaseQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<MntDatabaseDto>}
     */
    List<MntDatabaseDto> pojoListByQuery(MntDatabaseQuery query);

    /**
     * 根据 id 查询 {@link MntDatabaseDto}
     *
     * @param id {@link MntDatabase} 主键
     * @return {@link List<MntDatabaseDto>}
     */
    MntDatabaseDto pojoById(Serializable id);

    /**
     * 根据 query 查询 {@link MntDatabaseDto}
     *
     * @param query 查询条件，根据 MntDatabaseQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<MntDatabaseDto>}
     */
    MntDatabaseDto pojoByQuery(MntDatabaseQuery query);

    /**
     * 根据 query 查询满足条件的记录数
     *
     * @param query 查询条件，根据 MntDatabaseQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Integer}
     */
    Integer countByQuery(MntDatabaseQuery query);

    /**
     * 根据 id 列表查询 {@link MntDatabaseDto} 列表，并导出 excel 表单
     *
     * @param ids      id 列表
     * @param response {@link HttpServletResponse} 实体
     * @throws IOException /
     */
    void exportList(Collection<Serializable> ids, HttpServletResponse response) throws IOException;

    /**
     * 根据 query 和 pageable 查询 {@link MntDatabaseDto} 列表，并导出 excel 表单
     *
     * @param query    查询条件，根据 MntDatabaseQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @param response {@link HttpServletResponse} 实体
     * @throws IOException /
     */
    void exportPage(MntDatabaseQuery query, Pageable pageable, HttpServletResponse response) throws IOException;

    /**
     * {@link MntDatabase} 转为 {@link MntDatabaseDto}
     *
     * @param model 数据实体
     * @return /
     */
    MntDatabaseDto doToDto(MntDatabase model);

    /**
     * {@link MntDatabaseDto} 转为 {@link MntDatabase}
     *
     * @param dto Dto 实体
     * @return /
     */
    MntDatabase dtoToDo(MntDatabaseDto dto);

    boolean testConnection(MntDatabaseDto dto);

}
