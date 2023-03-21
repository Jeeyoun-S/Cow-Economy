import { sendMessageWord } from "@/api/chatGPT";

const quizGptStore = {
  namespaced: true,
  state: {
    similarityWord: [], // 경제단어와 유사한 단어 3개
  },
  getters: {
    getSimilarityWord: (state) => {
      return state.similarityWord;
    },
  },
  mutations: {
    SET_SIMILARITY_WORD: (state, similarityWord) => {
      state.similarityWord = similarityWord;
    },
  },
  actions: {
    // [@Method] chatGPT에게 해당 경제 단어와 유사한 단어 3개 조회 질문
    async excuteSendMessage({ commit }, word) {
      console.log("#21# chatGPT 질문 동작 word: ", word);
      const message =
        "경제용어 " + word + "와 유사한 경제용어 3개 설명없이 단어만 알려줘";
      console.log("#21# chatGPT 질문 동작 message: ", message);

      await sendMessageWord(
        message,
        async ({ data }) => {
          // console.log(
          //   "#21# chatGPT 질문 실행결과: ",
          //   data.choices[0].message.content
          // );
          // 경제단어 추출 [정규식 사용]
          const regex = /(?:\d\. )(.+?)(?=\n\d|\n|$)/g;
          var similarityWord = [];
          let match;
          while (
            (match = regex.exec(data.choices[0].message.content)) !== null
          ) {
            // console.log("#21# 단어 추출 확인: ", match[1]);
            similarityWord.push(match[1]);
          }
          console.log("#21# 유사 경제단어 확인: ", similarityWord);
          commit("SET_SIMILARITY_WORD", similarityWord);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    // [@Method] 유사 경제단어 조회
    getSimilarWordList() {},
  },
  modules: {},
};

export default quizGptStore;
