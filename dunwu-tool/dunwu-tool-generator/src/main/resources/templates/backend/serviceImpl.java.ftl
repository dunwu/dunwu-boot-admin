package ${package.ServiceImpl};

import cn.hutool.core.bean.BeanUtil;
import ${package.Entity}.${entity};
import ${package.Dto}.${table.dtoName};
import ${package.Query}.${table.queryName};
import ${package.Dao}.${table.daoName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * ${table.comment!} Service 类
 *
 * @author ${author}
 * @since ${date}
 */
@Service
<#if enableKotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass} implements ${table.serviceName} {

    private final ${table.daoName} dao;

    public ${table.serviceImplName}(${table.daoName} dao) {
        this.dao = dao;
    }

    @Override
    public boolean save(${entity} entity) {
        return dao.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<${entity}> list) {
        return dao.saveBatch(list);
    }

    @Override
    public boolean updateById(${entity} entity) {
        return dao.updateById(entity);
    }

    @Override
    public boolean updateBatchById(Collection<${entity}> list) {
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
    public List<${table.dtoName}> pojoListByQuery(${table.queryName} query) {
        return dao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public Page<${table.dtoName}> pojoPageByQuery(${table.queryName} query, Pageable pageable) {
        return dao.pojoPageByQuery(query, pageable, this::doToDto);
    }

    @Override
    public ${table.dtoName} pojoById(Serializable id) {
        return dao.pojoById(id, this::doToDto);
    }

    @Override
    public ${table.dtoName} pojoByQuery(${table.queryName} query) {
        return dao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(${table.queryName} query) {
        return dao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) throws IOException {
        List<${table.dtoName}> list = dao.pojoListByIds(ids, this::doToDto);
        dao.exportDtoList(list, response);
    }

    @Override
    public void exportPage(${table.queryName} query, Pageable pageable, HttpServletResponse response) throws IOException {
        Page<${table.dtoName}> page = dao.pojoPageByQuery(query, pageable, this::doToDto);
        dao.exportDtoList(page.getContent(), response);
    }

    @Override
    public ${table.dtoName} doToDto(${entity} model) {
        if (model == null) {
            return null;
        }

        return BeanUtil.toBean(model, ${table.dtoName}.class);
    }

    @Override
    public ${entity} dtoToDo(${table.dtoName} dto) {
        if (dto == null) {
            return null;
        }

        return BeanUtil.toBean(dto, ${entity}.class);
    }

}
</#if>
