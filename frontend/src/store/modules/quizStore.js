import {
  checkTodayQuizDone,
  getQuizWords,
  setQuizResult,
  sendMessageWord,
} from "@/api/quiz";
import { encrypt } from "@/store/js/crypto.js";

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
      store.commit("QUIZ_LOADING_STATUS", false);
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
      // # Back-end에서 현재 로그인 한 userId 사용함
      const info = {
        userId: 1,
      };

      await getQuizWords(
        info,
        async ({ data }) => {
          // i) 성공
          if (data.statusCode == 200) {
            // Quiz 제작
            const quiz = []; // Quiz
            const articleList = []; // Quiz 출제 시 선정한 기사 ID

            // 1) 가져온 경제용어로 문제 만들기
            for (const word of data.data) {
              const quizItem = new Object();
              const answers = new Object();
              const randomNum = Math.floor(Math.random() * (102 - 98) + 97); // 97-100 중 Random 숫자 뽑기 (for. 정답 자리), [a:97, b:98, c:99, d:100]
              // i) 문제, 정답 번호 setting
              // ! 문제 설명에 답 제거
              quizItem.question = word.economyWord.wordExpl.replaceAll(
                word.economyWord.word,
                "[ ]"
              );
              quizItem.correctAnswer = encrypt(
                String.fromCharCode(randomNum),
                process.env.VUE_APP_CRYPT_KEY
              );
              //
              // ii) 4지선다
              // - [호출] chatGPT로 유사한 단어 3개 가져오기
              await store.dispatch(
                "quizStore/excuteSendMessage",
                word.economyWord.word,
                {
                  root: true,
                }
              );
              // - 4지선다 setting
              let cnt = 0;
              for (let i = 97; i <= 100; i++) {
                if (i == randomNum) {
                  answers[String.fromCharCode(randomNum)] =
                    word.economyWord.word + "+";
                } else {
                  answers[String.fromCharCode(i)] = state.similarityWord[cnt];
                  cnt++;
                }
              }
              quizItem.answers = answers;
              quiz.push(quizItem);

              articleList.push(word.articleId);
            }
            // console.log("#21# quiz 확인: ", quiz);
            // console.log("#21# articleList 확인: ", articleList);
            await commit("SET_QUESTIONS", quiz);
            await commit("SET_SELECT_QUIZ_ARTICLE", articleList);
            // 이후 TodayQuizInfo 페이지에서 TodayQuiz 페이지로 이동
          }
          // ii) 사용자가 읽은 기사 내 경제 단어 7개 미만
          else {
            await commit("SET_QUESTIONS", [0]);
            await commit("SET_SELECT_QUIZ_ARTICLE", [0]);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    // [@Method] chatGPT에게 해당 경제 단어와 유사한 단어 3개 조회 질문
    async excuteSendMessage({ commit }, word) {
      // # for. quiz Loading 창
      store.commit("QUIZ_LOADING_STATUS", true);

      const message =
        "경제용어 " +
        word +
        "와 유사한 경제용어 3개 설명없이 단어만 1, 2, 3으로 출력해줘";

      await sendMessageWord(
        message,
        async ({ data }) => {
          // console.log("#21# chatGPT 질문 실행결과: ", data.data);
          // 경제단어 추출 [정규식 사용]
          const regex = /(?:\d\. )(.+?)(?=\(|\n|$)/g;
          var similarityWord = [];
          let match;
          while ((match = regex.exec(data.data)) !== null) {
            similarityWord.push(match[1]);
          }
          // 조회한 유사 경제단어 SET
          commit("SET_SIMILARITY_WORD", similarityWord);
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
      // # Back-end에서 현재 로그인한 userId로 사용함
      const info = {
        userId: 1,
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
