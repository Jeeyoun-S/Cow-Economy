const mypageStore = {
  namespaced: true,
  state: {
    index: 0,
  },
  getters: {
    getIndex: (state) => {
      return state.index;
    },
  },
  mutations: {
    SET_INDEX: (state, index) => {
      state.index = index;
    },
  },
  actions: {
    // [@Method] index 증가 (Quiz index)
    increaseIndex({ commit }, value) {
      console.log("#21# index 확인: ", value);

      commit("SET_INDEX", value + 1);
    },
  },
  modules: {},
};

export default mypageStore;
