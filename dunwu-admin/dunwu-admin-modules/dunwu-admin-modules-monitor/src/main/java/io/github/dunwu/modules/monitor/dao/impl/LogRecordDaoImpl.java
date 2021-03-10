package io.github.dunwu.modules.monitor.dao.impl;

import cn.hutool.core.util.StrUtil;
import io.github.dunwu.data.core.annotation.Dao;
import io.github.dunwu.data.mybatis.BaseExtDaoImpl;
import io.github.dunwu.modules.monitor.dao.LogRecordDao;
import io.github.dunwu.modules.monitor.dao.mapper.LogRecordMapper;
import io.github.dunwu.modules.monitor.entity.LogRecord;
import io.github.dunwu.modules.monitor.entity.dto.LogDto;
import io.github.dunwu.web.util.ServletUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * 系统日志记录 服务实现类
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-09
 */
@Dao
public class LogRecordDaoImpl extends BaseExtDaoImpl<LogRecordMapper, LogRecord> implements LogRecordDao {

    @Override
    public void exportData(List<LogDto> list, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (LogDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("日志描述", item.getDescription());
            map.put("日志级别", item.getLevel());
            map.put("异常详情", StrUtil.isNotBlank(item.getException()) ? item.getException() : "");
            map.put("调用方法", item.getMethod());
            map.put("调用方法参数", item.getParams());
            map.put("用户名", item.getUsername());
            map.put("IP", item.getRequestIp());
            map.put("IP来源", item.getRequestLocation());
            map.put("浏览器", item.getRequestBrowser());
            map.put("请求耗时/毫秒", item.getRequestTime());
            mapList.add(map);
        }
        ServletUtil.downloadExcel(response, mapList);
    }

}
