import { apiInstance } from "./index.js";

const api = apiInstance();

// [POST] 오늘의 Quiz로 출제할 경제 단어 가져오기
async function getQuizWords(userId, success, fail) {
  // console.log("#user - getQuizWords# params - userId: ", userId);

  await api
    .post(`/api/quiz/`, JSON.stringify(userId))
    .then(success)
    .catch(fail);
}

// [POST] chatGPT에게 유사 경제단어 질문
async function sendMessageWord(message, success, fail) {
  const info = { message: message };

  await api
    .post(`/api/chatGPT/askChatGPT`, JSON.stringify(info))
    .then(success)
    .catch(fail);
}

// [POST] Quiz 결과 저장 & 성공 시 경험치 획득
async function setQuizResult(info, success, fail) {
  await api
    .post(`/api/quiz/setResult`, JSON.stringify(info))
    .then(success)
    .catch(fail);
}

export { getQuizWords, sendMessageWord, setQuizResult };
