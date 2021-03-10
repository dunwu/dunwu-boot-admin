package io.github.dunwu.modules.system.entity.query;

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
 * 系统用户信息 Query 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-25
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "SysUserQuery", description = "系统用户信息")
public class SysUserQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @QueryField(blurry = { "username", "email", "nickname" })
    private String blurry;

    @ApiModelProperty(value = "昵称")
    @QueryField
    private String nickname;

    @ApiModelProperty(value = "用户名")
    @QueryField
    private String username;

    @ApiModelProperty(value = "邮箱")
    @QueryField
    private String email;

    @ApiModelProperty(value = "手机号")
    @QueryField
    private String phone;

    @ApiModelProperty(value = "性别")
    @QueryField
    private String gender;

    @ApiModelProperty(value = "部门ID")
    @QueryField
    private Long deptId;

    @ApiModelProperty(value = "岗位ID")
    @QueryField
    private Long jobId;

    @ApiModelProperty(value = "状态")
    @QueryField
    private Boolean enabled;

    @ApiModelProperty(value = "创建时间")
    @QueryField(type = QueryField.QueryType.BETWEEN)
    @JsonFormat(shape = JsonFormat.Shape.ARRAY, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private List<LocalDateTime> createTime;

}
