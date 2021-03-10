package io.github.dunwu.modules.system.dao.impl;

import io.github.dunwu.data.core.annotation.Dao;
import io.github.dunwu.data.mybatis.BaseExtDaoImpl;
import io.github.dunwu.modules.system.dao.SysJobDao;
import io.github.dunwu.modules.system.dao.mapper.SysJobMapper;
import io.github.dunwu.modules.system.entity.SysJob;
import io.github.dunwu.modules.system.entity.dto.SysJobDto;
import io.github.dunwu.web.util.ServletUtil;

import java.io.IOException;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统岗位信息 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@Dao
public class SysJobDaoImpl extends BaseExtDaoImpl<SysJobMapper, SysJob> implements SysJobDao {

    @Override
    public void exportDtoList(Collection<SysJobDto> list, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (SysJobDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("部门ID", item.getDeptId());
            map.put("岗位名称", item.getName());
            map.put("权重", item.getWeight());
            map.put("状态", item.getEnabled());
            map.put("备注", item.getNote());
            map.put("创建者", item.getCreateBy());
            map.put("更新者", item.getUpdateBy());
            mapList.add(map);
        }
        ServletUtil.downloadExcel(response, mapList);
    }

}
