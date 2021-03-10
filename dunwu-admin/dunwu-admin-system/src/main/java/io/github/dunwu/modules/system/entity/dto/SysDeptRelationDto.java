package io.github.dunwu.modules.system.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;
import javax.validation.constraints.NotNull;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-26
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysDeptRelationDto", description = "系统部门关联信息")
public class SysDeptRelationDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "Job ID 集合")
    private List<Long> jobIds;

    @ApiModelProperty(value = "User ID 集合")
    private List<Long> userIds;

}
