package io.github.dunwu.modules.monitor.dao.impl;

import io.github.dunwu.data.core.annotation.Dao;
import io.github.dunwu.data.mybatis.BaseExtDaoImpl;
import io.github.dunwu.modules.monitor.dao.SysLogDao;
import io.github.dunwu.modules.monitor.dao.mapper.SysLogMapper;
import io.github.dunwu.modules.monitor.entity.SysLog;
import io.github.dunwu.modules.monitor.entity.dto.SysLogDto;
import io.github.dunwu.web.util.ServletUtil;

import java.io.IOException;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统日志 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-10
 */
@Dao
public class SysLogDaoImpl extends BaseExtDaoImpl<SysLogMapper, SysLog> implements SysLogDao {

    @Override
    public void exportDtoList(Collection<SysLogDto> list, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (SysLogDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("日志描述信息", item.getDescription());
            map.put("日志级别", item.getLevel());
            map.put("异常信息，只有日志级别为ERROR时才有值", item.getException());
            map.put("被调用方法的名称", item.getMethod());
            map.put("被调用方法的参数", item.getParams());
            map.put("用户名", item.getUsername());
            map.put("HTTP请求的IP地址", item.getRequestIp());
            map.put("HTTP请求的地理地址", item.getRequestLocation());
            map.put("HTTP请求的浏览器", item.getRequestBrowser());
            map.put("HTTP请求的耗时", item.getRequestTime());
            map.put("日志记录时间", item.getCreateTime());
            mapList.add(map);
        }
        ServletUtil.downloadExcel(response, mapList);
    }

}
