import Vue from "vue";
import Vuex from "vuex";

// !store 값 유지: npm i --save vuex-persistedstate 설치 필요
import createPersistedState from "vuex-persistedstate";

import homeStore from "@/store/modules/homeStore.js";
import mainStore from "@/store/modules/mainStore.js";
import mypageStore from "@/store/modules/mypageStore.js";
import userStore from "@/store/modules/userStore.js";
import quizStore from "@/store/modules/quizStore.js";
import quizGptStore from "./modules/quizGptStore"; // Quiz 문제 출제 시 chatGPT를 사용하는 store

Vue.use(Vuex);

export default new Vuex.Store({
  state: {},
  getters: {},
  mutations: {},
  actions: {},
  modules: {
    homeStore: homeStore,
    mainStore: mainStore,
    mypageStore: mypageStore,
    userStore: userStore,
    quizStore: quizStore,
    quizGptStore: quizGptStore,
  },
  plugins: [
    createPersistedState({
      // ! localStorage에 저장할 store만을 path에 등록
      paths: ["quizStore"],
    }),
  ],
});
