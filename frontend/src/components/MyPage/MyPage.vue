<template>
  <div v-if="loaded">
    <div v-if="isLoggedIn">
      마이페이지
    </div>
    <div v-else>
      <kakaoLogin></kakaoLogin>
      로그인 페이지
    </div>
  </div>
  <div v-else>Loading...</div>
</template>

<script>
// import axios from "@/api/index.js";
import kakaoLogin from "@/components/MyPage/KakaoLogin.vue";
import { mapGetters, mapActions } from 'vuex';
// import userStore from '@/store/modules/userStore';

export default {
  name: "MyPage",
  data() {
    return {
      kakaoCode: null,
      loaded: false,
    };
  },
  watch: {
    isLoggedIn() {
      this.loaded =true;
    }
  },
  created() {
    this.loaded = false;
    // 인가 코드 추출
    this.kakaoCode = this.$route.query.code;
    if (this.kakaoCode != null) {
      this.kakao();
    }
    this.loaded = true;
  },
  components: {
    kakaoLogin
  },
  computed: {
    ...mapGetters("userStore", ["isLoggedIn"])
  },
  methods: {
    ...mapActions("userStore", ["executeToken"]),
    // 받은 인가 코드를 사용하여 Kakao Token 발급 요청
    async kakao () {
      await this.executeToken();
      // 받은 인가 코드 초기화
      this.kakaoCode = null;
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