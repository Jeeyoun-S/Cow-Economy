<!-- # for. quiz Loading 창 -->
<!-- <template>
  <div class="modal-mask" v-if="loading">
    <div class="lds-facebook">
      <div></div>
      <div></div>
      <div></div>
    </div>
  </div>
</template> -->
<template>
  <div class="quiz-loading-mask" v-if="loading">
    <v-card class="quiz-wrapper">
      <v-card-text class="quiz-wrapper-text">
        <p class="quiz-exp-title">Quiz 만드는 중</p>
      </v-card-text>
      <v-card-text class="quiz-wrapper-img">
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

<style>
.quiz-loading-mask {
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

.quiz-wrapper {
  width: 70%;
  height: 30%;

  margin-left: 15%;
  margin-top: 70%;
}
.quiz-wrapper-text {
  display: flex;
  flex-direction: column;
  align-items: center;

  font-family: var(--main-font-2);
  color: black;
}

.quiz-exp-title {
  font-size: larger;
  font-weight: 600;
  color: black;

  display: flex;
  flex-direction: column;
  align-items: center;

  margin-top: 5%;
}

.quiz-wrapper-img img {
  width: 250px;
  height: 100px;
}
</style>
