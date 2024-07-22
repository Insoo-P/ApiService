<template>

  <div>
    <h1>Side Project</h1>
    <table border=1>
      <tr>
        <td><router-link to="/apiService1">1. 사업자등록번호 조회</router-link></td>
      </tr>
      <tr>
        <td><router-link to="/apiService2">2. Excel -> json 파일 변환</router-link></td>
      </tr>
      <tr>
        <td><router-link to="/apiService3">3. kakaoMap API</router-link></td>
      </tr>
      <tr>
        <td><router-link to="/apiService4">4. Web Crawler</router-link></td>
      </tr>
      <tr>
        <td><router-link to="/apiService5">5. Calendar</router-link></td>
      </tr>
      <tr>
        <td><router-link to="/apiService6">6. 3일 뒤 온도 조회</router-link></td>
      </tr>
      <tr>
        <td><router-link to="/apiService7">7. Email 인증</router-link></td>
      </tr>
      <tr>
        <td><router-link to="/apiService8">8. WebSoket(채팅방)</router-link></td>
      </tr>
      <tr>
        <td><router-link to="/apiService9">9. WebRTC(그림판)</router-link></td>
      </tr>
      <tr>
        <td><router-link to="/apiService10">10. 그림판 + 채팅방</router-link></td>
      </tr>
    </table>
  </div>
</template>

<script>
export default {
  name: 'MainPage',
}
</script>
