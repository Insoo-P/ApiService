<template>
  <div>
    <h1>Dynamic Component Loader</h1>
    <table>
      <tr>
        <td><router-link to="/apiService1">1</router-link></td>
        <td><router-link to="/apiService2">2</router-link></td>
        <td><router-link to="/apiService3">3</router-link></td>
        <td><router-link to="/apiService4">4</router-link></td>
      </tr>
    </table>
    <router-view></router-view>
  </div>
</template>

<script>

export default {
  name: 'MainPage'
};
</script>
