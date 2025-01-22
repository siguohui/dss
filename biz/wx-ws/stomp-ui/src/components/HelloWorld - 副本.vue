<template>
  <h1 ref="te">{{msg}}</h1>
  <div>
    <el-button :disabled="disable" @click="openSocket('1')" style="width: 200px">创建用户1连接</el-button>
    <el-button :disabled="disable" @click="openSocket('user2')" style="width: 200px">创建用户2连接</el-button>
    <el-input v-model="content" style="width: 200px"></el-input>
    <el-button @click="sendMessage1()" style="width: 200px">发送消息给用户1</el-button>
    <el-input v-model="content3" style="width: 200px"></el-input>
    <el-button @click="sendMessage2()" style="width: 200px">发送消息给用户2</el-button>
    <el-input v-model="content1" style="width: 200px">xxx</el-input>
    <el-button @click="sendNoAppMsg()" style="width: 200px">发送无app消息</el-button>

    <el-upload
        class="upload-demo"
        drag
        action="https://run.mocky.io/v3/9d059bf9-4660-45f2-925d-ce80ad6c4d15"
        multiple
    >
      <el-icon class="el-icon--upload"><Plus /></el-icon>
      <div class="el-upload__text">点击或拖拽文件到此处上传</div>
      <div class="el-upload__text">支持jpg、png、gif</div>
      <template #tip>
        <div class="el-upload__tip">
          jpg/png files with a size less than 500kb
        </div>
      </template>
    </el-upload>

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
      msg:"测试"
    };
  },
  created(){

  },
  //挂载阶段
  //将要挂载，即将渲染出真实DOM
  beforeMount() {
    // console.log(this.msg+'---------------------2--------------');
    // console.log(this.$refs.te+'----------------1-------------------');
  },
  //挂载完毕
  mounted() {
    // console.log(this.$refs.te);//拿到渲染的真实DOM
    // console.log(this.$el);//拿到el，模板
    console.log("%c%s", "color:red","el     : " + this.$el);
    openSocket(1);

  },
  methods:{
    openSocket(userId){
      let socket = new SockJS('http://localhost:8080/ws');
      // 获取STOMP子协议的客户端对象
      this.stompClient = Stomp.over(socket);
      this.stompClient.connect({},()=>{
        this.disable = true;
        this.$message({showClose: true, message: "连接成功", type: 'success'});
        // 订阅主题
        this.stompClient.subscribe("/topic/sz",(msg)=>{
          console.log(msg);
          this.msg = msg.body
          // this.$message({showClose: true, message: msg.body, type: 'success'});
          this.$message({showClose: true, message: msg.body, type: 'success'});
        })

        // 订阅无app主题，
        this.stompClient.subscribe("/user/60/message"+userId,(msg)=>{
          console.log(msg);
          this.$message({showClose: true, message: msg.body, type: 'success'});
        })

        // 订阅无app主题，
        this.stompClient.subscribe("/topic/noapp",(msg)=>{
          console.log(msg);
          this.$message({showClose: true, message: msg.body, type: 'success'});
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
