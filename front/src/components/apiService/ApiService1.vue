<template>
    <h2>사업자등록번호 조회</h2>
    <form @submit.prevent="handleSubmit">
      <label for="brno">사업자번호:</label>
      <input id="brno" v-model="formData.brno" type="text" maxlength="10" required>
      <button type="submit">Submit</button>
      <p>{{ resultBrno }}</p>
    </form>
</template>

<script setup>
import { ref, inject } from 'vue'

const axios = inject('axios');
const resultBrno = ref('')
const formData = ref({
  brno: '',
})

const handleSubmit = () => {
  console.log('Submitted Data:', formData.value);

  const brno_arr = []
  brno_arr.push(formData.value.brno)
  console.log('proxy.$axios',axios)
  axios.post('/api/brno/data', {'b_no':brno_arr})
      .then(response => {
        resultBrno.value = response.data.data[0].tax_type
      })
      .catch(error => {
        console.error(error);
      });

};
</script>
