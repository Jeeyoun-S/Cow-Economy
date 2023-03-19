import axios from "axios";

const instance = axios.create({
  baseURL: process.env.VUE_APP_API_BASE_URL
});

instance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers['Authorization'] = 'Bearer ' + token;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default instance;
// function apiInstance() {
//   const instance = axios.create({
//     // baseURL: '/api',
//     headers: {
//       "Content-Type": "application/json; charset=utf-8",
//     },
//   });

//   return instance;
// }

// export { apiInstance };