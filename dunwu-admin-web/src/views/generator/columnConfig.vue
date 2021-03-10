<template>
  <el-card class="box-card" shadow="never">
    <div slot="header" class="clearfix">
      <span class="role-span">{{ tableName }} 字段级别配置</span>
      <el-button
        :loading="genLoading"
        icon="el-icon-s-promotion"
        size="mini"
        style="float: right; padding: 6px 9px;"
        type="success"
        @click="toGenerate"
      >
        保存&生成
      </el-button>
      <el-button
        :loading="configLoading"
        icon="el-icon-check"
        size="mini"
        style="float: right; padding: 6px 9px;margin-right: 9px"
        type="primary"
        @click="saveColumnConfig"
      >
        保存
      </el-button>
      <el-tooltip class="item" effect="dark" content="数据库中表字段变动时使用该功能" placement="top-start">
        <el-button
          :loading="syncLoading"
          icon="el-icon-refresh"
          size="mini"
          style="float: right; padding: 6px 9px;"
          type="info"
          @click="sync"
        >
          同步
        </el-button>
      </el-tooltip>
    </div>
    <el-form size="small" label-width="90px">
      <el-table
        v-loading="loading"
        :data="data"
        :max-height="tableHeight"
        stripe
        size="small"
        style="width: 100%;margin-bottom: 15px"
      >
        <el-table-column prop="fieldName" label="字段名称" width="150px" />
        <el-table-column prop="comment" label="字段注释" width="150px" />
        <el-table-column prop="type" label="字段数据类型" width="100px" />
        <el-table-column prop="javaType" label="字段 Java 类型" width="100px" />
        <el-table-column prop="keyType" label="字段KEY类型" />
        <el-table-column align="center" label="非空" width="70px">
          <template slot-scope="scope">
            <!--所有的键必须不为空-->
            <el-checkbox
              v-if="data[scope.$index].keyType === null || data[scope.$index].keyType === ''"
              v-model="data[scope.$index].notNull"
            />
            <el-checkbox v-else v-model="data[scope.$index].notNull" disabled />
          </template>
        </el-table-column>
        <el-table-column align="center" label="出现在列表" width="70px">
          <template slot-scope="scope">
            <el-checkbox v-model="data[scope.$index].enableList" />
          </template>
        </el-table-column>
        <el-table-column align="center" label="出现在表单" width="70px">
          <template slot-scope="scope">
            <el-checkbox v-if="data[scope.$index].keyType !== 'PRI'" v-model="data[scope.$index].enableForm" />
            <el-checkbox v-else v-model="data[scope.$index].enableForm" disabled />
          </template>
        </el-table-column>
        <el-table-column align="center" label="出现在查询" width="70px">
          <template slot-scope="scope">
            <el-checkbox v-model="data[scope.$index].enableQuery" />
          </template>
        </el-table-column>
        <el-table-column align="center" label="允许排序" width="70px">
          <template slot-scope="scope">
            <el-checkbox v-model="data[scope.$index].enableSort" />
          </template>
        </el-table-column>
        <el-table-column align="center" label="允许校验" width="70px">
          <template slot-scope="scope">
            <el-checkbox v-model="data[scope.$index].enableValidate" />
          </template>
        </el-table-column>
        <el-table-column label="列表类型" width="150px">
          <template slot-scope="scope">
            <el-select
              v-if="data[scope.$index].enableList"
              v-model="data[scope.$index].listType"
              filterable
              class="edit-input"
              clearable
              size="mini"
              placeholder="请选择"
            >
              <el-option label="文本" value="Text" />
              <el-option label="图片" value="Image" />
              <el-option label="日期" value="Date" />
            </el-select>
            <el-select
              v-else
              v-model="data[scope.$index].listType"
              filterable
              class="edit-input"
              clearable
              disabled
              size="mini"
              placeholder="请选择"
            />
          </template>
        </el-table-column>
        <el-table-column label="表单类型" width="150px">
          <template slot-scope="scope">
            <el-select
              v-if="data[scope.$index].enableForm"
              v-model="data[scope.$index].formType"
              filterable
              class="edit-input"
              clearable
              size="mini"
              placeholder="请选择"
            >
              <el-option label="文本框" value="Input" />
              <el-option label="文本域" value="Textarea" />
              <el-option label="单选框" value="Radio" />
              <el-option label="下拉框" value="Select" />
              <el-option label="日期框" value="Date" />
            </el-select>
            <el-select
              v-else
              v-model="data[scope.$index].formType"
              filterable
              class="edit-input"
              disabled
              clearable
              size="mini"
              placeholder="请选择"
            />
          </template>
        </el-table-column>
        <el-table-column label="查询类型" width="150px">
          <template slot-scope="scope">
            <el-select
              v-if="data[scope.$index].enableQuery"
              v-model="data[scope.$index].queryType"
              filterable
              class="edit-input"
              clearable
              size="mini"
              placeholder="请选择"
            >
              <el-option label="普通查询" value="EQUALS" />
              <el-option label="范围查询" value="BETWEEN" />
            </el-select>
            <el-select
              v-else
              v-model="data[scope.$index].queryType"
              filterable
              class="edit-input"
              clearable
              disabled
              size="mini"
              placeholder="请选择"
            />
          </template>
        </el-table-column>
        <el-table-column label="排序类型" width="100px">
          <template slot-scope="scope">
            <el-select
              v-if="data[scope.$index].enableSort"
              v-model="data[scope.$index].sortType"
              filterable
              class="edit-input"
              clearable
              size="mini"
              placeholder="请选择"
            >
              <el-option label="升序" value="asc" />
              <el-option label="降序" value="desc" />
            </el-select>
            <el-select
              v-else
              v-model="data[scope.$index].sortType"
              filterable
              class="edit-input"
              clearable
              disabled
              size="mini"
              placeholder="请选择"
            />
          </template>
        </el-table-column>
        <el-table-column label="校验类型" width="150px">
          <template slot-scope="scope">
            <el-select
              v-if="data[scope.$index].enableValidate"
              v-model="data[scope.$index].validateType"
              filterable
              class="edit-input"
              clearable
              size="mini"
              placeholder="请选择"
            >
              <el-option label="string" value="string" />
              <el-option label="number" value="number" />
              <el-option label="boolean" value="boolean" />
              <el-option label="integer" value="integer" />
              <el-option label="float" value="float" />
              <el-option label="url" value="url" />
              <el-option label="email" value="email" />
              <el-option label="date" value="date" />
            </el-select>
            <el-select
              v-else
              v-model="data[scope.$index].sortType"
              filterable
              class="edit-input"
              clearable
              disabled
              size="mini"
              placeholder="请选择"
            />
          </template>
        </el-table-column>
        <el-table-column label="字段展示名称" width="150px">
          <template slot-scope="scope">
            <el-input v-model="data[scope.$index].propertyName" size="mini" class="edit-input" />
          </template>
        </el-table-column>
        <el-table-column label="日期格式" width="150px">
          <template slot-scope="scope">
            <el-input
              v-if="data[scope.$index].type === 'datetime'"
              v-model="data[scope.$index].datePattern"
              size="mini"
              class="edit-input"
            />
            <el-input v-else v-model="data[scope.$index].datePattern" disabled size="mini" class="edit-input" />
          </template>
        </el-table-column>
      </el-table>
    </el-form>
  </el-card>
</template>

<script>
import codeApi from '@/api/generator/codeApi'
export default {
  name: 'ColumnConfig',
  components: {},
  data() {
    return {
      schemaName: '',
      tableName: '',
      tableHeight: 550,
      data: [],
      dicts: [],
      loading: false,
      configLoading: false,
      syncLoading: false,
      genLoading: false
    }
  },
  created() {
    this.tableHeight = document.documentElement.clientHeight - 385
    this.dbId = this.$route.params.dbId
    this.tableName = this.$route.params.tableName
    this.schemaName = this.$route.params.schemaName
    this.$nextTick(() => {
      this.queryColumnConfig()
    })
  },
  methods: {
    queryColumnConfig() {
      this.loading = true
      codeApi
        .queryColumnConfig({ dbId: this.dbId, schemaName: this.schemaName, tableName: this.tableName })
        .then(data => {
          this.loading = false
          this.data = data
        })
        .catch(err => {
          this.loading = false
          this.$notify({ title: err, type: 'error' })
        })
    },
    saveColumnConfig() {
      this.configLoading = true
      codeApi
        .saveColumnConfig({
          dbId: this.dbId,
          schemaName: this.schemaName,
          tableName: this.tableName,
          columns: this.data
        })
        .then(res => {
          this.configLoading = false
          this.$notify({ title: '保存成功', type: 'success' })
        })
        .catch(err => {
          this.configLoading = false
          console.log(err.response.data.message)
        })
    },
    sync() {
      this.syncLoading = true
      codeApi
        .syncTables({ schemaName: this.schemaName, tables: [this.tableName] })
        .then(() => {
          this.queryColumnConfig()
          this.$notify({ title: '同步成功', type: 'success' })
          this.syncLoading = false
        })
        .then(() => {
          this.syncLoading = false
        })
    },
    toGenerate() {
      this.genLoading = true
      codeApi
        .saveColumnConfig({ schemaName: this.schemaName, tableName: this.tableName, columns: this.data })
        .then(res => {
          this.$notify({ title: '保存成功', type: 'success' })
          // 生成代码
          codeApi
            .generateCode({ schemaName: this.schemaName, tableName: this.tableName })
            .then(data => {
              this.$notify({ title: '生成代码成功', type: 'success' })
              this.genLoading = false
            })
            .catch(err => {
              this.$notify({ title: '生成代码失败', type: 'error', message: err })
              this.genLoading = false
            })
        })
        .catch(err => {
          this.genLoading = false
          this.$notify({ title: '保存失败', type: 'error', message: err })
        })
    }
  }
}
</script>

<style scoped></style>
