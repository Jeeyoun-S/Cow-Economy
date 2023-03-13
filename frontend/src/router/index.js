import Vue from "vue";
import VueRouter from "vue-router";

import main from "@/router/modules/main";
import home from "@/router/modules/home";
import news from "@/router/modules/news";
import quiz from "@/router/modules/quiz";
import myPage from "@/router/modules/myPage";
import search from "@/router/modules/search";

Vue.use(VueRouter);

const routes = [...main, ...home, ...news, ...quiz, ...myPage, ...search];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
