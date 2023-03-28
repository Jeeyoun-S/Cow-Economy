import { getToken, logOut } from "@/api/user";
import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

const userStore = {
  namespaced: true,
  state: {
    isLoggedIn: false,
    readNews: [
      ['2022-08', 8],
      ['2022-09', 20],
      ['2022-10', 14],
      ['2022-11', 9],
      ['2022-12', 12],
      ['2023-01', 5],
      ['2023-02', 12],
      ['2023-03', 23],
    ]
  },
  getters: {
    isLoggedIn: (state) => state.isLoggedIn,
    getLastSixMonthsReadNews: (state) => {
      const currentDate = new Date();
      const sixMonthsAgo = new Date(currentDate.setMonth(currentDate.getMonth() - 6));
      return state.readNews.filter(([month]) => {
        const date = new Date(`${month}-01`);
        return date >= sixMonthsAgo;
      });
    },
  },
  mutations: {
    SET_IS_LOGGED_IN(state, value) {
      state.isLoggedIn = value;
      console.log("#SET_IS_LOGIN# isLogin 확인: ", state.isLogin);
    },
    SET_IS_LOGGED_OUT(state, value) {
      state.isLoggedIn = value;
      console.log("#SET_IS_LOGIN# isLogin 확인: ", state.isLogin)
    }
  },
  actions: {
    async executeToken({ commit }) {
      await getToken(
        ({ data }) => {
          // jwt 토큰 localstorage에 저장
          if (data.statusCode == 200) {
            const ACCESS_TOKEN = data.data.accessToken;
            localStorage.setItem("access-token", ACCESS_TOKEN);

            commit("SET_IS_LOGGED_IN", true);

            console.log("토큰 발급 성공!");
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

    // 로그아웃
    async executeLogOut({ commit }) {
      await logOut(
        ({data}) => {
          if (data.statusCode == 200) {
            // 로그아웃 성공 처리
            localStorage.removeItem("access-token")
            commit("SET_IS_LOGGED_IN", false);
            window.location.replace("/my-page");
          } else {
            console.error("카카오 로그 아웃 실패");
          }
        }
      )
    }
  },
  modules: {},
};

export default userStore;