import axios from "axios";
import store from "@/store";

function apiInstance() {
  const instance = axios.create({
    baseURL: process.env.VUE_APP_API_BASE_URL,
    headers: {
      "Content-Type": "application/json; charset=utf-8",
    },
  });
  
  instance.interceptors.request.user(
    (config) => {
      const token = localStorage.getItem('accessToken');
      if (token) {
        config.headers['Authorization'] = 'Bearer ' + token;
      }
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
  )

  instance.interceptors.request.use(function (config) {
    // # axios 통신 시 loading 창 출력
    store.commit("LOADING_STATUS", true);

    // 나중에 TOKEN 넣는 CODE 추가
    return config;
  });

  instance.interceptors.response.use(function (config) {
    // # axios 통신 시 loading 창 숨김
    store.commit("LOADING_STATUS", false);

    return config;
  });

  return instance;
}

export { apiInstance };
