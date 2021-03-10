package io.github.dunwu.modules.generator.service.impl;

import cn.hutool.core.bean.BeanUtil;
import io.github.dunwu.data.mybatis.ServiceImpl;
import io.github.dunwu.modules.generator.dao.CodeTableConfigDao;
import io.github.dunwu.modules.generator.entity.CodeTableConfig;
import io.github.dunwu.modules.generator.entity.dto.CodeTableConfigDto;
import io.github.dunwu.modules.generator.entity.query.CodeTableConfigQuery;
import io.github.dunwu.modules.generator.service.CodeTableConfigService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 代码生成-表级别配置 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-03
 */
@Service
public class CodeTableConfigServiceImpl extends ServiceImpl implements CodeTableConfigService {

    private final CodeTableConfigDao dao;

    public CodeTableConfigServiceImpl(CodeTableConfigDao dao) {
        this.dao = dao;
    }

    @Override
    public boolean save(CodeTableConfig entity) {
        return dao.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<CodeTableConfig> list) {
        return dao.saveBatch(list);
    }

    @Override
    public boolean updateById(CodeTableConfig entity) {
        return dao.updateById(entity);
    }

    @Override
    public boolean updateBatchById(Collection<CodeTableConfig> list) {
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
    public Page<CodeTableConfigDto> pojoPageByQuery(CodeTableConfigQuery query, Pageable pageable) {
        return dao.pojoPageByQuery(query, pageable, this::doToDto);
    }

    @Override
    public List<CodeTableConfigDto> pojoListByQuery(CodeTableConfigQuery query) {
        return dao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public CodeTableConfigDto pojoById(Serializable id) {
        return dao.pojoById(id, this::doToDto);
    }

    @Override
    public CodeTableConfigDto pojoByQuery(CodeTableConfigQuery query) {
        return dao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(CodeTableConfigQuery query) {
        return dao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<Serializable> ids, HttpServletResponse response) throws IOException {
        List<CodeTableConfigDto> list = dao.pojoListByIds(ids, this::doToDto);
        dao.exportDtoList(list, response);
    }

    @Override
    public void exportPage(CodeTableConfigQuery query, Pageable pageable, HttpServletResponse response)
        throws IOException {
        Page<CodeTableConfigDto> page = dao.pojoPageByQuery(query, pageable, this::doToDto);
        dao.exportDtoList(page.getContent(), response);
    }

    @Override
    public CodeTableConfigDto doToDto(CodeTableConfig model) {
        if (model == null) {
            return null;
        }

        return BeanUtil.toBean(model, CodeTableConfigDto.class);
    }

    @Override
    public CodeTableConfig dtoToDo(CodeTableConfigDto dto) {
        if (dto == null) {
            return null;
        }

        return BeanUtil.toBean(dto, CodeTableConfig.class);
    }

}
