import request from '@/utils/request'

/**
 * 添加一条记录
 * @param data
 * @returns {*}
 */
export function add(data) {
  return request({
    url: 'api/sys/menu/add',
    method: 'post',
    data
  })
}

/**
 * 根据 ID 集合批量删除
 * @param ids
 * @returns {*}
 */
export function delBatch(ids) {
  return request({
    url: 'api/sys/menu/del/batch',
    method: 'post',
    data: ids
  })
}

/**
 * 修改一条记录
 * @param data
 * @returns {*}
 */
export function edit(data) {
  return request({
    url: 'api/sys/menu/edit',
    method: 'post',
    data
  })
}

/**
 * 根据 params 条件，查询匹配条件的列表
 * @param params
 * @returns {*}
 */
export function list(params) {
  return request({
    url: 'api/sys/menu/list',
    method: 'get',
    params
  })
}

/**
 * 根据 params 条件，查询匹配条件的分页列表
 * @param params
 * @returns {*}
 */
export function page(params) {
  return request({
    url: 'api/sys/menu/page',
    method: 'get',
    params
  })
}

/**
 * 根据指定 id 列表，导出相应数据
 * @param ids
 * @returns {*}
 */
export function exportList(ids) {
  return request({
    url: 'api/sys/menu/export/list',
    method: 'post',
    responseType: 'blob',
    data: ids
  })
}

/**
 * 根据 params 条件，导出符合查询条件的分页数据
 * @param params
 * @returns {*}
 */
export function exportPage(params) {
  return request({
    url: 'api/sys/menu/export/page',
    method: 'get',
    responseType: 'blob',
    params
  })
}

/**
 * 根据 params 条件，返回树形列表
 * @param params
 */
export function treeList(params) {
  return request({
    url: 'api/sys/menu/treeList',
    method: 'get',
    params
  })
}

/**
 * 根据 params 条件，返回同级和上级的树形列表
 * @param idList
 */
export function superiorTreeList(idList) {
  const ids = idList.length || idList.length === 0 ? idList : Array.of(idList)
  return request({
    url: 'api/sys/menu/superiorTreeList',
    method: 'post',
    data: ids
  })
}

export function childrenIds(id) {
  return request({
    url: 'api/sys/menu/childrenIds?id=' + id,
    method: 'get'
  })
}

export function buildMenus() {
  return request({
    url: 'api/sys/menu/mine',
    method: 'get'
  })
}

export default { add, edit, delBatch, list, page, exportList, exportPage, treeList, superiorTreeList, childrenIds }
