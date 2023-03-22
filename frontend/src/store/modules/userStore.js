// import { checkLoggedIn, getToken } from '@/api/user';
import { getToken } from '@/api/user';
import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const userStore = {
  namespaced: true,
  state: {
    isLoggedIn: false,
  },
  getters: {
    isLoggedIn: state => state.isLoggedIn,
  },
  mutations: {
    SET_IS_LOGGED_IN(state, value) {
      state.isLoggedIn = value;
      console.log("#SET_IS_LOGIN# isLogin 확인: ", state.isLogin);
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
  },
  modules: {},
}

export default userStore;