package io.github.dunwu.modules.system.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.dunwu.data.core.annotation.Dao;
import io.github.dunwu.data.mybatis.BaseExtDaoImpl;
import io.github.dunwu.modules.system.dao.SysMenuDao;
import io.github.dunwu.modules.system.dao.mapper.SysMenuMapper;
import io.github.dunwu.modules.system.entity.SysMenu;
import io.github.dunwu.modules.system.entity.dto.SysMenuDto;
import io.github.dunwu.modules.system.entity.vo.MenuVo;
import io.github.dunwu.tool.util.tree.Node;
import io.github.dunwu.tool.util.tree.TreeNodeConfig;
import io.github.dunwu.tool.util.tree.TreeUtil;
import io.github.dunwu.web.util.ServletUtil;

import java.io.IOException;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统菜单信息 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@Dao
public class SysMenuDaoImpl extends BaseExtDaoImpl<SysMenuMapper, SysMenu> implements SysMenuDao {

    @Override
    public void exportDtoList(Collection<SysMenuDto> list, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (SysMenuDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("上级菜单ID", item.getPid());
            map.put("菜单名称", item.getName());
            map.put("菜单图标", item.getIcon());
            map.put("菜单链接地址", item.getPath());
            map.put("类型", item.getType());
            map.put("组件", item.getComponent());
            map.put("组件名称", item.getComponentName());
            map.put("权限", item.getPermission());
            map.put("权重", item.getWeight());
            map.put("是否为外链", item.getIFrame());
            map.put("是否缓存", item.getCache());
            map.put("是否隐藏", item.getHidden());
            map.put("状态", item.getEnabled());
            map.put("备注", item.getNote());
            map.put("创建者", item.getCreateBy());
            map.put("更新者", item.getUpdateBy());
            mapList.add(map);
        }
        ServletUtil.downloadExcel(response, mapList);
    }

    @Override
    public List<SysMenuDto> buildTreeList(Collection<SysMenuDto> list) {
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setPidKey("pid");
        treeNodeConfig.setDeep(10);
        treeNodeConfig.setSortKey("weight");
        treeNodeConfig.setSort(Node.SORT.ASC);
        return TreeUtil.build(list, 0L, treeNodeConfig, SysMenuDto.class);
    }

    @Override
    public Object treeObject(List<SysMenu> menus) {
        List<Map<String, Object>> list = new LinkedList<>();
        menus.forEach(menu -> {
                if (menu != null) {
                    List<SysMenu> menuList = listByPid(menu.getId());
                    Map<String, Object> map = new HashMap<>(16);
                    map.put("id", menu.getId());
                    map.put("label", menu.getName());
                    if (menuList != null && menuList.size() != 0) {
                        map.put("children", treeObject(menuList));
                    }
                    list.add(map);
                }
            }
        );
        return list;
    }

    @Override
    public List<MenuVo> buildFrontMenus(Collection<SysMenuDto> list) {
        List<MenuVo> finalVoList = new LinkedList<>();
        for (SysMenuDto entity : list) {
            if (entity == null) {
                continue;
            }

            Collection<SysMenuDto> children = entity.getChildren();
            MenuVo menuVo = new MenuVo();
            menuVo.setName(
                ObjectUtil.isNotEmpty(entity.getComponentName()) ? entity.getComponentName() : entity.getName());
            // 一级目录需要加斜杠，不然会报警告
            menuVo.setPath(entity.getPid() == null ? "/" + entity.getPath() : entity.getPath());
            menuVo.setHidden(entity.getHidden());
            // 如果不是外链
            if (!entity.getIFrame()) {
                if (entity.getPid() == null) {
                    menuVo.setComponent(StrUtil.isEmpty(entity.getComponent()) ? "Layout" : entity.getComponent());
                } else if (!StrUtil.isEmpty(entity.getComponent())) {
                    menuVo.setComponent(entity.getComponent());
                }
            }
            menuVo.setMeta(new MenuVo.MenuMetaVo(entity.getTitle(), entity.getIcon(), !entity.getCache()));
            if (CollectionUtil.isNotEmpty(children)) {
                menuVo.setAlwaysShow(true);
                menuVo.setRedirect("noredirect");
                menuVo.setChildren(buildFrontMenus(children));
                // 处理是一级菜单并且没有子菜单的情况
            } else if (entity.getPid() == null) {
                MenuVo menuVo1 = new MenuVo();
                menuVo1.setMeta(menuVo.getMeta());
                // 非外链
                if (!entity.getIFrame()) {
                    menuVo1.setPath("index");
                    menuVo1.setName(menuVo.getName());
                    menuVo1.setComponent(menuVo.getComponent());
                } else {
                    menuVo1.setPath(entity.getPath());
                }
                menuVo.setName(null);
                menuVo.setMeta(null);
                menuVo.setComponent("Layout");
                List<MenuVo> list1 = new ArrayList<>();
                list1.add(menuVo1);
                menuVo.setChildren(list1);
            }

            finalVoList.add(menuVo);
        }
        return finalVoList;
    }

    @Override
    public List<SysMenu> listByPid(Long pid) {
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("pid", pid);
        return list(wrapper);
    }

}
