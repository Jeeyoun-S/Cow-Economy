<template>
  <v-sheet
    id="info-first"
    height="100vh"
    color="transparent"
    class="d-flex flex-column align-center justify-center"
  >
    <div class="title">
      <span class="black-font"></span>
      <div class="cursor"></div>
    </div>
    <!-- <img height="50%" src="@/assets/images/icon/cow.svg" /> -->
    <div>경제 뉴스를 읽으며</div>
    <div>경제 공부를 시작해보세요</div>
    <v-btn
      class="px-10"
      elevation="0"
      color="var(--main-col-1)"
      to="/home"
      dark
      rounded
    >
      지금 시작하기
    </v-btn>
  </v-sheet>
</template>

<script>
import Scene from "scenejs";
import { typing } from "@scenejs/effects";

export default {
  name: "InfoPageFirst",
  mounted() {
    new Scene(
      {
        ".title span": typing({
          text: "소 귀에 경제 읽기.",
          duration: 7,
          delay: 0.5,
        }),
        ".title .cursor": {
          0: { opacity: 0 },
          0.5: { opacity: 0 },
          "0.5>": { opacity: 1 },
          1: { opacity: 1 },
          options: {
            iterationCount: 8,
          },
        },
      },
      {
        iterationCount: "infinite",
        direction: "alternate",
        selector: true,
      }
    ).play();
  },
};
</script>
<style scoped>
#info-first {
  background-image: url("@/assets/images/icon/cow.svg");
  background-position: 50% 25%;
  background-size: 100% 70%;
}
html,
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
}
.cursor {
  display: inline-block;
  width: 2px;
  height: 25px;
  background: #333;
}
</style>
