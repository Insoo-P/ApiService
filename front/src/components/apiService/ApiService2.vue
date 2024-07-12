<template>
  <div>
    <h2>엑셀 -> json파일 변환</h2>
    <input type="file" ref="fileInput" @change="handleFileChange">
    <button @click="uploadFile">Upload File</button>
    <div v-if="jsonResult">
      <h2>JSON Result</h2>
      <pre>{{ jsonResult }}</pre>
      <a :href="jsonFileUrl" download="result.json">Download JSON</a>
    </div>
  </div>
</template>

<script setup>
// 엑셀 파일 업로드 후 csv, json 파일로 변환
import { ref } from 'vue';
import axios from 'axios';

const fileInput = ref(null);
const file= ref(null)
const jsonResult = ref(null);
const jsonFileUrl = ref('');

const handleFileChange = (event) => {
  file.value = event.target.files[0];
};

const createBlobUrl = (data, type) => {
  const blob = new Blob([data], { type });
  return URL.createObjectURL(blob);
};

const uploadFile = async () => {
  
  file.value = fileInput.value.files[0];

  const formData = new FormData();
  formData.append('file', file.value); // 'file'은 서버에서 해당 파일을 받을 때 사용할 키

  try {
    const response = await axios.post('http://localhost:8081/api/excel/upload', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });

    
    console.log('File uploaded successfully:', response.data);
    // 업로드 성공 시 처리 로직 추가
    // Java 서버에서 받은 JSON 및 CSV 데이터 표시 및 다운로드 링크 설정
    jsonResult.value = response.data.jsonResult;
    jsonFileUrl.value = createBlobUrl(JSON.stringify(response.data.jsonResult), 'application/json');
  } catch (error) {
    console.error('Error uploading file:', error);
    // 업로드 실패 시 처리 로직 추가
  }
};

</script>
