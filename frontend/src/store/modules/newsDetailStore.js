import { getSelection } from "@/common/function/textSelection";

const newsDetailStore = {
  state: {
    memoBtn: false
  },
  getters: {
    getMemoBtn(state) {
      return state.memoBtn;
    }
  },
  mutations: {
    UPDATE_MEMO_BTN(state) {
      state.memoBtn = !state.memoBtn;
    }
  },
  actions: {
    changeMemoBtn({ commit }) {
      commit("UPDATE_MEMO_BTN");
    },
    getSelectionText({ commit }) {
      commit;
      commit("UPDATE_MEMO_BTN");
      getSelection()
    }
  },
  modules: {
  }
}

export default newsDetailStore;