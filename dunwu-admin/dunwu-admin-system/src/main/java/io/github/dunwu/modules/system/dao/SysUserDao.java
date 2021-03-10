package io.github.dunwu.modules.system.dao;

import io.github.dunwu.data.mybatis.IExtDao;
import io.github.dunwu.modules.system.entity.SysUser;
import io.github.dunwu.modules.system.entity.dto.SysUserDto;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统用户信息 Dao 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-25
 */
public interface SysUserDao extends IExtDao<SysUser> {

    /**
     * 根据传入的 SysUserDto 列表，导出 excel 表单
     *
     * @param list     {@link SysUserDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportDtoList(Collection<SysUserDto> list, HttpServletResponse response) throws IOException;

}
