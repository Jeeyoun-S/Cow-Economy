import { apiInstance } from "../index";

// api instance 가져오기
const api = apiInstance();

//[GET /wordcloud] word cloud 가져오기
async function getWordCloud() {
  var result = null;
  await api
    .get(`/wordcloud`)
    .then((res) => {
      if (res.data.statusCode == 200) {
        if (res.data.data != null) {
          result = { message: "OK", data: res.data.data };
        }
      }
    })
    .catch();
  return await Promise.resolve(result);
}

export { getWordCloud };
