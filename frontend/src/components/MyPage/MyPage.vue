<template>
  <div>
    <v-container class="login">
      <div class="login-title">LOGIN</div>
        <div class="login-text">로그인 후 이용 가능한 페이지입니다.<br />로그인 하시겠습니까?</div>
        <div class="social-login">
        <v-flex class="d-flex flex-column align-center justify-center ma-3">
          <v-img src="@/assets/images/login/google_login.png"  class="blur-on-hover"></v-img>
        </v-flex>
        <v-flex class="d-flex flex-column align-center justify-center">
          <v-img src="@/assets/images/login/kakao_login.png" @click="kakaoLogin()" class="blur-on-hover"></v-img>
        </v-flex>
      </div>
    </v-container>
  </div>
</template>

<script>
import axios from "@/api/index.js";

export default {
  methods: {
    async handleKakaoLogin(code) {
      try {
        console.log('Sending request to backend with code:', code);
        const response = await axios.post('/kakao/callback', { code });
        console.log('##############');
        if (response.status === 200) {
          const token = response.data.token;
          localStorage.setItem('token', token);
          console.log('#123#');
          const redirect = localStorage.getItem('redirect') || '/home';
          this.$router.push({ path: redirect });
        } else {
          console.error('카카오 로그인 실패');
        }
      } catch (error) {
        console.error('카카오 로그인 중 에러 발생', error);
      }
    },
    kakaoLogin() {
      const redirectUri = encodeURIComponent(process.env.VUE_APP_KAKAO_REDIRECT_URI);
      const oauthUrl = `https://kauth.kakao.com/oauth/authorize?client_id=${process.env.VUE_APP_KAKAO_REST_API_KEY}&redirect_uri=${redirectUri}&response_type=code`;
      window.location.href = oauthUrl;
    },
  },
  beforeRouteEnter(to, from, next) {
    const urlParams = new URLSearchParams(window.location.search);
    const code = urlParams.get('code');
    console.log('Code:', code);

    if (code) {
      console.log('Calling handleKakaoLogin...');
      next((vm) => vm.handleKakaoLogin(code));
    } else {
      next();
    }
  },
};
</script>

<style>
.login {
  background-color: #ffffff;
  margin-top: 10%;
  padding-top: 30%;
}

.login-title {
  font-family: var(--main-font-3);
  text-align: center;
  font-size: 30px;

  padding-top: 10px;
  padding-bottom: 10px;
}

.login-text {
  font-family: var(--main-font-2);
  color: var(--graph-2-col-1);
  text-align: center;

  margin-top: 10%;
}

.blur-on-hover:hover {
  transform: scale(1.05);
  filter: brightness(0.9);
  cursor: pointer;
}

.container {
  background-color: "#ffffff";
  max-width: 20rem;
  height: 40rem;
}

.social-login {
  margin-top: 10%;
}
</style>