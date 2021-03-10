package io.github.dunwu.modules.system.service.impl;

import io.github.dunwu.data.mybatis.ServiceImpl;
import io.github.dunwu.modules.system.dao.SysDictOptionDao;
import io.github.dunwu.modules.system.entity.SysDictOption;
import io.github.dunwu.modules.system.entity.dto.SysDictOptionDto;
import io.github.dunwu.modules.system.service.SysDictOptionService;
import io.github.dunwu.tool.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 系统数据字典项 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-24
 */
@Service
@RequiredArgsConstructor
public class SysDictOptionServiceImpl extends ServiceImpl implements SysDictOptionService {

    private final SysDictOptionDao dao;

    @Override
    public boolean save(SysDictOptionDto entity) {
        return dao.save(dtoToDo(entity));
    }

    @Override
    public boolean updateById(SysDictOptionDto entity) {
        return dao.updateById(dtoToDo(entity));
    }

    @Override
    public boolean removeById(Serializable id) {
        return dao.removeById(id);
    }

    @Override
    public boolean removeByIds(Collection<Serializable> ids) {
        return dao.removeByIds(ids);
    }

    @Override
    public Page<SysDictOptionDto> pojoPageByQuery(Object query, Pageable pageable) {
        return dao.pojoPageByQuery(query, pageable, SysDictOptionDto.class);
    }

    @Override
    public List<SysDictOptionDto> pojoListByQuery(Object query) {
        return dao.pojoListByQuery(query, SysDictOptionDto.class);
    }

    @Override
    public SysDictOptionDto pojoById(Serializable id) {
        return dao.pojoById(id, SysDictOptionDto.class);
    }

    @Override
    public SysDictOptionDto pojoByQuery(Object query) {
        return dao.pojoByQuery(query, SysDictOptionDto.class);
    }

    @Override
    public Integer countByQuery(Object query) {
        return dao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<Serializable> ids, HttpServletResponse response) throws IOException {
        List<SysDictOptionDto> list = dao.pojoListByIds(ids, SysDictOptionDto.class);
        dao.exportDtoList(list, response);
    }

    @Override
    public void exportPage(Object query, Pageable pageable, HttpServletResponse response) throws IOException {
        Page<SysDictOptionDto> page = dao.pojoPageByQuery(query, pageable, SysDictOptionDto.class);
        dao.exportDtoList(page.getContent(), response);
    }

    /**
     * 将数据实体转为 Dto
     *
     * @param entity {@link  SysDictOption} 数据实体
     * @return {@link SysDictOptionDto}
     */
    private SysDictOptionDto doToDto(SysDictOption entity) {
        return BeanUtil.toBean(entity, SysDictOptionDto.class);
    }

    /**
     * 将 Dto 转为数据实体
     *
     * @param dto Dto
     * @return {@link SysDictOption}
     */
    private SysDictOption dtoToDo(SysDictOptionDto dto) {
        return BeanUtil.toBean(dto, SysDictOption.class);
    }

}
