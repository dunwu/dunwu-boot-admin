<template>
  <div class="app-container">
    <div class="head-container">
      <el-row>
        <el-col :span="6">
          <el-select
            v-model="schemaName"
            clearable
            filterable
            remote
            placeholder="请输入Schema"
            style="width: 90%"
            :remote-method="querySchemas"
            :loading="schemaLoading"
            @change="selectSchema"
          >
            <el-option v-for="item in schemaOptions" :key="item.id" :label="item.schemaName" :value="item.id" />
          </el-select>
        </el-col>
        <el-col :span="6">
          <el-input
            v-if="schemaName !== '' && schemaName !== null"
            v-model="tableName"
            placeholder="请输入表名"
            style="width: 90%"
            @keyup.enter.native="queryTables"
          />
          <el-input
            v-else
            v-model="tableName"
            disabled
            placeholder="请输入表名"
            style="width: 90%"
            @keyup.enter.native="queryTables"
          />
        </el-col>
        <el-col :span="6">
          <span style="width: 90%">
            <el-button type="primary" icon="el-icon-search" @click="queryTables">
              查询
            </el-button>
            <el-button type="primary" icon="el-icon-refresh-left" @click="resetQuery">
              重置
            </el-button>
            <el-button type="info">
              <router-link :to="'/mnt/mnt/database/'">
                配置数据源
              </router-link>
            </el-button>
          </span>
        </el-col>
      </el-row>
    </div>
    <!--表格渲染-->
    <el-table ref="table" v-loading="loading" stripe :data="tables">
      <el-table-column type="selection" width="55" />
      <el-table-column :show-overflow-tooltip="true" prop="tableName" label="Table名称" />
      <el-table-column :show-overflow-tooltip="true" prop="comment" label="Table注释" />
      <el-table-column :show-overflow-tooltip="true" prop="engine" label="数据库引擎" />
      <el-table-column :show-overflow-tooltip="true" prop="coding" label="字符编码集" />
      <el-table-column prop="createTime" label="创建日期" />
      <el-table-column label="操作" width="160px" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button size="mini" style="margin-left: -1px; " type="text">
            <router-link
              :to="'/sys-tools/generator/config/' + dbId + '/' + scope.row.schemaName + '/' + scope.row.tableName"
            >
              配置
            </router-link>
          </el-button>
          <el-button size="mini" style="margin-left: -1px;" type="text">
            <router-link
              :to="'/sys-tools/generator/preview/' + dbId + '/' + scope.row.schemaName + '/' + scope.row.tableName"
            >
              预览
            </router-link>
          </el-button>
          <el-button type="text" style="margin-left: -1px; " size="mini" @click="toGenerate(scope.row)">
            生成
          </el-button>
          <el-button size="mini" style="margin-left: -1px; " type="text" @click="toDownload(scope.row)">
            下载
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-Pagination
      :page-size.sync="page.size"
      :total="page.total"
      :current-page.sync="page.page"
      style="margin-top: 8px;"
      layout="total, sizes, prev, pager, next, jumper"
      @size-change="sizeChangeHandler($event)"
      @current-change="pageChangeHandler"
    />
  </div>
</template>

<script>
import codeApi from '@/api/generator/codeApi'
import databaseApi from '@/api/mnt/databaseApi'
import { downloadFile } from '@/utils/index'

export default {
  name: 'GeneratorIndex',
  mixins: [],
  data() {
    return {
      loading: false,
      schemaLoading: false,
      syncLoading: false,
      dbId: null,
      schemaName: null,
      tableName: null,
      schemaOptions: [],
      tables: [],
      page: {
        page: 0,
        size: 10,
        total: 0
      }
    }
  },
  created() {
    this.querySchemas()
  },
  methods: {
    toGenerate(row) {
      // 生成代码
      codeApi.generateCode({ schemaName: row.schemaName, tableName: row.tableName }).then(data => {
        this.$notify({ title: '生成成功', type: 'success' })
      })
    },
    toDownload(row) {
      // 打包下载
      codeApi.downloadCode({ schemaName: row.schemaName, tableName: row.tableName }).then(data => {
        downloadFile(data, row.tableName, 'zip')
        this.$notify({ title: '下载成功', type: 'success' })
      })
    },
    toSync() {
      const tables = []
      this.crud.selections.forEach(val => {
        tables.push(val.tableName)
      })
      this.syncLoading = true
      codeApi
        .syncTables({ schemaName: this.schemaName, tables })
        .then(() => {
          this.crud.refresh()
          this.syncLoading = false
          this.$notify({ title: '同步成功', type: 'success' })
        })
        .then(() => {
          this.syncLoading = false
        })
    },
    querySchemas(val) {
      this.schemaLoading = true
      if (val) {
        databaseApi.list({ schemaName: val }).then(res => {
          this.schemaOptions = res
          this.schemaLoading = false
          if (this.schemaOptions.length === 0) {
            this.$notify({ title: '未找到数据源', type: 'warning', message: '请配置数据源' })
          }
        })
      } else {
        databaseApi.list().then(res => {
          this.schemaOptions = res
          this.schemaLoading = false
          if (this.schemaOptions.length === 0) {
            this.$notify({ title: '未找到数据源', type: 'warning', message: '请配置数据源' })
          }
        })
      }
    },
    selectSchema(val) {
      console.log('selectSchema', val)
      if (val) {
        this.schemaOptions.forEach(i => {
          if (val === i.id) {
            this.dbId = i.id
            this.schemaName = i.schemaName
            this.queryTables(val)
          }
        })
      }
    },
    queryTables() {
      this.loading = true
      codeApi
        .getAllTableInSchema({
          schemaName: this.schemaName,
          tableName: this.tableName,
          page: this.page.page - 1,
          size: this.page.size
        })
        .then(res => {
          this.loading = false
          this.tables = res.content
          this.page.total = res.totalElements
        })
        .catch(err => {
          this.loading = false
          this.$notify({ title: '查询表数据失败', type: 'error', message: err })
        })
    },
    resetQuery() {
      this.schemaName = null
      this.tableName = null
      this.tables = []
      this.page.total = 0
    },
    // 当前页改变
    pageChangeHandler(val) {
      this.page.page = val
      this.queryTables()
    },
    // 每页条数改变
    sizeChangeHandler(val) {
      this.page.size = val
      this.page.page = 1
      this.queryTables()
    }
  }
}
</script>

<style scoped></style>
