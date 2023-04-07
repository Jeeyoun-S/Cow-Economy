<template>
  <v-dialog v-model="dialog" max-width="300">
    <v-sheet class="pa-3 d-flex flex-column align-center">
      <v-btn class="align-self-end" icon @click="dialog = false">
        <v-icon>mdi-close</v-icon>
      </v-btn>
      <img
        width="40%"
        :src="require('@/assets/images/illustration/something_went_wrong.png')"
      />
      <div class="mx-3 mt-4 point-md xl-font">서버 에러</div>
      <div class="mx-3 my-2 d-flex flex-column align-center sm-font">
        <div>서버 상의 문제로</div>
        <div>Quiz 문제를 가져올 수 없습니다.</div>
        <div>인터넷 연결을 확인해 주세요.</div>
      </div>
      <div class="mx-3 my-2 mb-4">
        <v-btn
          dark
          class="px-10"
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
  name: "ServerError",
  data() {
    return {
      dialog: false,
    };
  },
  methods: {
    // [@Method] Modal 닫기
    closeModal() {
      this.dialog = false;
    },
    openDialog() {
      this.dialog = true;
    },
  },
};
</script>

<style></style>
