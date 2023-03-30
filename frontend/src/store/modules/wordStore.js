const wordStore = {
  namespaced: true,
  state: {
    wordModal: null,
    isWordModalOpen: false,
    wordInfo: {}
  },
  getters: {
  },
  mutations: {
    UPDATE_IS_WORD_MODAL(state, payload) {
      state.isWordModalOpen = payload
    },
    UPDATE_WORD_INFO(state, payload) {
      state.wordInfo = payload
    }
  },
  actions: {
    setIsWordModal({ commit }, isOpen) {
      commit("UPDATE_IS_WORD_MODAL", isOpen)
    },
    setWordInfo({ commit }, wordInfo) {
      commit("UPDATE_WORD_INFO", wordInfo)
    }
  },
  modules: {
  }
}

export default wordStore;