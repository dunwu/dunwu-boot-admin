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
 * 代码生成-全局配置
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-03
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "CodeGlobalConfig", description = "代码生成-全局配置")
public class CodeGlobalConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "开启权限校验")
    private Boolean enablePermission;

    @ApiModelProperty(value = "开启文件覆盖模式")
    private Boolean enableOverride;

    @ApiModelProperty(value = "开启Swagger")
    private Boolean enableSwagger;

    @ApiModelProperty(value = "作者")
    private String author;

    @ApiModelProperty(value = "输出路径")
    private String outputDir;

    @ApiModelProperty(value = "后端代码路径")
    private String backendPath;

    @ApiModelProperty(value = "前端代码路径")
    private String frontendPath;

    @ApiModelProperty(value = "包路径")
    private String packagePath;

    @ApiModelProperty(value = "主键类型")
    private String idType;

    @ApiModelProperty(value = "时间类型")
    private String dateType;

    @ApiModelProperty(value = "时间格式化")
    private String datePattern;

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
