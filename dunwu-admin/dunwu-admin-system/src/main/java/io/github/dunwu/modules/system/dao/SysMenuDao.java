package io.github.dunwu.modules.system.dao;

import io.github.dunwu.data.mybatis.IExtDao;
import io.github.dunwu.modules.system.entity.SysMenu;
import io.github.dunwu.modules.system.entity.dto.SysMenuDto;
import io.github.dunwu.modules.system.entity.vo.MenuVo;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统菜单信息 Dao 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
public interface SysMenuDao extends IExtDao<SysMenu> {

    /**
     * 根据传入的 SysMenuDto 列表，导出 excel 表单
     *
     * @param list     {@link SysMenuDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportDtoList(Collection<SysMenuDto> list, HttpServletResponse response) throws IOException;

    List<SysMenuDto> buildTreeList(Collection<SysMenuDto> menuDtos);

    Object treeObject(List<SysMenu> menus);

    List<MenuVo> buildFrontMenus(Collection<SysMenuDto> menuDtos);

    List<SysMenu> listByPid(Long pid);

}
