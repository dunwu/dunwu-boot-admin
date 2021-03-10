package io.github.dunwu.modules.monitor.service;

import io.github.dunwu.data.core.annotation.QueryField;
import io.github.dunwu.data.mybatis.IService;
import io.github.dunwu.modules.monitor.entity.SysLog;
import io.github.dunwu.modules.monitor.entity.dto.SysLogDto;
import io.github.dunwu.modules.monitor.entity.query.SysLogQuery;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统日志 Service 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-10
 */
public interface SysLogService extends IService {

    /**
     * 添加一条 {@link SysLog} 记录
     *
     * @param entity {@link SysLog} 数据实体
     * @return true / false
     */
    boolean save(SysLog entity);

    /**
     * 批量添加 {@link SysLog} 记录
     *
     * @param list {@link SysLog} 数据实体列表
     * @return true / false
     */
    boolean saveBatch(Collection<SysLog> list);

    /**
     * 根据 ID 更新一条 {@link SysLog} 记录
     *
     * @param entity {@link SysLog} 数据实体
     * @return true / false
     */
    boolean updateById(SysLog entity);

    /**
     * 根据 ID 批量更新 {@link SysLog} 记录
     *
     * @param list {@link SysLog} 数据实体列表
     * @return true / false
     */
    boolean updateBatchById(Collection<SysLog> list);

    /**
     * 根据 ID 删除一条 {@link SysLog} 记录
     *
     * @param id {@link SysLog} 主键
     * @return true / false
     */
    boolean removeById(Serializable id);

    /**
     * 根据 ID 列表批量删除 {@link SysLog} 记录
     *
     * @param ids {@link SysLog} 主键列表
     * @return true / false
     */
    boolean removeByIds(Collection<? extends Serializable> ids);

    /**
     * 根据 {@link SysLogQuery} 查询 {@link SysLogDto} 列表
     *
     * @param query 查询条件，根据 SysLogQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<SysLogDto>}
     */
    List<SysLogDto> pojoListByQuery(SysLogQuery query);

    /**
     * 根据 {@link SysLogQuery} 和 {@link Pageable} 分页查询 {@link SysLogDto} 列表
     *
     * @param query    查询条件，根据 SysLogQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @return {@link Page<SysLogDto>}
     */
    Page<SysLogDto> pojoPageByQuery(SysLogQuery query, Pageable pageable);

    /**
     * 根据 id 查询 {@link SysLogDto}
     *
     * @param id {@link SysLog} 主键
     * @return {@link List<SysLogDto>}
     */
    SysLogDto pojoById(Serializable id);

    /**
     * 根据 {@link SysLogQuery} 查询 {@link SysLogDto} 列表
     *
     * @param query 查询条件，根据 SysLogQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<SysLogDto>}
     */
    SysLogDto pojoByQuery(SysLogQuery query);

    /**
     * 根据 {@link SysLogQuery} 查询匹配条件的记录数
     *
     * @param query 查询条件，根据 SysLogQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Integer}
     */
    Integer countByQuery(SysLogQuery query);

    /**
     * 根据 id 列表查询 {@link SysLogDto} 列表，并导出 excel 表单
     *
     * @param ids      id 列表
     * @param response {@link HttpServletResponse} 实体
     * @throws IOException /
     */
    void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) throws IOException;

    /**
     * 根据 {@link SysLogQuery} 和 {@link Pageable} 分页查询 {@link SysLogDto} 列表，并导出 excel 表单
     *
     * @param query    查询条件，根据 SysLogQuery 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @param response {@link HttpServletResponse} 实体
     * @throws IOException /
     */
    void exportPage(SysLogQuery query, Pageable pageable, HttpServletResponse response) throws IOException;

    /**
     * 将 {@link SysLog} 转为 {@link SysLogDto}
     *
     * @param model 数据实体
     * @return /
     */
    SysLogDto doToDto(SysLog model);

    /**
     * 将 {@link SysLogDto} 转为 {@link SysLog}
     *
     * @param dto Dto 实体
     * @return /
     */
    SysLog dtoToDo(SysLogDto dto);

    /**
     * 保存日志数据
     *
     * @param username  用户
     * @param browser   浏览器
     * @param ip        请求IP
     * @param joinPoint /
     * @param log       日志实体
     */
    @Async
    void save(String username, String browser, String ip, ProceedingJoinPoint joinPoint, SysLog log);

}
