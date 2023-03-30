<!-- # for. quiz Loading 창 -->
<template>
  <v-dialog max-width="300" persistent v-model="dialog">
    <v-sheet class="pa-8 d-flex flex-column align-center">
      <div class="mb-3 b-font xl-font">Quiz 만드는 중</div>
      <img
        :src="require('@/assets/images/mypage/quiz/quiz_loading_spinner.gif')"
      />
    </v-sheet>
  </v-dialog>
</template>

<script>
export default {
  data() {
    return {
      dialog: false,
    };
  },
  props: {
    loading: {
      type: Boolean,
      required: true,
    },
  },
  watch: {
    loading() {
      this.dialog = this.loading;
    },
  },
};
</script>

<style></style>
