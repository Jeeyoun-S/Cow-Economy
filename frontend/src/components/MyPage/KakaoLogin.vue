<template>
  <v-sheet
    height="100%"
    color="transparent"
    class="ma-0 d-flex flex-column align-center"
  >
    <div class="wrap ma-5">
      <div
        class="login-container d-flex flex-column align-center justify-center"
      >
        <h1 class="login-gradient black-font x-big-large-font">LOGIN</h1>
        <!-- <div class="py-10"> -->
        <div class="login-gradient">로그인 후 이용 가능한 페이지입니다.</div>
        <div class="login-gradient">로그인 하시겠습니까?</div>
        <div style="height: 50px"></div>
        <img
          class="blur-on-hover img-white"
          width="250"
          @click="kakaoLogin"
          src="@/assets/images/login/kakao_login.png"
        />
        <!-- </div> -->
      </div>
      <div class="wave-container">
        <div class="wave b-font"></div>
        <div class="wave layer_1"></div>
      </div>
    </div>
  </v-sheet>
</template>

<script>
export default {
  name: "kakaoLogin",
  data() {
    return {
      sheetShake: false,
    };
  },
  components: {},
  computed: {},
  mounted() {
    this.shakeSheet();
  },
  methods: {
    // 카카오 인가 코드 받기, redirect는 my-page로
    kakaoLogin() {
      window.location.replace(
        `https://kauth.kakao.com/oauth/authorize?client_id=${process.env.VUE_APP_KAKAO_REST_API_KEY}&redirect_uri=${process.env.VUE_APP_KAKAO_REDIRECT_URI}&response_type=code`
      );
    },
    shakeSheet() {
      this.sheetShake = true;
      setTimeout(() => {
        this.sheetShake = false;
      }, 1000);
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
/* img {
  border-radius: 10px;
  max-width: 1000px;
} */
/* .sheet-shake {
  animation: shake 1s cubic-bezier(.36,.07,.19,.97) both;
  transform: translate3d(0, 0, 0);
  backface-visibility: hidden;
  perspective: 1000px;
} */
@keyframes shake {
  10%,
  90% {
    transform: translate3d(-1px, 0, 0);
  }
  20%,
  80% {
    transform: translate3d(2px, 0, 0);
  }
  30%,
  50%,
  70% {
    transform: translate3d(-4px, 0, 0);
  }
  40%,
  60% {
    transform: translate3d(4px, 0, 0);
  }
}

/* .frame {
  position: absolute;
  display: flex;
  justify-content: center;
  align-items: center;
  top: 50%;
  left: 50%;
  width: 400px;
  height: 1000px;
  margin-top: -200px;
  margin-left: -200px;
  border-radius: 2px;
  box-shadow: 4px 8px 16px 0 rgba(0,0,0,0.1);
} */

@keyframes drift {
  100% {
    transform: rotate(-360deg);
  }
}

.wrap {
  width: 90%;
  height: 100%;
  position: relative;
  overflow: hidden;
  /* border-radius: 50%; */
  background: var(--main-col-1);
  background: linear-gradient(
    180deg,
    var(--graph-1-col-4) 37%,
    var(--main-col-4) 81%
  );
  /* box-shadow: 4px 8px 16px 0 rgba(0, 0, 0, 0.1); */
  transform: translate3d(0, 0, 0);
}

.login-container {
  position: absolute;
  z-index: 2;
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

h1,
.login-container div {
  color: white;
}

.wave-container {
  position: absolute;
  z-index: 1;
  width: 100%;
  height: 100%;
}

.wave {
  width: 520px;
  height: 520px;
  position: absolute;
  margin-left: -260px;
  margin-bottom: -260px;
  bottom: 50%;
  left: 50%;
  /* top: 60%; */
  /* left: -6%; */
  border-radius: 40%;
  animation: drift 4s infinite linear;
  background: rgba(255, 255, 255, 0.4);
}

.wave.layer_1 {
  animation: drift 8s infinite linear;
  background: rgba(255, 255, 255, 0.2);
}
</style>
