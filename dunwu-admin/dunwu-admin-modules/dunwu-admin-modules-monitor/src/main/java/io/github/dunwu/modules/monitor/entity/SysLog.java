package io.github.dunwu.modules.monitor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.dunwu.data.validator.annotation.EditCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

/**
 * 系统日志
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-10
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysLog", description = "系统日志")
public class SysLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @NotNull(groups = EditCheck.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "日志描述信息")
    private String description;

    @ApiModelProperty(value = "日志级别")
    private String level;

    @ApiModelProperty(value = "异常信息，只有日志级别为ERROR时才有值")
    private String exception;

    @ApiModelProperty(value = "被调用方法的名称")
    private String method;

    @ApiModelProperty(value = "被调用方法的参数")
    private String params;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "HTTP请求的IP地址")
    private String requestIp;

    @ApiModelProperty(value = "HTTP请求的地理地址")
    private String requestLocation;

    @ApiModelProperty(value = "HTTP请求的浏览器")
    private String requestBrowser;

    @ApiModelProperty(value = "HTTP请求的耗时")
    private Long requestTime;

    @ApiModelProperty(value = "日志记录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    public SysLog(String level, Long time) {
        this.level = level;
        this.requestTime = time;
    }

}
