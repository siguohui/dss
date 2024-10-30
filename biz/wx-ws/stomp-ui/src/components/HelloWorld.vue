<template>
  <h1 ref="te">{{msg}}</h1>
  <div>{{stu.age}}</div>
</template>

<script setup>
import SockJS from 'sockjs-client/dist/sockjs.min.js';
import Stomp from 'stompjs'
import {ref, onMounted, onUnmounted, reactive, toRefs} from 'vue';
const msg = ref('')
const stu = reactive({
  "name":"xiaosi",
  "age":25
});

onMounted(() => {
  connect();
});

const connect = () => {
  let socket = new SockJS('http://localhost:8080/ws');
  // 获取STOMP子协议的客户端对象
  const stompClient = Stomp.over(socket);
  stompClient.connect({},()=>{
    // 订阅主题
    stompClient.subscribe("/topic/sz",(res)=>{
      msg.value = res.body
      // this.$message({showClose: true, message: msg.body, type: 'success'});
    })

    stompClient.subscribe("/app/sz",(res)=>{
      msg.value = res.body
      // this.$message({showClose: true, message: msg.body, type: 'success'});
    })
  })
};
</script>

<style scoped>

</style>
