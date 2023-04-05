<template>
  <v-sheet
    height="100vh"
    color="transparent"
    class="gradient d-flex flex-column align-center justify-center"
  >
    <!-- <div class="line-top"></div>
    <div class="motion">
      <div class="glitter glitter1"></div>
      <div class="glitter glitter2"></div>
      <div class="glitter glitter3"></div>
      <div class="glitter glitter4"></div>
      <div class="glitter glitter5"></div>
      <div class="glitter glitter6"></div>
      <div class="glitter glitter7"></div>
      <div class="glitter glitter8"></div>
      <div class="circle circle1"></div>
      <div class="circle circle2"></div>
      <div class="circle circle3"></div>
      <div class="circle circle4"></div>
    </div> -->
    <img
      class="mb-4 logo-gradient"
      height="100px"
      :src="require('@/assets/images/logo/logo_full.png')"
    />
    <v-sheet
      class="loader mb-5 d-flex flex-column justify-center align-center team-gradient"
      width="40%"
      color="transparent"
    >
      <v-progress-linear indeterminate color="white"></v-progress-linear>
    </v-sheet>
    <span class="loader white-col-1 md-font team-gradient"
      >경제 기사를 읽으며</span
    >
    <span class="loader white-col-1 md-font team-gradient"
      >경제에 대해 공부하는 서비스</span
    >
    <!-- <div class="title">
      <span class="black-font"></span>
      <div class="cursor"></div>
    </div> -->
    <div
      class="team d-flex flex-column justify-center align-center white-col-1 team-gradient sm-font"
    >
      <div class="point-th">SSAFY 8기 A509 오난쥉이조</div>
      <div class="point-th">엄희정 민동주 서지윤 손승환 신도연</div>
    </div>
  </v-sheet>
</template>

<script>
// import Scene from "scenejs";
// import { typing } from "@scenejs/effects";
import { mapActions } from "vuex";

const mainStore = "mainStore";

export default {
  name: "InfoPageFirst",
  computed: {},
  methods: {
    ...mapActions(mainStore, [
      "updateWordCloud",
      "updateHotNews",
      "updateTodayAllNews",
    ]),
  },
  mounted() {
    this.updateWordCloud(); //워드 클라우드 설정
    this.updateHotNews(); //인기 뉴스 설정
    this.updateTodayAllNews(); //카테고리별 뉴스 설정
    // new Scene(
    //   {
    //     ".line-top": {
    //       0: {
    //         transform: "translateY(10%) scaleY(0.5)",
    //       },
    //       0.3: {
    //         transform: "translateY(0%) scaleY(0)",
    //       },
    //       options: {
    //         easing: "ease-in",
    //       },
    //     },
    //     ".title": {
    //       3: {
    //         opacity: 0,
    //       },
    //       3.5: {
    //         opacity: 1,
    //       },
    //     },
    //     ".circle1": {
    //       0: {
    //         "border-width": "100px",
    //         transform: "scale(0)",
    //       },
    //       0.7: {
    //         opacity: 1,
    //       },
    //       0.9: {
    //         "border-width": "0px",
    //         transform: "scale(1)",
    //         opacity: 0,
    //       },
    //       options: {
    //         delay: 0.5,
    //       },
    //     },
    //     ".circle2": {
    //       0: {
    //         "border-width": "200px",
    //         transform: "scale(0)",
    //       },
    //       0.1: {
    //         opacity: 1,
    //       },
    //       0.7: {
    //         "border-width": "0px",
    //         transform: "scale(1)",
    //         opacity: 0,
    //       },
    //       1.8: 1,
    //       options: {
    //         delay: 1.1,
    //       },
    //     },
    //     ".glitter": (i) => ({
    //       0: {
    //         width: "0px",
    //         transform: {
    //           rotate: `${(360 / 8) * i}deg`,
    //           translate: "0px",
    //           scaleX: 1,
    //         },
    //         opacity: 0,
    //       },
    //       0.3: {
    //         width: "50px",
    //         opacity: 1,
    //       },
    //       0.8: {
    //         width: "0px",
    //         transform: {
    //           translate: "100px",
    //           scaleX: 0,
    //         },
    //         opacity: 0,
    //       },
    //       options: {
    //         delay: 0.5,
    //       },
    //     }),
    //     ".logo": {
    //       0: {
    //         opacity: 0,
    //         transform: "scale(0.3)",
    //         // top: "-100px",
    //         // bottom: "-100px",
    //       },
    //       0.1: {
    //         opacity: 1,
    //       },
    //       0.8: {
    //         opacity: 1,
    //       },
    //       1: {
    //         transform: "translateY(40%) scale(1)",
    //         opacity: 1,
    //       },
    //       2: {
    //         transform: "translateY(-20%) scale(1)",
    //       },
    //       options: {
    //         delay: 1.3,
    //       },
    //     },
    //     ".loader": {
    //       3.3: {
    //         transform: "scale(0)",
    //       },
    //       3.5: {
    //         transform: "scale(1)",
    //       },
    //     },
    //     ".detail": {
    //       0: {
    //         opacity: 0,
    //       },
    //       2.3: {
    //         opacity: 1,
    //       },
    //       options: {
    //         delay: 2.3,
    //       },
    //     },
    //   },
    //   {
    //     easing: "ease-out",
    //     selector: true,
    //     iterationCount: "1",
    //   }
    // ).playCSS();
    // new Scene(
    //   {
    //     ".circle3": {
    //       0: {
    //         "border-width": "150px",
    //         transform: "scale(0)",
    //       },
    //       0.7: {
    //         opacity: 1,
    //       },
    //       0.9: {
    //         "border-width": "0px",
    //         transform: "scale(1)",
    //         opacity: 0,
    //       },
    //       // options: {
    //       //   delay: 0.5,
    //       // },
    //     },
    //     ".circle4": {
    //       0: {
    //         "border-width": "250px",
    //         transform: "scale(0)",
    //       },
    //       0.1: {
    //         opacity: 1,
    //       },
    //       0.7: {
    //         "border-width": "0px",
    //         transform: "scale(1)",
    //         opacity: 0,
    //       },
    //       1.8: 1,
    //       options: {
    //         delay: 0.6,
    //       },
    //     },
    //   },
    //   {
    //     iterationCount: "infinite",
    //     selector: true,
    //     delay: 3,
    //   }
    // ).playCSS();
    // new Scene(
    //   {
    //     ".title span": typing({
    //       text: "Loading · · ·",
    //       duration: 5,
    //       delay: 0.5,
    //     }),
    //     ".title .cursor": {
    //       0: { opacity: 0 },
    //       0.5: { opacity: 0 },
    //       "0.5>": { opacity: 1 },
    //       1: { opacity: 1 },
    //       options: {
    //         iterationCount: 8,
    //       },
    //     },
    //   },
    //   {
    //     iterationCount: "infinite",
    //     direction: "alternate",
    //     selector: true,
    //   }
    // ).play();
  },
};
</script>
<style scoped>
/* #info-first {
  background-image: url("@/assets/images/cow.svg");
  background-position: 50% 50%;
  background-size: 80% 80%;
} */
/* #info-first {
  background-color: #333;
} */
/* html,
body {
  position: relative;
  height: 100%;
  margin: 0;
  padding: 0;
  overflow: hidden;
}
.container {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  text-align: center;
  color: #333;
}
.container span {
  font-size: 30px;
  font-weight: bold;
} */
.cursor {
  display: inline-block;
  width: 2px;
  height: 25px;
  background: #333;
}
/* 첫 페이지 서비스 로고 등장 효과 */
.line-top {
  position: absolute;
  bottom: 50%;
  left: 0;
  right: 0;
  width: 2px;
  height: 70%;
  transform-origin: 50% 100%;
  background: white;
  margin: auto;
  transform: translateY(-100%);
}
.motion {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  margin: auto;
  width: 100%;
  height: 50%;
  max-width: 600px;
  max-height: 600px;
}
.motion .circle {
  position: absolute;
  width: 140px;
  height: 140px;
  border-radius: 50%;
  box-sizing: border-box;
  border: 70px solid white;
  left: -100px;
  right: -100px;
  top: -100px;
  bottom: -100px;
  margin: auto;
  opacity: 0;
}
.motion .circle2 {
  width: 300px;
  height: 300px;
}
.motion .circle4 {
  width: 300px;
  height: 300px;
}
.motion .glitter {
  position: absolute;
  left: 50%;
  top: 50%;
  width: 100px;
  height: 2px;
  background: white;
  transform-origin: 0px 1px;
}
.detail {
  margin-top: 60px;
}
/* .logo {
  position: absolute;
  left: -100px;
  right: -100px;
  top: -100px;
  bottom: -100px;
  margin: auto;
} */
.team {
  position: absolute;
  bottom: 5%;
}
</style>
