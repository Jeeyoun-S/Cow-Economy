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
    UPDATE_HOT_NEWS(state, news) {
      state.hotNews = news;
      state.hotNewsFlag = true;
    },
    UPDATE_CATEGORY_NEWS(state, news) {
      state.categoryNews = news;
      state.categoryNewsFlag = true;
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
    async updateHotNews({ commit }) {
      let result = await getTodayHotNews();
      commit("UPDATE_HOT_NEWS", result);
    },
    async updateTodayAllNews({ commit }) {
      let result = await getTodayAllNews();
      commit("UPDATE_CATEGORY_NEWS", result);
    },
  },
  modules: {},
};

export default mainStore;
