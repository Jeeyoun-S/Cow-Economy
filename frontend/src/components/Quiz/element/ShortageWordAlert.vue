<template>
  <v-dialog v-model="dialog" max-width="390">
    <v-sheet class="pa-7 d-flex flex-column align-center">
      <div class="b-font xl-font d-flex flex-row align-center">
        <img height="25" src="@/assets/images/emoji/no.png" />
        <span class="mx-3">Quiz 출제 불가</span>
        <img height="25" src="@/assets/images/emoji/no.png" />
      </div>
      <img :src="require('@/assets/images/mypage/quiz/fail.gif')" />
      <div class="py-5 d-flex flex-column align-center">
        <div>읽은 기사가 부족해</div>
        <div>Quiz를 출제할 수 없습니다.</div>
        <div>기사를 많이 읽어주세요!</div>
      </div>
      <v-btn
        class="blue-shadow"
        block
        dark
        color="var(--main-col-2)"
        @click="closeModal()"
        >기사 읽으러 가기</v-btn
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
    // [@Method] Modal 닫고 Article 페이지로 이동
    closeModal() {
      this.dialog = false;
      this.$router.push("/news");
    },
    openDialog() {
      this.dialog = true;
    },
  },
};
</script>

<style></style>
