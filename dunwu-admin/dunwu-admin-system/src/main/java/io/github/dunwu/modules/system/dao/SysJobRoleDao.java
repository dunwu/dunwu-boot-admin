package io.github.dunwu.modules.system.dao;

import io.github.dunwu.data.mybatis.IExtDao;
import io.github.dunwu.modules.system.entity.SysJobRole;
import io.github.dunwu.modules.system.entity.dto.SysJobRoleDto;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统岗位/角色关系表 Dao 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-30
 */
public interface SysJobRoleDao extends IExtDao<SysJobRole> {

    /**
     * 根据传入的 SysJobRoleDto 列表，导出 excel 表单
     *
     * @param list     {@link SysJobRoleDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportDtoList(Collection<SysJobRoleDto> list, HttpServletResponse response) throws IOException;

}
