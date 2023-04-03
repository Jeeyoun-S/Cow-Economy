<template>
  <v-dialog v-model="dialog" max-width="300" overlay-opacity="0.2" persistent>
    <!-- <v-sheet
      class="pa-5 d-flex flex-row justify-center"
      :color="correctFlag ? '#eafdfd' : '#ffeefe'"
    > -->
    <img
      class="img-mint"
      width="100%"
      v-if="correctFlag"
      src="@/assets/images/mypage/quiz/correct.png"
    />
    <img
      class="img-pink"
      width="100%"
      v-else
      src="@/assets/images/mypage/quiz/wrong.png"
    />
    <!-- </v-sheet> -->
  </v-dialog>
</template>

<script>
export default {
  name: "AnswerCorrectAlert",
  data() {
    return {
      dialog: false,
    };
  },
  props: {
    correctFlag: Boolean,
  },
  methods: {
    openDialog() {
      // console.log("openDialog");
      this.dialog = true;
    },
    closeDialog() {
      this.dialog = false;
    },
  },
};
</script>

<style></style>
