package io.github.dunwu.modules.generator.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 代码生成-字段级别配置
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-02
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "CodeColumnConfig", description = "代码生成-字段级别配置")
public class CodeColumnConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "Table ID")
    private Long tableId;

    @ApiModelProperty(value = "Schema名称")
    private String schemaName;

    @ApiModelProperty(value = "Table名称")
    private String tableName;

    @ApiModelProperty(value = "字段名称")
    private String fieldName;

    @ApiModelProperty(value = "字段展示名称（实体字段）")
    private String propertyName;

    @ApiModelProperty(value = "字段注释")
    private String comment;

    @ApiModelProperty(value = "字段数据类型")
    private String type;

    @ApiModelProperty(value = "字段 Java 类型")
    private String javaType;

    @ApiModelProperty(value = "键类型")
    private String keyType;

    @ApiModelProperty(value = "不允许为空")
    private Boolean notNull;

    @ApiModelProperty(value = "出现在表单")
    private Boolean enableForm;

    @ApiModelProperty(value = "出现在列表")
    private Boolean enableList;

    @ApiModelProperty(value = "出现在查询")
    private Boolean enableQuery;

    @ApiModelProperty(value = "允许排序")
    private Boolean enableSort;

    @ApiModelProperty(value = "允许校验")
    private Boolean enableValidate;

    @ApiModelProperty(value = "表单类型")
    private String formType;

    @ApiModelProperty(value = "列表类型")
    private String listType;

    @ApiModelProperty(value = "查询类型")
    private String queryType;

    @ApiModelProperty(value = "排序类型")
    private String sortType;

    @ApiModelProperty(value = "校验类型")
    private String validateType;

    @ApiModelProperty(value = "时间类型")
    private String dateType;

    @ApiModelProperty(value = "时间格式")
    private String datePattern;

    @ApiModelProperty(value = "字典名称")
    private String dictName;

    @ApiModelProperty(value = "@TableField 填充属性")
    private String fill;

    @ApiModelProperty(value = "扩展属性")
    private String extra;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

}
