<template>
  <div class="modal-mask">
    <v-card class="modal-wrapper">
      <v-card-text class="modal-wrapper-text">
        <div class="modal-score-title">최종 스코어</div>
        <div class="modal-score-text">{{ this.correctCount }}/7</div>
        <div v-if="this.isPass">
          <img
            :src="require('@/assets/images/mypage/quiz/congratulations.gif')"
          />
          <!-- <div class="modal-exp-title">경험치를 획득했어요!</div> -->
          <p class="modal-exp-title">경험치를 획득했어요!</p>
          <div class="modal-exp-text">
            현재 경험치 {{ this.experience }} EXP
          </div>
        </div>
        <div v-else>
          <img :src="require('@/assets/images/mypage/quiz/fail.gif')" />
          <p class="modal-exp-title">아쉽네요</p>
          <div class="modal-exp-text">테스트를 통과하지 못했습니다.</div>
        </div>
      </v-card-text>
      <v-card-actions class="modal-button">
        <v-btn block dark color="var(--main-col-2)" @click="closeModal()"
          >퀴즈 종료하기</v-btn
        >
      </v-card-actions>
    </v-card>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
const quizStore = "quizStore";

export default {
  name: "TestResultModal",
  data() {
    return {};
  },
  computed: {
    ...mapState(quizStore, ["isPass", "experience", "correctCount"]),
  },
  methods: {
    ...mapActions(quizStore, ["initQuiz"]),
    // [@Method] Modal 닫고 마이페이지로 이동
    closeModal() {
      this.initQuiz();
      location.href = `${process.env.VUE_APP_BASE_URL}/my-page`;
    },
  },
};
</script>

<style>
/* back-ground, modal 뒷배경 */
.modal-mask {
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

/* modal 창 */
.modal-wrapper {
  width: 70%;
  height: 50%;

  margin-left: 15%;
  margin-top: 50%;
}
.modal-wrapper-text {
  display: flex;
  flex-direction: column;
  align-items: center;

  font-family: var(--main-font-2);
  color: black;
}
.modal-wrapper-text img {
  width: 250px;
  height: 100px;

  margin-bottom: 5%;
}

/* modal - score 부분 */
.modal-score-title {
  font-size: large;
  margin-top: 10%;

  color: black;
}
.modal-score-text {
  margin-top: 1%;
  margin-bottom: 5%;

  color: black;
}

/* modal - 경험치 부분 */
.modal-exp-title {
  font-size: larger;
  font-weight: 600;
  color: black;

  display: flex;
  flex-direction: column;
  align-items: center;
}
.modal-exp-text {
  font-size: medium;
  font-weight: 400;
  color: black;

  margin-bottom: 10%;

  display: flex;
  flex-direction: column;
  align-items: center;
}

/* modal - button 부분 */
.modal-button {
  width: 80%;
  margin-left: 10%;
}
</style>
