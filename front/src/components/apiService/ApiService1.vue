<template>
  <div>
    <h2>Component 1</h2>
    <p>This is content for Component 1.</p>
    {{ message }}
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'ApiService1',
  data() {
    return {
      message: ''
    }
  },
  created() {
    axios.get('http://localhost:8081/api/hello')
      .then(response => {
        this.message = response.data;
      })
      .catch(error => {
        console.error(error);
      });
  }
}
</script>
