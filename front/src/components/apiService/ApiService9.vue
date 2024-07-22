<template>
    <h2>Component 9</h2>
    <button @click="resetCanvas">초기화</button><br>
    <canvas id="drawingCanvas" width="800" height="600" style="border: 1px solid black;"></canvas>
</template>

<script setup>
import { ref, onMounted } from 'vue';

const canvas = ref(null)
const ctx = ref(null)
const ws = ref(null)

onMounted(() => {
    canvas.value = document.getElementById('drawingCanvas')
    ctx.value = canvas.value.getContext('2d')
    ws.value = new WebSocket(`${process.env.VUE_APP_WS_URL}/drawing`)

    let drawing = false;

    // Canvas 마우스 이벤트 핸들러
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

    // WebSocket을 통해 그림 데이터 수신 및 캔버스 업데이트
    ws.value.onmessage = (event) => {
        const { x, y } = JSON.parse(event.data);
        draw(x, y);
    };

})


function resetCanvas(){
    ctx.value.clearRect(0, 0, canvas.value.width, canvas.value.height);
}  


// 그리기 함수
function draw(x, y) {
    ctx.value.lineWidth = 5;
    ctx.value.lineCap = 'round';
    ctx.value.strokeStyle = 'black';

    ctx.value.lineTo(x, y);
    ctx.value.stroke();
    ctx.value.beginPath();
    ctx.value.moveTo(x, y);
}

// WebSocket을 통해 그림 데이터 전송
function sendDrawingData(x, y) {
    const data = JSON.stringify({ x, y });
    ws.value.send(data);
}
</script>