<template>
  <div class="quiz-info">
    <div class="quiz-center">
      <div class="quiz-title">
        <img :src="require('@/assets/images/mypage/pencil.png')" />
        오늘의 퀴즈
        <div class="quiz-title-bar"></div>
      </div>
      <!-- 1st -->
      <div class="quiz-center-item-first">
        <div>
          7문제 중 5문제 이상 통과하면<br />
          경험치 +100을 얻을 수 있습니다.
        </div>
        <img :src="require('@/assets/images/mypage/book.png')" />
      </div>
      <!-- 2ed -->
      <div class="quiz-center-item-second">
        <img :src="require('@/assets/images/mypage/question-mark.png')" />
        <div>
          문제로는 경제 용어에 대한<br />
          설명이 주어집니다.
        </div>
      </div>
      <!-- 3th -->
      <div class="quiz-center-item-third">
        <div>
          보기 4개 중 설명에 맞는 단어를<br />
          선택하시면 됩니다.
        </div>
        <img :src="require('@/assets/images/mypage/winking-emoji.gif')" />
      </div>
      <!-- 4th -->
      <div class="quiz-center-item-fourth">
        <div>
          한 문제에 10초의 시간⏰이 주어집니다.<br />
          1일 1회 도전 가능합니다.
        </div>
      </div>
      <div class="quiz-center-button">
        <v-btn block dark color="var(--main-col-2)" @click="moveQuiz()"
          >시작하기</v-btn
        >
      </div>
    </div>
  </div>
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

<style>
.quiz-info {
  font-family: var(--main-font-2);
}

.quiz-center {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;

  width: 327px;
  height: 518px;
  background-color: var(--quiz-1-col-6);

  margin-left: 17%;
  margin-top: 10%;
}

/* 제목 */
.quiz-center .quiz-title {
  font-size: 24px;
  font-weight: bold;

  width: 50%;

  margin-top: 15%;
  margin-right: 40%;
}
.quiz-center .quiz-title img {
  width: 30px;
  height: 30px;

  margin-left: 3%;
  margin-right: 3%;
}
/* 제목 바 */
.quiz-center .quiz-title-bar {
  width: 170%;
  height: 4%;
  background-color: var(--main-col-2);
}

/* first 문단 */
.quiz-center .quiz-center-item-first {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;

  width: 90%;
  margin-left: 5%;
}
.quiz-center .quiz-center-item-first img {
  /* width: 36px;
  height: 36px; */
  width: 40px;
  height: 40px;

  margin-right: 10%;
}

/* second 문단 */
.quiz-center .quiz-center-item-second {
  display: flex;
  flex-direction: row;
  align-items: center;

  width: 90%;
}
.quiz-center .quiz-center-item-second img {
  width: 45px;
  height: 50px;

  margin-left: 5%;
  margin-right: 5%;
}

/* third 문단 */
.quiz-center .quiz-center-item-third {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;

  width: 90%;
  margin-left: 5%;
}
.quiz-center .quiz-center-item-third img {
  width: 50px;
  height: 50px;

  margin-right: 10%;
}

/* fourth 문단 */
.quiz-center .quiz-center-item-fourth {
  display: flex;
  flex-direction: row;
  align-items: center;

  width: 90%;
  margin-left: 5%;
}

/* 시작하기 Button */
.quiz-center .quiz-center-button {
  margin-bottom: 10%;
  width: 85%;
}
</style>
