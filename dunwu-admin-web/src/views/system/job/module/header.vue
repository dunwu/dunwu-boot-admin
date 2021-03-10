<template>
  <div v-if="crud.props.searchToggle">
    <el-row>
      <el-col :span="6">
        <el-input
          v-model="query.name"
          clearable
          size="small"
          placeholder="输入岗位名称搜索"
          class="filter-item"
          style="width: 90%"
          @keyup.enter.native="crud.toQuery"
        />
      </el-col>
      <el-col :span="6">
        <el-select
          v-model="query.enabled"
          clearable
          size="small"
          placeholder="状态"
          class="filter-item"
          style="width: 90%"
          @keyup.enter.native="crud.toQuery"
        >
          <el-option v-for="item in dict.job_status.options" :key="item.code" :label="item.name" :value="item.code" />
        </el-select>
      </el-col>

      <template v-if="crud.showExtendSearch">
        <el-col :span="6">
          <date-range-picker v-model="query.createTime" class="date-item" style="width: 90%" />
        </el-col>
      </template>

      <el-col :span="6">
        <TableQueryOperation />
        <el-button v-if="crud.showExtendSearch" type="text" @click="crud.toggleExtendSearch">
          折叠
          <i class="el-icon-arrow-up el-icon--right" />
        </el-button>
        <el-button v-else type="text" @click="crud.toggleExtendSearch">
          展开
          <i class="el-icon-arrow-down el-icon--right" />
        </el-button>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { header } from '@crud/crud'
import TableQueryOperation from '@crud/TableQueryOperation'
import DateRangePicker from '@/components/DateRangePicker'
export default {
  components: { TableQueryOperation, DateRangePicker },
  mixins: [header()],
  props: {
    dict: {
      type: Object,
      required: true
    },
    permission: {
      type: Object,
      required: true
    }
  }
}
</script>

<style>
.el-dropdown-link {
  cursor: pointer;
  color: #409eff;
}
.el-icon-arrow-down {
  font-size: 12px;
}
</style>
