package io.github.dunwu.modules.generator.service;

import io.github.dunwu.generator.config.builder.ConfigBuilder;
import io.github.dunwu.generator.engine.CodeGenerateContentDto;
import io.github.dunwu.modules.generator.entity.CodeColumnConfig;
import io.github.dunwu.modules.generator.entity.CodeGlobalConfig;
import io.github.dunwu.modules.generator.entity.CodeTableConfig;
import io.github.dunwu.modules.generator.entity.dto.*;
import io.github.dunwu.modules.generator.entity.query.CodeColumnConfigQuery;
import io.github.dunwu.modules.generator.entity.query.CodeTableConfigQuery;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 代码生成服务接口
 *
 * @author peng.zhang
 * @date 2021/3/3
 */
public interface GeneratorService {

    /**
     * 同步表信息
     */
    void syncTables(TableSyncDto tableSyncDto);

    /**
     * 查询当前用户的 CodeGlobalConfigDto 配置
     *
     * @return /
     */
    CodeGlobalConfigDto queryGlobalConfigByCurrentUser();

    /**
     * 保存当前用户的 CodeGlobalConfigDto 配置
     *
     * @param entity 实体
     * @return /
     */
    boolean saveGlobalConfigByCurrentUser(CodeGlobalConfig entity);

    /**
     * 查询当前用户的 {@link CodeTableConfigDto} 配置
     *
     * @param query 查询实体
     * @return /
     */
    CodeTableConfigDto queryTableConfigByCurrentUser(CodeTableConfigQuery query);

    /**
     * 保存当前用户的 {@link CodeTableConfig} 配置
     *
     * @param entity 实体
     * @return /
     */
    boolean saveTableConfigByCurrentUser(CodeTableConfig entity);

    /**
     * 查询当前用户的 {@link CodeColumnConfigDto} 配置
     *
     * @param query 查询实体
     * @return /
     */
    List<CodeColumnConfigDto> queryColumnConfigByCurrentUser(CodeColumnConfigQuery query);

    /**
     * 保存当前用户的 {@link CodeColumnConfig} 配置
     *
     * @param entity 实体
     * @return /
     */
    boolean saveColumnsConfigByCurrentUser(TableColumnInfoDto entity);

    /**
     * 根据表级别配置、列级别配置生成代码
     *
     * @param query 查询实体
     * @return /
     */
    ConfigBuilder generateCode(CodeTableConfigQuery query);

    /**
     * 根据表级别配置、列级别配置生成代码并下载到前端
     *
     * @param query 查询实体
     */
    void downloadCode(CodeTableConfigQuery query, HttpServletRequest request,
        HttpServletResponse response);

    /**
     * 根据表级别配置、列级别配置返回生成代码的预览内容
     *
     * @param query 查询实体
     * @return /
     */
    List<CodeGenerateContentDto> previewCode(CodeTableConfigQuery query);

    CodeTableConfigDto queryOrCreateCodeTableConfig(CodeTableConfigQuery query);

}
