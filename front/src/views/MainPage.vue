<template>

  <div>
    <h1>Side Project</h1>
    <table border=1>
      <tr>
        <td><router-link to="/apiService1">사업자등록번호 조회</router-link></td>
      </tr>
      <tr>
        <td><router-link to="/apiService2">Excel -> json 파일 변환</router-link></td>
      </tr>
      <tr>
        <td><router-link to="/apiService3">kakaoMap API</router-link></td>
      </tr>
      <tr>
        <td><router-link to="/apiService4">Web Crawler</router-link></td>
      </tr>
      <tr>
        <td><router-link to="/apiService5">Calendar</router-link></td>
      </tr>
    </table>
  </div>
</template>

<script>
export default {
  name: 'MainPage',
}
</script>
