const mainStore = {
  namespaced: true,
  state: {
    isAllComplete: false,
    //word cloud
    words: null,
    wordsFlag: false,
    //인기기사

    //카테고리별
  },
  getters: {},
  mutations: {
    UPDATE_WORDCLOUD(state, words) {
      state.words = words;
      state.wordsFlag = true;
    },
  },
  actions: {
    updateWordCloud({ commit }, words) {
      commit("UPDATE_WORDCLOUD", words);
    },
  },
  modules: {},
};

export default mainStore;
