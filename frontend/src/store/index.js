import Vue from "vue";
import Vuex from "vuex";

// !store 값 유지: npm i --save vuex-persistedstate 설치 필요
import createPersistedState from "vuex-persistedstate";

import homeStore from "@/store/modules/homeStore.js";
import mainStore from "@/store/modules/mainStore.js";
import mypageStore from "@/store/modules/mypageStore.js";
import userStore from "@/store/modules/userStore.js";
import quizStore from "@/store/modules/quizStore.js";
import memoStore from "@/store/modules/memoStore.js";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    // # for. axios 통신 시 Loading 창
    loadingStatus: false,
    // # for. quiz Loading 창
    quizLoadingStatus: false,
  },
  getters: {},
  mutations: {
    // # for. axios 통신 시 Loading 창
    LOADING_STATUS: (state, loadingStatus) => {
      state.loadingStatus = loadingStatus;
    },
    // # for. quiz Loading 창
    QUIZ_LOADING_STATUS: (state, quizLoadingStatus) => {
      state.quizLoadingStatus = quizLoadingStatus;
    },
  },
  actions: {},
  modules: {
    homeStore: homeStore,
    mainStore: mainStore,
    mypageStore: mypageStore,
    userStore: userStore,
    quizStore: quizStore,
    memoStore: memoStore,
  },
  plugins: [
    createPersistedState({
      // ! localStorage에 저장할 store만을 path에 등록
      paths: ["quizStore"],
    }),
  ],
});
