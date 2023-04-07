<template>
  <v-dialog v-model="dialog" max-width="300" persistent>
    <v-sheet class="pa-3 d-flex flex-column align-center">
      <v-btn class="align-self-end" icon @click="stopQuiz()">
        <v-icon>mdi-close</v-icon>
      </v-btn>
      <img
        width="40%"
        :src="require('@/assets/images/illustration/person.png')"
      />
      <div class="mx-3 mt-4 point-md xl-font">퀴즈를 시작하겠습니까?</div>
      <div class="mx-3 my-2 d-flex flex-column align-center sm-font">
        <div>창 닫기, 새로고침, 뒤로 가기 등</div>
        <div>페이지 재로딩 또는 이동 시</div>
        <div>풀어둔 퀴즈 정보는 사라집니다.</div>
      </div>
      <div class="mx-3 my-2 mb-4">
        <v-btn
          dark
          color="var(--main-col-2)"
          @click="startQuiz()"
          elevation="0"
          rounded
          class="mr-2 px-10"
          >시작하기</v-btn
        >
        <v-btn
          dark
          color="var(--main-col-2)"
          @click="stopQuiz()"
          elevation="0"
          rounded
          outlined
          >취소</v-btn
        >
      </div>
    </v-sheet>
  </v-dialog>
</template>

<script>
export default {
  name: "TodayQuizStart",
  data() {
    return {
      dialog: true,
    };
  },
  methods: {
    startQuiz() {
      this.$emit("startQuiz");
      this.dialog = false;
    },
    stopQuiz() {
      this.$router.push("/quiz");
      this.dialog = false;
    },
  },
};
</script>

<style></style>
