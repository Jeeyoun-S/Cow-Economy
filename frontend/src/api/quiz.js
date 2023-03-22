import { apiInstance } from "./index.js";

const api = apiInstance();

// [POST] 오늘의 Quiz로 출제할 경제 단어 가져오기
async function getQuizWords(userId, success, fail) {
  // console.log("#user - getQuizWords# params - userId: ", userId);

  await api
    .post(
      `${process.env.VUE_APP_API_BASE_URL}/api/quiz/`,
      JSON.stringify(userId)
    )
    .then(success)
    .catch(fail);
}

// [POST] 경험치 획득
async function getExp(userId, success, fail) {
  await api
    .post(`/quiz/getExp`, JSON.stringify(userId))
    .then(success)
    .catch(fail);
}

export { getQuizWords, getExp };
