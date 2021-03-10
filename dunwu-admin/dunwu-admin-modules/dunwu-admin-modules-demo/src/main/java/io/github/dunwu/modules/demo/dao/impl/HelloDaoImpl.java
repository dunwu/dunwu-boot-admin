package io.github.dunwu.modules.demo.dao.impl;

import io.github.dunwu.data.core.annotation.Dao;
import io.github.dunwu.data.mybatis.BaseExtDaoImpl;
import io.github.dunwu.modules.demo.dao.HelloDao;
import io.github.dunwu.modules.demo.dao.mapper.HelloMapper;
import io.github.dunwu.modules.demo.entity.Hello;
import io.github.dunwu.modules.demo.entity.dto.HelloDto;
import io.github.dunwu.web.util.ServletUtil;

import java.io.IOException;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

/**
 * 测试 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-10
 */
@Dao
public class HelloDaoImpl extends BaseExtDaoImpl<HelloMapper, Hello> implements HelloDao {

    @Override
    public void exportDtoList(Collection<HelloDto> list, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (HelloDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("名字", item.getName());
            map.put("年龄", item.getAge());
            map.put("创建时间", item.getCreateTime());
            mapList.add(map);
        }
        ServletUtil.downloadExcel(response, mapList);
    }

}
