<template>
  <div>
    <el-button :disabled="disable" @click="openSocket('user1')" style="width: 200px">创建用户1连接</el-button>
    <el-button :disabled="disable" @click="openSocket('user2')" style="width: 200px">创建用户2连接</el-button>
    <el-input v-model="content" style="width: 200px"></el-input>
    <el-button @click="sendMessage1()" style="width: 200px">发送消息给用户1</el-button>
    <el-input v-model="content3" style="width: 200px"></el-input>
    <el-button @click="sendMessage2()" style="width: 200px">发送消息给用户2</el-button>
    <el-input v-model="content1" style="width: 200px">xxx</el-input>
    <el-button @click="sendNoAppMsg()" style="width: 200px">发送无app消息</el-button>
  </div>
</template>

<script>
// import SockJS from 'sockjs-client'
import SockJS from 'sockjs-client/dist/sockjs.min.js';
import Stomp from 'stompjs'
export default {
  name: "TestWebsocket",
  data(){
    return {
      stompClient:'',
      content:"",
      content1:"",
      content3:"",
      disable: false,
    };
  },
  created(){
  },
  methods:{
    openSocket(){
      let socket = new SockJS('http://localhost:9000/test2');
      // 获取STOMP子协议的客户端对象
      this.stompClient = Stomp.over(socket);
      this.stompClient.connect({},()=>{
        this.disable = true;
        this.$message({showClose: true, message: "连接成功", type: 'success'});
        // 订阅主题
        this.stompClient.subscribe("/topic/msg",(msg)=>{
          console.log(msg);
          // this.$message({showClose: true, message: msg.body, type: 'success'});
          this.$message({showClose: true, message: JSON.parse(msg.body).message, type: 'success'});
        })

        // 订阅无app主题，
        this.stompClient.subscribe("/topic/noapp",(msg)=>{
          console.log(msg);
          this.$message({showClose: true, message: JSON.parse(msg.body).message, type: 'success'});
        })
      })
    },
    sendMessage1(){
      let obj = {
        userName:'user1',
        content:this.content
      }
      this.stompClient.send("/app/chat/message",{},JSON.stringify(obj))
      this.content = "";
    },
    sendMessage2(){
      let obj = {
        userName:'user2',
        content:this.content3
      }
      this.stompClient.send("/app/chat/message",{},JSON.stringify(obj))
      this.content = "";
    },
    sendNoAppMsg(){
      let obj = {
        userId:'dsx',
        message:this.content1
      }
      this.stompClient.send("/topic/noapp",{},JSON.stringify(obj))
      this.content1 = "";
    }
  }
}
</script>

<style scoped>

</style>
