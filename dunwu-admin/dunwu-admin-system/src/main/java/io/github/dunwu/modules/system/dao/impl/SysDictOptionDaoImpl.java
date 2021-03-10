package io.github.dunwu.modules.system.dao.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.dunwu.data.core.annotation.Dao;
import io.github.dunwu.data.mybatis.BaseExtDaoImpl;
import io.github.dunwu.modules.system.dao.SysDictOptionDao;
import io.github.dunwu.modules.system.dao.mapper.SysDictOptionMapper;
import io.github.dunwu.modules.system.entity.SysDictOption;
import io.github.dunwu.modules.system.entity.dto.SysDictOptionDto;
import io.github.dunwu.tool.bean.BeanUtil;
import io.github.dunwu.web.util.ServletUtil;

import java.io.IOException;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统数据字典项 Dao 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@Dao
public class SysDictOptionDaoImpl extends BaseExtDaoImpl<SysDictOptionMapper, SysDictOption>
    implements SysDictOptionDao {

    @Override
    public void exportDtoList(Collection<SysDictOptionDto> list, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (SysDictOptionDto item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("ID", item.getId());
            map.put("所属字典ID", item.getDictId());
            map.put("字典项编码", item.getCode());
            map.put("字典项名称", item.getName());
            map.put("状态", item.getEnabled());
            map.put("备注", item.getNote());
            map.put("创建者", item.getCreateBy());
            map.put("更新者", item.getUpdateBy());
            map.put("创建时间", item.getCreateTime());
            map.put("更新时间", item.getUpdateTime());
            mapList.add(map);
        }
        ServletUtil.downloadExcel(response, mapList);
    }

    @Override
    public List<SysDictOptionDto> pojoDictOptionsByDictId(Long dictId) {
        SysDictOption sysDictOption = new SysDictOption();
        sysDictOption.setDictId(dictId);
        List<SysDictOption> list = list(Wrappers.query(sysDictOption));
        return BeanUtil.toBeanList(list, SysDictOptionDto.class);
    }

}
