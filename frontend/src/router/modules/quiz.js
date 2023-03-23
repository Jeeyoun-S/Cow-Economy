import HomeHeader from "@/views/Header/HomeHeader.vue";
import TodayQuizInfo from "@/components/Quiz/TodayQuizInfo.vue";
import TodayQuiz from "@/components/Quiz/TodayQuiz.vue";

const quiz = [
  // {
  //   path: "/quiz",
  //   name: "quiz",
  //   components: {
  //     header: HomeHeader,
  //     default: TodayQuiz,
  //   },
  // },
  // Quiz Info 페이지
  {
    path: "/quiz",
    name: "quiz",
    components: {
      header: HomeHeader,
      default: TodayQuizInfo,
    },
  },
  // Quiz 페이지
  {
    path: "/today-quiz",
    name: "today-quiz",
    components: {
      header: HomeHeader,
      default: TodayQuiz,
    },
  },
];

export default quiz;
