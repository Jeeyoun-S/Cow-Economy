import {searchNews} from '@/api/modules/article.js';
const newsStore = {
  namespaced: true,
  state: {
    beforeSearch: false,
    searchText: "",
    searched: false,
    news: [],
  },
  mutations: {
    SET_BEFORE_SEARCH(state, payload) {
      state.beforeSearch = payload;
    },
    SET_SEARCH_TEXT(state, payload) {
      console.log(payload);
      state.searchText = payload;
    },
    SET_SEARCHED(state, payload) {
      state.searched = payload;
    },
    SET_NEWS(state, news) {
      state.news = news;
    },
  },
  actions: {
    init({commit}){
      console.log("## 기사 초기화 동작");
      commit("SET_NEWS", []); 
      commit("SET_SEARCH_TEXT", "");
      commit("SET_SEARCHED", false);
      commit("SET_BEFORE_SEARCH", false);
    },
    setSearchText({ commit }, payload) {
      commit("SET_SEARCH_TEXT", payload);
    },
    setSearched({ commit }, payload) {
      commit("SET_SEARCHED", payload);
    },
    setBeforeSearch({ commit }, payload) {
      commit("SET_BEFORE_SEARCH", payload);
    },
    async setNews({commit}, param){
      console.log(param.keyword+ " " + param.lastArticleId);
      await searchNews(param,
        async({data}) => {
          if(data.data.lenth>0){
            // console.log(data.data);
            commit("SET_SEARCHED", true);
          }
          commit("SET_BEFORE_SEARCH", true);
          commit("SET_NEWS",data.data);
        }
      ),
      (error) => {
        console.log(error);
      }
    }
  },
  getters: {
    allNews(state) {
      return state.news;
    },
    searchNews(state) {
      if (!state.searchText || state.news === []) {
        return [];
      }
      // const searchTextLowerCase = state.searchText.toLowerCase();
      // return state.news.filter((newsItem) =>
      //   newsItem.articleTitle.toLowerCase().includes(searchTextLowerCase)
      // );
      return state.news;
    },
  },
};

export default newsStore;
