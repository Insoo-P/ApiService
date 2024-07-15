<template>
  <div>
    <h2>Component 3</h2>
    <p>This is content for Component 1.</p>
    <div id="map" ref="kakaoMap" style="width:500px;height:400px;"></div>
    <input v-model="address" type="text">
    <button @click="test">test</button>
  </div>
</template>

<script setup>

import { ref, onMounted } from 'vue';
import axios from 'axios';

const kakaoMap = ref(null)
const address= ref('')

onMounted(async()=>{
  const script = document.createElement('script');
  script.src = '//dapi.kakao.com/v2/maps/sdk.js?appkey=c234da2a33b61d33179e7f77f4550f43&autoload=false'
  document.head.append(script);
  
  script.onload = () => {
    window.kakao.maps.load(() => {
      console.log(window.kakao)
      let container = document.getElementById("map");
      const options = {
        // 지도를 생성할 때 필요한 기본 옵션
        center: new window.kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 3, // 지도의 레벨(확대, 축소 정도)
      };

      kakaoMap.value = new window.kakao.maps.Map(container, options);
  })}
})


const test = () =>{
  axios.get(`https://dapi.kakao.com/v2/local/search/address.json?query=${address.value}`, {
    headers: {Authorization: 'KakaoAK 5f3d52c1b852cb00023d520266cdf503'}, })
      .then(response => {
        console.log(window.kakao.maps)
        console.log(response)
        console.log(response.data.documents[0])
        const resultData = response.data.documents[0]
        // console.log(kakaoMap.value)
        kakaoMap.value.panTo(new window.kakao.maps.LatLng(resultData.y, resultData.x))
      })
      .catch(error => {
        console.error(error);
      });
}
</script>
