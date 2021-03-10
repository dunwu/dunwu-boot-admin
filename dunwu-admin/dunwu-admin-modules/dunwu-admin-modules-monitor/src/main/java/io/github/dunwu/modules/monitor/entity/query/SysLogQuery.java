package io.github.dunwu.modules.monitor.entity.query;

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
 * 系统日志 Query 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-10
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysLogQuery", description = "系统日志")
public class SysLogQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @QueryField(blurry = { "username", "description", "method", "params" }, type = QueryField.QueryType.LIKE)
    @ApiModelProperty(value = "模糊查询字段")
    private String blurry;

    @ApiModelProperty(value = "ID")
    @QueryField
    private Long id;

    @ApiModelProperty(value = "日志描述信息")
    @QueryField
    private String description;

    @ApiModelProperty(value = "日志级别")
    @QueryField
    private String level;

    @ApiModelProperty(value = "异常信息，只有日志级别为ERROR时才有值")
    @QueryField
    private String exception;

    @ApiModelProperty(value = "被调用方法的名称")
    @QueryField
    private String method;

    @ApiModelProperty(value = "被调用方法的参数")
    @QueryField
    private String params;

    @ApiModelProperty(value = "用户名")
    @QueryField
    private String username;

    @ApiModelProperty(value = "HTTP请求的IP地址")
    @QueryField
    private String requestIp;

    @ApiModelProperty(value = "HTTP请求的地理地址")
    @QueryField
    private String requestLocation;

    @ApiModelProperty(value = "HTTP请求的浏览器")
    @QueryField
    private String requestBrowser;

    @ApiModelProperty(value = "HTTP请求的耗时")
    @QueryField
    private Long requestTime;

    @ApiModelProperty(value = "日志记录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @QueryField
    private List<LocalDateTime> createTimeRange;

}
