<template>
  <div>
    <button @click="connect">Connect</button>
    <button @click="sendMessage" :disabled="!connected">Send Message</button>
    <button @click="disconnect" :disabled="!connected">Disconnect</button>
  </div>
</template>

<script setup>
import { ref,onMounted,onUnmounted } from 'vue';

const ws = ref(null);
const connected = ref(false);

onMounted(() => {
  connect();
});
onUnmounted(() => {
  disconnect();
})
const connect = () => {
  ws.value = new WebSocket('ws://192.168.16.90:8080/websocket/test');
  ws.value.onopen = () => {
    connected.value = true;
  };

  ws.value.onclose = () => {
    connected.value = false;
  };
  ws.value.onmessage = (event) => {
    // 处理收到的消息数据
    console.log('收到消息:', event.data);
  }
  ws.value.onerror = (error) => {
    console.error('Error:', error);
  };
};

const sendMessage = () => {
  if (ws.value && ws.value.readyState === WebSocket.OPEN) {
    ws.value.send('123');
  }
};

const disconnect = () => {
  if (ws.value) {
    ws.value.close();
    ws.value = null;
  }
};
</script>
