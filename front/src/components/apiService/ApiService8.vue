<template>
  <h2>WebSocket Chat</h2>
  <div class="chat-container">
      <div class="chat-output" id="chatOutput">
          <!-- 채팅 메시지가 표시될 곳 -->
      </div>
      <input type="text" v-model="message" @keydown.enter="sendMessage(message)">
      <button @click="sendMessage(message)" >Send</button>
  </div>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue';

const message = ref('')
const socket = ref(null)

onMounted(() => {

  socket.value = new WebSocket("ws://localhost:8081/chat");
    // WebSocket이 연결되었을 때 호출되는 이벤트 핸들러
  socket.value.onopen = function(event) {
    console.log(event, "WebSocket 연결 성공!");
    sendMessage("접속했습니다.")
  };

  // 서버로부터 메시지를 받았을 때 호출되는 이벤트 핸들러
  socket.value.onmessage = function(event) {
    console.log("서버에서 메시지 받음: " + event.data);
    appendMessage("상대방: " + event.data);
  }

  // WebSocket 오류가 발생했을 때 호출되는 이벤트 핸들러
  socket.value.onerror = function(event) {
    console.error("WebSocket 오류 발생: " + event);
  };

})

onBeforeUnmount(() => {
  if (socket.value) {
    socket.value.send("나갔습니다")
    // WebSocket 연결이 닫힐 때 호출되는 이벤트 핸들러 등록
    socket.value.onclose = function(event) {
      console.log(event, "WebSocket 연결 닫힘");
      
    };
    // WebSocket 연결 종료
    socket.value.close();
  }
})

function sendMessage(msg){
  socket.value.send(msg); 
  message.value = ''
  appendMessage("나: " + msg);
}

function appendMessage(msg) {
    const chatOutput = document.getElementById("chatOutput");
    const messageElement = document.createElement("div");
    messageElement.innerText = msg;
    chatOutput.appendChild(messageElement);
    chatOutput.scrollTop = chatOutput.scrollHeight; // 맨 아래로 스크롤
}

</script>
<style>
.chat-container {
    width: 400px;
    background-color: #fff;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    padding: 20px;
}
.chat-output {
    height: 300px;
    overflow-y: scroll;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 5px;
    margin-bottom: 10px;
}
</style>