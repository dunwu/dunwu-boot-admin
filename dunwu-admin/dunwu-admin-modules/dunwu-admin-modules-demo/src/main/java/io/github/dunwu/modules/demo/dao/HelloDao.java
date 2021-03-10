package io.github.dunwu.modules.demo.dao;

import io.github.dunwu.data.mybatis.IExtDao;
import io.github.dunwu.modules.demo.entity.Hello;
import io.github.dunwu.modules.demo.entity.dto.HelloDto;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

/**
 * 测试 Dao 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-10
 */
public interface HelloDao extends IExtDao<Hello> {

    /**
     * 根据传入的 HelloDto 列表，导出 excel 表单
     *
     * @param list     {@link HelloDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportDtoList(Collection<HelloDto> list, HttpServletResponse response) throws IOException;

}
