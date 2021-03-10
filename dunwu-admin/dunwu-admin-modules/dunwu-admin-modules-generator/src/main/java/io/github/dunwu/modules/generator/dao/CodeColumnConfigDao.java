package io.github.dunwu.modules.generator.dao;

import io.github.dunwu.data.mybatis.IExtDao;
import io.github.dunwu.modules.generator.entity.CodeColumnConfig;
import io.github.dunwu.modules.generator.entity.dto.CodeColumnConfigDto;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.http.HttpServletResponse;

/**
 * 代码生成-字段级别配置 Dao 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-02
 */
public interface CodeColumnConfigDao extends IExtDao<CodeColumnConfig> {

    /**
     * 根据传入的 CodeColumnConfigDto 列表，导出 excel 表单
     *
     * @param list     {@link CodeColumnConfigDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportDtoList(Collection<CodeColumnConfigDto> list, HttpServletResponse response) throws IOException;

}
