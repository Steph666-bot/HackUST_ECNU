<template>
  <div class="layout">
    <div class="word">Pick</div>
    <div class="ball"></div>
    <div class="word">ne</div>
  </div>
</template>

<style scoped>
.layout {
  display: flex;
}
.word {
  font-size: 25px;
  font-weight: 700;
}
.ball {
  margin-top: 2px;
  margin-left: 2px;
  margin-right: 2px;
  display: inline-block;
  background: #165dff;
  width: 23px;
  height: 23px;
  border-radius: 50%;
}
</style>
