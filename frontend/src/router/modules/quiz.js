import HomeHeader from "@/views/Header/HomeHeader.vue";
import TodayQuiz from "@/components/Quiz/TodayQuiz.vue";

const quiz = [
  {
    path: "/quiz",
    name: "quiz",
    components: {
      header: HomeHeader,
      default: TodayQuiz
    },
  },
]

export default quiz;