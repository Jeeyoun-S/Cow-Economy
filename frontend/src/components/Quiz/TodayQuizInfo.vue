<template>
  <div class="pa-5">
    <v-sheet class="pa-2" rounded="lg" color="transparent">
      <div class="pb-2 d-flex flex-row justify-space-between">
        <div class="d-flex flex-column narrow white-col-1">
          <span class="xxl-font blue-gredient point-b">오늘의</span
          ><span class="point-b blue-gredient x-big-large-font"
            >경제 용어 Quiz</span
          >
        </div>
        <img
          class="mr-2"
          height="50"
          :src="require('@/assets/images/emoji/pencil.png')"
        />
      </div>
      <!-- <v-sheet class="my-1" height="2" color="var(--main-col-2)"></v-sheet> -->
      <div class="my-3 d-flex flex-row justify-space-between flex-wrap">
        <v-card
          v-for="(info, index) in infos"
          :key="index"
          width="47%"
          height="180"
          elevation="0"
          class="point-th mb-3 pa-3 d-flex flex-column justify-center align-center"
          rounded="xl"
        >
          <img class="mb-5" :src="info.image" height="60" />
          <div class="sm-font">{{ info.message[0] }}</div>
          <div class="sm-font">{{ info.message[1] }}</div>
          <div class="sm-font" v-if="info.message[2]">
            {{ info.message[2] }}
          </div>
        </v-card>
      </div>
      <v-btn
        class="gradient-2"
        rounded
        block
        dark
        color="var(--main-col-2)"
        @click="moveQuiz()"
        large
        >시작하기</v-btn
      >
      <!-- 오늘의 Quiz 진입불가 Alert -->
      <!-- i) 하루에 한 번 기회 소진 -->
      <today-not-enter-alert ref="todaynot"></today-not-enter-alert>
      <!-- ii) Quiz 출제를 위한 경제단어 부족 -->
      <shortage-word-alert ref="shortage"></shortage-word-alert>
      <!-- iii) 로그인 안 된 상태 -->
      <today-quiz-not-user ref="notuser"></today-quiz-not-user>
    </v-sheet>
    <TheQuizLoading :loading="loading"></TheQuizLoading>
  </div>
</template>

<script>
import { mapActions, mapState, mapGetters } from "vuex";
import ShortageWordAlert from "./alert/ShortageWordAlert.vue";
import TodayNotEnterAlert from "./alert/TodayNotEnterAlert.vue";
import TodayQuizNotUser from "./alert/TodayQuizNotUser.vue";
import TheQuizLoading from "@/views/TheQuizLoading.vue";

const quizStore = "quizStore";

export default {
  name: "TodayQuizInfo",
  components: {
    ShortageWordAlert,
    TodayNotEnterAlert,
    TodayQuizNotUser,
    TheQuizLoading,
  },
  data() {
    return {
      loading: false, // 퀴즈 출제 시도 시 로딩 창 활성화 여부
      infos: [
        {
          message: ["읽었던 기사 속", "단어를", "맞추는 퀴즈"],
          image: require("@/assets/images/emoji/question-mark.png"),
        },
        {
          message: ["10초 내에", "보기 4개 중", "답을 선택"],
          image: require("@/assets/images/emoji/winking-emoji.gif"),
        },
        {
          message: ["7문제 중 5개", "이상 맞추면", "경험치 100 증가"],
          image: require("@/assets/images/emoji/books.png"),
        },
        {
          message: ["1일 1회", "도전 가능", ""],
          image: require("@/assets/images/emoji/alarm_clock.png"),
        },
      ],
    };
  },
  computed: {
    ...mapState(quizStore, ["questions", "todayQuizFlag"]),
    ...mapGetters("userStore", ["isLoggedIn"]),
  },
  watch: {
    questions() {
      if (0 < this.questions.length < 7) {
        // Modal 창 열기
        this.$refs.shortage.openDialog();
      } else {
        this.$router.push("/today-quiz");
      }
    },
  },
  async created() {
    // 로그인된 상태라면
    if (this.isLoggedIn) {
      // [@Method] Quiz 진행 여부 판단
      await this.checkTodayQuiz();
    }
    // console.log("# Quiz 진행 여부 확인[true = 가능]: ", this.todayQuizFlag);
  },
  methods: {
    ...mapActions(quizStore, ["setExamQuestions", "checkTodayQuiz"]),
    // [@Method] Quiz 페이지로 이동 or 알림창 출력
    async moveQuiz() {
      // 로그인 된 상태인 경우
      if (this.isLoggedIn) {
        // 퀴즈를 푼 적이 없는 경우
        if (this.todayQuizFlag == true) {
          // 퀴즈 출제
          await this.setExamQuestions().then((res) => {
            // 퀴즈 출제가 가능한 경우
            if (res) {
              this.loading = true;
            }
            // 퀴즈 출제가 불가능한 경우
            else {
              this.$refs.shortage.openDialog();
            }
          }); // [@Method] Quiz 문제 출제
        }
        // 퀴즈는 푼 적이 있는 경우
        else {
          this.$refs.todaynot.openDialog(); // ! 오늘 Quiz 다 했다고 alert 창 띄우기
        }
      }
      // 로그인 안 된 상태인 경우
      else {
        this.$refs.notuser.openDialog(); // 로그인 안 한 상태라고 창 띄우기
      }
    },
  },
};
</script>

<style scoped>
/* .first {
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
} */
</style>
