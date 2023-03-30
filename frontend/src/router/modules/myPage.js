import HomeHeader from "@/views/Header/HomeHeader.vue";
import MyPage from "@/components/MyPage/MyPage.vue";

const myPage = [
  {
    path: "/my-page",
    name: "myPage",
    components: {
      header: HomeHeader,
      default: MyPage,
    },
  },
]

export default myPage;