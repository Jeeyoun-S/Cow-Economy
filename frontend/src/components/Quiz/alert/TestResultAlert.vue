<template>
  <div>
    <v-dialog v-model="dialog" persistent max-width="300">
      <v-sheet class="pa-3 d-flex flex-column align-center">
        <v-chip class="my-2 px-3 gradient-2 point-md" dark>
          {{ correctCount }} / 7
        </v-chip>
        <div v-if="isPass" class="d-flex flex-column align-center">
          <img
            class="my-2"
            :src="require('@/assets/images/mypage/quiz/congratulations.gif')"
          />
          <span class="mr-2 point-md xl-font">최종 스코어</span>
          <div class="mx-3 my-2 d-flex flex-column align-center">
            <div>경험치를 획득했어요!</div>
            <div>
              현재 나의 경험치 <span class="b-font">{{ experience }} EXP</span>
            </div>
          </div>
        </div>
        <div v-else class="d-flex flex-column align-center">
          <img
            class="my-2"
            :src="require('@/assets/images/mypage/quiz/fail.gif')"
          />
          <span class="mr-2 point-md xl-font">최종 스코어</span>
          <div class="mx-3 my-2 d-flex flex-column align-center">
            <div>테스트를 통과하지 못했습니다.</div>
          </div>
        </div>
        <div class="mx-3 my-2 mb-4">
          <v-btn
            block
            dark
            color="var(--main-col-2)"
            @click="closeModal()"
            elevation="0"
            class="px-10"
            rounded
            >퀴즈 종료하기</v-btn
          >
        </div>
      </v-sheet>
    </v-dialog>
  </div>
</template>

<script>
import { mapState } from "vuex";
const quizStore = "quizStore";

export default {
  name: "TestResultAlert",
  data() {
    return {
      dialog: false,
    };
  },
  computed: {
    ...mapState(quizStore, ["isPass", "experience", "correctCount"]),
  },
  methods: {
    openDialog() {
      this.dialog = true;
    },
    // [@Method] Modal 닫고 마이페이지로 이동
    closeModal() {
      this.dialog = false;
      this.$router.push("/my-page");
    },
  },
};
</script>

<style></style>
