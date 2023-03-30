import { getToken } from "@/api/user";
import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

const userStore = {
  namespaced: true,
  state: {
    isLoggedIn: false,
    readNews: [
      ["2022-08", 8],
      ["2022-09", 20],
      ["2022-10", 14],
      ["2022-11", 9],
      ["2022-12", 12],
      ["2023-01", 5],
      ["2023-02", 12],
      ["2023-03", 23],
    ],
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
  },
  modules: {},
};

export default userStore;
