<script setup>
import {ref} from 'vue'
const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: '',
  fileName: '',
  size: '',
  url: '',
  path: ''
})

const formRef = ref()

/** 打开弹窗 */
const open = async (type, id) => {
  dialogVisible.value = true
  formType.value = type
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      // formData.value = await DeptApi.getDept(id)
    } finally {
      formLoading.value = false
    }
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  if (!formRef) return
  const valid = await formRef.value.validate()
  if (!valid) return
  // 提交请求
  formLoading.value = true
  try {
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    fileName: undefined,
    size: undefined,
    url: undefined,
    path: undefined
  }
  formRef.value?.resetFields()
}

</script>

<template>
  <Dialog v-model="dialogVisible" :title="dialogTitle">
    <el-form
        ref="formRef"
        v-loading="formLoading"
        :model="formData"
        :rules="formRules"
        label-width="80px"
    >
      <el-form-item label="文件名" prop="fileName">
        <el-input v-model="formData.fileName" placeholder="请输入文件名" />
      </el-form-item>
      <el-form-item label="大小" prop="size">
        <el-input-number v-model="formData.size" :min="0" controls-position="right" />
      </el-form-item>
      <el-form-item label="访问地址" prop="url">
        <el-input v-model="formData.url" maxlength="11" placeholder="请输入访问地址" />
      </el-form-item>
      <el-form-item label="存储目录" prop="path">
        <el-input v-model="formData.path" maxlength="50" placeholder="请输入存储目录" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>

<style scoped>

</style>
