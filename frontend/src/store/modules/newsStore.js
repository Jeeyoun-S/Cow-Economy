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
        article_title:
          "미국 상업용부동산 대출 7280조원, 은행 이어 또 다른 금융위험",
        article_content: "기사 내용",
        article_thumbnail:
          "https://imgnews.pstatic.net/image/025/2023/03/27/0003268541_001_20230327063201095.jpg?type=w647",
        article_hits: 1,
      },
      {
        articleId: 2,
        article_category: "금융",
        article_regtime: "2023-10-23 12:12:12",
        article_editor: "박싸피 기사",
        article_press: "엄희정",
        article_title:
          "中, 글로벌 CEO 100명 모아놓고 “공급망 단절 안돼” 美 공격",
        article_content: "기사 내용2",
        article_thumbnail:
          "https://imgnews.pstatic.net/image/020/2023/03/27/0003487611_001_20230327031504004.jpg?type=w647",
        article_hits: 2,
      },
      {
        articleId: 3,
        article_category: "산업/재계",
        article_regtime: "2023-10-22 12:12:12",
        article_editor: "김싸피 기사",
        article_press: "지윤",
        article_title:
          "AI투자 플랫폼 ‘탱고픽’ 운영사 알파브릿지, ChatGPT 연동 ‘탱고픽 AI Chat’ 서비스 출시…AI와의 대화를 통한 투자 종목 탐색",
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
    categoryNews: [], // 선택한 Category News
  },
  mutations: {
    setSearchText(state, payload) {
      state.searchText = payload;
    },
    setSearched(state, payload) {
      state.searched = payload;
    },
    SET_NEWS(state, news) {
      state.news = news;
    },
    SET_CATEGORY_NEWS(state, category) {
      // 선택한 category news만을 set
      state.categoryNews = state.news.filter((article) =>
        article.article_category.includes(category)
      );
    },
  },
  actions: {
    setSearchText({ commit }, payload) {
      commit("setSearchText", payload);
    },
    setSearched({ commit }, payload) {
      commit("setSearched", payload);
    },
    // [@Method] News 저장 (for. 카테고리 별 정렬)
    setCategoryNews({ commit }, category) {
      commit("SET_CATEGORY_NEWS", category);
    },
  },
};

export default newsStore;
