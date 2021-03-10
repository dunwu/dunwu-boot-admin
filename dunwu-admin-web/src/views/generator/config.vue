<template>
  <div class="app-container">
    <el-tabs v-model="activeName" type="card">
      <el-tab-pane label="全局级别配置" name="globalConfig">
        <globalConfig />
      </el-tab-pane>
      <el-tab-pane label="表级别配置" name="tableConfig">
        <tableConfig />
      </el-tab-pane>
      <el-tab-pane label="字段级别配置" name="columnConfig">
        <columnConfig />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import globalConfig from './globalConfig'
import tableConfig from './tableConfig'
import columnConfig from './columnConfig'

export default {
  name: 'GeneratorConfig',
  components: { globalConfig, tableConfig, columnConfig },
  data() {
    return {
      activeName: 'globalConfig'
    }
  },
  created() {},
  methods: {}
}
</script>

<style rel="stylesheet/scss" lang="scss">
.edit-input {
  .el-input__inner {
    border: 1px solid #e5e6e7;
  }
}
</style>

<style scoped>
::v-deep .input-with-select .el-input-group__prepend {
  background-color: #fff;
}
</style>
