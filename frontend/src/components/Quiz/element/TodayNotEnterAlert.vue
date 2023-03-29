<template>
  <v-dialog v-model="dialog" max-width="390">
    <v-sheet class="pa-7 d-flex flex-column align-center">
      <div class="b-font xl-font d-flex flex-row align-center">
        <img height="25" src="@/assets/images/emoji/no.png" />
        <span class="mx-3">오늘의 Quiz 끝</span>
        <img height="25" src="@/assets/images/emoji/no.png" />
      </div>
      <img :src="require('@/assets/images/mypage/quiz/fail.gif')" />
      <div class="py-5 d-flex flex-column align-center">
        <div>오늘의 Quiz</div>
        <div>도전 기회가 소진되었습니다.</div>
      </div>
      <v-btn
        block
        dark
        color="var(--main-col-2)"
        @click="dialog = false"
        elevation="0"
        >돌아가기</v-btn
      >
    </v-sheet>
  </v-dialog>
</template>

<script>
export default {
  name: "TodayNotEnterAlert",
  data() {
    return {
      dialog: false,
    };
  },
  methods: {
    openDialog() {
      this.dialog = true;
    },
  },
};
</script>

<style></style>
