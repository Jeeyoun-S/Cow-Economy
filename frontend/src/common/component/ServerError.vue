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
      <div class="mx-3 mt-4 point-md xl-font">Server Error</div>
      <div class="mx-3 my-2 d-flex flex-column align-center sm-font">
        <div>서버 오류로 정보를 불러오지 못했습니다.</div>
        <div>인터넷을 확인해 주시기 바랍니다.</div>
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
    // [@Method] Modal 닫기
    closeModal() {
      this.dialog = false;
      this.$router.push("/");
    },
    openDialog() {
      this.dialog = true;
    },
  },
};
</script>

<style></style>
