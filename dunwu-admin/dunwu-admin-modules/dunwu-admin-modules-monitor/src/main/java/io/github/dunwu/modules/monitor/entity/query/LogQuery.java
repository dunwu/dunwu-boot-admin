package io.github.dunwu.modules.monitor.entity.query;

import io.github.dunwu.data.core.annotation.QueryField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-09
 */
@Data
@Accessors(chain = true)
public class LogQuery implements Serializable {

    @QueryField(blurry = { "username", "description", "method", "params" }, type = QueryField.QueryType.LIKE)
    @ApiModelProperty(value = "模糊查询字段")
    private String blurry;

    @QueryField
    @ApiModelProperty(value = "日志类型")
    private String logType;

    @QueryField
    @ApiModelProperty(value = "日志描述信息")
    private String description;

    @QueryField
    @ApiModelProperty(value = "日志级别")
    private String level;

    @QueryField(type = QueryField.QueryType.LIKE)
    @ApiModelProperty(value = "异常信息，只有日志级别为ERROR时才有值")
    private String exception;

    @QueryField(type = QueryField.QueryType.LIKE)
    @ApiModelProperty(value = "被调用方法的名称")
    private String method;

    @QueryField
    @ApiModelProperty(value = "用户名")
    private String username;

    @QueryField(type = QueryField.QueryType.BETWEEN)
    @ApiModelProperty(value = "日志记录时间")
    private List<LocalDateTime> createTime;

}
