import { apiInstance } from "../index";

// api instance 가져오기
const api = apiInstance();

// [GET /user/info] 기사 상세정보 조회
async function getUserInfo() {
  await api.get(`user/info`)
    .then((res) => {
      if (res.data.statusCode == 200) {
        console.log(res)
      }
    })
}

export { getUserInfo }