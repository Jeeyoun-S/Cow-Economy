const newsStore = {
  namespaced: true,
  state: {
    searchText: "",
    searched: false,
    news: [
      {
        articleId: 1,
        article_category: "부동산",
        article_regtime: "2023-10-24 12:12:12",
        article_editor: "김싸피 기사",
        article_press: "손승환",
        article_title: "기사 제목",
        article_content: "기사 내용",
        article_thumbnail: require("@/assets/images/mypage/quiz/book.png"),
        article_hits: 1,
      },
      {
        articleId: 2,
        article_category: "금융",
        article_regtime: "2023-10-23 12:12:12",
        article_editor: "박싸피 기사",
        article_press: "엄희정",
        article_title: "기사 제목2",
        article_content: "기사 내용2",
        article_thumbnail: require("@/assets/images/mypage/quiz/pencil.png"),
        article_hits: 2,
      },
      {
        articleId: 3,
        article_category: "산업/재계",
        article_regtime: "2023-10-22 12:12:12",
        article_editor: "김싸피 기사",
        article_press: "지윤",
        article_title: "기사 제목3",
        article_content: "기사 내용3",
        article_thumbnail: null,
        article_hits: 3,
      },
      {
        articleId: 4,
        article_category: "산업/재계",
        article_regtime: "2023-10-22 12:12:12",
        article_editor: "김싸피 기사",
        article_press: "동주",
        article_title: "기사 제목3",
        article_content: "기사 내용3",
        article_thumbnail: null,
        article_hits: 2,
      },
      {
        articleId: 4,
        article_category: "산업/재계",
        article_regtime: "2023-10-22 12:12:12",
        article_editor: "김싸피 기사",
        article_press: "도연",
        article_title: "기사 제목3",
        article_content: "기사 내용3",
        article_thumbnail: null,
        article_hits: 1,
      },
    ],
  },
  mutations: {
    setSearchText(state, payload) {
      state.searchText = payload;
    },
    setSearched(state, payload) {
      state.searched = payload;
    },
  },
  actions: {
    setSearchText({ commit }, payload) {
      commit("setSearchText", payload);
    },
    setSearched({ commit }, payload) {
      commit("setSearched", payload);
    },
  },
};

export default newsStore;
