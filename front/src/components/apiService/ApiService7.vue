<template>
  <h2>Email 인증</h2>
  <p>naver SMTP 사용</p>
  <div>
    <label>사용자 email(인증 할 이메일):</label>
    <input type="email" v-model="clientEmail">
    <label>smtpPassword:(임시)</label>
    <input type="password" v-model="smtpPassword">
    <button @click="sendEmailCode">Email 인증번호 보내기</button><br><br>
    <input type="text" v-model="clientEmailCode">
    <button @click="emailCodeValidation">Email 인증번호 확인</button>
  </div>
</template>

<script setup>
import { ref, inject } from 'vue';
const axios = inject('axios');

const emailCode = ref('')
const clientEmailCode = ref('')
const clientEmail = ref('')
const smtpPassword= ref('')

function sendEmailCode(){
  axios.post('/api/email/sendEmailCode', {clientEmail : clientEmail.value, smtpPassword : smtpPassword.value})
    .then(res => {
      alert('인증 번호를 이메일에 전달되었습니다.')
      console.log(res)
      console.log(res.data.emailCode)
      emailCode.value = res.data.emailCode
      console.log(emailCode.value)
    })
}

function emailCodeValidation(){
  if(clientEmailCode.value === emailCode.value){
    alert('인증 성공')
  }else{
    alert('인증 실패')
  }
}

</script>