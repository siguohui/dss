<script setup>
import { post, get, del } from '@/utils/request';
import {onMounted,reactive} from 'vue'
import {ElMessage} from "element-plus";
// onMounted(async () => {
//   console.log('挂载完毕');
//   const {data} = await get('/image/list', {page: 1, size: 10});
//   console.log(data);
// });

const state = reactive({
  tableData: {
    data: [],
    loading: false,
    param: {

    }
  },
});

const deleteFile =  async (id) => {
  const {code} = await del('/image/del/' + id);
  ElMessage.success('删除成功');
  const {data} = await get('/image/list');
  state.tableData.data = data;

}

const editUser = (row) => {
  console.log(row.id);
}


const getTableData = async () => {
  state.tableData.loading = true;
  state.tableData.data = [];
  const {data} = await get('/image/list');
  state.tableData.data = data;
  setTimeout(() => {
    state.tableData.loading = false;
  }, 500);
};

// 页面加载时
onMounted(() => {
  getTableData();
})

const tableRowClassName = ({rowIndex}) => {
  if (rowIndex === 1) {
    return 'warning-row'
  } else if (rowIndex === 3) {
    return 'success-row'
  }
  return ''
}
</script>

<template>
  <el-table :data="state.tableData.data"
            v-loading="state.tableData.loading"
            stripe border style="width: 1500px" :row-class-name="tableRowClassName">
    <el-table-column prop="fileName" label="fileName" width="300" />
    <el-table-column prop="size" label="大小" width="200" />
    <el-table-column prop="url" label="访问地址" width="400" >
      <template #default="{ row }">
        <el-button text type="primary">
          <el-image
              v-if="row.url && row.url.length != 0"
              style="width: 120px; height: 120px"
              :preview-teleported="true"
              :src="row.url"
              :zoom-rate="1.2"
              :max-scale="7"
              :min-scale="0.2"
              :preview-src-list="[row.url]"
              :initial-index="0"
              fit="cover"
          >
          </el-image>
          <span v-else>-</span>
        </el-button>
      </template>
    </el-table-column>
    <el-table-column prop="path" label="存储目录" width="400" align="center" />
    <el-table-column label="操作" width="200">
      <template #default="scope">
        <el-button  size="small" @click="editUser(scope.row)">编辑</el-button>
        <el-button  size="small" @click="deleteFile(scope.row.id)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<style scoped>
.el-table .warning-row {
  --el-table-tr-bg-color: var(--el-color-warning-light-9);
}
.el-table .success-row {
  --el-table-tr-bg-color: var(--el-color-success-light-9);
}

</style>
