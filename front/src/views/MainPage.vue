<template>

  <div>
    <h1>API Service</h1>
    <table border=1>
      <tr>
        <td><router-link to="/apiService1">사업자등록번호</router-link></td>
      </tr>
      <tr>
        <td><router-link to="/apiService2">Excel -> json 파일 변환</router-link></td>
      </tr>
      <tr>
        <td><router-link to="/apiService3">3</router-link></td>
      </tr>
      <tr>
        <td><router-link to="/apiService4">4</router-link></td>
      </tr>
    </table>
  </div>
</template>

<script>
export default {
  name: 'MainPage',
}
</script>
