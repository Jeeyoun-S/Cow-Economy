import { apiInstance } from "../index";

// api instance 가져오기
const api = apiInstance();

//[GET /wordcloud] word cloud 가져오기
async function getWordCloud(success, fail) {
  await api.get(`/wordcloud`).then(success).catch(fail);
}

export { getWordCloud };
