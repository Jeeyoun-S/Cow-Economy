import { apiInstance } from "@/api/index.js";
import { getReferenceHTML } from "@/common/function/textSelection.js";

// api instance 가져오기
const api = apiInstance();

// [GET /article/{article_id}] 기사 상세정보 조회
async function getNewsDetail(articleId) {
  var result = null;
  await api.get(`/article/${articleId}`)
    .then((res) => {
      // console.log(res.data)
      if (res.data.statusCode == 200) {
        result = res.data.data;
        if (result.userArticleMemoListMine) {
          for (var i = 0; i < result.userArticleMemoListMine.length; i++) {
            const target = result.userArticleMemoListMine[i];
            const startRange = target.memoStartRange;
            const endRange = target.memoEndRange;
            const startIndex = target.memoStartIndex;
            const endIndex = target.memoEndIndex;

            if (
              startRange == 0 &&
              endRange == 0 &&
              startIndex == 0 &&
              endIndex == 0
            )
              continue;
            result.userArticleMemoListMine[i].referenceText = getReferenceHTML(
              startRange,
              endRange,
              startIndex,
              endIndex,
              result.articleContent
            ).split("@@@");
          }
        } else {
          result.userArticleMemoListMine = [];
        }

        for (var j = 0; j < result.userArticleMemoListOther.length; j++) {
          const target = result.userArticleMemoListOther[j];
          const startRange = target.memoStartRange;
          const endRange = target.memoEndRange;
          const startIndex = target.memoStartIndex;
          const endIndex = target.memoEndIndex;

          if (
            startRange == 0 &&
            endRange == 0 &&
            startIndex == 0 &&
            endIndex == 0
          )
            continue;
          result.userArticleMemoListOther[j].referenceText = getReferenceHTML(
            startRange,
            endRange,
            startIndex,
            endIndex,
            result.articleContent
          ).split("@@@");
        }
      } else {
        result = res.data.data;
      }
    })
    .catch();
  return await Promise.resolve(result);
}

// [POST /article/{articleId}] 기사 읽음 처리
async function updateReading(articleId) {
  var result = false;
  await api.post(`/article/${articleId}`)
    .then((res) => {
      result = res.data.data;
    }).catch();
  return await Promise.resolve(result);
}

async function getTodayHotNews(){
  var result = null;
  await api.get('/article/hot-news').then((res)=>{
    result = res.data.data;
  });
  return await Promise.resolve(result);
}

async function getTodayAllNews() {
  var result = null;
  await api.get('/article/category-news').then((res)=>{
    result = res.data.data;
  });
  return await Promise.resolve(result);
}

async function searchNews(param, success, fail){
  await api.post(`/article/search?keyword=${param.keyword}`,
  JSON.stringify(param.categoryLast)
  ).then(success).catch(fail);
}

async function getAllNews(param){
  var result = null;
  let temp = [];
  temp.push(param.hot);
  temp.push(param.recent);
  await api.post('/article/all-news',temp).then((res)=>{
    console.log(res.data.data);
    result = res.data.data;
  });
  return await Promise.resolve(result);
}

export { updateReading, getNewsDetail, getTodayHotNews, getTodayAllNews, searchNews, getAllNews };
