package io.github.dunwu.modules.system.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * 系统菜单信息 Dto 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SysMenuDto", description = "系统菜单信息")
public class SysMenuDto implements Serializable, Comparable<SysMenuDto> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "上级菜单ID")
    private Long pid;

    @ApiModelProperty(value = "菜单标题")
    private String title;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "菜单链接地址")
    private String path;

    @ApiModelProperty(value = "类型")
    private Integer type;

    @ApiModelProperty(value = "组件")
    private String component;

    @ApiModelProperty(value = "组件名称")
    private String componentName;

    @ApiModelProperty(value = "权限")
    private String permission;

    @ApiModelProperty(value = "权重")
    private Integer weight;

    @ApiModelProperty(value = "是否为外链")
    private Boolean iFrame;

    @ApiModelProperty(value = "是否缓存")
    private Boolean cache;

    @ApiModelProperty(value = "是否隐藏")
    private Boolean hidden;

    @ApiModelProperty(value = "状态")
    private Boolean enabled;

    @ApiModelProperty(value = "备注")
    private String note;

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

    // ========================================================================
    // 以下字段在构建树形列表时会自动填充

    @ApiModelProperty(value = "标签")
    private String label;

    @ApiModelProperty(value = "是否有子菜单")
    private boolean hasChildren;

    @ApiModelProperty(value = "是否为叶子节点")
    private boolean leaf;

    @ApiModelProperty(value = "子菜单")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Collection<SysMenuDto> children;

    // ========================================================================

    @Override
    public int compareTo(SysMenuDto o) {
        if (o.weight.equals(this.weight)) {
            return o.id.compareTo(this.id);
        } else {
            return o.weight.compareTo(this.weight);
        }
    }

}
