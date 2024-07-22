<template>
  <div>
    <h2>Component 3</h2>
    <p>서울특별시 근처 병원 목록 조회</p>
    <div id="map" style="width:100%;height:70vh;"></div>
    <p>예시 ) 서울특별시만 검색</p>
    <p>https://developers.kakao.com/console/app/824079/config/platform -> 안되면 여기서 사이트 도메인 확인</p>
    <input v-model="address" type="text">
    <button @click="test">검색</button>
  </div>
</template>

<script setup>

import axios from 'axios'
import { ref, onMounted } from 'vue';

const kakaoMap2 = ref(null)
const address= ref('')
// let kakaoMap2 = ''

onMounted(async()=>{
  const script = document.createElement('script');
  script.src = '//dapi.kakao.com/v2/maps/sdk.js?appkey=c234da2a33b61d33179e7f77f4550f43&autoload=false'
  document.head.append(script);
  
  script.onload = () => {
    window.kakao.maps.load(() => {
      console.log('windowKakao',window.kakao)
      let container = document.getElementById("map");
      const options = {
        // 지도를 생성할 때 필요한 기본 옵션
        center: new window.kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 3, // 지도의 레벨(확대, 축소 정도)
      };

      kakaoMap2.value = new window.kakao.maps.Map(container, options);
      console.log('kakaoMap' , kakaoMap2.value)
  })}
})


const test = () =>{
  axios.get(`https://dapi.kakao.com/v2/local/search/address.json?query=${address.value}`, {
    headers: { Authorization: 'KakaoAK 5f3d52c1b852cb00023d520266cdf503'}, })
      .then(response => {
        console.log(window.kakao.maps)
        console.log(response)
        console.log(response.data.documents[0])
        const resultData = response.data.documents[0]
        // console.log(kakaoMap.value)
        const moveLatLon = new window.kakao.maps.LatLng(resultData.y, resultData.x)
        console.log('moveLatLon', moveLatLon)
        kakaoMap2.value.setCenter(moveLatLon)
        test2(address.value)
      })
      .catch(error => {
        console.error(error);
      });
}

const test2 = (dutyAddr) => {
  axios.get(`https://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=xqCmt1IwsBOcknFwJPerGVSfexBpw8S%2F%2Flv9z9QjEEabqun4boY3K2yBFgFf9br7MwuyXhpga3Wzc1FcfT%2FnnA%3D%3D&Q0=${dutyAddr}&pageNo=1&numOfRows=10`)
    .then(response => {
      console.log(response.data.response.body.items.item)

      const responseQ1 = response.data.response.body.items.item
      for (let index = 0; index < responseQ1.length; index++) {
        displayMarker(responseQ1[index])
      }
    })
}

const displayMarker = (addr) => {
  var markerPosition  = new window.kakao.maps.LatLng(addr.wgs84Lat, addr.wgs84Lon)
      
    var marker = new window.kakao.maps.Marker({
      position: markerPosition,
    });
    marker.setMap(kakaoMap2.value);  
}
</script>
