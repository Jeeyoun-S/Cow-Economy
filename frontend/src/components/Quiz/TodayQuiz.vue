<template>
  <div>quiz</div>
</template>

<script>
export default {

    // BGM 실행
    if (this.index == 0) this.play();
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
      // console.log("#21# 정답/오답 확인: ", this.correctFlag);

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
      // console.log("#21# alert창 닫기: ", this.moveTry);
    },
  },
};
</script>

<style></style>
