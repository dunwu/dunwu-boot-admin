package io.github.dunwu.modules.system.dao;

import io.github.dunwu.data.mybatis.IExtDao;
import io.github.dunwu.modules.system.entity.SysDictOption;
import io.github.dunwu.modules.system.entity.dto.SysDictOptionDto;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统数据字典项 Dao 接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
public interface SysDictOptionDao extends IExtDao<SysDictOption> {

    /**
     * 根据传入的 SysDictOptionDto 列表，导出 excel 表单
     *
     * @param list     {@link SysDictOptionDto} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportDtoList(Collection<SysDictOptionDto> list, HttpServletResponse response) throws IOException;

    List<SysDictOptionDto> pojoDictOptionsByDictId(Long dictId);

}
