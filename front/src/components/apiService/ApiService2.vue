<template>
  <div>
    <h2>Component 2</h2>
    <input type="file" ref="fileInput" @change="handleFileChange">
    <button @click="uploadFile">Upload File</button>
  </div>
</template>

<script setup>
// 엑셀 파일 업로드 후 csv, json 파일로 변환
import { ref } from 'vue';
import axios from 'axios';

const fileInput = ref(null);
const file= ref(null)

const handleFileChange = (event) => {
  file.value = event.target.files[0];
  // 여기서 파일을 어떻게 처리할지 로직 추가 가능
};

const uploadFile = async () => {
  
  file.value = fileInput.value.files[0];

  const formData = new FormData();
  formData.append('file', file.value); // 'file'은 서버에서 해당 파일을 받을 때 사용할 키

  try {
    const response = await axios.post('http://localhost:8080/api/upload', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });
    console.log('File uploaded successfully:', response.data);
    // 업로드 성공 시 처리 로직 추가
  } catch (error) {
    console.error('Error uploading file:', error);
    // 업로드 실패 시 처리 로직 추가
  }
};

</script>
