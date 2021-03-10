package io.github.dunwu.modules.system.dao;

import io.github.dunwu.data.mybatis.IExtDao;
import io.github.dunwu.modules.system.entity.SysJob;
import io.github.dunwu.modules.system.entity.dto.SysJobDto;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统岗位信息 Dao 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
public interface SysJobDao extends IExtDao<SysJob> {

    /**
     * 根据传入的 SysJobDto 列表，导出 excel 表单
     *
     * @param list     {@link SysJobDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportDtoList(Collection<SysJobDto> list, HttpServletResponse response) throws IOException;

}
