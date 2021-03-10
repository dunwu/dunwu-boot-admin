<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <el-input
          v-model="query.name"
          clearable
          size="small"
          placeholder="输入部门名称搜索"
          style="width: 200px;"
          class="filter-item"
          @keyup.enter.native="crud.toQuery"
        />
        <date-range-picker v-model="query.createTime" class="date-item" />
        <el-select
          v-model="query.enabled"
          clearable
          size="small"
          placeholder="状态"
          class="filter-item"
          style="width: 90px"
          @change="crud.toQuery"
        >
          <el-option v-for="item in enabledTypeOptions" :key="item.key" :label="item.display_name" :value="item.key" />
        </el-select>
        <TableQueryOperation />
      </div>
      <TableOperation :permission="permission" />
    </div>
    <!--表单组件-->
    <el-dialog
      append-to-body
      :close-on-click-modal="false"
      :before-close="crud.cancelCU"
      :visible.sync="crud.status.cu > 0"
      :title="crud.status.title"
      width="500px"
    >
      <el-form ref="form" inline :model="form" :rules="rules" size="small" label-width="80px">
        <el-form-item label="部门名称" prop="name">
          <el-input v-model="form.name" style="width: 370px;" />
        </el-form-item>
        <el-form-item label="部门权重" prop="weight">
          <el-input-number
            v-model.number="form.weight"
            :min="0"
            :max="999"
            controls-position="right"
            style="width: 370px;"
          />
        </el-form-item>
        <el-form-item label="顶级部门">
          <el-radio-group v-model="form.isTop" style="width: 140px">
            <el-radio label="1">是</el-radio>
            <el-radio label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="状态" prop="enabled">
          <el-radio
            v-for="item in dict['dept_status'].options"
            :key="item.id"
            v-model="form.enabled"
            :label="item.name"
          >
            {{ item.name }}
          </el-radio>
        </el-form-item>
        <el-form-item label="备注" prop="note">
          <el-input
            v-model="form.note"
            type="textarea"
            :autosize="{ minRows: 2, maxRows: 4 }"
            placeholder="请输入内容"
            style="width: 370px;"
          />
        </el-form-item>
        <el-form-item v-if="form.isTop === '0'" style="margin-bottom: 0;" label="上级部门" prop="pid">
          <treeselect
            v-model="form.pid"
            :load-options="loadDepts"
            :options="depts"
            style="width: 370px;"
            :searchable="false"
            :show-count="true"
            :default-expand-level="1"
          >
            <label
              slot="option-label"
              slot-scope="{ node, shouldShowCount, count, labelClassName, countClassName }"
              :class="labelClassName"
            >
              {{ node.label }}
              <span v-if="shouldShowCount" :class="countClassName">({{ count }})</span>
            </label>
          </treeselect>
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
      lazy
      :data="crud.data"
      :load="getDepts"
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
      row-key="id"
      @select="crud.selectChange"
      @select-all="crud.selectAllChange"
      @selection-change="crud.selectionChangeHandler"
    >
      <el-table-column :selectable="checkboxT" type="selection" width="55" />
      <el-table-column label="名称" prop="name" />
      <el-table-column label="排序" prop="weight" />
      <el-table-column label="状态" align="center" prop="enabled">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.enabled"
            :disabled="scope.row.id === 1"
            active-color="#409EFF"
            inactive-color="#F56C6C"
            @change="changeEnabled(scope.row, scope.row.enabled)"
          />
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建日期" />
      <el-table-column
        v-if="checkPer(['admin', 'dept:edit', 'dept:del'])"
        label="操作"
        width="130px"
        align="center"
        fixed="right"
      >
        <template slot-scope="scope">
          <TableColumnOperation
            :data="scope.row"
            :permission="permission"
            :disabled-dle="scope.row.id === 1"
            msg="确定删除吗,如果存在下级节点则一并删除，此操作不能撤销！"
          />
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import deptApi from '@/api/system/dept'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import { LOAD_CHILDREN_OPTIONS } from '@riophae/vue-treeselect'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import TableQueryOperation from '@crud/TableQueryOperation'
import TableOperation from '@crud/TableOperation'
import TableColumnOperation from '@crud/TableColumnOperation'
import DateRangePicker from '@/components/DateRangePicker'

const defaultForm = { id: null, name: null, isTop: '1', subCount: 0, pid: 0, weight: 999, enabled: 'true' }
export default {
  name: 'Dept',
  components: { Treeselect, TableOperation, TableQueryOperation, TableColumnOperation, DateRangePicker },
  cruds() {
    return CRUD({
      title: '部门',
      url: 'api/sys/dept',
      tableType: 'tree',
      crudMethod: { ...deptApi }
    })
  },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  /**
   * 设置数据字典
   */
  dicts: ['dept_status'],
  data() {
    return {
      depts: [],
      /**
       * 权限表达式
       */
      permission: {
        add: ['admin', 'dept:add'],
        edit: ['admin', 'dept:edit'],
        del: ['admin', 'dept:del']
      },
      /**
       * 表单校验规则
       */
      rules: {
        name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
        weight: [{ required: true, message: '请输入序号', trigger: 'blur', type: 'number' }]
      },
      enabledTypeOptions: [{ key: 'true', display_name: '正常' }, { key: 'false', display_name: '禁用' }]
    }
  },
  methods: {
    // 添加与编辑前做的操作
    [CRUD.HOOK.afterToCU](crud, form) {
      this.depts = []
      if (form.pid !== 0) {
        form.isTop = '0'
      } else if (form.id !== null) {
        form.isTop = '1'
      }
      form.enabled = `${form.enabled}`
      if (form.id != null) {
        this.getSuperiorTreeList(form.id)
      } else {
        this.depts.push({ id: 0, label: '顶级类目', children: null })
      }
    },
    getDepts(tree, treeNode, resolve) {
      const params = { pid: tree.id }
      setTimeout(() => {
        deptApi.treeList(params).then(res => {
          resolve(res)
        })
      }, 100)
    },
    getSuperiorTreeList(id) {
      console.log('getSuperiorTreeList id', id)
      deptApi.superiorTreeList(id).then(res => {
        const children = res.map(function(obj) {
          return obj
        })
        const depts = [{ id: 0, label: '顶级类目', children: children }]
        this.buildDepts(depts)
        this.depts = depts
      })
    },
    buildDepts(depts) {
      depts.forEach(data => {
        if (data.children) {
          this.buildDepts(data.children)
        }
        // if (data.hasChildren && !data.children) {
        //   data.children = null
        // }
      })
    },
    getAllEnableDepts() {
      deptApi.treeList({ enabled: true }).then(res => {
        this.depts = res.map(function(obj) {
          return obj
        })
      })
    },
    // 获取弹窗内部门数据
    loadDepts({ action, parentNode, callback }) {
      if (action === LOAD_CHILDREN_OPTIONS) {
        deptApi.treeList({ enabled: true, pid: parentNode.id !== 0 ? parentNode.id : null }).then(res => {
          parentNode.children = res.map(function(obj) {
            return obj
          })
          setTimeout(() => {
            callback()
          }, 100)
        })
      }
    },
    // 提交前的验证
    [CRUD.HOOK.afterValidateCU]() {
      if (this.form.pid !== 0 && this.form.pid === this.form.id) {
        this.$message({
          message: '上级部门不能为空',
          type: 'warning'
        })
        return false
      }
      if (this.form.isTop === '1') {
        this.form.pid = 0
      }
      return true
    },
    // 改变状态
    changeEnabled(data, val) {
      this.$confirm('此操作将 "' + this.dict.label.dept_status[val] + '" ' + data.name + '部门, 是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          deptApi
            .edit(data)
            .then(res => {
              this.crud.notify(CRUD.NOTIFICATION_TYPE.SUCCESS, this.dict.label.dept_status[val] + '成功')
            })
            .catch(err => {
              data.enabled = !data.enabled
              console.log(err.response.data.message)
            })
        })
        .catch(() => {
          data.enabled = !data.enabled
        })
    },
    checkboxT(row, rowIndex) {
      return row.id !== 1
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
::v-deep .vue-treeselect__control,
::v-deep .vue-treeselect__placeholder,
::v-deep .vue-treeselect__single-value {
  height: 30px;
  line-height: 30px;
}
::v-deep .el-input-number .el-input__inner {
  text-align: left;
}
</style>
