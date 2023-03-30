import {
  getReadCategory,
} from "@/api/modules/mypage";

const newsStore = {
    namespaced: true,
    state: {
      searchText: "",
      searched: false,
      articleCntList: [], // 6개월 간 읽은 기사 수
      readCategoryList: [], // 경제 용어 카테고리
      news: [
        {
          articsleId: 1, 
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
          articleId: 5, 
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
      category: [
        ["금융", 2],
        ["증권", 12],
        ["산업/재계", 9],
        ["중기/벤처", 7],
        ["부동산", 2],
        ["글로벌 경제", 2],
        ["생활경제", 3],
        ["경제 일반", 5],
      ]
    },
    mutations: {
      setSearchText(state, payload) {
        state.searchText = payload;
      },
      setSearched(state, payload) {
        state.searched = payload;
      },
      SET_ARTICLE_CNT_LIST(state, articleCntList) {
        state.articleCntList = articleCntList; 
      },
      SET_READ_CATEGORY(state, readCategoryList) {
        console.log(readCategoryList)
        state.readCategoryList = readCategoryList;
        console.log("저장하니?")
        console.log(state.readCategoryList)
      },
    },
    actions: {
      setSearchText({ commit }, payload) {
        commit("setSearchText", payload);
      },
      setSearched({ commit }, payload) {
        commit("setSearched", payload);
      },
      // [@Method] 회원이 6개월 간 읽은 기사 개수 저장(set)
      setUserReadArticleCount({ commit }, articleList) {
        // console.log("#21# 읽은 기사 개수 SET: ", articleList)
        commit("SET_ARTICLE_CNT_LIST", articleList);
      },
      // [@Method] 경제 용어 카테고리 불러오기
      async fetchReadCategory({ commit }, year) {
        console.log("그럼 여기는");
        try {
          const data = await getReadCategory(year);
          console.log("성공 했니?");
          console.log(data.data);
          commit("SET_READ_CATEGORY", data.data);
        } catch (error) {
          console.error("Error in fetchReadCategory:", error);
        }
      }
    },
  };

  export default newsStore;