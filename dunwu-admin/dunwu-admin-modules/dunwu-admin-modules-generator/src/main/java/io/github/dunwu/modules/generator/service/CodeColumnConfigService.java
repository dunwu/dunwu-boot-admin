package io.github.dunwu.modules.generator.service;

import io.github.dunwu.data.core.annotation.QueryField;
import io.github.dunwu.data.mybatis.IService;
import io.github.dunwu.modules.generator.entity.CodeColumnConfig;
import io.github.dunwu.modules.generator.entity.dto.CodeColumnConfigDto;
import io.github.dunwu.modules.generator.entity.query.CodeColumnConfigQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 代码生成-字段级别配置 Service 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-02
 */
public interface CodeColumnConfigService extends IService {

    /**
     * 添加一条 {@link CodeColumnConfig} 记录
     *
     * @param entity {@link CodeColumnConfig} 数据实体
     * @return true / false
     */
    boolean save(CodeColumnConfig entity);

    /**
     * 批量添加 {@link CodeColumnConfig} 记录
     *
     * @param list {@link CodeColumnConfig} 数据实体列表
     * @return true / false
     */
    boolean saveBatch(Collection<CodeColumnConfig> list);

    /**
     * 根据 ID 更新一条 {@link CodeColumnConfig} 记录
     *
     * @param entity {@link CodeColumnConfig} 数据实体
     * @return true / false
     */
    boolean updateById(CodeColumnConfig entity);

    /**
     * 根据 ID 删除一条 {@link CodeColumnConfig} 记录
     *
     * @param id {@link CodeColumnConfig} 主键
     * @return true / false
     */
    boolean removeById(Serializable id);

    /**
     * 根据 ID 列表批量删除 {@link CodeColumnConfig} 记录
     *
     * @param ids {@link CodeColumnConfig} 主键列表
     * @return true / false
     */
    boolean removeByIds(Collection<Serializable> ids);

    /**
     * 根据 query 和 pageable 分页查询 {@link CodeColumnConfigDto}
     *
     * @param query    查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @return {@link Page<CodeColumnConfigDto>}
     */
    Page<CodeColumnConfigDto> pojoPageByQuery(CodeColumnConfigQuery query, Pageable pageable);

    /**
     * 根据 query 查询 {@link CodeColumnConfigDto} 列表
     *
     * @param query 查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<CodeColumnConfigDto>}
     */
    List<CodeColumnConfigDto> pojoListByQuery(CodeColumnConfigQuery query);

    /**
     * 根据 id 查询 {@link CodeColumnConfigDto}
     *
     * @param id {@link CodeColumnConfig} 主键
     * @return {@link List<CodeColumnConfigDto>}
     */
    CodeColumnConfigDto pojoById(Serializable id);

    /**
     * 根据 query 查询 {@link CodeColumnConfigDto}
     *
     * @param query 查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<CodeColumnConfigDto>}
     */
    CodeColumnConfigDto pojoByQuery(Object query);

    /**
     * 根据 query 查询满足条件的记录数
     *
     * @param query 查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Integer}
     */
    Integer countByQuery(Object query);

    /**
     * 根据 id 列表查询 {@link CodeColumnConfigDto} 列表，并导出 excel 表单
     *
     * @param ids      id 列表
     * @param response {@link HttpServletResponse} 实体
     * @throws IOException /
     */
    void exportList(Collection<Serializable> ids, HttpServletResponse response) throws IOException;

    /**
     * 根据 query 和 pageable 查询 {@link CodeColumnConfigDto} 列表，并导出 excel 表单
     *
     * @param query    查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @param response {@link HttpServletResponse} 实体
     * @throws IOException /
     */
    void exportPage(Object query, Pageable pageable, HttpServletResponse response) throws IOException;

    /**
     * {@link CodeColumnConfig} 转为 {@link CodeColumnConfigDto}
     *
     * @param model 数据实体
     * @return /
     */
    CodeColumnConfigDto doToDto(CodeColumnConfig model);

    /**
     * {@link CodeColumnConfigDto} 转为 {@link CodeColumnConfig}
     *
     * @param dto Dto 实体
     * @return /
     */
    CodeColumnConfig dtoToDo(CodeColumnConfigDto dto);

}
