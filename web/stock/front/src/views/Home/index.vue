<script setup lang="ts">
import { config } from '@/axios/config'
import {type Ref, ref, watch} from 'vue'
import { useCssVar, useMouse, useMousePressed, useTextSelection, useWindowSize, useTitle, useStorage } from '@vueuse/core'

import { OnClickOutside } from '@vueuse/components'

const { x, y } = useMouse()
const { pressed } = useMousePressed()
const state = useTextSelection()
const { width, height } = useWindowSize()
// 动态设置页面标题
const title = ref('四国辉')
useTitle(title)
// 使用 localStorage 存储数据
const storedValue = useStorage('my-storage-key', '123')

const close = () => {
  console.log(el)
}

const themes = {
  defaultTheme: {
    '--sy-primary-bg-color': '#fff',
    '--sy-primary-title-color': '#000',
  },
  customTheme: {
    '--sy-primary-bg-color': '#000',
    '--sy-primary-title-color': '#fff',
  },
};
type Theme = typeof themes;
export type ThemeTypes = keyof Theme;
export type CssVarTypes = keyof Theme['defaultTheme'];
// export function useTheme(
//     el: HTMLElement | Ref<HTMLElement | null | undefined>,
//     type: Ref<ThemeTypes> = ref('defaultTheme')
// ) {
//   const colors: Record<CssVarTypes | string, Ref<any>> = {};
//
//   const switchColor = () => {
//     Object.keys(themes[type.value]).forEach((item) => {
//       colors[item].value = themes[type.value][item as CssVarTypes];
//     });
//     setCache('theme', type.value);
//   };
//
//   onMounted(() => {
//     Object.keys(themes.defaultTheme).forEach((item) => {
//       colors[item] = useCssVar(item, el);
//     });
//     switchColor();
//   });
//
//   return {
//     switchColor,
//   };
// }

const person : {name:'zhangsan',} = {
  name

</script>

<template>
  <OnClickOutside @trigger="close">
    <div>
      Click Outside of Me
    </div>
  </OnClickOutside>
  <div>
    <h2>Window Size</h2>
    <p>Width: {{ width }}</p>
    <p>Height: {{ height }}</p>
    <p>{{ pressed }}</p>
    <p>被鼠标选择的文字是：{{ state.text }}</p>
  </div>
  <div>
    <h2>Mouse Position</h2>
    <p>X: {{ x }}</p>
    <p>Y: {{ y }}</p>
  </div>
  <div>
    <h2>Page Title</h2>
    <input v-model="title" placeholder="Enter page title" />
  </div>
  <div>
    <h2>Local Storage</h2>
    <input v-model="storedValue" placeholder="Enter something to store" />
    <p>Stored Value: {{ storedValue }}</p>
  </div>

</template>

<style scoped>

</style>
