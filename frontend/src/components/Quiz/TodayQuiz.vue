<template>
  <v-sheet v-if="going" class="pa-6 d-flex flex-column gradient" height="100vh">
    <!-- Check going start -->
    <TodayQuizStart @startQuiz="startQuiz"></TodayQuizStart>
    <!-- Title & Logo -->
    <v-sheet
      class="px-3 d-flex flex-row justify-space-between align-center"
      color="transparent"
      height="40px"
    >
      <span
        class="pink-gradient align-self-center point-md xxxxl-font white-col-1"
      >
        오늘의 Quiz
      </span>
      <img height="80%" :src="require('@/assets/images/logo.png')" />
    </v-sheet>
    <!-- 프로그레스 바 -->
    <step-progress></step-progress>
    <!-- 정답/오답 결과 출력 -->
    <!-- <div v-if="this.correctFlag == true">
      <answer-correct></answer-correct>
    </div>
    <div v-else-if="this.correctFlag == false && this.timeoutFlag == false">
      <answer-wrong></answer-wrong>
    </div>
    <div v-if="this.timeoutFlag == true">
      <answer-wrong></answer-wrong>
    </div> -->
    <!-- 문제 -->
    <div v-if="!answerResultFlag">
      <v-sheet
        rounded="xl"
        elevation="10"
        class="pa-5 d-flex flex-column justify-space-between flex-wrap"
      >
        <v-sheet width="100%" class="px-2">
          <v-sheet
            width="100%"
            class="pb-1 d-flex flex-row justify-space-between align-center"
          >
            <!-- 문제 번호 -->
            <div class="logo-b xxxxl-font">{{ this.index + 1 }}.</div>
            <!-- 스탑워치 -->
            <stop-watch v-if="start"></stop-watch>
          </v-sheet>
          <div class="my-2 b-font lg-font">
            {{ this.questions[this.index]["question"].split(".")[0] }}
          </div>
        </v-sheet>
        <v-sheet width="100%" color="transparent">
          <v-item-group v-model="selectedAnswer">
            <!-- 정답 후보 [4지선다] -->
            <div
              v-for="(answer, key) in this.questions[this.index]['answers']"
              :key="key"
              class="py-2"
            >
              <v-item v-slot="{ active, toggle }" :value="key">
                <v-btn
                  class="d-flex justify-start px-8"
                  :color="active ? 'var(--graph-1-col-5)' : ''"
                  :dark="active ? true : false"
                  :outlined="active ? false : true"
                  elevation="0"
                  @click="toggle"
                  width="100%"
                  rounded
                  :id="key"
                  large
                >
                  <span class="mr-2 black-font md-font">{{ key }}.</span>
                  <span class="md-font">{{ answer }}</span>
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
          @click="checkAnswer()"
        >
          <span>다음</span>
        </v-btn>
      </v-sheet>
    </div>
    <div v-else>
      <!-- 정답 -->
      <answer-correct
        v-if="correctFlag"
        @nextQuestion="nextQuestion"
      ></answer-correct>
      <!-- 오답 -->
      <answer-wrong
        v-else
        :selectedAnswer="selectedAnswer"
        @nextQuestion="nextQuestion"
      ></answer-wrong>
    </div>
    <!-- 최종 결과 -->
    <!-- <div v-if="this.index == 7"> -->
    <test-result-alert
      ref="result"
      :correctAnswer="correctAnswer"
    ></test-result-alert>
    <!-- </div> -->
    <answer-alert ref="answer" :correctFlag="correctFlag"></answer-alert>
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
import AnswerAlert from "./alert/AnswerAlert.vue";

import bgm from "@/assets/images/mypage/quiz/quiz_bgm.mp3";

// import { decrypt } from "@/store/js/crypto.js";

const quizStore = "quizStore";

export default {
  name: "TodayQuiz",
  data() {
    return {
      time: 15000, // 문제 풀이 시간, 10초(10000)
      timer: null, // 타이머 interval
      // timerVisiFlag: true, // 타이머 Front 표시 판별 Flag
      answerResultFlag: false, // 문제 푼 결과창 표시
      correctFlag: false, // 현재 문제 정답/오답 판별 Flag
      // timeoutFlag: null, // timeout 판별 Flag
      correctAnswer: 0, // 정답 개수
      wrongAnswer: 0, // 오답 개수
      count: 7, // 문제 수
      audio: null, // Audio 객체 (BGM)
      going: false, // 퀴즈 진행 여부
      start: false, // 퀴즈 시작 여부
      selectedAnswer: "z", // 선택된 답
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
    // 음악 중단
    if (this.audio) this.audio.pause();
  },
  // mounted() {
  //   decrypt;
  // },
  components: {
    StepProgress,
    StopWatch,
    AnswerCorrect,
    AnswerWrong,
    TestResultAlert,
    TodayQuizStart,
    AnswerAlert,
  },
  computed: {
    ...mapState(quizStore, ["index", "questions"]),
  },
  methods: {
    ...mapActions(quizStore, ["increaseIndex", "setQuizResult", "initQuiz"]),
    startQuiz() {
      // 퀴즈 시작
      this.start = true;
      this.timer = setTimeout(this.checkAnswer, this.time);
      // BGM 실행
      if (this.index == 0) this.play();
    },
    // [@Method] TimeOut에 따른 Data 값 변경
    // timeOut() {
    //   if (this.correctFlag == null) {
    //     this.wrongAnswer++;
    //     this.timeoutFlag = true;
    //     this.checkAnswer(null);
    //   }
    // },
    // [@Method] 선택한 답변 정답 확인
    checkAnswer() {
      clearTimeout(this.timer);

      // console.log("#21# 선택한 정답 번호: ", key);

      // i) 시간 초과
      // if (this.timeoutFlag == true) {
      //   this.timeoutFlag = true;
      //   this.wrongAnswer++;
      //   this.correctFlag = false;
      //   this.timerVisiFlag = false;
      // }
      // ii) 정답
      if (
        this.selectedAnswer.charCodeAt() ==
        this.questions[this.index]["correctAnswer"]
        // decrypt(
        //   this.questions[this.index]["correctAnswer"],
        //   process.env.VUE_APP_CRYPT_KEY
        // )
      ) {
        this.correctAnswer++;
        this.correctFlag = true;
        // this.timerVisiFlag = false;
      }
      // iii) 오답
      else {
        this.wrongAnswer++;
        this.correctFlag = false;
        // this.timerVisiFlag = false;
      }

      // 정답 여부 보여주기
      this.$refs.answer.openDialog();
      // this.answerResultFlag = true;
      setTimeout(this.showAnswer, 700); // 0.5초 후 결과창으로 넘어감
    },
    // [@Method] 선택한 답변 정답 확인
    showAnswer() {
      // 문제 결과 Alert창 닫고
      this.$refs.answer.closeDialog();
      // 결과 보기
      this.answerResultFlag = true;
    },
    // [@Method] 다음 문제로 이동 + 결과 반영
    nextQuestion() {
      // 문제 끝 > 결과 출력
      if (this.index == this.count - 1) {
        // this.timerVisiFlag = false;
        // this.timeoutFlag = false;

        // [@Method] Quiz 결과 저장 (quizStore)
        this.setQuizResult(this.correctAnswer).then(() => {
          // 결과창 열기
          this.$refs.result.openDialog();
        });
      } else {
        this.increaseIndex(this.index); // [@Method] quizStore - index 증가

        this.answerResultFlag = false; // 정답 화면에서 넘어가기
        this.selectedAnswer = "z"; // 선택한 정답 초기화
        this.correctFlag = false; // 맞았는지 확인 초기화

        this.timer = setTimeout(this.checkAnswer, this.time);
      }

      // this.correctFlag = null;
      // this.timeoutFlag = false;
      // this.timerVisiFlag = true;

      // this.timer = setInterval(this.timeOut, this.time);
    },
    // [@Method] BGM 재생
    play() {
      this.audio = new Audio(bgm);
      this.audio.loop = true; // 반복 재생
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
