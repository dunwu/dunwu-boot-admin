<template>
  <el-dialog
    :close-on-click-modal="false"
    :before-close="crud.cancelCU"
    :visible.sync="crud.status.cu > 0"
    :title="crud.status.title"
    width="640px"
  >
    <el-form ref="form" :model="form"<#if table.enableValidate> :rules="rules"</#if> size="small" label-width="100px">
<#list table.formFields as field>
  <#if field.enableForm>
      <el-form-item label="<#if field.comment != ''>${field.comment}<#else>${field.propertyName}</#if>"<#if field.notNull> prop="${field.propertyName}"</#if>>
    <#if field.formType = 'Input'>
        <el-input v-model="form.${field.propertyName}" style="width: 90%" />
    <#elseif field.formType = 'Textarea'>
        <el-input v-model="form.${field.propertyName}" :rows="3" type="textarea" style="width: 90%" />
    <#elseif field.formType = 'Radio'>
          <#if (field.dictName)?? && (field.dictName)!="">
            <el-radio v-model="form.${field.propertyName}" v-for="item in dict.${field.dictName}" :key="item.id" :label="item.value">{{ item.label }}</el-radio>
          <#else>
            未设置字典，请手动设置 Radio
          </#if>
    <#elseif field.formType = 'Select'>
          <#if (field.dictName)?? && (field.dictName)!="">
          <el-select v-model="form.${field.propertyName}" filterable placeholder="请选择">
            <el-option
              v-for="item in dict.${field.dictName}"
              :key="item.id"
              :label="item.label"
              :value="item.value" />
          </el-select>
    <#else>
          未设置字典，请手动设置 Select
    </#if>
  <#else>
      <el-date-picker v-model="form.${field.propertyName}" type="datetime" style="width: 90%" />
  </#if>
      </el-form-item>
</#if>
</#list>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="crud.cancelCU">取消</el-button>
      <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {form} from '@crud/crud'

  const defaultForm = {<#if table.formFields??><#list table.formFields as field> ${field.propertyName}: null,</#list></#if> }
export default {
  name: '${table.formName}',
  mixins: [form(defaultForm)],
  data() {
    return {
      rules: {
<#list table.formFields as field>
  <#if field.enableValidate>
        ${field.propertyName}: [
    <#if field.validateType??>
          { required: true, trigger: 'blur', type: '${field.validateType}' }
    <#else>
          { required: true, message: '<#if field.comment != ''>${field.comment}</#if>不能为空', trigger: 'blur' }
    </#if>
        ],
  </#if>
</#list>
      }
    }
  }
}
</script>

<style scoped></style>
