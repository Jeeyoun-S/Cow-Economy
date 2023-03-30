<template>
  <v-sheet class="pa-8" color="transparent">
    <!-- 프로그레스 바 -->
    <div><step-progress></step-progress></div>
    <!-- 페이지 이동 금지 알림 -->
    <v-alert dense shaped type="error" v-show="moveTry"
      >Quiz를 다 풀어주세요! •̀ㅅ•́</v-alert
    >
    <!-- 정답/오답 결과 출력 -->
    <div v-if="this.correctFlag == true"><answer-correct></answer-correct></div>
    <div v-else-if="this.correctFlag == false && this.timeoutFlag == false">
      <answer-wrong></answer-wrong>
    </div>
    <div v-if="this.timeoutFlag == true">
      <answer-wrong></answer-wrong>
    </div>
    <!-- 문제 -->
    <div v-if="this.index < count">
      <v-sheet
        color="transparent"
        class="pa-5 b-font lg-font d-flex flex-row align-center justify-space-between"
      >
        <!-- 스탑워치 -->
        <div v-if="this.timerVisiFlag == true">
          <stop-watch></stop-watch>
        </div>
        <!-- ! 현재는 잘라서 넣는 방식으로 수정 필요 ! -->
        <v-sheet width="80%" color="transparent">{{
          this.questions[this.index]["question"].split(".")[0]
        }}</v-sheet>
      </v-sheet>
      <v-sheet
        color="transparent"
        height="350px"
        class="d-flex flex-row justify-space-between flex-wrap"
      >
        <!-- 정답 후보 [4지선다] -->
        <v-btn
          class="quiz spacing-all"
          elevation="0"
          width="47%"
          height="150px"
          v-for="(answer, key) in this.questions[this.index]['answers']"
          color="white"
          :key="key"
          :id="key"
          :value="key"
          :disabled="answerResultFlag"
          @click="checkAnswer(key)"
        >
          {{ answer }}
        </v-btn>
      </v-sheet>
    </div>
    <!-- 최종 결과 -->
    <div v-if="this.index == 7">
      <test-result-alert></test-result-alert>
    </div>
  </v-sheet>
</template>

<script>
import { mapActions, mapState } from "vuex";
import StepProgress from "./element/StepProgress.vue";
import StopWatch from "./element/StopWatch.vue";

import AnswerCorrect from "./element/AnswerCorrect.vue";
import AnswerWrong from "./element/AnswerWrong.vue";
import TestResultAlert from "./element/TestResultAlert.vue";

import bgm from "@/assets/images/mypage/quiz/quiz_bgm.mp3";

import { decrypt } from "@/store/js/crypto.js";

const quizStore = "quizStore";

export default {
  name: "TodayQuiz",
  data() {
    return {
      time: 10000, // 문제 풀이 시간, 10초(10000)
      timer: null, // 타이머 interval
      timerVisiFlag: true, // 타이머 Front 표시 판별 Flag
      answerResultFlag: false, // 4지선다 버튼 disabled 판별 Flag
      correctFlag: null, // 현재 문제 정답/오답 판별 Flag
      timeoutFlag: null, // timeout 판별 Flag
      correctAnswer: 0, // 정답 개수
      wrongAnswer: 0, // 오답 개수
      count: 7, // 문제 수
      audio: null, // Audio 객체 (BGM)
      moveTry: false, // 다른 페이지로 이동 시도 여부
    };
  },
  created() {
    // this.timer = setInterval(this.timeOut, this.time);

    // BGM 실행
    if (this.index == 0) this.play();
  },
  mounted() {
    decrypt;
  },
  components: {
    StepProgress,
    StopWatch,
    AnswerCorrect,
    AnswerWrong,
    TestResultAlert,
  },
  computed: {
    ...mapState(quizStore, ["index", "questions", "todayQuizFlag"]),
  },
  // Quiz를 도전하던 도중 나갈려고 할 경우 막기
  beforeRouteLeave(next) {
    if (this.todayQuizFlag == true) {
      next();
    } else {
      this.moveTry = true;
      setTimeout(this.showAlert, 2000);
    }
  },
  methods: {
    ...mapActions(quizStore, ["increaseIndex", "setQuizResult"]),
    // [@Method] 선택한 답변 정답 확인
    checkAnswer(key) {
      this.answerResultFlag = true;
      // console.log("#21# 선택한 정답 번호: ", key);

      // i) 시간 초과
      if (this.timeoutFlag == true) {
        this.timeoutFlag = true;
        this.wrongAnswer++;
        this.correctFlag = false;
        this.timerVisiFlag = false;
      }
      // ii) 정답
      else if (
        key ==
        decrypt(
          this.questions[this.index]["correctAnswer"],
          process.env.VUE_APP_CRYPT_KEY
        )
      ) {
        this.correctAnswer++;
        this.correctFlag = true;
        this.timerVisiFlag = false;
      }
      // iii) 오답
      else {
        this.wrongAnswer++;
        this.correctFlag = false;
        this.timerVisiFlag = false;
      }

      clearInterval(this.timer);
      setTimeout(this.nextQuestion, 2000); // 2초 후 다음 문제로 넘어감
    },
    // [@Method] 다음 문제로 이동 + 결과 반영
    nextQuestion() {
      this.increaseIndex(this.index); // [@Method] quizStore - index 증가
      this.correctFlag = null;
      this.timeoutFlag = false;
      this.timerVisiFlag = true;
      this.answerResultFlag = false;

      // this.timer = setInterval(this.timeOut, this.time);
      // 문제 끝 > 결과 출력
      if (this.index == 7) {
        this.timerVisiFlag = false;
        this.timeoutFlag = false;

        // [@Method] Quiz 결과 저장 (quizStore)
        this.setQuizResult(this.correctAnswer);

        // timer stop
        clearInterval(this.timer);
      }
    },
    // [@Method] TimeOut에 따른 Data 값 변경
    timeOut() {
      if (this.correctFlag == null) {
        this.wrongAnswer++;
        this.timeoutFlag = true;

        this.checkAnswer(null);
      }
    },
    // [@Method] BGM 재생
    play() {
      this.audio = new Audio(bgm);
      // this.audio.loop = true; // 반복 재생
      this.audio.volume = 0.3;
      this.audio.play();
    },
    // [@Method] Alert 창 노출 여부
    showAlert() {
      this.moveTry = !this.moveTry;
    },
  },
};
</script>

<style>
/* 퀴즈 */
.quizBox {
  font-family: var(--main-font-2);

  margin-left: 8%;
  margin-right: 8%;
}

/* 퀴즈 문제 Txt */
.questionTxt {
  text-align: center;
  font-size: 20px;
  letter-spacing: normal;
  line-height: 28px;

  margin-top: 3%;
  margin-bottom: 10%;
}

/* 퀴즈 답변 Button (4지선다) */
.answerBtn {
  width: 150px;
  height: 150px;

  margin-left: 9%;
  margin-bottom: 9%;
}
</style>
