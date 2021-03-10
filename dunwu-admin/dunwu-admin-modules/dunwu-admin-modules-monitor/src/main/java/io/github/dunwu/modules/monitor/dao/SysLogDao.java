package io.github.dunwu.modules.monitor.dao;

import io.github.dunwu.data.mybatis.IExtDao;
import io.github.dunwu.modules.monitor.entity.SysLog;
import io.github.dunwu.modules.monitor.entity.dto.SysLogDto;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统日志 Dao 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-10
 */
public interface SysLogDao extends IExtDao<SysLog> {

    /**
     * 根据传入的 SysLogDto 列表，导出 excel 表单
     *
     * @param list     {@link SysLogDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportDtoList(Collection<SysLogDto> list, HttpServletResponse response) throws IOException;

}
