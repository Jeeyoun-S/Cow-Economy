import HomeHeader from "@/views/Header/HomeHeader.vue";
import MyPage from "@/components/MyPage/MyPage.vue";
import Oauth from "@/components/MyPage/OAuthRedirectHandler.vue";
const myPage = [
  {
    path: "/my-page",
    name: "myPage",
    components: {
      header: HomeHeader,
      default: MyPage
    },
  },
  {
    path: "/oauth/callback/kakao",
    name: "Oauth",
    components: {
      header: HomeHeader,
      default: Oauth
    },
  },
]

export default myPage;