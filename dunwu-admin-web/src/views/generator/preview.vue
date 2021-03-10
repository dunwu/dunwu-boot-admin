<template>
  <el-tabs v-model="activeName" type="card">
    <el-tab-pane v-for="item in data" :key="item.name" :lazy="true" :label="item.name" :name="item.name">
      <Java :value="item.content" :height="height" />
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import Java from '@/components/JavaEdit/index'
import codeApi from '@/api/generator/codeApi'
export default {
  name: 'Preview',
  components: { Java },
  data() {
    return {
      data: null,
      height: '',
      activeName: 'Entity'
    }
  },
  created() {
    this.height = document.documentElement.clientHeight - 180 + 'px'
    const schemaName = this.$route.params.schemaName
    const tableName = this.$route.params.tableName
    codeApi
      .previewCode({ schemaName, tableName })
      .then(data => {
        this.data = data
      })
      .catch(err => {
        this.$notify({ title: '预览代码失败', type: 'error', message: err })
        this.$router.go(-1)
      })
  }
}
</script>
