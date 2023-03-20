<template>
  <div>
    <v-container class="login">
      <div class="login-title">LOGIN</div>
        <div class="login-text">로그인 후 이용 가능한 페이지입니다.<br />로그인 하시겠습니까?</div>
        <div class="social-login">
        <v-flex class="d-flex flex-column align-center justify-center ma-3">
          <v-img src="@/assets/images/login/google_login.png"  class="blur-on-hover"></v-img>
        </v-flex>
        <!-- 카카오 로그인 버튼 -->
        <v-flex class="d-flex flex-column align-center justify-center">
          <kakaoLogin></kakaoLogin>
        </v-flex>
      </div>
    </v-container>
  </div>
</template>

<script>
import axios from "@/api/index.js";
import kakaoLogin from "@/components/MyPage/KakaoLogin.vue";

export default {
  name: "MyPage",
  components: {
    kakaoLogin
  },
  methods: {
    async handleKakaoLogin(token) {
      try {
        const response = await axios.post('/kakao/callback', { token });

        if (response.status === 302) {
          const token = response.data.token;
          localStorage.setItem('token', token);
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
    const token = urlParams.get('token');

    console.log(localStorage.getItem('token'));

    if (token) {
      console.log('Calling handleKakaoLogin...');
      next((vm) => vm.handleKakaoLogin(token));
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