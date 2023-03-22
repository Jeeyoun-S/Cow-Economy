import Vue from 'vue'
import VueRouter from 'vue-router'

import main from "@/router/modules/main";
import home from "@/router/modules/home";
import news from "@/router/modules/news";
import quiz from "@/router/modules/quiz";
import myPage from "@/router/modules/myPage";
import search from "@/router/modules/search";

import store from "@/store";

Vue.use(VueRouter)

const routes = [
  ...main,
  ...home,
  ...news,
  ...quiz,
  ...myPage,
  ...search,
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});

router.beforeEach((to, from, next) => {
  const isLoggedIn = store.state.userStore.isLoggedIn;
  const requiresAuth = to.matched.some(record => record.meta.requiresAuth);

  if (requiresAuth && !isLoggedIn) {
    next("/my-age");
  } else {
    next();
  }
})
export default router