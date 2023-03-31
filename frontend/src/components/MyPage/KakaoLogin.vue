<template>
  <v-sheet class="px-7 py-16 ma-7" height="100%" rounded="lg">
    <div class="my-7 d-flex flex-column align-center">
      <h1 class="b-font">LOGIN</h1>
      <div class="py-10">
        <div>로그인 후 이용 가능한 페이지입니다.</div>
        <div>로그인 하시겠습니까?</div>
      </div>
      <!-- 카카오톡 로그인 버튼 -->
      <!-- <v-flex
          class="blue-shadow blur-on-hover d-flex flex-clumn align-center justify-center ma-3"
        > -->
      <!-- <a id="custom-login-btn"> -->
      <img
        class="blur-on-hover blue-shadow"
        width="100%"
        @click="kakaoLogin"
        src="@/assets/images/login/kakao_login.png"
      />
      <!-- </a> -->
      <!-- </v-flex> -->
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
img {
  border-radius: 10px;
  max-width: 300px;
}
</style>
