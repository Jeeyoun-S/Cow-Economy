import { getSelection } from "@/common/function/textSelection";

const memoStore = {
  namespaced: true,
  state: {
    memoBtn: false,
    done: false,
    selectionText: null,
    selectionResult: {
      startIndex: null,
      endIndex: null,
      startRange: null,
      endRange: null
    },
    newMemo: {
      memoId: null,
      isModify: false,
      memoContent: null,
      memoPublicScope: false,
      index: null
    },
    highlightReference: {
      endNode: null,
      startRange: null,
      endRange: null
    },
    reading: false
  },
  getters: {

  },
  mutations: {
    UPDATE_MEMO_BTN(state) {
      state.memoBtn = !state.memoBtn;
    },
    UPDATE_REFERENCE(state, payload) {
      if (payload.text == null) state.selectionText = null;
      else if (Array.isArray(payload.text)) state.selectionText = payload.text;
      else state.selectionText = payload.text.split("@@@");

      state.selectionResult["startIndex"] = payload.startIndex;
      state.selectionResult["endIndex"] = payload.endIndex;
      state.selectionResult["startRange"] = payload.startRange;
      state.selectionResult["endRange"] = payload.endRange;
    },
    // UPDATE_MY_MEMO(state, payload) {
    //   if (payload.sort == "최신순") {
    //     state.myMemoList.unshift(payload.memo);
    //   } else {
    //     state.myMemoList.push(payload.memo);
    //   }
    // },
    // UPDATE_PUBLIC_SCOPE(state, payload) {
    //   state.myMemoList[payload].memoPublicScope = !state.myMemoList[payload].memoPublicScope;
    // },
    // DELETE_MEMO(state, payload) {
    //   state.myMemoList.splice(payload, 1);
    // },
    UPDATE_NEW_MEMO(state, payload) {
      state.newMemo.isModify = payload.isModify;
      state.newMemo.memoContent = payload.memoContent;
      state.newMemo.memoPublicScope = payload.memoPublicScope;
      state.newMemo.memoId = payload.memoId;
      state.newMemo.index = payload.index;
    },
    // UPDATE_ONE_MY_MEMO(state, payload) {
    //   state.myMemoList[payload.index] = payload.newMemo;
    // }
    UPDATE_READING(state, payload) {
      state.reading = payload;
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
    updateSelectionText({ commit }, selection) {
      commit("UPDATE_REFERENCE", selection);
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
    // addMyMemo({ commit }, data) {
    //   commit("UPDATE_MY_MEMO", data);
    // },
    // updatePublicScope({ commit }, index) {
    //   commit("UPDATE_PUBLIC_SCOPE", index);
    // },
    deleteMemo({ commit }, index) {
      commit("DELETE_MEMO", index);
    },
    updateNewMemo({ commit }, memo) {
      commit("UPDATE_NEW_MEMO", memo);
    },
    updateReading({ commit }, reading) {
      commit("UPDATE_READING", reading)
    }
    // modifyMyMemo({ commit }, memo) {
    //   commit("UPDATE_ONE_MY_MEMO", memo);
    // }
  },
  modules: {
  }
}

export default memoStore;