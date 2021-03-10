package io.github.dunwu.modules.monitor.service;

import java.util.Map;

/**
 * @author Zheng Jie
 * @date 2020-05-02
 */
public interface ServerInfoService {

    /**
     * 查询数据分页
     *
     * @return Map<String, Object>
     */
    Map<String, Object> getServers();

}
