package io.github.dunwu.modules.system.dao.impl;

import io.github.dunwu.data.core.annotation.Dao;
import io.github.dunwu.data.mybatis.BaseExtDaoImpl;
import io.github.dunwu.modules.system.dao.SysUserDao;
import io.github.dunwu.modules.system.dao.mapper.SysUserMapper;
import io.github.dunwu.modules.system.entity.SysUser;
import io.github.dunwu.modules.system.entity.dto.SysUserDto;
import io.github.dunwu.web.util.ServletUtil;

import java.io.IOException;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统用户信息 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-25
 */
@Dao
public class SysUserDaoImpl extends BaseExtDaoImpl<SysUserMapper, SysUser> implements SysUserDao {

    @Override
    public void exportDtoList(Collection<SysUserDto> list, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (SysUserDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("用户ID", item.getId());
            map.put("昵称", item.getNickname());
            map.put("用户名", item.getUsername());
            map.put("邮箱", item.getEmail());
            map.put("手机号", item.getPhone());
            map.put("性别", item.getGender());
            map.put("头像", item.getAvatar());
            map.put("部门ID", item.getDeptId());
            map.put("岗位ID", item.getJobId());
            map.put("状态", item.getEnabled());
            map.put("创建者", item.getCreateBy());
            map.put("更新者", item.getUpdateBy());
            mapList.add(map);
        }
        ServletUtil.downloadExcel(response, mapList);
    }

}
