import { getSelection } from "@/common/function/textSelection";

const memoStore = {
  state: {
    memoBtn: false,
    selectionText: null,
    selectionResult: {},
    highlightReference: {
      endNode: null,
      startRange: null,
      endRange: null
    }
  },
  getters: {
    getMemoBtn(state) {
      return state.memoBtn;
    },
    getSelectioNText(state) {
      return state.selectionText;
    }
  },
  mutations: {
    UPDATE_MEMO_BTN(state) {
      state.memoBtn = !state.memoBtn;
    },
    UPDATE_REFERENCE(state, payload) {
      state.selectionText = payload.text;
      state.selectionResult["startIndex"] = payload.startIndex;
      state.selectionResult["endIndex"] = payload.endIndex;
      state.selectionResult["startRange"] = payload.startRange;
      state.selectionResult["endRange"] = payload.endRange;
      console.log("selectionResult", state.selectionResult);
    }
  },
  actions: {
    changeMemoBtn({ commit }) {
      commit("UPDATE_MEMO_BTN");
    },
    getSelectionText({ commit }) {
      commit("UPDATE_REFERENCE", getSelection());
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
    }
  },
  modules: {
  }
}

export default memoStore;