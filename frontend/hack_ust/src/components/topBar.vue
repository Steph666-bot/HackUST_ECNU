<template>
  <div class="topBox">
    <logo class="logo"></logo>
    <div class="userBar">
      <div class="icon">{{ username[0] }}</div>
      <div class="username">{{ username }}</div>
    </div>
  </div>
</template>

<script>
import logo from "./logoBox.vue";

export default {
  props: {
    username: String,
  },
  components: {
    logo,
  },
};
</script>

<style scoped>
.topBox {
  width: 100%;
  height: 80px;
  background-color: #fff;
  box-shadow: 1px 1px 10px #ccc;
  margin: 0;
}
.logo {
  margin-top: 25px;
  margin-left: 20px;
}
.userBar {
  position: absolute;
  right: 20px;
  top: 0px;
  display: flex;
}
.icon {
  margin: 25px 10px 0 0;
  width: 30px;
  height: 30px;
  border-radius: 50%;
  background-color: #165dff;
  line-height: 26px;
  color: #fff;
  text-align: center;
  font-weight: bold;
  font-size: 20px;
}
.username {
  line-height: 80px;
}
</style>
