import request from '@/utils/request'

/**
 * 根据 ID 集合批量删除
 * @param ids
 * @returns {*}
 */
export function delBatch(ids) {
  return request({
    url: 'api/auth/online/del/batch',
    method: 'post',
    data: ids
  })
}

/**
 * 根据 params 条件，查询匹配条件的分页列表
 * @param params
 * @returns {*}
 */
export function page(params) {
  return request({
    url: 'api/auth/online/page',
    method: 'get',
    params
  })
}

/**
 * 根据 params 条件，导出符合查询条件的分页数据
 * @param params
 * @returns {*}
 */
export function exportPage(params) {
  return request({
    url: 'api/auth/online/export/page',
    method: 'get',
    responseType: 'blob',
    params
  })
}

export default { delBatch, page, exportPage }
