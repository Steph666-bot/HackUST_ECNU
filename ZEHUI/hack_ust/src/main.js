import { createApp } from "vue";
import App from "./App.vue";
import { createRouter, createWebHistory } from "vue-router";
import Client from "./pages/client/clientPage.vue";
import ClientResult from "./pages/client/resultPage.vue";
import Admin from "./pages/admin/adminPage.vue";
import Login from "./pages/login/indexPage.vue";
import Register from "./pages/register/indexPage.vue";
import axios from "@/plugins/axiosInstance.js";
import JsonExcel from "vue-json-excel";

const router = createRouter({
  // 4. 内部提供了 history 模式的实现。为了简单起见，我们在这里使用 hash 模式。
  history: createWebHistory(),
  routes: [
    {
      path: "/",
      name: "Login",
      // component: () => import("./pages/login/indexPage.vue"),
      component: Login,
    },
    {
      path: "/register",
      name: "Register",
      component: Register,
    },
    {
      path: "/client/:username",
      name: "Client",
      component: Client,
    },
    {
      path: "/client/resultPage:username",
      name: "ClientResult",
      component: ClientResult,
    },
    {
      path: "/admin/:username",
      name: "Admin",
      component: Admin,
    },
  ], // `routes: routes` 的缩写
});

// 5. 创建并挂载根实例
const app = createApp(App);
//确保 _use_ 路由实例使
//整个应用支持路由。

app.use(router);
app.mount("#app");

// createApp(App).use(router).mount("#app");
app.config.globalProperties.$axios = axios; //配置axios的全局引用
