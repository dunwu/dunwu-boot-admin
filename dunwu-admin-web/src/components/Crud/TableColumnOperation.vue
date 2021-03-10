<!--
  - 表格行公共操作按钮组合，包含 [ 编辑、删除 ] 按钮。
  -->

<template>
  <div>
    <!--执行权限校验-->
    <div v-if="crud.enablePermission">
      <!--列表行记录操作按钮 - 编辑-->
      <el-button
        v-permission="permission.edit"
        :loading="crud.status.cu === 2"
        :disabled="disabledEdit"
        size="mini"
        type="text"
        @click="crud.toEdit(data)"
      >
        编辑
      </el-button>
      <el-divider direction="vertical" />
      <!--列表行记录操作按钮 - 删除-->
      <el-popover
        v-model="pop"
        v-permission="permission.del"
        placement="top"
        width="180"
        trigger="manual"
        @show="onPopoverShow"
        @hide="onPopoverHide"
      >
        <p>{{ msg }}</p>
        <div style="text-align: right; margin: 0">
          <el-button size="mini" type="text" @click="doCancel">取消</el-button>
          <el-button
            :loading="crud.dataStatus[crud.getDataId(data)].delete === 2"
            type="primary"
            size="mini"
            @click="crud.doDelete(data)"
          >
            确定
          </el-button>
        </div>
        <el-button slot="reference" :disabled="disabledDle" size="mini" type="text" @click="toDelete">
          删除
        </el-button>
      </el-popover>
    </div>
    <!--不执行权限校验-->
    <div v-else>
      <!--列表行记录操作按钮 - 编辑-->
      <el-button
        :loading="crud.status.cu === 2"
        :disabled="disabledEdit"
        size="mini"
        type="text"
        @click="crud.toEdit(data)"
      >
        编辑
      </el-button>
      <el-divider direction="vertical" />
      <!--列表行记录操作按钮 - 删除-->
      <el-popover
        v-model="pop"
        placement="top"
        width="180"
        trigger="manual"
        @show="onPopoverShow"
        @hide="onPopoverHide"
      >
        <p>{{ msg }}</p>
        <div style="text-align: right; margin: 0">
          <el-button size="mini" type="text" @click="doCancel">取消</el-button>
          <el-button
            :loading="crud.dataStatus[crud.getDataId(data)].delete === 2"
            type="primary"
            size="mini"
            @click="crud.doDelete(data)"
          >
            确定
          </el-button>
        </div>
        <el-button slot="reference" :disabled="disabledDle" size="mini" type="text" @click="toDelete">
          删除
        </el-button>
      </el-popover>
    </div>
  </div>
</template>

<script>
import CRUD, { crud } from '@crud/crud'
export default {
  mixins: [crud()],
  props: {
    data: {
      type: Object,
      required: true
    },
    permission: {
      type: Object,
      default: () => {
        return {}
      }
    },
    disabledEdit: {
      type: Boolean,
      default: false
    },
    disabledDle: {
      type: Boolean,
      default: false
    },
    msg: {
      type: String,
      default: '确定删除本条数据吗？'
    }
  },
  data() {
    return {
      pop: false
    }
  },
  methods: {
    doCancel() {
      this.pop = false
      this.crud.cancelDelete(this.data)
    },
    toDelete() {
      this.pop = true
    },
    [CRUD.HOOK.afterDelete](crud, data) {
      if (data === this.data) {
        this.pop = false
      }
    },
    onPopoverShow() {
      setTimeout(() => {
        document.addEventListener('click', this.handleDocumentClick)
      }, 0)
    },
    onPopoverHide() {
      document.removeEventListener('click', this.handleDocumentClick)
    },
    handleDocumentClick(event) {
      this.pop = false
    }
  }
}
</script>
