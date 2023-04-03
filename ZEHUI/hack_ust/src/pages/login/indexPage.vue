<template>
  <div>
    <logo class="logo"></logo>
    <div class="page">
      <loginBox />
      <img src="..\..\assets\Image.png" />
    </div>
  </div>
</template>

<script>
import logo from "../../components/logoBox.vue";
import loginBox from "./components/loginBox.vue";

export default {
  components: {
    logo,
    loginBox,
  },
};
</script>

<style scoped>
.logo {
  position: absolute;
  left: 20px;
  top: 20px;
}
.page {
  position: absolute;
  top: 0px;
  left: 0px;
  display: flex;
  width: 100%;
  height: 100%;
}
loginBox {
  width: 50%;
}
img {
  width: 50%;
  height: 100%;
}
</style>
