<template>
  <div>
    <div v-if="query.dictId === null">
      <div class="my-code">点击字典查看详情</div>
    </div>
    <div v-else>
      <!--工具栏-->
      <div class="head-container">
        <div v-if="crud.props.searchToggle">
          <!-- 搜索 -->
          <el-input
            v-model="query.code"
            clearable
            size="small"
            placeholder="输入字典编码查询"
            style="width: 200px;"
            class="filter-item"
            @keyup.enter.native="toQuery"
          />
          <TableQueryOperation />
        </div>
      </div>
      <!--表单组件-->
      <el-dialog
        append-to-body
        :close-on-click-modal="false"
        :before-close="crud.cancelCU"
        :visible="crud.status.cu > 0"
        :title="crud.status.title"
        width="500px"
      >
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="字典编码" prop="code">
            <el-input v-model="form.code" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="字典名称" prop="name">
            <el-input v-model="form.name" style="width: 370px;" />
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
        style="width: 100%;"
        @selection-change="crud.selectionChangeHandler"
      >
        <el-table-column prop="code" label="字典编码" />
        <el-table-column prop="name" label="字典名称" />
        <el-table-column
          v-if="checkPer(['admin', 'dict:edit', 'dict:del'])"
          label="操作"
          width="130px"
          align="center"
          fixed="right"
        >
          <template slot-scope="scope">
            <TableColumnOperation :data="scope.row" :permission="permission" />
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <Pagination />
    </div>
  </div>
</template>

<script>
import crudDictOption from '@/api/system/dictOption'
import CRUD, { presenter, header, form } from '@crud/crud'
import Pagination from '@crud/Pagination'
import TableQueryOperation from '@crud/TableQueryOperation'
import TableColumnOperation from '@crud/TableColumnOperation'

const defaultForm = { id: null, code: null, name: null }

export default {
  components: { Pagination, TableQueryOperation, TableColumnOperation },
  cruds() {
    return [
      CRUD({
        title: '字典详情',
        url: 'api/sys/dict/option',
        query: { dictId: null },
        sort: ['id,desc'],
        crudMethod: { ...crudDictOption },
        optShow: {
          add: true,
          edit: true,
          del: true,
          export: true,
          reset: false
        },
        queryOnPresenterCreated: false
      })
    ]
  },
  mixins: [
    presenter(),
    header(),
    form(function() {
      return Object.assign({ dict: { id: this.dictId }}, defaultForm)
    })
  ],
  data() {
    return {
      dictId: null,
      rules: {
        code: [{ required: true, message: '请输入字典编码', trigger: 'blur' }],
        name: [{ required: true, message: '请输入字典名称', trigger: 'blur' }]
      },
      permission: {
        add: ['admin', 'dict:add'],
        edit: ['admin', 'dict:edit'],
        del: ['admin', 'dict:del']
      }
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
::v-deep .el-input-number .el-input__inner {
  text-align: left;
}
</style>
