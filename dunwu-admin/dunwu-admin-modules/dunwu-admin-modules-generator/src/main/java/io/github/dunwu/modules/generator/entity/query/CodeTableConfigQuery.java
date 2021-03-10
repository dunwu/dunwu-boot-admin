package io.github.dunwu.modules.generator.entity.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.dunwu.data.core.annotation.QueryField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 代码生成-表级别配置 Query 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-04
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "CodeTableConfigQuery", description = "代码生成-表级别配置")
public class CodeTableConfigQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "数据库ID")
    @QueryField
    private Long dbId;

    @ApiModelProperty(value = "Schema名称")
    @QueryField
    private String schemaName;

    @ApiModelProperty(value = "Table名称")
    @QueryField
    private String tableName;

    @ApiModelProperty(value = "Table注释")
    @QueryField
    private String comment;

    @ApiModelProperty(value = "开启权限校验")
    @QueryField
    private Boolean enablePermission;

    @ApiModelProperty(value = "开启文件覆盖模式")
    @QueryField
    private Boolean enableOverride;

    @ApiModelProperty(value = "开启Swagger2")
    @QueryField
    private Boolean enableSwagger;

    @ApiModelProperty(value = "作者")
    @QueryField
    private String author;

    @ApiModelProperty(value = "输出路径")
    @QueryField
    private String outputDir;

    @ApiModelProperty(value = "后端代码路径")
    @QueryField
    private String backendPath;

    @ApiModelProperty(value = "前端代码路径")
    @QueryField
    private String frontendPath;

    @ApiModelProperty(value = "包路径")
    @QueryField
    private String packagePath;

    @ApiModelProperty(value = "主键类型")
    @QueryField
    private String idType;

    @ApiModelProperty(value = "时间类型")
    @QueryField
    private String dateType;

    @ApiModelProperty(value = "时间格式")
    @QueryField
    private String datePattern;

    @ApiModelProperty(value = "允许表单")
    @QueryField
    private Boolean enableForm;

    @ApiModelProperty(value = "允许列表")
    @QueryField
    private Boolean enableList;

    @ApiModelProperty(value = "允许查询")
    @QueryField
    private Boolean enableQuery;

    @ApiModelProperty(value = "允许排序")
    @QueryField
    private Boolean enableSort;

    @ApiModelProperty(value = "允许校验")
    @QueryField
    private Boolean enableValidate;

    @ApiModelProperty(value = "模块名称")
    @QueryField
    private String moduleName;

    @ApiModelProperty(value = "表前缀")
    @QueryField
    private String tablePrefix;

    @ApiModelProperty(value = "REST接口根路径")
    @QueryField
    private String apiBaseUrl;

    @ApiModelProperty(value = "创建者")
    @QueryField
    private String createBy;

    @ApiModelProperty(value = "更新者")
    @QueryField
    private String updateBy;

    @ApiModelProperty(value = "创建时间")
    @QueryField(type = QueryField.QueryType.BETWEEN)
    @JsonFormat(shape = JsonFormat.Shape.ARRAY, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private List<LocalDateTime> createTime;

    @ApiModelProperty(value = "更新时间")
    @QueryField(type = QueryField.QueryType.BETWEEN)
    @JsonFormat(shape = JsonFormat.Shape.ARRAY, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private List<LocalDateTime> updateTime;

}
