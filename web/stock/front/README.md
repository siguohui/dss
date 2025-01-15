# Vue 3 + TypeScript + Vite

This template should help get you started developing with Vue 3 and TypeScript in Vite. The template uses Vue 3 `<script setup>` SFCs, check out the [script setup docs](https://v3.vuejs.org/api/sfc-script-setup.html#sfc-script-setup) to learn more.

Learn more about the recommended Project Setup and IDE Support in the [Vue Docs TypeScript Guide](https://vuejs.org/guide/typescript/overview.html#project-setup).

npm install -g pnpm
pnpm create vite

配置eslint
pnpm i eslint -D
生成配置文件: .eslint.cjs
npx eslint --init


pnpm install element-plus
# 按需引入
pnpm install -D unplugin-vue-components unplugin-auto-import
在 Vite 的配置文件vite.config.ts中 添加如下代码

import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
