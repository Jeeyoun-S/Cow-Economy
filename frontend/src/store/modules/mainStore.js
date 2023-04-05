import { getWordCloud } from "@/api/modules/wordcloud";

const mainStore = {
  namespaced: true,
  state: {
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
    async updateWordCloud({ commit }) {
      await getWordCloud(
        ({ data }) => {
          if (data.message == "SUCCESS") {
            let temp = [];
            data.data.forEach((el) => {
              temp.push({ name: el.name, value: el.value });
            });
            commit("UPDATE_WORDCLOUD", temp);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
  modules: {},
};

export default mainStore;
