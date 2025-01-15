<script setup lang="ts">
import { ref } from 'vue'

import ChildComponent from "@/components/ChildComponent.vue";
const refChild = ref<InstanceType<typeof ChildComponent>>(null);
interface IImgSize{
  width:number,
  height:number
}
const imgSize = ref<IImgSize>(null)
imgSize.value = {
  width:300,
  height:100
}

defineProps<{ msg: string }>()

const count = ref(0)
</script>

<template>
  <el-button type="primary">使用element-plus的第一个按钮</el-button>
  <img src="@/assets/1.png" />
  <ChildComponent ref="refChild" :size="imgSize"/>
</template>

<style scoped>
.read-the-docs {
  color: #888;
}
</style>
