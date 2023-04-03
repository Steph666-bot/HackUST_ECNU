import { createRouter, createWebHistory } from "vue-router";
import Client from "../pages/client/clientPage.vue";
import Admin from "../pages/admin/adminPage.vue";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: "/client",
      name: "Client",
      component: Client,
    },
    {
      path: "/admin",
      name: "Admin",
      component: Admin,
    },
  ],
});
