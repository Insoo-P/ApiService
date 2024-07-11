<template>
  <button v-if="route.path !== '/'" @click="goBack">뒤로 가기</button>
  <!-- <img alt="Vue logo" src="./assets/logo.png"> -->
  <router-view></router-view>

</template>

<script setup>
import { useRouter, useRoute } from 'vue-router';

const router = useRouter();
const route = useRoute();

function goBack() {
  router.go(-1);
}
</script>

