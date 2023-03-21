import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const userStore = new Vuex.Store({
  state: {
    token: null,
    isLogin: false, 
  },
  getters: {
    getToken: (state) => {
      return state.token;
    }
  },
  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token;
    },
  },
  actions: {
    async executeKakaoToken({ commit }, code) {
      const kakaoInfo = {
        grant_type: ""
      }
    }
  },
  modules: {
  }
})

export default userStore;