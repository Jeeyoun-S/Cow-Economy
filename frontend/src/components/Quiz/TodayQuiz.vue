<template>
  <div class="quiz">
    <!-- 프로그레스 바 -->
    <div><step-progress></step-progress></div>
    <!-- 스탑워치 -->
    <div class="stopWatch" v-if="this.timerVisiFlag == true">
      <stop-watch></stop-watch>
    </div>
    <!-- 페이지 이동 금지 알림 -->
    <v-alert dense border="left" shaped type="error" v-show="moveTry"
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
    <div class="quizBox" v-if="this.index < count">
      <div class="questionTxt">
        <!-- {{ questions[this.index]["question"] }} -->
        {{ this.questions[this.index]["question"] }}
      </div>
      <!-- <v-card-content
        class="answerBox"
        :key="key"
        v-for="(answer, key) in questions[this.index]['answers']"
      > -->
      <v-card-content
        class="answerBox"
        :key="key"
        v-for="(answer, key) in this.questions[this.index]['answers']"
      >
        <!-- 정답 후보 -->
        <v-btn
          class="answerBtn"
          style="height: 150px"
          :color="`var(--quiz-1-col-6)`"
          :id="key"
          :value="key"
          @click="checkAnswer(key)"
          >{{ answer }}</v-btn
        >
      </v-card-content>
    </div>
    <!-- 최종 결과 -->
    <div v-if="this.index == 7">
      <test-result-modal></test-result-modal>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
import StepProgress from "./element/StepProgress.vue";
import StopWatch from "./element/StopWatch.vue";

import AnswerCorrect from "./element/AnswerCorrect.vue";
import AnswerWrong from "./element/AnswerWrong.vue";
import TestResultModal from "./element/TestResultModal.vue";

import bgm from "@/assets/images/mypage/quiz/quiz_bgm.mp3";

const quizStore = "quizStore";

export default {
  name: "TodayQuiz",
  data() {
    return {
      // index: 0, // 전체 문제 no
      time: 10000, // 문제 풀이 시간, 10초(10000)
      timer: null, // 타이머 interval
      timerVisiFlag: true, // 타이머 Front 표시 판별 Flag
      correctFlag: null, // 현재 문제 정답/오답 판별 Flag
      timeoutFlag: null, // timeout 판별 Flag
      endFlag: false, // TEST 종료 판별 Flag
      correctAnswer: 0, // 정답 개수
      wrongAnswer: 0, // 오답 개수
      count: 7, // 문제 수
      // questions: [
      //   {
      //     question:
      //       "[ ]란 OECD 기준에 따라 가구를 소득 순으로 나열했을 때, 한가운데에 있는 가구소득(중위소득)의 50~150% 범위에 속한 가구를 뜻한다.",
      //     answers: {
      //       a: "중산층가구",
      //       b: "0.5인 가구",
      //       c: "중위소득",
      //       d: "4차 산업혁명",
      //     },
      //     correctAnswer: "a",
      //   },
      //   {
      //     question: "Q. 문제 - 2",
      //     answers: {
      //       a: "2000",
      //       b: "2001",
      //       c: "2002",
      //       d: "2003",
      //     },
      //     correctAnswer: "b",
      //   },
      //   {
      //     question: "Q. 문제 - 3",
      //     answers: {
      //       a: "3000",
      //       b: "3001",
      //       c: "3002",
      //       d: "3003",
      //     },
      //     correctAnswer: "c",
      //   },
      //   {
      //     question: "Q. 문제 - 4",
      //     answers: {
      //       a: "4000",
      //       b: "4001",
      //       c: "4002",
      //       d: "4003",
      //     },
      //     correctAnswer: "d",
      //   },
      //   {
      //     question: "Q. 문제 - 5",
      //     answers: {
      //       a: "5000",
      //       b: "5001",
      //       c: "5002",
      //       d: "5003",
      //     },
      //     correctAnswer: "a",
      //   },
      //   {
      //     question: "Q. 문제 - 6",
      //     answers: {
      //       a: "6000",
      //       b: "6001",
      //       c: "6002",
      //       d: "6003",
      //     },
      //     correctAnswer: "b",
      //   },
      //   {
      //     question: "Q. 문제 - 7",
      //     answers: {
      //       a: "7000",
      //       b: "7001",
      //       c: "7002",
      //       d: "7003",
      //     },
      //     correctAnswer: "c",
      //   },
      // ],
      audio: null, // Audio 객체 (BGM)
      moveTry: false, // 다른 페이지로 이동 시도 여부
    };
  },
  watch: {
    moveTry: {},
  },
  created() {
    this.timer = setInterval(this.timeOut, this.time);

    // BGM 실행
    if (this.index == 0) this.play();

    // Quiz
    console.log("#21# [info 페이지] Quiz 확인: ", this.questions);
  },
  components: {
    StepProgress,
    StopWatch,
    AnswerCorrect,
    AnswerWrong,
    TestResultModal,
  },
  computed: {
    ...mapState(quizStore, ["index", "questions", "todayQuizFlag"]),
  },
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
      // console.log("#21# 선택한 정답 번호: ", key);
      // i) 시간 초과
      if (this.timeoutFlag == true) {
        this.timeoutFlag = true;
        this.wrongAnswer++;
        this.correctFlag = false;
        this.timerVisiFlag = false;
      }
      // ii) 정답
      else if (key == this.questions[this.index]["correctAnswer"]) {
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
      console.log("#21# 정답/오답 확인: ", this.correctFlag);

      clearInterval(this.timer);
      // setTimeout(this.nextQuestion, 5000); // 5초 후 다음 문제로 넘어감
      setTimeout(this.nextQuestion, 2000); // 2초 후 다음 문제로 넘어감
    },
    // [@Method] 다음 문제로 이동
    nextQuestion() {
      this.increaseIndex(this.index); // [@Method] quizStore - index 증가
      this.correctFlag = null;
      this.timeoutFlag = false;
      this.timerVisiFlag = true;

      this.timer = setInterval(this.timeOut, this.time);
      // 문제 끝 > 결과 출력
      if (this.index == 7) {
        this.timerVisiFlag = false;
        this.timeoutFlag = false;
        clearInterval(this.timer);

        // [@Method] Quiz 결과 저장 (quizStore)
        this.setQuizResult(this.correctAnswer);

        // timer stop
        clearInterval(this.timer);
        this.endFlag = true;
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
    // [@Method] Alert 창 노출
    showAlert() {
      this.moveTry = !this.moveTry;
      console.log("#21# alert창 닫기: ", this.moveTry);
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
