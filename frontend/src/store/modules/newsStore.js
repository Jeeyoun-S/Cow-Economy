const newsStore = {
    namespaced: true,
    state: {
      searchText: "",
      searched: false,
      news: [
        {
          articleId: 1, 
          article_category: "부동산", 
          article_regtime: "2023-10-23 HH:MM:ss",
          article_editor: "김싸피 기사", 
          article_press: "싸피셜",
          article_title: "기사 제목", 
          article_content: "기사 내용", 
          article_thumnail: "기사 섬네일",
          article_hits: 11,
        },
        {
          articleId: 2, 
          article_category: "금융", 
          article_regtime: "2023-10-23 HH:MM:ss",
          article_editor: "박싸피 기사", 
          article_press: "싸피셜",
          article_title: "기사 제목2", 
          article_content: "기사 내용2", 
          article_thumnail: "기사 섬네일2",
          article_hits: 22,
        },
        {
          articleId: 3, 
          article_category: "산업/재계", 
          article_regtime: "2023-10-23 HH:MM:ss",
          article_editor: "김싸피 기사", 
          article_press: "싸피셜",
          article_title: "기사 제목3", 
          article_content: "기사 내용3", 
          article_thumnail: "기사 섬네일3",
          article_hits: 33,
        },
      ],
    },
    mutations: {
      setSearchText(state, payload) {
        state.searchText = payload;
      },
      setSearched(state, payload) {
        state.searched = payload;
      }
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