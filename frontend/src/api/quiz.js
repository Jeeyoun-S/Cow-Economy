import { apiInstance } from "./index.js";

const api = apiInstance();

// [POST] 오늘의 Quiz로 출제할 경제 단어 가져오기
async function getQuizWords(userId, success, fail) {
  // console.log("#user - getQuizWords# params - userId: ", userId);

  await api
    .post(
      `/api/quiz`,
      JSON.stringify(userId)
      // {userId: userId}
    )
    .then(success)
    .catch(fail);
}

export { getQuizWords };
