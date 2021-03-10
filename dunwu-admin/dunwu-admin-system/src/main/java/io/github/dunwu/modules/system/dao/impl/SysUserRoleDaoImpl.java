package io.github.dunwu.modules.system.dao.impl;

import io.github.dunwu.data.mybatis.BaseExtDaoImpl;
import io.github.dunwu.modules.system.dao.SysUserRoleDao;
import io.github.dunwu.modules.system.dao.mapper.SysUserRoleMapper;
import io.github.dunwu.modules.system.entity.SysUserRole;
import org.springframework.stereotype.Service;

/**
 * 系统用户角色关联信息 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-06
 */
@Service
public class SysUserRoleDaoImpl extends BaseExtDaoImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleDao {

}
