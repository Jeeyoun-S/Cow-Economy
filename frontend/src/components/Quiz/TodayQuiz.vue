<template>
  <v-sheet v-if="going" class="pa-8 d-flex flex-column gradient" height="100vh">
    <!-- Check going start -->
    <TodayQuizStart @startQuiz="startQuiz"></TodayQuizStart>
    <!-- Title & Logo -->
    <v-sheet
      class="d-flex flex-row justify-space-between align-center"
      color="transparent"
      height="40px"
    >
      <span class="align-self-center white-col-1 point-b xxxxl-font">
        오늘의 Quiz
      </span>
      <img height="100%" :src="require('@/assets/images/logo.png')" />
    </v-sheet>
    <!-- 프로그레스 바 -->
    <step-progress></step-progress>
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
        rounded="xl"
        elevation="10"
        class="pa-5 d-flex flex-column justify-space-between flex-wrap"
      >
        <v-sheet width="100%" class="px-2">
          <v-sheet
            width="100%"
            class="d-flex flex-row justify-space-between align-center"
          >
            <!-- 문제 번호 -->
            <div class="black-font xxxxl-font">{{ this.index + 1 }}.</div>
            <!-- 스탑워치 -->
            <stop-watch></stop-watch>
          </v-sheet>
          <div class="my-2 b-font lg-font">
            {{ this.questions[this.index]["question"].split(".")[0] }}
          </div>
        </v-sheet>
        <v-sheet width="100%" color="transparent"
          ><v-item-group>
            <!-- 정답 후보 [4지선다] -->
            <div
              v-for="(answer, key) in this.questions[this.index]['answers']"
              :key="key"
              class="py-2"
            >
              <v-item v-slot="{ active, toggle }">
                <v-btn
                  class="d-flex justify-start px-8"
                  :color="active ? 'var(--graph-1-col-5)' : ''"
                  :dark="active ? true : false"
                  :outlined="active ? false : true"
                  elevation="0"
                  @click="toggle"
                  width="100%"
                  height="50"
                  rounded
                  :id="key"
                  :value="key"
                  :disabled="answerResultFlag"
                >
                  <span class="mr-2 black-font">{{ key }}.</span>
                  <span>{{ answer }}</span>
                </v-btn>
              </v-item>
            </div>
          </v-item-group>
        </v-sheet>
        <v-btn
          class="px-6 mt-2 gradient-2 align-self-end"
          rounded
          elevation="0"
          dark
          >다음</v-btn
        >
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
import TestResultAlert from "./alert/TestResultAlert.vue";
import TodayQuizStart from "./alert/TodayQuizStart.vue";

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
      going: false, // 퀴즈 진행 여부
    };
  },
  async created() {
    // 새로 고침 또는 시작 버튼이 아닌 경로로 접근 시
    if (this.questions.length < 7) this.$router.push("/quiz");
    // 정상 접근 시, 페이지 로드
    else this.going = true;
  },
  destroyed() {
    // vuex에 저장된 값 초기화
    this.initQuiz();
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
    TodayQuizStart,
  },
  computed: {
    ...mapState(quizStore, ["index", "questions", "todayQuizFlag"]),
  },
  methods: {
    ...mapActions(quizStore, ["increaseIndex", "setQuizResult", "initQuiz"]),
    startQuiz() {
      // 퀴즈 시작

      // this.timer = setInterval(this.timeOut, this.time);

      // BGM 실행
      if (this.index == 0) this.play();
    },
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
      // setTimeout(this.nextQuestion, 2000); // 2초 후 다음 문제로 넘어감
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
    // showAlert() {
    //   this.moveTry = !this.moveTry;
    // },
  },
};
</script>

<style>
/* 퀴즈 */
/* .quizBox {
  font-family: var(--main-font-2);

  margin-left: 8%;
  margin-right: 8%;
} */

/* 퀴즈 문제 Txt */
/* .questionTxt {
  text-align: center;
  font-size: 20px;
  letter-spacing: normal;
  line-height: 28px;

  margin-top: 3%;
  margin-bottom: 10%;
} */

/* 퀴즈 답변 Button (4지선다) */
/* .answerBtn {
  width: 150px;
  height: 150px;

  margin-left: 9%;
  margin-bottom: 9%;
} */
</style>
