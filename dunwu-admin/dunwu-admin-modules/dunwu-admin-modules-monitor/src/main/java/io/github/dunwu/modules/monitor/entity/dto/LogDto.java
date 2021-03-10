package io.github.dunwu.modules.monitor.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 系统日志记录
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "SysLog对象", description = "系统日志记录")
public class LogDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "日志描述信息")
    private String description;

    @ApiModelProperty(value = "日志级别")
    private String level;
    private String logType;

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
    private String requestTime;

    private String browser;

    private String address;

    @ApiModelProperty(value = "日志记录时间")
    private LocalDateTime createTime;

}
