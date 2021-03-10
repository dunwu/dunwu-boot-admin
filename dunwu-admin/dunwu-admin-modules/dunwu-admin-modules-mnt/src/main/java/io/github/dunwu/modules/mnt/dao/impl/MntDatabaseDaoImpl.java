package io.github.dunwu.modules.mnt.dao.impl;

import io.github.dunwu.data.core.annotation.Dao;
import io.github.dunwu.data.mybatis.BaseExtDaoImpl;
import io.github.dunwu.modules.mnt.dao.MntDatabaseDao;
import io.github.dunwu.modules.mnt.dao.mapper.MntDatabaseMapper;
import io.github.dunwu.modules.mnt.entity.MntDatabase;
import io.github.dunwu.modules.mnt.entity.dto.MntDatabaseDto;
import io.github.dunwu.web.util.ServletUtil;

import java.io.IOException;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

/**
 *  Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-07
 */
@Dao
public class MntDatabaseDaoImpl extends BaseExtDaoImpl<MntDatabaseMapper, MntDatabase> implements MntDatabaseDao {

    @Override
    public void exportDtoList(Collection<MntDatabaseDto> list, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (MntDatabaseDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("名称", item.getName());
            map.put("jdbc连接", item.getJdbcUrl());
            map.put("账号", item.getUsername());
            map.put("密码", item.getPassword());
            map.put("创建者", item.getCreateBy());
            map.put("更新者", item.getUpdateBy());
            mapList.add(map);
        }
        ServletUtil.downloadExcel(response, mapList);
    }

}
