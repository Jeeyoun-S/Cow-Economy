import axios from "axios";
import store from "@/store";

function apiInstance() {
  const instance = axios.create({
    baseURL: process.env.VUE_APP_API_BASE_URL,
    headers: {
      "Content-Type": "application/json; charset=utf-8",
    },
  });

  // instance.interceptors.request.use(
  //   (config) => {
  //     const token = localStorage.getItem("accessToken");
  //     if (token) {
  //       config.headers["Authorization"] = "Bearer " + token;
  //     }
  //     return config;
  //   },
  //   (error) => {
  //     return Promise.reject(error);
  //   }
  // );

  instance.interceptors.request.use(function (config) {
    // # axios 통신 시 loading 창 출력
    store.commit("LOADING_STATUS", true);

    // 나중에 TOKEN 넣는 CODE 추가
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

    return config;
  });

  instance.interceptors.response.use(function (config) {
    // # axios 통신 시 loading 창 숨김
    store.commit("LOADING_STATUS", false);

    // 새로 발급된 access-token이 있다면 로컬 스토리지에 저장
    if (config.headers["access-token"]) {
      localStorage.setItem("access-token", config.headers["access-token"]);
    }

    return config;
  });

  return instance;
}

export { apiInstance };
