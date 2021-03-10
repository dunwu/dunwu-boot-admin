<!--
  - Element UI 分页组件（Pagination）封装
  -->

<template>
  <el-Pagination
    :page-size.sync="page.size"
    :total="page.total"
    :current-page.sync="page.page"
    style="margin-top: 8px;"
    layout="total, sizes, prev, pager, next, jumper"
    @size-change="crud.sizeChangeHandler($event)"
    @current-change="crud.pageChangeHandler"
  />
</template>

<script>
import { Pagination } from '@crud/crud'
export default {
  mixins: [Pagination()]
}
</script>
