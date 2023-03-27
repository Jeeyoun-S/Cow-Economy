<template>
  <div class="modal-mask">
    <v-card class="modal-wrapper">
      <v-card-text class="modal-wrapper-text">
        <div class="modal-score-title">🚫 오늘의 Quiz 🚫</div>
        <div>
          <img :src="require('@/assets/images/mypage/quiz/fail.gif')" />
          <p class="modal-exp-title">오늘의 Quiz 끝</p>
          <div class="modal-exp-text">
            오늘의 Quiz 도전 기회가 소진되었습니다.
            <div class=""></div>
          </div>
        </div>
      </v-card-text>
      <v-card-actions class="modal-button">
        <v-btn block dark color="var(--main-col-2)" @click="closeModal()"
          >돌아가기</v-btn
        >
      </v-card-actions>
    </v-card>
  </div>
</template>

<script>
export default {
  name: "TodayNotEnterAlert",
  data() {
    return {};
  },
  methods: {
    // [@Method] Modal 닫고 Quiz info 페이지로 이동
    closeModal() {
      location.href = `${process.env.VUE_APP_BASE_URL}/quiz`;
    },
  },
};
</script>

<style></style>
