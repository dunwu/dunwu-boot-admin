<!--
  - 【公共按钮组件】搜索+重置
  -->

<!--搜索与重置-->
<template>
  <span style="width: 90%">
    <el-button size="mini" type="primary" icon="el-icon-search" class="filter-item" @click="crud.toQuery">
      查询
    </el-button>
    <el-button
      v-if="crud.optShow.reset"
      size="mini"
      type="primary"
      icon="el-icon-refresh-left"
      class="filter-item"
      @click="crud.resetQuery()"
    >
      重置
    </el-button>
  </span>
</template>

<script>
import { crud } from '@crud/crud'
export default {
  mixins: [crud()],
  props: {
    itemClass: {
      type: String,
      required: false,
      default: ''
    }
  }
}
</script>
