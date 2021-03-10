package io.github.dunwu.modules.generator.service;

import io.github.dunwu.data.core.annotation.QueryField;
import io.github.dunwu.data.mybatis.IService;
import io.github.dunwu.modules.generator.entity.CodeTableConfig;
import io.github.dunwu.modules.generator.entity.dto.CodeTableConfigDto;
import io.github.dunwu.modules.generator.entity.query.CodeTableConfigQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 代码生成-表级别配置 Service 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-03
 */
public interface CodeTableConfigService extends IService {

    /**
     * 添加一条 {@link CodeTableConfig} 记录
     *
     * @param entity {@link CodeTableConfig} 数据实体
     * @return true / false
     */
    boolean save(CodeTableConfig entity);

    /**
     * 批量添加 {@link CodeTableConfig} 记录
     *
     * @param list {@link CodeTableConfig} 数据实体列表
     * @return true / false
     */
    boolean saveBatch(Collection<CodeTableConfig> list);

    /**
     * 根据 ID 更新一条 {@link CodeTableConfig} 记录
     *
     * @param entity {@link CodeTableConfig} 数据实体
     * @return true / false
     */
    boolean updateById(CodeTableConfig entity);

    /**
     * 根据 ID 批量更新 {@link CodeTableConfig} 记录
     *
     * @param list {@link CodeTableConfig} 数据实体列表
     * @return true / false
     */
    boolean updateBatchById(Collection<CodeTableConfig> list);

    /**
     * 根据 ID 删除一条 {@link CodeTableConfig} 记录
     *
     * @param id {@link CodeTableConfig} 主键
     * @return true / false
     */
    boolean removeById(Serializable id);

    /**
     * 根据 ID 列表批量删除 {@link CodeTableConfig} 记录
     *
     * @param ids {@link CodeTableConfig} 主键列表
     * @return true / false
     */
    boolean removeByIds(Collection<Serializable> ids);

    /**
     * 根据 query 和 pageable 分页查询 {@link CodeTableConfigDto}
     *
     * @param query    查询条件，根据 CodeTableConfigQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @return {@link Page<CodeTableConfigDto>}
     */
    Page<CodeTableConfigDto> pojoPageByQuery(CodeTableConfigQuery query, Pageable pageable);

    /**
     * 根据 query 查询 {@link CodeTableConfigDto} 列表
     *
     * @param query 查询条件，根据 CodeTableConfigQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<CodeTableConfigDto>}
     */
    List<CodeTableConfigDto> pojoListByQuery(CodeTableConfigQuery query);

    /**
     * 根据 id 查询 {@link CodeTableConfigDto}
     *
     * @param id {@link CodeTableConfig} 主键
     * @return {@link List<CodeTableConfigDto>}
     */
    CodeTableConfigDto pojoById(Serializable id);

    /**
     * 根据 query 查询 {@link CodeTableConfigDto}
     *
     * @param query 查询条件，根据 CodeTableConfigQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<CodeTableConfigDto>}
     */
    CodeTableConfigDto pojoByQuery(CodeTableConfigQuery query);

    /**
     * 根据 query 查询满足条件的记录数
     *
     * @param query 查询条件，根据 CodeTableConfigQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Integer}
     */
    Integer countByQuery(CodeTableConfigQuery query);

    /**
     * 根据 id 列表查询 {@link CodeTableConfigDto} 列表，并导出 excel 表单
     *
     * @param ids      id 列表
     * @param response {@link HttpServletResponse} 实体
     * @throws IOException /
     */
    void exportList(Collection<Serializable> ids, HttpServletResponse response) throws IOException;

    /**
     * 根据 query 和 pageable 查询 {@link CodeTableConfigDto} 列表，并导出 excel 表单
     *
     * @param query    查询条件，根据 CodeTableConfigQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @param response {@link HttpServletResponse} 实体
     * @throws IOException /
     */
    void exportPage(CodeTableConfigQuery query, Pageable pageable, HttpServletResponse response) throws IOException;

    /**
     * {@link CodeTableConfig} 转为 {@link CodeTableConfigDto}
     *
     * @param model 数据实体
     * @return /
     */
    CodeTableConfigDto doToDto(CodeTableConfig model);

    /**
     * {@link CodeTableConfigDto} 转为 {@link CodeTableConfig}
     *
     * @param dto Dto 实体
     * @return /
     */
    CodeTableConfig dtoToDo(CodeTableConfigDto dto);

}
