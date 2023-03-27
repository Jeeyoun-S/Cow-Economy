<template>
  <div class="modal-mask">
    <v-card class="modal-wrapper">
      <v-card-text class="modal-wrapper-text">
        <div class="modal-score-title">🚫 오늘의 Quiz 🚫</div>
        <div>
          <img :src="require('@/assets/images/mypage/quiz/fail.gif')" />
          <p class="modal-exp-title">Quiz 출제 불가</p>
          <div class="modal-exp-text">
            기사를 많이 읽어주세요!
            <div class=""></div>
          </div>
        </div>
      </v-card-text>
      <v-card-actions class="modal-button">
        <v-btn block dark color="var(--main-col-2)" @click="closeModal()"
          >기사 읽으러 가기</v-btn
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
    // [@Method] Modal 닫고 Article 페이지로 이동
    closeModal() {
      location.href = `${process.env.VUE_APP_BASE_URL}/news`;
    },
  },
};
</script>

<style></style>
