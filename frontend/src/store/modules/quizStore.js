import { getQuizWords } from "@/api/quiz";

const quizStore = {
  namespaced: true,
  state: {
    // questions: [
    //   {
    //     question:
    //       "[ ]란 OECD 기준에 따라 가구를 소득 순으로 나열했을 때, 한가운데에 있는 가구소득(중위소득)의 50~150% 범위에 속한 가구를 뜻한다.",
    //     answers: {
    //       a: "중산층가구",
    //       b: "0.5인 가구",
    //       c: "중위소득",
    //       d: "4차 산업혁명",
    //     },
    //     correctAnswer: "a",
    //   },
    //   {
    //     question: "Q. 문제 - 2",
    //     answers: {
    //       a: "2000",
    //       b: "2001",
    //       c: "2002",
    //       d: "2003",
    //     },
    //     correctAnswer: "b",
    //   },
    //   {
    //     question: "Q. 문제 - 3",
    //     answers: {
    //       a: "3000",
    //       b: "3001",
    //       c: "3002",
    //       d: "3003",
    //     },
    //     correctAnswer: "c",
    //   },
    //   {
    //     question: "Q. 문제 - 4",
    //     answers: {
    //       a: "4000",
    //       b: "4001",
    //       c: "4002",
    //       d: "4003",
    //     },
    //     correctAnswer: "d",
    //   },
    //   {
    //     question: "Q. 문제 - 5",
    //     answers: {
    //       a: "5000",
    //       b: "5001",
    //       c: "5002",
    //       d: "5003",
    //     },
    //     correctAnswer: "a",
    //   },
    //   {
    //     question: "Q. 문제 - 6",
    //     answers: {
    //       a: "6000",
    //       b: "6001",
    //       c: "6002",
    //       d: "6003",
    //     },
    //     correctAnswer: "b",
    //   },
    //   {
    //     question: "Q. 문제 - 7",
    //     answers: {
    //       a: "7000",
    //       b: "7001",
    //       c: "7002",
    //       d: "7003",
    //     },
    //     correctAnswer: "c",
    //   },
    // ], // Quiz
    questions: [],
    todayQuizFlag: false, // 금일 오늘의 Quiz 진행 여부
    index: 0, // Quiz index
    isPass: false, // Quiz 통과 여부
    experience: 0, // 사용자 경험치
    correctCount: 0, // 맞은 Quiz 개수
  },
  getters: {
    getQuestions: (state) => {
      return state.questions;
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
      console.log("#21# SET_QUESTIONS: ", state.questions);
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
  },
  actions: {
    // [@Method] Quiz 문제 출제
    async setExamQuestions({ commit }) {
      // !FIX! 나중에 로그인 완료되면 현 login ID 붙이기
      const info = {
        userId: 1,
      };

      await getQuizWords(
        info,
        async ({ data }) => {
          console.log("#21# getQuizWords 실행결과: ", data);
          // i) 성공
          //if (data.message == `${process.env.VUE_APP_API_RESULT_SUCCESS}`) {
          if (data.statusCode == 200) {
            console.log("#21# Quiz 단어 가져오기 성공: ", data);

            // Quiz 출제
            const quiz = [
              {
                question:
                  "[ ]란 OECD 기준에 따라 가구를 소득 순으로 나열했을 때, 한가운데에 있는 가구소득(중위소득)의 50~150% 범위에 속한 가구를 뜻한다.",
                answers: {
                  a: "중산층가구",
                  b: "0.5인 가구",
                  c: "중위소득",
                  d: "4차 산업혁명",
                },
                correctAnswer: "a",
              },
              {
                question: "Q. 문제 - 2",
                answers: {
                  a: "2000",
                  b: "2001",
                  c: "2002",
                  d: "2003",
                },
                correctAnswer: "b",
              },
              {
                question: "Q. 문제 - 3",
                answers: {
                  a: "3000",
                  b: "3001",
                  c: "3002",
                  d: "3003",
                },
                correctAnswer: "c",
              },
              {
                question: "Q. 문제 - 4",
                answers: {
                  a: "4000",
                  b: "4001",
                  c: "4002",
                  d: "4003",
                },
                correctAnswer: "d",
              },
              {
                question: "Q. 문제 - 5",
                answers: {
                  a: "5000",
                  b: "5001",
                  c: "5002",
                  d: "5003",
                },
                correctAnswer: "a",
              },
              {
                question: "Q. 문제 - 6",
                answers: {
                  a: "6000",
                  b: "6001",
                  c: "6002",
                  d: "6003",
                },
                correctAnswer: "b",
              },
              {
                question: "Q. 문제 - 7",
                answers: {
                  a: "7000",
                  b: "7001",
                  c: "7002",
                  d: "7003",
                },
                correctAnswer: "c",
              },
            ]; // Quiz
            await commit("SET_QUESTIONS", quiz);
            // 이후 TodayQuizInfo 페이지에서 TodayQuiz 페이지로 이동
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    // [@Method] index 증가 (Quiz index)
    increaseIndex({ commit }, value) {
      //   console.log("#21# index 확인: ", value);
      commit("SET_INDEX", value + 1);
    },
    // [@Method] Quiz 통과 여부 반영
    setQuizResult({ commit }, correctAnswerCount) {
      //   console.log("#21# Quiz 통과 여부 확인: ", correctAnswerCount);
      commit("SET_CORRECTCOUNT", correctAnswerCount);

      if (correctAnswerCount >= 5) {
        commit("SET_ISPASS", true);
      } else {
        commit("SET_ISPASS", false);
      }
    },
    // [@Method] Quiz 끝 + 초기화
    initQuiz({ commit }) {
      commit("SET_QUESTIONS", []);
      commit("SET_INDEX", 0);
      commit("SET_TODAY_QUIZ_FLAG", true);
      commit("SET_ISPASS", false);
      commit("SET_EXPERIENCE", 0);
      commit("SET_CORRECTCOUNT", 0);
    },
  },
  modules: {},
};

export default quizStore;
