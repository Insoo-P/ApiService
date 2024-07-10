<template>

<div class="container">
  <div class="panel active" style="background-image: url('https://images.unsplash.com/photo-1558979158-65a1eaa08691?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80')">
    <h3><router-link to="/apiService1">Click</router-link></h3>
  </div>
  <div class="panel" style="background-image: url('https://images.unsplash.com/photo-1572276596237-5db2c3e16c5d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1350&q=80')">
    <h3><router-link to="/apiService2">Click</router-link></h3>
  </div>
  <div class="panel" style="background-image: url('https://images.unsplash.com/photo-1507525428034-b723cf961d3e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1353&q=80')">
    <h3><router-link to="/apiService3">Click</router-link></h3>
  </div>
  <div class="panel" style="background-image: url('https://images.unsplash.com/photo-1551009175-8a68da93d5f9?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1351&q=80')">
    <h3><router-link to="/apiService4">Click</router-link></h3>
  </div>
</div>

  <!-- <div>
    <h1>Dynamic Component Loader</h1>
    <table>
      <tr>
        <td><router-link to="/apiService1">1</router-link></td>
        <td><router-link to="/apiService2">2</router-link></td>
        <td><router-link to="/apiService3">3</router-link></td>
        <td><router-link to="/apiService4">4</router-link></td>
      </tr>
    </table>
  </div> -->
</template>

<script>
export default {
  name: 'MainPage',
  mounted() {
    const panels = document.querySelectorAll('.panel')

    panels.forEach(panel => {
      panel.addEventListener('click', () => {
        this.removeActiveClasses(panels)
        panel.classList.add('active')
      })
    })
  },
  methods: {
    removeActiveClasses(panels) {
      panels.forEach(panel => {
        panel.classList.remove('active')
      })
    }
  }
}
</script>

<style>

* {
  box-sizing: border-box;
}

body {
  font-family: 'Muli', sans-serif;
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100vh;
  overflow: hidden;
  margin: 0;
}

.container {
  display: flex;
  width: 90vw;
}

.panel {
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  height: 80vh;
  border-radius: 50px;
  color: #fff;
  cursor: pointer;
  flex: 0.5;
  margin: 10px;
  position: relative;
  -webkit-transition: all 700ms ease-in;
}

.panel h3 {
  font-size: 24px;
  position: absolute;
  bottom: 20px;
  left: 20px;
  margin: 0;
  opacity: 0;
}

.panel.active {
  flex: 5;
}

.panel.active h3 {
  opacity: 1;
  transition: opacity 0.3s ease-in 0.4s;
}

@media (max-width: 480px) {
  .container {
    width: 100vw;
  }

  .panel:nth-of-type(4),
  .panel:nth-of-type(5) {
    display: none;
  }
}
</style>
