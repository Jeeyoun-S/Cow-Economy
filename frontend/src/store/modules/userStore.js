import { getToken } from "@/api/user";
import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

const userStore = {
  namespaced: true,
  state: {
    isLoggedIn: false,
    // * graph에 필요한 data
    articleList: {
      articleCntList: [],
      readCategoryList: [],
      wordCategoryList: [],
    },
  },
  getters: {
    isLoggedIn: (state) => state.isLoggedIn,
    getLastSixMonthsReadNews: (state) => {
      const currentDate = new Date();
      const sixMonthsAgo = new Date(
        currentDate.setMonth(currentDate.getMonth() - 6)
      );
      return state.readNews.filter(([month]) => {
        const date = new Date(`${month}-01`);
        return date >= sixMonthsAgo;
      });
    },
  },
  mutations: {
    SET_IS_LOGGED_IN(state, value) {
      state.isLoggedIn = value;
      // console.log("#SET_IS_LOGIN# isLogin 확인: ", state.isLogin);
    },
    SET_ARTICLE_LIST(state, articleList) {
      state.articleList.articleCntList = articleList.articleCntList;
      state.articleList.readCategoryList = articleList.articleCategoryCnt;
      state.articleList.wordCategoryList = articleList.quizPassWordCategoryCnt;
      // console.log("articleList 저장확인", state.articleList.articleCntList);
    },
  },
  actions: {
    async executeToken({ commit }) {
      await getToken(
        ({ data }) => {
          // jwt acces-token localstorage에 저장
          if (data.statusCode == 200) {
            const ACCESS_TOKEN = data.data.accessToken;
            const REFRESH_TOKEN = data.data.refreshToken;

            localStorage.setItem("access-token", ACCESS_TOKEN);
            localStorage.setItem("refresh-token", REFRESH_TOKEN);

            // vuex 로그인 처리
            commit("SET_IS_LOGGED_IN", true);
            // console.log("토큰 발급 성공!");
            // my-page로 이동
            window.location.replace("/my-page");
          } else {
            console.error("토큰 발급 실패");
          }
        },
        (error) => {
          console.error(error);
        }
      );
    },
    logout({ commit }) {
      localStorage.removeItem("access-token");
      window.location.replace("/home");
      commit("SET_IS_LOGGED_IN", false);
    },
    // [@Method] 읽은 기사 수, 카테고리, 경제 용어 data 저장(set)
    setUserGraphData({ commit }, articleList) {
      console.log("#21# 그래프 data 확인: ", articleList);
      commit("SET_ARTICLE_LIST", articleList);
    },
  },
  modules: {},
};

export default userStore;
