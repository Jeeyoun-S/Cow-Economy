import { getSelection } from "@/common/function/textSelection";

const memoStore = {
  namespaced: true,
  state: {
    memoBtn: false,
    selectionText: null,
    selectionResult: {
      startIndex: null,
      endIndex: null,
      startRange: null,
      endRange: null
    },
    highlightReference: {
      endNode: null,
      startRange: null,
      endRange: null
    },
    otherMemoList: [],
    myMemoList: []
  },
  getters: {
    getMemoBtn(state) {
      return state.memoBtn;
    },
    getSelectionText(state) {
      return state.selectionText;
    },
    getSelectionResult(state) {
      return state.selectionResult;
    },
    getMyMemoList(state) {
      return state.myMemoList;
    },
    getOtherMemoList(state) {
      return state.otherMemoList;
    }
  },
  mutations: {
    UPDATE_MEMO_BTN(state) {
      state.memoBtn = !state.memoBtn;
    },
    UPDATE_REFERENCE(state, payload) {
      if (payload.text == null) state.selectionText = null
      else state.selectionText = payload.text.split("@@@");

      state.selectionResult["startIndex"] = payload.startIndex;
      state.selectionResult["endIndex"] = payload.endIndex;
      state.selectionResult["startRange"] = payload.startRange;
      state.selectionResult["endRange"] = payload.endRange;
    },
    UPDATE_MY_MEMO(state, payload) {
      state.myMemoList.push(payload);
    },
    UPDATE_PUBLIC_SCOPE(state, payload) {
      state.myMemoList[payload].memoPublicScope = !state.myMemoList[payload].memoPublicScope;
    },
    DELETE_MEMO(state, payload) {
      state.myMemoList.splice(payload, 1);
    }
  },
  actions: {
    changeMemoBtn({ commit }) {
      commit("UPDATE_MEMO_BTN");
    },
    getSelectionText({ commit }) {
      const selection = getSelection();
      if (selection != null) commit("UPDATE_REFERENCE", selection);
      commit("UPDATE_MEMO_BTN");
    },
    removeSelectionText({ commit }) {
      commit("UPDATE_REFERENCE", {
        "text": null,
        "startIndex": null,
        "endIndex": null,
        "startRange": null,
        "endRange": null,
      });
    },
    addMyMemo({ commit }, memo) {
      commit("UPDATE_MY_MEMO", memo);
    },
    updatePublicScope({ commit }, index) {
      commit("UPDATE_PUBLIC_SCOPE", index);
    },
    deleteMemo({ commit }, index) {
      commit("DELETE_MEMO", index);
    }
  },
  modules: {
  }
}

export default memoStore;