package io.github.dunwu.modules.system.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 构建前端路由时用到
 *
 * @author Zheng Jie
 * @date 2018-12-20
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MenuVo implements Serializable {

    private String name;

    private String path;

    private Boolean hidden;

    private String redirect;

    private String component;

    private Boolean alwaysShow;

    private MenuMetaVo meta;

    private String label;

    private boolean hasChildren;

    private List<MenuVo> children;

    /**
     * @author Zheng Jie
     * @date 2018-12-20
     */
    @Data
    @AllArgsConstructor
    public static class MenuMetaVo implements Serializable {

        private String title;
        private String icon;
        private Boolean noCache;

    }

}
