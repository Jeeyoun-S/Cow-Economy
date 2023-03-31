import {
  checkTodayQuizDone,
  getQuizWords,
  setQuizResult,
  sendMessageWord,
} from "@/api/quiz";
// import { encrypt } from "@/store/js/crypto.js";

import store from "@/store/index.js";

const quizStore = {
  namespaced: true,
  state: {
    questions: [], // Quiz 문제
    similarityWord: [], // 경제단어와 유사한 단어 3개
    todayQuizFlag: false, // 금일 오늘의 Quiz 진행 여부
    index: 0, // Quiz index
    isPass: false, // Quiz 통과 여부
    experience: 0, // 사용자 경험치
    correctCount: 0, // 맞은 Quiz 개수
    selectQuizArticle: [], // Quiz로 출제하기 위해 선정한 기사 List
  },
  getters: {
    getQuestions: (state) => {
      return state.questions;
    },
    getSimilarityWord: (state) => {
      return state.similarityWord;
    },
    getIndex: (state) => {
      return state.index;
    },
    getExperience: (state) => {
      return state.experience;
    },
  },
  mutations: {
    SET_QUESTIONS: (state, questions) => {
      state.questions = questions;

      // # for. quiz Loading 창 닫기
      // store.commit("QUIZ_LOADING_STATUS", false);
    },
    SET_SIMILARITY_WORD: (state, similarityWord) => {
      state.similarityWord = similarityWord;
    },
    SET_INDEX: (state, index) => {
      state.index = index;
    },
    SET_TODAY_QUIZ_FLAG: (state, todayQuizFlag) => {
      state.todayQuizFlag = todayQuizFlag;
    },
    SET_ISPASS: (state, isPass) => {
      state.isPass = isPass;
    },
    SET_EXPERIENCE: (state, experience) => {
      state.experience = experience;
    },
    SET_CORRECTCOUNT: (state, correctCount) => {
      state.correctCount = correctCount;
    },
    SET_SELECT_QUIZ_ARTICLE: (state, selectQuizArticle) => {
      state.selectQuizArticle = selectQuizArticle;
    },
  },
  actions: {
    // [@Method] Quiz 도전 가능/불가능 판단
    async checkTodayQuiz({ commit }) {
      await checkTodayQuizDone(
        async ({ data }) => {
          if (data.statusCode == 200) {
            commit("SET_TODAY_QUIZ_FLAG", data.data);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    // [@Method] Quiz 문제 출제
    async setExamQuestions({ commit, state }) {
      // Quiz 문제 가져오기
      await getQuizWords(
        async ({ data }) => {

          // i) 성공
          let simiIndex = 0; // 4지선다 set 시 사용 (similarityWord index 번호)

          if (data.statusCode == 200) {
            console.log("# 문제 가져오기 #", data.data);
            const quiz = []; // Quiz
            const articleList = []; // Quiz 출제 시 선정한 기사 ID

            // 1) [호출] chatGPT로 유사한 단어 3개 가져오기 (for. 4지선다)
            //    - 성공 시 similarityWord 에 유사 경제용어 저장
            await store.dispatch("quizStore/excuteSendMessage", data.data, {
              root: true,
            });

            // 2) Quiz 제작
            for (const word of data.data) {
              const quizItem = new Object();
              const answers = new Object();
              const randomNum = Math.floor(Math.random() * (102 - 98) + 97); // 97-100 중 Random 숫자 뽑기 (for. 정답 자리), [a:97, b:98, c:99, d:100]

              // i) 문제 & 정답 번호 set
              // - 문제 저장 시 문제 있는 경제용어(답) [ ]으로 변경
              quizItem.question = word.economyWord.wordExpl.replaceAll(
                word.economyWord.word,
                "[ ]"
              );
              // - 문제 답 저장 시 user가 봐도 모르도록 암호화
              // quizItem.correctAnswer = encrypt(
              //   String.fromCharCode(randomNum),
              //   process.env.VUE_APP_CRYPT_KEY
              // );
              quizItem.correctAnswer = randomNum;

              // ii) 4지선다 set
              for (let i = 97; i <= 100; i++) {
                // 정답 (개발 중엔 + 표시를 붙여 정답을 알아볼 수 있도록 함)
                if (i == randomNum) {
                  answers[String.fromCharCode(randomNum)] =
                    word.economyWord.word + "+";
                }
                // 정답 외 단어
                else {
                  answers[String.fromCharCode(i)] =
                    state.similarityWord[simiIndex];
                  simiIndex++;
                }
              }
              quizItem.answers = answers;
              quiz.push(quizItem);

              articleList.push(word.articleId);
            }

            // 3) 완성한 Quiz 저장
            await commit("SET_QUESTIONS", quiz);
            await commit("SET_SELECT_QUIZ_ARTICLE", articleList);
            // 이후 TodayQuizInfo 페이지에서 TodayQuiz 페이지로 이동
            console.log("# index", state.index)
            console.log("# questions", state.questions)

            return Promise.resolve(true);
          }
          // ii) 사용자가 읽은 기사 내 경제 단어 7개 미만
          else {
            await commit("SET_QUESTIONS", [0]);
            await commit("SET_SELECT_QUIZ_ARTICLE", [0]);

            return Promise.resolve(false);
          }
        },
        (error) => {
          console.log(error);
        }
      );

    },
    // [@Method] chatGPT에게 해당 경제 단어와 유사한 단어 3개 조회 질문
    async excuteSendMessage({ commit }, words) {
      // # for. quiz Loading 창
      // store.commit("QUIZ_LOADING_STATUS", true);

      // Quiz로 출제할 경제용어 List
      const quizWord = [];
      for (const word of words) {
        quizWord.push(word.economyWord.word);
      }
      const info = {
        wordList: quizWord,
      };

      await sendMessageWord(
        info,
        async ({ data }) => {
          console.log(data.data);
          // S) 경제용어와 유사한 단어 similarityWord에 저장
          commit("SET_SIMILARITY_WORD", data.data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    // [@Method] index 증가 (Quiz index)
    increaseIndex({ commit }, value) {
      commit("SET_INDEX", value + 1);
    },
    // [@Method] Quiz 통과 여부 반영
    async setQuizResult({ commit, state }, correctAnswerCount) {
      commit("SET_CORRECTCOUNT", correctAnswerCount);

      // i) 통과
      if (correctAnswerCount >= 5) {
        commit("SET_ISPASS", true);
      }
      // ii) 실패
      else {
        commit("SET_ISPASS", false);
      }

      // [@Method] Quiz 결과 저장 & 성공 시 경험치 획득
      // # userId는 Back-end에서 현재 로그인한 userId로 사용함
      const info = {
        userId: 0,
        isPassFlag: state.isPass,
        selectArticleId: state.selectQuizArticle,
      };

      await setQuizResult(
        info,
        async ({ data }) => {
          if (data.data != null) commit("SET_EXPERIENCE", data.data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    // [@Method] Quiz 끝 + 초기화
    initQuiz({ commit }) {
      commit("SET_QUESTIONS", []);
      commit("SET_INDEX", 0);
      commit("SET_ISPASS", false);
      commit("SET_EXPERIENCE", 0);
      commit("SET_CORRECTCOUNT", 0);
    },
  },
  modules: {},
};

export default quizStore;
