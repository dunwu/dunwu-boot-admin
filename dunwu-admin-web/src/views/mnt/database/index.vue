<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <el-row>
          <el-col :span="6">
            <el-input
              v-model="query.id"
              clearable
              placeholder="请输入ID"
              style="width: 90%;"
              class="filter-item"
              @keyup.enter.native="crud.toQuery"
            />
          </el-col>
          <el-col :span="6">
            <el-input
              v-model="query.name"
              clearable
              placeholder="请输入名称"
              style="width: 90%;"
              class="filter-item"
              @keyup.enter.native="crud.toQuery"
            />
          </el-col>
          <el-col :span="6">
            <el-input
              v-model="query.schemaName"
              clearable
              placeholder="请输入schema名"
              style="width: 90%;"
              class="filter-item"
              @keyup.enter.native="crud.toQuery"
            />
          </el-col>
          <template v-if="crud.showExtendSearch">
            <el-col :span="6">
              <el-input
                v-model="query.jdbcUrl"
                clearable
                placeholder="请输入jdbc连接"
                style="width: 90%;"
                class="filter-item"
                @keyup.enter.native="crud.toQuery"
              />
            </el-col>
            <el-col :span="6">
              <date-range-picker v-model="query.createTimeRange" class="date-item" style="width: 90%" />
            </el-col>
          </template>
          <el-col :span="6">
            <TableQueryOperation :crud="crud" />
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
      <TableOperation :permission="permission">
        <el-button
          slot="right"
          v-permission="['admin', 'database:add']"
          :disabled="!selectIndex"
          class="filter-item"
          size="mini"
          type="warning"
          icon="el-icon-upload"
          @click="execute"
        >
          执行脚本
        </el-button>
      </TableOperation>
    </div>
    <!--表单组件-->
    <eForm ref="execute" :database-info="currentRow" />
    <el-dialog
      append-to-body
      :close-on-click-modal="false"
      :before-close="crud.cancelCU"
      :visible.sync="crud.status.cu > 0"
      :title="crud.status.title"
      width="600px"
    >
      <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" style="width: 450px;" />
        </el-form-item>
        <el-form-item label="jdbc连接" prop="jdbcUrl">
          <el-input v-model="form.jdbcUrl" style="width: 400px;" />
          <el-button :loading="loading" type="success" @click="testDbConnection">测试</el-button>
        </el-form-item>
        <el-form-item label="账号" prop="username">
          <el-input v-model="form.username" style="width: 450px;" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password" style="width: 450px" />
        </el-form-item>
        <el-form-item label="schema名" prop="schemaName">
          <el-input v-model="form.schemaName" style="width: 450px" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="text" @click="crud.cancelCU">取消</el-button>
        <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
      </div>
    </el-dialog>

    <!--表格渲染-->
    <el-table
      ref="table"
      v-loading="crud.loading"
      :data="crud.data"
      highlight-current-row
      stripe
      @sort-change="crud.changeTableSort"
      @selection-change="handleCurrentChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column prop="name" width="130px" label="数据库名称" />
      <el-table-column prop="jdbcUrl" label="连接地址" />
      <el-table-column prop="username" width="200px" label="用户名" />
      <el-table-column prop="schemaName" width="200px" label="schema名" />
      <el-table-column prop="createTime" width="200px" label="创建日期" />
      <el-table-column
        v-if="checkPer(['admin', 'database:edit', 'database:del'])"
        label="操作"
        width="150px"
        align="center"
      >
        <template slot-scope="scope">
          <TableColumnOperation :data="scope.row" :permission="permission" />
        </template>
      </el-table-column>
    </el-table>

    <!--分页组件-->
    <Pagination />
  </div>
</template>

<script>
import databaseApi from '@/api/mnt/databaseApi'
import eForm from './execute'
import CRUD, { crud, form, header, presenter } from '@crud/crud'
import TableQueryOperation from '@crud/TableQueryOperation'
import TableOperation from '@crud/TableOperation'
import TableColumnOperation from '@crud/TableColumnOperation'
import Pagination from '@crud/Pagination'
import DateRangePicker from '@/components/DateRangePicker'

const defaultForm = { id: null, name: null, jdbcUrl: 'jdbc:mysql://', username: null, password: null }
export default {
  name: 'DataBase',
  components: { eForm, Pagination, TableOperation, TableQueryOperation, TableColumnOperation, DateRangePicker },
  cruds() {
    return CRUD({ title: '数据库', url: 'api/mnt/database', crudMethod: { ...databaseApi }})
  },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  data() {
    return {
      currentRow: {},
      selectIndex: '',
      databaseInfo: '',
      loading: false,
      permission: {
        add: ['admin', 'database:add'],
        edit: ['admin', 'database:edit'],
        del: ['admin', 'database:del']
      },
      rules: {
        name: [{ required: true, message: '请输入数据库名称', trigger: 'blur' }],
        jdbcUrl: [{ required: true, message: '请输入数据库连接地址', trigger: 'blur' }],
        username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入数据库密码', trigger: 'blur' }]
      }
    }
  },
  methods: {
    testDbConnection() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          this.loading = true
          databaseApi
            .testDbConnection(this.form)
            .then(res => {
              this.loading = false
              this.crud.notify(res ? 'success' : 'error', res ? '连接成功' : '连接失败')
            })
            .catch(() => {
              this.loading = false
            })
        }
      })
    },
    execute() {
      this.$refs.execute.dialog = true
    },
    handleCurrentChange(selection) {
      this.crud.selections = selection
      if (selection.length === 1) {
        const row = selection[0]
        this.selectIndex = row.id
        this.currentRow = row
      } else {
        this.currentRow = {}
        this.selectIndex = ''
      }
    }
  }
}
</script>

<style scoped></style>
