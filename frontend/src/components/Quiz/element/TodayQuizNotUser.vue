<template>
  <v-dialog v-model="dialog" max-width="350">
    <v-sheet class="pa-3 d-flex flex-column align-center">
      <v-btn class="align-self-end" icon @click="dialog = false">
        <v-icon>mdi-close</v-icon>
      </v-btn>
      <img
        width="40%"
        :src="require('@/assets/images/illustration/no_results_found.png')"
      />
      <div class="mx-3 mt-4 point-md xl-font">비회원 이용 불가</div>
      <div class="mx-3 my-2">로그인 후 이용 가능합니다.</div>
      <div class="mx-3 my-2 mb-4">
        <v-btn
          dark
          color="var(--main-col-2)"
          @click="moveLogin()"
          elevation="0"
          rounded
          class="mr-2 px-10"
          >로그인</v-btn
        >
        <v-btn
          dark
          color="var(--main-col-2)"
          @click="dialog = false"
          elevation="0"
          rounded
          outlined
          >닫기</v-btn
        >
      </div>
    </v-sheet>
  </v-dialog>
</template>

<script>
export default {
  name: "TodayQuizNotUser",
  data() {
    return {
      dialog: false,
    };
  },
  methods: {
    openDialog() {
      this.dialog = true;
    },
    moveLogin() {
      this.openDialog();
      this.$router.push("/my-page");
    },
  },
};
</script>

<style></style>
