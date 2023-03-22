<!-- # for. axios 통신 시 Loading 창 -->
<!-- <template>
  <div class="lds-facebook" v-if="loading">
    <div></div>
    <div></div>
    <div></div>
  </div>
</template> -->
<!-- !#! 임시로 해둠 -->
<template>
  <div class="main-loading-mask" v-if="loading">
    <v-card class="main-wrapper">
      <v-card-text class="main-wrapper-text">
        <p class="main-exp-title">Quiz 만드는 중</p>
      </v-card-text>
      <v-card-text class="main-wrapper-img">
        <img
          :src="require('@/assets/images/mypage/quiz/quiz_loading_spinner.gif')"
        />
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
export default {
  props: {
    loading: {
      type: Boolean,
      required: true,
    },
  },
};
</script>

<!-- <style>
.lds-facebook {
  display: inline-block;
  position: fixed;
  width: 64px;
  height: 64px;
  top: 50%;
  left: 50%;
}

.lds-facebook div {
  display: inline-block;
  position: absolute;
  left: 6px;
  width: 13px;
  background: #42b883;
  animation: lds-facebook 1.2s cubic-bezier(0, 0.5, 0.5, 1) infinite;
}

.lds-facebook div:nth-child(1) {
  left: 6px;
  animation-delay: -0.24s;
}

.lds-facebook div:nth-child(2) {
  left: 26px;
  animation-delay: -0.12s;
}

.lds-facebook div:nth-child(3) {
  left: 45px;
  animation-delay: 0;
}

@keyframes lds-facebook {
  0% {
    top: 6px;
    height: 51px;
  }

  50%,
  100% {
    top: 19px;
    height: 26px;
  }
}
</style> -->
<style>
.main-loading-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: table;
  transition: opacity 0.3s ease;
}

.main-wrapper {
  width: 70%;
  height: 30%;

  margin-left: 15%;
  margin-top: 70%;
}
.main-wrapper-text {
  display: flex;
  flex-direction: column;
  align-items: center;

  font-family: var(--main-font-2);
  color: black;
}

.main-exp-title {
  font-size: larger;
  font-weight: 600;
  color: black;

  display: flex;
  flex-direction: column;
  align-items: center;

  margin-top: 5%;
}

.main-wrapper-img img {
  width: 250px;
  height: 100px;
}
</style>
