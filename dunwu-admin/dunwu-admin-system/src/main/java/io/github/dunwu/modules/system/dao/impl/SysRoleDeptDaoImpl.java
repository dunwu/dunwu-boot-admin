package io.github.dunwu.modules.system.dao.impl;

import io.github.dunwu.data.mybatis.BaseExtDaoImpl;
import io.github.dunwu.modules.system.dao.SysRoleDeptDao;
import io.github.dunwu.modules.system.dao.mapper.SysRoleDeptMapper;
import io.github.dunwu.modules.system.entity.SysRoleDept;
import org.springframework.stereotype.Service;

/**
 * 系统角色部门关联信息 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-14
 */
@Service
public class SysRoleDeptDaoImpl extends BaseExtDaoImpl<SysRoleDeptMapper, SysRoleDept> implements SysRoleDeptDao {

}
