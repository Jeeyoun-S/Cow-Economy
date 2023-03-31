<template>
  <div class="pa-8">
    <v-sheet class="pa-9" rounded="lg">
      <div class="pb-2 d-flex flex-row align-center">
        <img
          class="mr-2"
          height="30"
          :src="require('@/assets/images/emoji/pencil.png')"
        />
        <span class="black-font xxxxl-font">오늘의 퀴즈</span>
      </div>
      <v-sheet class="my-1" height="2" color="var(--main-col-2)"></v-sheet>
      <div>
        <!-- 1st -->
        <v-sheet
          class="first pt-3 d-flex flex-row justify-start align-center white-shadow"
          height="80"
        >
          <v-sheet color="transparent">
            7문제 중 5문제 이상 통과하면<br />
            경험치 +100을 얻을 수 있습니다.
          </v-sheet>
        </v-sheet>
        <!-- 2ed -->
        <v-sheet
          class="second py-3 d-flex flex-row justify-start align-center white-shadow"
          height="80"
        >
          <v-sheet class="ml-10 pl-8" color="transparent">
            문제로는 경제 용어에 대한<br />
            설명이 주어집니다.
          </v-sheet>
        </v-sheet>
        <!-- 3th -->
        <v-sheet
          class="third py-3 d-flex flex-row justify-start align-center white-shadow"
          height="80"
        >
          <v-sheet color="transparent">
            보기 4개 중 설명에 맞는 단어를<br />
            선택하시면 됩니다.
          </v-sheet>
        </v-sheet>
        <!-- 4th -->
        <div class="py-3 d-flex flex-row justify-space-between">
          <div>
            <div>
              한 문제에 10초의 시간<img
                height="17"
                :src="require('@/assets/images/emoji/alarm_clock.png')"
              />이 주어집니다.
            </div>
            <div>1일 1회 도전 가능합니다.</div>
          </div>
        </div>
        <div class="pt-3">
          <v-btn block dark color="var(--main-col-2)" @click="moveQuiz()" large
            >시작하기</v-btn
          >
        </div>
      </div>
      <!-- 오늘의 Quiz 진입불가 Alert -->
      <!-- i) 하루에 한 번 기회 소진 -->
      <today-not-enter-alert ref="todaynot"></today-not-enter-alert>
      <!-- ii) Quiz 출제를 위한 경제단어 부족 -->
      <shortage-word-alert ref="shortage"></shortage-word-alert>
      <!-- iii) 로그인 안 된 상태 -->
      <today-quiz-not-user ref="notuser"></today-quiz-not-user>
    </v-sheet>
  </div>
</template>

<script>
import { mapActions, mapState, mapGetters } from "vuex";
import ShortageWordAlert from "./element/ShortageWordAlert.vue";
import TodayNotEnterAlert from "./element/TodayNotEnterAlert.vue";
import TodayQuizNotUser from "./element/TodayQuizNotUser.vue";

const quizStore = "quizStore";

export default {
  name: "TodayQuizInfo",
  components: {
    ShortageWordAlert,
    TodayNotEnterAlert,
    TodayQuizNotUser,
  },
  computed: {
    ...mapState(quizStore, ["questions", "todayQuizFlag"]),
    ...mapGetters("userStore", ["isLoggedIn"]),
  },
  watch: {
    questions() {
      if (this.questions.length < 7) {
        // Modal 창 열기
        this.$refs.shortage.openDialog();
      } else {
        this.$router.push("/today-quiz");
      }
    },
  },
  created() {
    // 로그인된 상태라면
    if (this.isLoggedIn) {
      // [@Method] Quiz 진행 여부 판단
      this.checkTodayQuiz();
    }
    // console.log("# Quiz 진행 여부 확인[true = 가능]: ", this.todayQuizFlag);
  },
  methods: {
    ...mapActions(quizStore, ["setExamQuestions", "checkTodayQuiz"]),
    // [@Method] Quiz 페이지로 이동 or 알림창 출력
    moveQuiz() {
      if (this.isLoggedIn) {
        if (this.todayQuizFlag == true) {
          this.setExamQuestions(); // [@Method] Quiz 문제 출제
        } else {
          this.$refs.todaynot.openDialog(); // ! 오늘 Quiz 다 했다고 alert 창 띄우기
        }
      } else {
        this.$refs.notuser.openDialog(); // 로그인 안 한 상태라고 창 띄우기
      }
    },
  },
};
</script>

<style scoped>
.first {
  background-image: url("@/assets/images/emoji/books.png");
  background-size: 70px;
  background-position-x: right;
  background-position-y: bottom;
}
.second {
  background-image: url("@/assets/images/emoji/question-mark.png");
  background-size: 70px;
  background-position-x: left;
  background-position-y: bottom;
}
.third {
  background-image: url("@/assets/images/emoji/winking-emoji.gif");
  background-size: 70px;
  background-position-x: right;
  background-position-y: bottom;
}
</style>
