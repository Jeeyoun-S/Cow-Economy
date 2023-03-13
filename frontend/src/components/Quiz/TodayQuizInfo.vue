<template>
  <div><v-btn @click="moveQuiz()">시작하기</v-btn></div>
</template>

<script>
export default {
  name: "TodayQuizInfo",
  data() {
    return {};
  },
  methods: {
    // [@Method] Quiz 페이지로 이동
    moveQuiz() {
      location.href = `${process.env.VUE_APP_BASE_URL}/today-quiz`;
    },
  },
};
</script>

<style></style>
