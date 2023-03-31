<template>
  <v-dialog v-model="dialog" max-width="350">
    <v-sheet class="pa-3 d-flex flex-column align-center">
      <v-btn class="align-self-end" icon @click="dialog = false">
        <v-icon>mdi-close</v-icon>
      </v-btn>
      <img
        width="40%"
        :src="require('@/assets/images/illustration/page_not_found.png')"
      />
      <div class="mx-3 mt-4 point-md xl-font">Quiz 출제 불가</div>
      <div class="mx-3 my-2 d-flex flex-column align-center sm-font">
        <div>읽은 기사가 부족해</div>
        <div>Quiz를 출제할 수 없습니다.</div>
        <div>기사를 많이 읽어주세요!</div>
      </div>
      <div class="mx-3 my-2 mb-4">
        <v-btn
          dark
          color="var(--main-col-2)"
          @click="closeModal()"
          elevation="0"
          rounded
          class="mr-2"
          >기사 읽으러 가기</v-btn
        ><v-btn
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
