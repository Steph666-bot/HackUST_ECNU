<template>
  <div>
    <logo class="logo"></logo>
    <div class="page">
      <registerBox />
      <img src="..\..\assets\Image.png" />
    </div>
  </div>
</template>

<script>
import logo from "../../components/logoBox.vue";
import registerBox from "./components/registerBox.vue";

export default {
  components: {
    logo,
    registerBox,
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
