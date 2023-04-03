import { apiInstance } from "./index.js";

const api = apiInstance();

// [GET] 카카오 인가코드로 jwt 토큰 발급 api
async function getToken(success, fail) {
  var code = new URL(window.location.href).searchParams.get("code");
  await api.get(`/auth/login/kakao?code=${code}`).then(success).catch(fail);
}

export { getToken };
