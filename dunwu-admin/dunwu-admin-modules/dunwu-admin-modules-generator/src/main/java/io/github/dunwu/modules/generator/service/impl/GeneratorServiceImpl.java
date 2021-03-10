package io.github.dunwu.modules.generator.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import io.github.dunwu.data.core.DataException;
import io.github.dunwu.data.core.GlobalException;
import io.github.dunwu.generator.CodeGenerator;
import io.github.dunwu.generator.config.*;
import io.github.dunwu.generator.config.builder.ConfigBuilder;
import io.github.dunwu.generator.config.po.TableField;
import io.github.dunwu.generator.config.po.TableInfo;
import io.github.dunwu.generator.config.rules.JavaColumnType;
import io.github.dunwu.generator.engine.CodeGenerateContentDto;
import io.github.dunwu.modules.generator.entity.CodeColumnConfig;
import io.github.dunwu.modules.generator.entity.CodeGlobalConfig;
import io.github.dunwu.modules.generator.entity.CodeTableConfig;
import io.github.dunwu.modules.generator.entity.dto.*;
import io.github.dunwu.modules.generator.entity.query.CodeColumnConfigQuery;
import io.github.dunwu.modules.generator.entity.query.CodeGlobalConfigQuery;
import io.github.dunwu.modules.generator.entity.query.CodeTableConfigQuery;
import io.github.dunwu.modules.generator.service.*;
import io.github.dunwu.modules.mnt.entity.dto.MntDatabaseDto;
import io.github.dunwu.modules.mnt.service.MntDatabaseService;
import io.github.dunwu.util.SecurityUtils;
import io.github.dunwu.web.util.ServletUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 代码生成服务接口实现类
 *
 * @author peng.zhang
 * @date 2021/3/3
 */
@Slf4j
@Service
public class GeneratorServiceImpl implements GeneratorService {

    private final CodeGlobalConfigService globalConfigService;
    private final CodeTableConfigService tableConfigService;
    private final CodeColumnConfigService columnConfigService;
    private final MntDatabaseService databaseService;
    private final TableService tableService;

    public GeneratorServiceImpl(CodeGlobalConfigService globalConfigService,
        CodeTableConfigService tableConfigService,
        CodeColumnConfigService columnConfigService,
        MntDatabaseService databaseService, TableService tableService) {
        this.globalConfigService = globalConfigService;
        this.tableConfigService = tableConfigService;
        this.columnConfigService = columnConfigService;
        this.databaseService = databaseService;
        this.tableService = tableService;
    }

    @Override
    public void syncTables(TableSyncDto tableSyncDto) {
        for (String tableName : tableSyncDto.getTables()) {
            syncOneTable(tableSyncDto.getDbId(), tableSyncDto.getSchemaName(), tableName);
        }
    }

    private void syncOneTable(Long dbId, String schemaName, String tableName) {
        String username = SecurityUtils.getCurrentUsername();
        if (StrUtil.isBlank(username)) {
            username = "admin";
        }

        CodeColumnConfigQuery columnQuery = new CodeColumnConfigQuery();
        columnQuery.setSchemaName(schemaName)
                   .setTableName(tableName)
                   .setCreateBy(username);
        List<CodeColumnConfigDto> codeColumnConfigDtos = columnConfigService.pojoListByQuery(columnQuery);
        if (CollectionUtil.isEmpty(codeColumnConfigDtos)) {
            return;
        }
        List<CodeColumnConfig> oldColumns = codeColumnConfigDtos.stream()
                                                                .map(columnConfigService::dtoToDo)
                                                                .collect(Collectors.toList());
        Map<String, CodeColumnConfig> columnMap = oldColumns.stream()
                                                            .collect(Collectors.toMap(CodeColumnConfig::getFieldName,
                                                                Function.identity()));

        TableInfo tableInfo = queryTableInfo(dbId, schemaName, tableName);
        if (tableInfo == null) {
            throw new GlobalException(StrUtil.format("未找到 {} 表信息", tableName));
        }
        List<CodeColumnConfig> newColumns = tableInfo.getFields().stream()
                                                     .map(i -> transToCodeColumnConfig(i, schemaName, tableName))
                                                     .collect(Collectors.toList());

        List<CodeColumnConfig> mergeColumns = new ArrayList<>();
        for (CodeColumnConfig n : newColumns) {
            CodeColumnConfig entity = columnMap.getOrDefault(n.getFieldName(), n);
            mergeColumns.add(entity);
        }

        Set<Serializable> ids = oldColumns.stream().map(CodeColumnConfig::getId).collect(Collectors.toSet());
        columnConfigService.removeByIds(ids);
        columnConfigService.saveBatch(mergeColumns);
    }

    @Override
    public CodeGlobalConfigDto queryGlobalConfigByCurrentUser() {
        String username = SecurityUtils.getCurrentUsername();
        if (StrUtil.isBlank(username)) {
            username = "admin";
        }

        CodeGlobalConfigQuery query = new CodeGlobalConfigQuery();
        query.setCreateBy(username);
        CodeGlobalConfigDto dto = globalConfigService.pojoByQuery(query);
        if (dto != null) {
            return dto;
        }

        GlobalConfig globalConfig = new GlobalConfig();
        return BeanUtil.toBean(globalConfig, CodeGlobalConfigDto.class);
    }

    @Override
    public boolean saveGlobalConfigByCurrentUser(CodeGlobalConfig entity) {
        // CodeGlobalConfigDto dto = findGlobalConfigByCurrentUser();
        if (entity.getId() == null) {
            return globalConfigService.save(entity);
        } else {
            return globalConfigService.updateById(entity);
        }
    }

    @Override
    public CodeTableConfigDto queryTableConfigByCurrentUser(CodeTableConfigQuery query) {
        String username = SecurityUtils.getCurrentUsername();
        if (StrUtil.isBlank(username)) {
            username = "admin";
        }
        query.setCreateBy(username);
        return tableConfigService.pojoByQuery(query);
    }

    @Override
    public boolean saveTableConfigByCurrentUser(CodeTableConfig entity) {
        if (entity.getId() == null) {
            return tableConfigService.save(entity);
        } else {
            return tableConfigService.updateById(entity);
        }
    }

    @Override
    public List<CodeColumnConfigDto> queryColumnConfigByCurrentUser(CodeColumnConfigQuery query) {
        String username = SecurityUtils.getCurrentUsername();
        if (StrUtil.isBlank(username)) {
            username = "admin";
        }
        query.setCreateBy(username);
        List<CodeColumnConfigDto> columnConfigRecords = columnConfigService.pojoListByQuery(query);
        if (CollectionUtil.isNotEmpty(columnConfigRecords)) {
            return columnConfigRecords;
        }
        TableInfo tableInfo = queryTableInfo(query.getDbId(), query.getSchemaName(), query.getTableName());
        return tableInfo.getFields().stream()
                        .map(i -> transToCodeColumnConfigDto(i, query.getSchemaName(), query.getTableName()))
                        .collect(Collectors.toList());
    }

    @Override
    public boolean saveColumnsConfigByCurrentUser(TableColumnInfoDto entity) {
        Collection<CodeColumnConfig> list = entity.getColumns();
        if (CollectionUtil.isEmpty(list)) {
            return true;
        }

        String username = SecurityUtils.getCurrentUsername();
        CodeColumnConfigQuery query = new CodeColumnConfigQuery();
        query.setSchemaName(entity.getSchemaName())
             .setTableName(entity.getTableName())
             .setCreateBy(username);

        List<CodeColumnConfigDto> oldRecords = columnConfigService.pojoListByQuery(query);
        if (CollectionUtil.isNotEmpty(oldRecords)) {
            Set<Serializable> ids = oldRecords.stream()
                                              .map(CodeColumnConfigDto::getId)
                                              .collect(Collectors.toSet());

            if (CollectionUtil.isNotEmpty(ids)) {
                columnConfigService.removeByIds(ids);
            }
        }
        return columnConfigService.saveBatch(list);
    }

    @Override
    public ConfigBuilder generateCode(CodeTableConfigQuery query) {
        String username = SecurityUtils.getCurrentUsername();
        CodeGlobalConfigDto globalConfigDto = queryGlobalConfigByCurrentUser();
        if (globalConfigDto == null) {
            throw new DataException("未配置全局配置");
        }

        CodeTableConfigDto tableConfigDto = queryTableConfigByCurrentUser(query);
        if (tableConfigDto == null) {
            throw new DataException("未配置表级别配置");
        }

        CodeColumnConfigQuery codeColumnConfigQuery = new CodeColumnConfigQuery();
        codeColumnConfigQuery.setTableName(query.getSchemaName())
                             .setTableName(query.getTableName())
                             .setCreateBy(username);
        List<CodeColumnConfigDto> columns = columnConfigService.pojoListByQuery(codeColumnConfigQuery);
        if (CollectionUtil.isEmpty(columns)) {
            throw new DataException("未配置列级别配置");
        }

        tableConfigDto.setColumns(columns);
        ConfigBuilder builder = createConfigBuilder(tableConfigDto);
        CodeGenerator codeGenerator = new CodeGenerator(builder);
        codeGenerator.generate();
        return builder;
    }

    @Override
    public void downloadCode(CodeTableConfigQuery query, HttpServletRequest request,
        HttpServletResponse response) {
        String username = SecurityUtils.getCurrentUsername();
        CodeGlobalConfigDto globalConfigDto = queryGlobalConfigByCurrentUser();
        if (globalConfigDto == null) {
            throw new DataException("未配置全局配置");
        }

        CodeTableConfigDto tableConfigDto = queryTableConfigByCurrentUser(query);
        if (tableConfigDto == null) {
            throw new DataException("未配置表级别配置");
        }

        CodeColumnConfigQuery codeColumnConfigQuery = new CodeColumnConfigQuery();
        codeColumnConfigQuery.setTableName(query.getSchemaName())
                             .setTableName(query.getTableName())
                             .setCreateBy(username);
        List<CodeColumnConfigDto> columns = columnConfigService.pojoListByQuery(codeColumnConfigQuery);
        if (CollectionUtil.isEmpty(columns)) {
            throw new DataException("未配置列级别配置");
        }

        tableConfigDto.setColumns(columns);
        String tmpSchemaPath = System.getProperty("java.io.tmpdir")
            + "dunwu"
            + File.separator
            + query.getSchemaName();
        String tmpTablePath = tmpSchemaPath + File.separator + query.getTableName();
        globalConfigDto.setOutputDir(tmpTablePath)
                       .setBackendPath(tmpTablePath + "/backend")
                       .setFrontendPath(tmpTablePath + "/frontend");
        tableConfigDto.setOutputDir(tmpTablePath)
                      .setBackendPath(tmpTablePath + "/backend")
                      .setFrontendPath(tmpTablePath + "/frontend");

        log.info("临时代码生成路径：{}", tmpTablePath);

        ConfigBuilder builder = createConfigBuilder(tableConfigDto);
        CodeGenerator codeGenerator = new CodeGenerator(builder);
        codeGenerator.generate();
        String zipFilePath = tmpSchemaPath + File.separator + "codes.zip";
        File zip = ZipUtil.zip(tmpTablePath, zipFilePath);
        ServletUtil.downloadFile(request, response, zip, true);
    }

    @Override
    public List<CodeGenerateContentDto> previewCode(CodeTableConfigQuery query) {
        String username = SecurityUtils.getCurrentUsername();
        CodeGlobalConfigDto globalConfigDto = queryGlobalConfigByCurrentUser();
        if (globalConfigDto == null) {
            throw new DataException("未配置全局配置");
        }

        CodeTableConfigDto tableConfigDto = queryTableConfigByCurrentUser(query);
        if (tableConfigDto == null) {
            throw new DataException("未配置表级别配置");
        }

        CodeColumnConfigQuery codeColumnConfigQuery = new CodeColumnConfigQuery();
        codeColumnConfigQuery.setTableName(query.getSchemaName())
                             .setTableName(query.getTableName())
                             .setCreateBy(username);
        List<CodeColumnConfigDto> columns = columnConfigService.pojoListByQuery(codeColumnConfigQuery);
        if (CollectionUtil.isEmpty(columns)) {
            throw new DataException("未配置列级别配置");
        }

        tableConfigDto.setColumns(columns);
        ConfigBuilder builder = createConfigBuilder(tableConfigDto);
        CodeGenerator codeGenerator = new CodeGenerator(builder);
        return codeGenerator.preview();
    }

    /**
     * 将 {@link CodeTableConfigDto} 转为 {@link ConfigBuilder}
     *
     * @param tableConfigDto CodeTableConfigDto 对象
     * @return /
     */
    public ConfigBuilder createConfigBuilder(CodeTableConfigDto tableConfigDto) {

        if (tableConfigDto.getDbId() == null) {
            throw new DataException("dbId 为空");
        }

        MntDatabaseDto databaseDto = databaseService.pojoById(tableConfigDto.getDbId());
        if (databaseDto == null) {
            throw new DataException(StrUtil.format("未找到数据库 dbId = {}", tableConfigDto.getDbId()));
        }

        DataSourceConfig dataSourceConfig = new DataSourceConfig(databaseDto.getJdbcUrl(), "com.mysql.cj.jdbc.Driver",
            databaseDto.getUsername(), databaseDto.getPassword(), tableConfigDto.getSchemaName());

        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setAuthor(tableConfigDto.getAuthor())
                    .setOutputDir(tableConfigDto.getOutputDir())
                    .setBackendDir(tableConfigDto.getBackendPath())
                    .setFrontendDir(tableConfigDto.getFrontendPath());

        PackageConfig packageConfig = new PackageConfig(tableConfigDto.getPackagePath(),
            tableConfigDto.getModuleName());

        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setInclude(tableConfigDto.getTableName());

        TemplateConfig templateConfig = new TemplateConfig();

        TableInfo tableInfo = transToTableInfo(tableConfigDto);

        ConfigBuilder builder = new ConfigBuilder(dataSourceConfig, globalConfig, packageConfig, strategyConfig,
            templateConfig);
        tableInfo = builder.processTable(tableInfo);
        builder.setTableInfoList(Collections.singletonList(tableInfo));
        return builder;
    }

    @Override
    public CodeTableConfigDto queryOrCreateCodeTableConfig(CodeTableConfigQuery query) {
        String username = SecurityUtils.getCurrentUsername();
        CodeTableConfigDto tableConfigDto = queryTableConfigByCurrentUser(query);
        CodeGlobalConfigDto globalConfig = queryGlobalConfigByCurrentUser();
        ConfigBuilder configBuilder;
        if (tableConfigDto != null) {
            configBuilder = createConfigBuilder(tableConfigDto);
        } else {
            CopyOptions copyOptions = CopyOptions.create().setIgnoreProperties("id");
            tableConfigDto = BeanUtil.toBean(globalConfig, CodeTableConfigDto.class, copyOptions);
            tableConfigDto.setDbId(query.getDbId())
                          .setSchemaName(query.getSchemaName())
                          .setTableName(query.getTableName())
                          .setCreateBy(username);
            configBuilder = createConfigBuilder(tableConfigDto);
        }

        List<TableInfo> tableInfos = configBuilder.queryTableInfoList();
        TableInfo tableInfo = tableInfos.get(0);
        tableConfigDto.setComment(tableInfo.getComment());
        return tableConfigDto;
    }

    /**
     * 根据 schemaName、tableName 查询表的实际数据信息
     *
     * @param dbId       数据库 ID
     * @param schemaName schema 名
     * @param tableName  table 名
     * @return /
     */
    private TableInfo queryTableInfo(Long dbId, String schemaName, String tableName) {
        String username = SecurityUtils.getCurrentUsername();
        if (StrUtil.isBlank(schemaName)) {
            schemaName = tableService.getCurrentSchema();
        }

        CodeTableConfigQuery tableQuery = new CodeTableConfigQuery();
        tableQuery.setSchemaName(schemaName).setTableName(tableName);
        CodeTableConfigDto tableConfigDto = queryTableConfigByCurrentUser(tableQuery);
        CodeGlobalConfigDto globalConfig = queryGlobalConfigByCurrentUser();
        ConfigBuilder configBuilder;
        if (tableConfigDto != null) {
            configBuilder = createConfigBuilder(tableConfigDto);
        } else {
            CodeTableConfigDto newTableConfigDto = BeanUtil.toBean(globalConfig, CodeTableConfigDto.class);
            newTableConfigDto.setDbId(dbId)
                             .setSchemaName(schemaName)
                             .setTableName(tableName)
                             .setCreateBy(username);
            configBuilder = createConfigBuilder(newTableConfigDto);
        }

        List<TableInfo> tableInfos = configBuilder.queryTableInfoList();
        return tableInfos.get(0);
    }

    /**
     * 将 {@link CodeTableConfigDto} 转为 {@link TableInfo}
     *
     * @param tableConfigDto CodeTableConfigDto 对象
     * @return /
     */
    private TableInfo transToTableInfo(CodeTableConfigDto tableConfigDto) {
        TableInfo tableInfo = BeanUtil.toBean(tableConfigDto, TableInfo.class);

        if (CollectionUtil.isNotEmpty(tableConfigDto.getColumns())) {
            List<TableField> fields = new ArrayList<>();
            Map<String, TableField> fieldNameMap = new HashMap<>(fields.size());
            for (CodeColumnConfigDto column : tableConfigDto.getColumns()) {
                TableField field = transToTableField(column);
                fields.add(field);
                fieldNameMap.put(field.getFieldName(), field);
            }
            tableInfo.setFields(fields);
            tableInfo.setFieldMap(fieldNameMap);
        }

        return tableInfo;
    }

    private CodeTableConfigDto transToCodeTableConfigDto(TableInfo table) {
        return BeanUtil.toBean(table, CodeTableConfigDto.class);
    }

    /**
     * 将 {@link CodeColumnConfigDto} 转为 {@link TableField}
     *
     * @param columnConfigDto CodeColumnConfigDto 对象
     * @return /
     */
    private TableField transToTableField(CodeColumnConfigDto columnConfigDto) {
        CopyOptions copyOptions = CopyOptions.create().setIgnoreProperties("javaType");
        TableField tableField = BeanUtil.toBean(columnConfigDto, TableField.class, copyOptions);
        tableField.setJavaType(JavaColumnType.getJavaColumnTypeByType(columnConfigDto.getJavaType()));
        return tableField;
    }

    private CodeColumnConfigDto transToCodeColumnConfigDto(TableField field, String schemaName, String tableName) {
        CodeColumnConfigDto column = BeanUtil.toBean(field, CodeColumnConfigDto.class);
        column.setSchemaName(schemaName).setTableName(tableName);
        if (field.getJavaType() != null) {
            column.setJavaType(field.getJavaType().getType());
        }
        return column;
    }

    private CodeColumnConfig transToCodeColumnConfig(TableField field, String schemaName, String tableName) {
        CodeColumnConfig column = BeanUtil.toBean(field, CodeColumnConfig.class);
        column.setSchemaName(schemaName).setTableName(tableName);
        if (field.getJavaType() != null) {
            column.setJavaType(field.getJavaType().getType());
        }
        return column;
    }

}
