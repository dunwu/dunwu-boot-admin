package io.github.dunwu.modules.generator.service.impl;

import cn.hutool.core.bean.BeanUtil;
import io.github.dunwu.data.mybatis.ServiceImpl;
import io.github.dunwu.modules.generator.dao.CodeGlobalConfigDao;
import io.github.dunwu.modules.generator.entity.CodeGlobalConfig;
import io.github.dunwu.modules.generator.entity.dto.CodeGlobalConfigDto;
import io.github.dunwu.modules.generator.entity.query.CodeGlobalConfigQuery;
import io.github.dunwu.modules.generator.service.CodeGlobalConfigService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 代码生成-全局配置 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-03
 */
@Service
public class CodeGlobalConfigServiceImpl extends ServiceImpl implements CodeGlobalConfigService {

    private final CodeGlobalConfigDao dao;

    public CodeGlobalConfigServiceImpl(CodeGlobalConfigDao dao) {
        this.dao = dao;
    }

    @Override
    public boolean save(CodeGlobalConfig entity) {
        return dao.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<CodeGlobalConfig> list) {
        return dao.saveBatch(list);
    }

    @Override
    public boolean updateById(CodeGlobalConfig entity) {
        return dao.updateById(entity);
    }

    @Override
    public boolean updateBatchById(Collection<CodeGlobalConfig> list) {
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
    public Page<CodeGlobalConfigDto> pojoPageByQuery(CodeGlobalConfigQuery query, Pageable pageable) {
        return dao.pojoPageByQuery(query, pageable, this::doToDto);
    }

    @Override
    public List<CodeGlobalConfigDto> pojoListByQuery(CodeGlobalConfigQuery query) {
        return dao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public CodeGlobalConfigDto pojoById(Serializable id) {
        return dao.pojoById(id, this::doToDto);
    }

    @Override
    public CodeGlobalConfigDto pojoByQuery(CodeGlobalConfigQuery query) {
        return dao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(CodeGlobalConfigQuery query) {
        return dao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<Serializable> ids, HttpServletResponse response) throws IOException {
        List<CodeGlobalConfigDto> list = dao.pojoListByIds(ids, this::doToDto);
        dao.exportDtoList(list, response);
    }

    @Override
    public void exportPage(CodeGlobalConfigQuery query, Pageable pageable, HttpServletResponse response)
        throws IOException {
        Page<CodeGlobalConfigDto> page = dao.pojoPageByQuery(query, pageable, this::doToDto);
        dao.exportDtoList(page.getContent(), response);
    }

    @Override
    public CodeGlobalConfigDto doToDto(CodeGlobalConfig model) {
        if (model == null) {
            return null;
        }

        return BeanUtil.toBean(model, CodeGlobalConfigDto.class);
    }

    @Override
    public CodeGlobalConfig dtoToDo(CodeGlobalConfigDto dto) {
        if (dto == null) {
            return null;
        }

        return BeanUtil.toBean(dto, CodeGlobalConfig.class);
    }

}
