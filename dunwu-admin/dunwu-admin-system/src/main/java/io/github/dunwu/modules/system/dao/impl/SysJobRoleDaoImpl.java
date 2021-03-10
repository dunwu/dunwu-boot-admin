package io.github.dunwu.modules.system.dao.impl;

import io.github.dunwu.data.core.annotation.Dao;
import io.github.dunwu.data.mybatis.BaseExtDaoImpl;
import io.github.dunwu.modules.system.dao.SysJobRoleDao;
import io.github.dunwu.modules.system.dao.mapper.SysJobRoleMapper;
import io.github.dunwu.modules.system.entity.SysJobRole;
import io.github.dunwu.modules.system.entity.dto.SysJobRoleDto;
import io.github.dunwu.web.util.ServletUtil;

import java.io.IOException;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统岗位/角色关系表 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-30
 */
@Dao
public class SysJobRoleDaoImpl extends BaseExtDaoImpl<SysJobRoleMapper, SysJobRole> implements SysJobRoleDao {

    @Override
    public void exportDtoList(Collection<SysJobRoleDto> list, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (SysJobRoleDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("岗位ID", item.getJobId());
            map.put("角色ID", item.getRoleId());
            mapList.add(map);
        }
        ServletUtil.downloadExcel(response, mapList);
    }

}
