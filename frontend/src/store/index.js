import Vue from 'vue'
import Vuex from 'vuex'

import homeStore from "@/store/modules/homeStore.js";
import mainStore from "@/store/modules/mainStore.js";
import mypageStore from "@/store/modules/mypageStore.js";
import userStore from "@/store/modules/userStore.js";

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
  },
  getters: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    homeStore: homeStore,
    mainStore: mainStore,
    mypageStore: mypageStore,
    userStore: userStore,
  }
})
