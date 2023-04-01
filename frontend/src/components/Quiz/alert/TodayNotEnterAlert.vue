<template>
  <v-dialog v-model="dialog" max-width="300">
    <v-sheet class="pa-3 d-flex flex-column align-center">
      <v-btn class="align-self-end" icon @click="dialog = false">
        <v-icon>mdi-close</v-icon>
      </v-btn>
      <img
        width="40%"
        :src="require('@/assets/images/illustration/calendar.png')"
      />
      <div class="mx-3 mt-4 point-md xl-font">오늘의 Quiz 끝</div>
      <div class="mx-3 my-2 d-flex flex-column align-center sm-font">
        <div>도전 기회가 소진되었습니다.</div>
        <div>오늘의 Quiz는 하루에 한 번만</div>
        <div>풀 수 있습니다!</div>
      </div>
      <div class="mx-3 my-2 mb-4">
        <v-btn
          block
          dark
          color="var(--main-col-2)"
          @click="dialog = false"
          elevation="0"
          class="px-10"
          rounded
          >돌아가기</v-btn
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
    openDialog() {
      this.dialog = true;
    },
  },
};
</script>

<style></style>
