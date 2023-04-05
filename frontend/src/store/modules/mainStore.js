import { getWordCloud } from "@/api/modules/wordcloud";
import { getTodayHotNews, getTodayAllNews } from "@/api/modules/article";

const mainStore = {
  namespaced: true,
  state: {
    //word cloud
    words: null,
    wordsFlag: false,
    //인기기사
    hotNews: null,
    hotNewsFlag: false,
    //카테고리별
    categoryNews: null,
    categoryNewsFlag: false,
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
    async updateHotNews() {
      await getTodayHotNews();
    },
    async updateTodayAllNews() {
      await getTodayAllNews();
    },
  },
  modules: {},
};

export default mainStore;
