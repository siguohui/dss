<script setup>
import { get } from '@/utils/request';
</script>

<template>
  <div>
    <router-link to="/">Overview</router-link>
    <router-link to="/profile">Profile</router-link>
    <router-view></router-view>
    <el-button type="primary">主要按钮</el-button>
  </div>
</template>

<style scoped>

</style>
