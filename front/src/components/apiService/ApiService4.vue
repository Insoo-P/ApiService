<template>
  <div>
    <h2>크롤링이 가능한 사이트인지 검사하고 크롤링 하시오</h2>
    <div>
      <p>예시 ) https://www.snu.ac.kr</p>
      <input v-model="validationUrl" placeholder="https://www.snu.ac.kr" type="text">
      <button @click="validationBtn">크롤링 검증 검색</button>
    </div>
    <div>
      <p>예시 ) https://www.snu.ac.kr/snunow/notice/genernal?sc=y</p>
      <p><a href="https://www.snu.ac.kr/snunow/notice/genernal?sc=y" target="_blank">여기에 있는 제목 목록을 크롤링 해옴</a></p>
      <input v-model="url" type="text" placeholder="https://www.snu.ac.kr/snunow/notice/genernal?sc=y">
      <button @click="submitBtn">크롤링 검색</button>
    </div>
    <div>Response Data : 제목 목록</div>
    <ul v-if="responseDataList.length > 0">
      <li v-for="data in responseDataList" :key="data">
        {{ data }}
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref, inject} from 'vue';
const axios = inject('axios');

const validationUrl = ref('https://www.snu.ac.kr')
const url = ref('https://www.snu.ac.kr/snunow/notice/genernal?sc=y')
const responseDataList = ref([])

function validationBtn(){
  verification(validationUrl.value)
}

async function fetchAndConvertToJson(url) {
  try{
    const response = await fetch(url);
    const text = await response.text();

    const lines = text.split('\n').map(line => line.trim()).filter(line => line);
    const jsonResult = {};
    let currentUserAgent = null;
    lines.forEach(line => {
      if (line.startsWith('User-agent:')) {
        currentUserAgent = line.split(': ')[0];
        jsonResult[currentUserAgent] = line.split(': ')[1];
      } else if (line.startsWith('Disallow:') && currentUserAgent) {
        currentUserAgent = line.split(': ')[0];
        jsonResult[currentUserAgent] = line.split(': ')[1];
      } else if (line.startsWith('Allow:') && currentUserAgent) {
        currentUserAgent = line.split(': ')[0];
        jsonResult[currentUserAgent] = line.split(': ')[1];
      }
    });
    return jsonResult
  } catch (error){
    console.error('Error fetching or processing robots.txt:', error);
  }
}

function addRobotsTxt(url) {
    // 만약 이미 URL 끝에 /robots.txt가 포함되어 있다면 그대로 반환
    if (url.endsWith('/robots.txt')) {
        return url;
    }
    
    // URL 끝에 /robots.txt를 추가하여 새로운 URL을 생성하여 반환
    return url + '/robots.txt';
}

async function verification(url){
  const verificationUrl = addRobotsTxt(url)
  const json = await fetchAndConvertToJson(verificationUrl);

  if (json['User-agent'] != null && json['User-agent'] == '*') {
    alert('웹크롤링 허용 가능한 url 입니다.')
  } else {
    alert('웹크롤링 허용 불가한 url 입니다.');
  }
}

function submitBtn(){
  axios.post('/api/crawler', {'url': url.value})
      .then(response => {
        responseDataList.value = response.data["제목"]
        console.log(response)
      })
      .catch(error => {
        console.error(error);
      });
}
</script>
