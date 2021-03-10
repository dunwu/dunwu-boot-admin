package io.github.dunwu.modules.demo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import io.github.dunwu.data.mybatis.ServiceImpl;
import io.github.dunwu.modules.demo.dao.HelloDao;
import io.github.dunwu.modules.demo.entity.Hello;
import io.github.dunwu.modules.demo.entity.dto.HelloDto;
import io.github.dunwu.modules.demo.entity.query.HelloQuery;
import io.github.dunwu.modules.demo.service.HelloService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * 测试 Service 类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-03-10
 */
@Service
public class HelloServiceImpl extends ServiceImpl implements HelloService {

    private final HelloDao dao;

    public HelloServiceImpl(HelloDao dao) {
        this.dao = dao;
    }

    @Override
    public boolean save(Hello entity) {
        return dao.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<Hello> list) {
        return dao.saveBatch(list);
    }

    @Override
    public boolean updateById(Hello entity) {
        return dao.updateById(entity);
    }

    @Override
    public boolean updateBatchById(Collection<Hello> list) {
        return dao.updateBatchById(list);
    }

    @Override
    public boolean removeById(Serializable id) {
        return dao.removeById(id);
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> ids) {
        return dao.removeByIds(ids);
    }

    @Override
    public List<HelloDto> pojoListByQuery(HelloQuery query) {
        return dao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public Page<HelloDto> pojoPageByQuery(HelloQuery query, Pageable pageable) {
        return dao.pojoPageByQuery(query, pageable, this::doToDto);
    }

    @Override
    public HelloDto pojoById(Serializable id) {
        return dao.pojoById(id, this::doToDto);
    }

    @Override
    public HelloDto pojoByQuery(HelloQuery query) {
        return dao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(HelloQuery query) {
        return dao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) throws IOException {
        List<HelloDto> list = dao.pojoListByIds(ids, this::doToDto);
        dao.exportDtoList(list, response);
    }

    @Override
    public void exportPage(HelloQuery query, Pageable pageable, HttpServletResponse response) throws IOException {
        Page<HelloDto> page = dao.pojoPageByQuery(query, pageable, this::doToDto);
        dao.exportDtoList(page.getContent(), response);
    }

    @Override
    public HelloDto doToDto(Hello model) {
        if (model == null) {
            return null;
        }

        return BeanUtil.toBean(model, HelloDto.class);
    }

    @Override
    public Hello dtoToDo(HelloDto dto) {
        if (dto == null) {
            return null;
        }

        return BeanUtil.toBean(dto, Hello.class);
    }

}
