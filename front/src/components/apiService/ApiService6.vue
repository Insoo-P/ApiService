<template>
  <h2>3일 ~ 10일 뒤까지 온도 조회</h2>
  <a href="https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15059468" target="_blank">공공마이데이터 기상청_중기예보 조회서비스</a>
  <ul v-if="countryList.length > 0">
    <li v-for="country in countryList" :key="country">
      <button value="country.regId" @click="weatherBtn(country.regId)">{{country.title}}</button>
    </li>
  </ul>

  <ul v-if="apiResponseData != null">
    <li>3일 뒤 최대 온도 : {{apiResponseData.taMax3}}</li>
    <li>3일 뒤 최소 온도 : {{apiResponseData.taMin3}}</li>
  </ul>
  
</template>

<script setup>
import { ref, onMounted, inject } from 'vue';
// import { axios } from 'axios'
const axios = inject('axios');

const countryList = ref([
  {title:'서울',regId:'11B10101'},
  {title:'인천',regId:'11B20201'},
  {title:'대전',regId:'11C20401'},
])
const apiResponseData = ref(null)

const serviceKey = ref('xqCmt1IwsBOcknFwJPerGVSfexBpw8S//lv9z9QjEEabqun4boY3K2yBFgFf9br7MwuyXhpga3Wzc1FcfT/nnA==');
const pageNo = ref(1);
const numOfRows = ref(10);
const dataType = ref('json');
const tmFc = ref('')

onMounted(() => {
  tmFc.value = getFormatDate()
})

function weatherBtn(regId){
  console.log(tmFc.value)
  axios.get(`https://apis.data.go.kr/1360000/MidFcstInfoService/getMidTa?serviceKey=${serviceKey.value}&pageNo=${pageNo.value}&numOfRows=${numOfRows.value}&dataType=${dataType.value}&regId=${regId}&tmFc=${tmFc.value}`)
    .then(res => {
      console.log(res)
      apiResponseData.value = res.data.response.body.items.item[0]
      console.log(apiResponseData.value)
    })
}

/**
 *  yyyyMMdd 포맷으로 반환
 */
 function getFormatDate(){
    // 현재 시각을 기준으로 Date 객체 생성
    const currentDate = new Date();

    // 현재 날짜에서 하루를 빼기 위해 24시간(1일)을 밀리초로 계산
    const oneDayInMillis = 24 * 60 * 60 * 1000;

    // 현재 날짜에서 하루 전의 날짜 구하기
    const previousDate = new Date(currentDate.getTime() - oneDayInMillis);

    // 날짜를 YYYYMMDD 형식의 문자열로 변환
    const year = previousDate.getFullYear();
    const month = String(previousDate.getMonth() + 1).padStart(2, '0'); // getMonth()는 0부터 시작하므로 +1 필요
    const day = String(previousDate.getDate()).padStart(2, '0');

    // YYYYMMDD 형식으로 반환
    return `${year}${month}${day}1800`;
}

</script>
