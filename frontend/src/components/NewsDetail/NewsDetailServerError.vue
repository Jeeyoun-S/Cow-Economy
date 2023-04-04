<template>
  <v-dialog v-model="dialog" max-width="300">
    <v-sheet class="pa-3 d-flex flex-column align-center">
      <v-btn class="align-self-end" icon @click="closeModal()">
        <v-icon>mdi-close</v-icon>
      </v-btn>
      <img
        width="40%"
        :src="require('@/assets/images/illustration/something_went_wrong.png')"
      />
      <div class="mx-3 mt-4 point-md xl-font">잘못된 접근</div>
      <div class="mx-3 my-2 d-flex flex-column align-center sm-font">
        <div>존재하지 않는 기사입니다.</div>
      </div>
      <div class="mx-3 my-2 mb-4">
        <v-btn
          dark
          class="px-10"
          color="var(--main-col-2)"
          @click="closeModal()"
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
