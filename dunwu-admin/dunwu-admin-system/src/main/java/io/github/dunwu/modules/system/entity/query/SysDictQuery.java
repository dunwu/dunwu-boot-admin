package io.github.dunwu.modules.system.entity.query;

import io.github.dunwu.data.core.annotation.QueryField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 系统数据字典 Query 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysDictQuery", description = "系统数据字典")
public class SysDictQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @QueryField(blurry = { "code", "name", "note" }, type = QueryField.QueryType.EQUALS)
    private String blurry;

    @ApiModelProperty(value = "字典编码")
    @QueryField(type = QueryField.QueryType.LIKE)
    private String code;

    @ApiModelProperty(value = "字典名称")
    @QueryField(type = QueryField.QueryType.LIKE)
    private String name;

    @ApiModelProperty(value = "状态")
    @QueryField
    private Boolean enabled;

}
