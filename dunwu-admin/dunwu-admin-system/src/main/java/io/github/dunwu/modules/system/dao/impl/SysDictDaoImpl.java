package io.github.dunwu.modules.system.dao.impl;

import io.github.dunwu.data.core.annotation.Dao;
import io.github.dunwu.data.mybatis.BaseExtDaoImpl;
import io.github.dunwu.modules.system.dao.SysDictDao;
import io.github.dunwu.modules.system.dao.mapper.SysDictMapper;
import io.github.dunwu.modules.system.entity.SysDict;
import io.github.dunwu.modules.system.entity.dto.SysDictDto;
import io.github.dunwu.web.util.ServletUtil;

import java.io.IOException;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统数据字典 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@Dao
public class SysDictDaoImpl extends BaseExtDaoImpl<SysDictMapper, SysDict> implements SysDictDao {

    @Override
    public void exportDtoList(Collection<SysDictDto> list, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (SysDictDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("字典编码", item.getCode());
            map.put("字典名称", item.getName());
            map.put("状态", item.getEnabled());
            map.put("备注", item.getNote());
            map.put("创建者", item.getCreateBy());
            map.put("更新者", item.getUpdateBy());
            mapList.add(map);
        }
        ServletUtil.downloadExcel(response, mapList);
    }

}
