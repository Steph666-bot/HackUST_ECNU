<template>
  <!-- <img alt="Vue logo" src="./assets/logo.png"> -->
  <!-- <login></login> -->
  <RouterView> </RouterView>
</template>

<script>
import login from "./pages/login/indexPage.vue";

export default {
  name: "App",
  components: {
    login,
  },
};
</script>

<style>
/* #app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
} */
</style>
