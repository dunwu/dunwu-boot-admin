package io.github.dunwu.modules.system.dao.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.dunwu.data.core.annotation.Dao;
import io.github.dunwu.data.mybatis.BaseExtDaoImpl;
import io.github.dunwu.modules.system.dao.SysDeptDao;
import io.github.dunwu.modules.system.dao.mapper.SysDeptMapper;
import io.github.dunwu.modules.system.entity.SysDept;
import io.github.dunwu.modules.system.entity.dto.SysDeptDto;
import io.github.dunwu.tool.bean.BeanUtil;
import io.github.dunwu.tool.util.tree.Node;
import io.github.dunwu.tool.util.tree.TreeNodeConfig;
import io.github.dunwu.tool.util.tree.TreeUtil;
import io.github.dunwu.web.util.ServletUtil;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统部门信息 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@Dao
public class SysDeptDaoImpl extends BaseExtDaoImpl<SysDeptMapper, SysDept> implements SysDeptDao {

    @Override
    public void exportDtoList(Collection<SysDeptDto> list, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (SysDeptDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("上级部门ID", item.getPid());
            map.put("部门名称", item.getName());
            // map.put("权重", item.getWeight());
            map.put("状态", item.getEnabled());
            map.put("备注", item.getNote());
            map.put("创建者", item.getCreateBy());
            map.put("更新者", item.getUpdateBy());
            map.put("创建时间", item.getCreateTime());
            map.put("更新时间", item.getUpdateTime());
            mapList.add(map);
        }
        ServletUtil.downloadExcel(response, mapList);
    }

    @Override
    public List<SysDeptDto> buildTreeList(Collection<SysDeptDto> list) {
        if (CollectionUtil.isEmpty(list)) {
            return Collections.emptyList();
        }

        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setWeightKey("id");
        treeNodeConfig.setPidKey("pid");
        treeNodeConfig.setSort(Node.SORT.ASC);
        treeNodeConfig.setDeep(10);
        list = list.stream().filter(Objects::nonNull).collect(Collectors.toList());
        return TreeUtil.build(list, 0L, treeNodeConfig, SysDeptDto.class);
    }

    @Override
    public List<SysDeptDto> listByPid(Serializable pid) {
        QueryWrapper<SysDept> wrapper = new QueryWrapper<>();
        wrapper.eq("pid", pid);
        List<SysDept> list = list(wrapper);
        return BeanUtil.toBeanList(list, SysDeptDto.class);
    }

    @Override
    public Set<SysDeptDto> getDeleteDepts(List<SysDept> deptList, Set<SysDeptDto> deptDtos) {
        for (SysDept dept : deptList) {
            SysDeptDto deptDto = BeanUtil.toBean(dept, SysDeptDto.class);
            deptDtos.add(deptDto);
            QueryWrapper<SysDept> wrapper = new QueryWrapper<>();
            wrapper.eq("pid", dept.getPid());
            List<SysDept> depts = baseMapper.selectList(wrapper);
            if (depts != null && depts.size() != 0) {
                getDeleteDepts(depts, deptDtos);
            }
        }
        return deptDtos;
    }

}
