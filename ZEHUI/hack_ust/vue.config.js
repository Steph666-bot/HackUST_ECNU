const { defineConfig } = require("@vue/cli-service");
module.exports = {
  transpileDependencies: true,
  devServer: {
    // host: "free.iule.net", //指定要使用的 host
    port: 8081, //指定端口号以侦听
    // hotOnly: false, //启用热模块替换，而无需页面刷新作为构建失败时的回退。
  },
};

// defineConfig({
//   transpileDependencies: true,
// });
