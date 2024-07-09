<template>
  <img alt="Vue logo" src="./assets/logo.png">
  <router-view></router-view>
  <button @click="goBack">뒤로 가기</button>
</template>

<script>

export default {
  name: 'App',
  methods: {
    goBack() {
      this.$router.go(-1)
    }
  }
}
</script>

