package io.github.dunwu.modules.monitor.dao;

import io.github.dunwu.data.mybatis.IExtDao;
import io.github.dunwu.modules.monitor.entity.LogRecord;
import io.github.dunwu.modules.monitor.entity.dto.LogDto;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 系统日志记录 服务类
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-09
 */
public interface LogRecordDao extends IExtDao<LogRecord> {

    void exportData(List<LogDto> list, HttpServletResponse response) throws IOException;

}
