const quizStore = {
  namespaced: true,
  state: {
    index: 0, // Quiz index
    isPass: false, // Quiz 통과 여부
    experience: 0, // 사용자 경험치
    correctCount: 0, // 맞은 Quiz 개수
  },
  getters: {
    getIndex: (state) => {
      return state.index;
    },
    getExperience: (state) => {
      return state.experience;
    },
  },
  mutations: {
    SET_INDEX: (state, index) => {
      state.index = index;
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
  },
  modules: {},
};

export default quizStore;
