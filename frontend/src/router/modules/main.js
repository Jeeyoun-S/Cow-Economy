import MainHeader from "@/views/Header/MainHeader.vue";
import MainPage from "@/components/MainPage/MainPage.vue";

const main = [
  {
    path: "/",
    name: "main",
    components: {
      header: MainHeader,
      default: MainPage,
    },
  },
]

export default main;