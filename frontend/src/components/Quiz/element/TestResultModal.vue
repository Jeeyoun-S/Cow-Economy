<template>
  <div>
    <v-dialog v-model="dialog" persistent max-width="400px">
      <v-sheet class="pa-7 d-flex flex-column align-center">
        <h2>최종 스코어</h2>
        <p>{{ this.correctCount }} / 7</p>
        <div class="d-flex flex-column align-center" v-if="this.isPass">
          <img
            :src="require('@/assets/images/mypage/quiz/congratulations.gif')"
          />
          <!-- <div class="modal-exp-title">경험치를 획득했어요!</div> -->
          <div class="lg-font b-font">경험치를 획득했어요!</div>
          <p>현재 경험치 {{ this.experience }} EXP</p>
        </div>
        <div class="d-flex flex-column align-center" v-else>
          <img
            class="pb-3"
            :src="require('@/assets/images/mypage/quiz/fail.gif')"
          />
          <div class="lg-font b-font">아쉽네요</div>
          <p>테스트를 통과하지 못했습니다.</p>
        </div>
        <v-btn
          elevation="0"
          block
          dark
          color="var(--main-col-2)"
          @click="closeModal()"
          >퀴즈 종료하기</v-btn
        >
      </v-sheet>
    </v-dialog>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
const quizStore = "quizStore";

export default {
  name: "TestResultModal",
  data() {
    return {
      dialog: true,
    };
  },
  computed: {
    ...mapState(quizStore, ["isPass", "experience", "correctCount"]),
  },
  methods: {
    ...mapActions(quizStore, ["initQuiz"]),
    // [@Method] Modal 닫고 마이페이지로 이동
    closeModal() {
      this.dialog = false;
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
