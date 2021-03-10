package io.github.dunwu.modules.system.dao;

import io.github.dunwu.data.mybatis.IExtDao;
import io.github.dunwu.modules.system.entity.SysRole;
import io.github.dunwu.modules.system.entity.dto.SysRoleDto;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统角色信息 Dao 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
public interface SysRoleDao extends IExtDao<SysRole> {

    /**
     * 根据传入的 SysRoleDto 列表，导出 excel 表单
     *
     * @param list     {@link SysRoleDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportDtoList(Collection<SysRoleDto> list, HttpServletResponse response) throws IOException;

}
