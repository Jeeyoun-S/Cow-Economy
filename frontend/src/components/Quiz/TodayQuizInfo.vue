<template>
  <div class="pa-8">
    <v-sheet class="pa-8" rounded="xl">
      <div class="py-2 d-flex flex-row align-center">
        <img
          class="mr-2"
          height="30"
          :src="require('@/assets/images/mypage/quiz/pencil.png')"
        />
        <span class="black-font xxxl-font">오늘의 퀴즈</span>
      </div>
      <v-divider class="my-1"></v-divider>
      <div class="xl-font">
        <!-- 1st -->
        <div class="py-3 d-flex flex-row justify-space-between">
          <v-sheet width="85%">
            7문제 중 5문제 이상 통과하면<br />
            경험치 +100을 얻을 수 있습니다.
          </v-sheet>
          <img
            width="10%"
            :src="require('@/assets/images/mypage/quiz/book.png')"
          />
        </div>
        <!-- 2ed -->
        <div class="py-3 d-flex flex-row justify-space-between">
          <img
            width="10%"
            aspect-ratio="1"
            :src="require('@/assets/images/mypage/quiz/question-mark.png')"
          />
          <v-sheet width="85%">
            문제로는 경제 용어에 대한<br />
            설명이 주어집니다.
          </v-sheet>
        </div>
        <!-- 3th -->
        <div class="py-3 d-flex flex-row justify-space-between">
          <v-sheet width="85%">
            보기 4개 중 설명에 맞는 단어를<br />
            선택하시면 됩니다.
          </v-sheet>
          <img
            width="10%"
            height="auto"
            :src="require('@/assets/images/mypage/quiz/winking-emoji.gif')"
          />
        </div>
        <!-- 4th -->
        <div class="py-3 d-flex flex-row justify-space-between">
          <div>
            한 문제에 10초의 시간⏰이 주어집니다.<br />
            1일 1회 도전 가능합니다.
          </div>
        </div>
        <div class="py-3">
          <v-btn block dark color="var(--main-col-2)" @click="moveQuiz()" large
            >시작하기</v-btn
          >
        </div>
      </div>
      <!-- 오늘의 Quiz 진입불가 Alert -->
      <div v-if="alertQuizFlag">
        <today-not-enter-modal></today-not-enter-modal>
      </div>
    </v-sheet>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
import TodayNotEnterModal from "./element/TodayNotEnterModal.vue";

const quizStore = "quizStore";

export default {
  name: "TodayQuizInfo",
  data() {
    return {
      alertQuizFlag: false, // 오늘의 Quiz 진행 여부에 따른 alert창
      isLoading: false, // 오늘의 Quiz가 준비될 동안 보여줄 Loading
    };
  },
  components: {
    TodayNotEnterModal,
  },
  computed: {
    ...mapState(quizStore, ["questions", "todayQuizFlag"]),
  },
  watch: {
    questions() {
      location.href = `${process.env.VUE_APP_BASE_URL}/today-quiz`;
    },
  },
  methods: {
    ...mapActions(quizStore, ["setExamQuestions"]),
    // [@Method] Quiz 페이지로 이동
    moveQuiz() {
      if (this.todayQuizFlag == false) {
        this.setExamQuestions(); // Quiz 문제 출제 - QuizStor
      } else {
        // ! 오늘 Quiz 다 했다고 alert 창 띄우기
        this.alertQuizFlag = true;
      }
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

  margin-left: 7%;
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
