<template>
  <v-sheet class="px-7 py-16 ma-7" height="100%" rounded="lg">
    <div class="my-7 d-flex flex-column align-center">
      <h1 class="b-font">LOGIN</h1>
      <div class="py-10">
        <div>로그인 후 이용 가능한 페이지입니다.</div>
        <div>로그인 하시겠습니까?</div>
      </div>
      <v-btn
        class="blur-on-hover blue-shadow mt-4"
        width="100%"
        height="50"
        @click="kakaoLogin"
        color="yellow accent-4"
        dark
      >
        <img
          class="kakao-logo"
          width="100%"
          src="@/assets/images/login/kakao_login.png"
          alt="kakao login"
        />
        <span class="kakao-text pl-2">카카오톡으로 로그인</span>
      </v-btn>
    </div>
  </v-sheet>
</template>

<script>
export default {
  name: "kakaoLogin",
  components: {},
  computed: {},
  methods: {
    // 카카오 인가 코드 받기, redirect는 my-page로
    kakaoLogin() {
      window.location.replace(
        `https://kauth.kakao.com/oauth/authorize?client_id=${process.env.VUE_APP_KAKAO_REST_API_KEY}&redirect_uri=${process.env.VUE_APP_KAKAO_REDIRECT_URI}&response_type=code`
      );
    },
  },
};
</script>

<style scoped>
.blur-on-hover:hover {
  transform: scale(1.05);
  filter: brightness(0.9);
  cursor: pointer;
}
img.kakao-logo {
  border-radius: 10px;
  max-width: 40px;
}
.kakao-text {
  font-weight: bold;
}
</style>
