package io.github.dunwu.modules.system.entity.query;

import io.github.dunwu.data.core.annotation.QueryField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 系统岗位/角色关系表 Query 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysJobRoleQuery", description = "系统岗位/角色关系表")
public class SysJobRoleQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "岗位ID")
    @QueryField
    private Long jobId;

    @ApiModelProperty(value = "角色ID")
    @QueryField
    private Long roleId;



}
