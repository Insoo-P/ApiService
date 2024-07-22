<template>
  <h2>그림판 + 채팅방</h2>
  <button @click="resetCanvas">초기화</button><br>
  <canvas id="drawingCanvas" width="800" height="600" style="border: 1px solid black;"></canvas>

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

const canvas = ref(null)
const ctx = ref(null)
const ws = ref(null)

const message = ref('')
const socket = ref(null)

onMounted(() => {

  // 그림판 설정
  canvas.value = document.getElementById('drawingCanvas')
  ctx.value = canvas.value.getContext('2d')
  ws.value = new WebSocket(`${process.env.VUE_APP_WS_URL}/drawing`)

  let drawing = false;

  canvas.value.addEventListener('mousedown', () => {
      drawing = true;
  });

  canvas.value.addEventListener('mouseup', () => {
      drawing = false;
      ctx.value.beginPath();
  });

  canvas.value.addEventListener('mousemove', (event) => {
      if (drawing) {
          const x = event.clientX - canvas.value.offsetLeft;
          const y = event.clientY - canvas.value.offsetTop;
          draw(x, y);
          sendDrawingData(x, y);
      }
  });

  ws.value.onmessage = (event) => {
      const { x, y } = JSON.parse(event.data);
      draw(x, y);
  };


  
  // 채팅방 설정
  socket.value = new WebSocket(`${process.env.VUE_APP_WS_URL}/chat`);
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

/**
 * 그림판
 */

function resetCanvas(){
    ctx.value.clearRect(0, 0, canvas.value.width, canvas.value.height);
}  

function draw(x, y) {
    ctx.value.lineWidth = 5;
    ctx.value.lineCap = 'round';
    ctx.value.strokeStyle = 'black';

    ctx.value.lineTo(x, y);
    ctx.value.stroke();
    ctx.value.beginPath();
    ctx.value.moveTo(x, y);
}

function sendDrawingData(x, y) {
    const data = JSON.stringify({ x, y });
    ws.value.send(data);
}

/**
 * 채팅방
 */

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