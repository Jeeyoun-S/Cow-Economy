import Vue from "vue";
import VueRouter from "vue-router";

import main from "@/router/modules/main";
import home from "@/router/modules/home";
import news from "@/router/modules/news";
import quiz from "@/router/modules/quiz";
import myPage from "@/router/modules/myPage";
import search from "@/router/modules/search";
import error from "@/router/modules/error";

import store from "@/store";

Vue.use(VueRouter);

const routes = [
  ...main,
  ...home,
  ...news,
  ...quiz,
  ...myPage,
  ...search,
  ...error,
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

// # for. 로그인 token 검증 후 로그인 안되었다면 my-page로 이동
router.beforeEach((to, from, next) => {
  const isLoggedIn = store.state.userStore.isLoggedIn;
  const requiresAuth = to.matched.some((record) => record.meta.requiresAuth);

  // 로그인 해야하는데 로그인 안되어 있을 시 my-page로 이동
  if (requiresAuth && !isLoggedIn) {
    next("/my-page");
  } else {
    next();
  }
});
export default router;
