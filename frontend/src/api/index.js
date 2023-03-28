import axios from "axios";

function apiInstance() {
  const instance = axios.create({
    baseURL: process.env.VUE_APP_API_BASE_URL,
    headers: {
      "Content-Type": "application/json; charset=utf-8",
    },
  });

  instance.interceptors.request.use(function (config) {
    // # axios 통신 시 loading 창 출력
    // store.commit("LOADING_STATUS", true);

    // # JWT Token Header 추가
    const token = localStorage.getItem("access-token");
    const refreshToken = localStorage.getItem("refresh-token");
    if (token) {
      // console.log("#21# token 넣기 확인: ", token);
      config.headers["Authorization"] = "Bearer " + token;
    }

    // refresh-token도 포함 시키기
    if (refreshToken) {
      config.headers["Refresh"] = "Refrsh " + refreshToken;
    }

    //   // 나중에 TOKEN 넣는 CODE 추가

    instance.interceptors.response.use(function (config) {
      // # axios 통신 시 loading 창 숨김
      // store.commit("LOADING_STATUS", false);

      // 새로 발급된 access-token이 있다면 로컬 스토리지에 저장
      if (config.headers["access-token"]) {
        localStorage.setItem("access-token", config.headers["access-token"]);
      }

      // instance.interceptors.response.use(function (config) {
      //   // # axios 통신 시 loading 창 숨김
      //   store.commit("LOADING_STATUS", false);

      //   return config;
      // });

      return instance;
    }

export { apiInstance };
