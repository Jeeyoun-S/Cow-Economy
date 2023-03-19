import Vue from 'vue'
import VueRouter from 'vue-router'

import main from "@/router/modules/main";
import home from "@/router/modules/home";
import news from "@/router/modules/news";
import quiz from "@/router/modules/quiz";
import myPage from "@/router/modules/myPage";
import search from "@/router/modules/search";
import login from "@/router/modules/login";

Vue.use(VueRouter)

const routes = [
  ...main,
  ...home,
  ...news,
  ...quiz,
  ...myPage,
  ...search,
  ...login,
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});

router.beforeEach((to, from, next) => {
  // 인증이 필요한 페이지 확인
  if(to.matched.some(record => record.meta.requireAuth)) {
    // 로컬 스토리지에서 토큰 가져오기
    const token = localStorage.getItem('token');

    ///토큰이 있는지 확인
    if (token) {
      next();
    } else {
      next({ path: '/my-page', query: { redirect: to.fullPath} });
    }
  } else {
    next();
  }
});

export default router