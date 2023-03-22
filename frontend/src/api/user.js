import { apiInstance } from "./index.js";

const api = apiInstance();

async function getToken(success, fail) {
  var code = new URL(window.location.href).searchParams.get("code");
  await api
    .get({ url: `/auth/login/kakao?code=${code}` })
    .then(success)
    .catch(fail);
}

export { getToken };
