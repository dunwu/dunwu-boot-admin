package io.github.dunwu.modules.mnt.service.impl;

import cn.hutool.core.bean.BeanUtil;
import io.github.dunwu.data.mybatis.ServiceImpl;
import io.github.dunwu.modules.mnt.dao.MntDatabaseDao;
import io.github.dunwu.modules.mnt.entity.MntDatabase;
import io.github.dunwu.modules.mnt.entity.dto.MntDatabaseDto;
import io.github.dunwu.modules.mnt.entity.query.MntDatabaseQuery;
import io.github.dunwu.modules.mnt.service.MntDatabaseService;
import io.github.dunwu.modules.mnt.util.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-07
 */
@Slf4j
@Service
public class MntDatabaseServiceImpl extends ServiceImpl implements MntDatabaseService {

    private final MntDatabaseDao dao;

    public MntDatabaseServiceImpl(MntDatabaseDao dao) {
        this.dao = dao;
    }

    @Override
    public boolean save(MntDatabase entity) {
        return dao.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<MntDatabase> list) {
        return dao.saveBatch(list);
    }

    @Override
    public boolean updateById(MntDatabase entity) {
        return dao.updateById(entity);
    }

    @Override
    public boolean updateBatchById(Collection<MntDatabase> list) {
        return dao.updateBatchById(list);
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
    public Page<MntDatabaseDto> pojoPageByQuery(MntDatabaseQuery query, Pageable pageable) {
        return dao.pojoPageByQuery(query, pageable, this::doToDto);
    }

    @Override
    public List<MntDatabaseDto> pojoListByQuery(MntDatabaseQuery query) {
        return dao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public MntDatabaseDto pojoById(Serializable id) {
        return dao.pojoById(id, this::doToDto);
    }

    @Override
    public MntDatabaseDto pojoByQuery(MntDatabaseQuery query) {
        return dao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(MntDatabaseQuery query) {
        return dao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<Serializable> ids, HttpServletResponse response) throws IOException {
        List<MntDatabaseDto> list = dao.pojoListByIds(ids, this::doToDto);
        dao.exportDtoList(list, response);
    }

    @Override
    public void exportPage(MntDatabaseQuery query, Pageable pageable, HttpServletResponse response) throws IOException {
        Page<MntDatabaseDto> page = dao.pojoPageByQuery(query, pageable, this::doToDto);
        dao.exportDtoList(page.getContent(), response);
    }

    @Override
    public MntDatabaseDto doToDto(MntDatabase model) {
        if (model == null) {
            return null;
        }

        return BeanUtil.toBean(model, MntDatabaseDto.class);
    }

    @Override
    public MntDatabase dtoToDo(MntDatabaseDto dto) {
        if (dto == null) {
            return null;
        }

        return BeanUtil.toBean(dto, MntDatabase.class);
    }

    @Override
    public boolean testConnection(MntDatabaseDto dto) {
        try {
            return SqlUtils.testConnection(dto.getJdbcUrl(), dto.getUsername(), dto.getPassword());
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

}
