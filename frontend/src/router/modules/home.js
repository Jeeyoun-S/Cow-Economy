import HomeHeader from "@/views/Header/HomeHeader.vue";
import HomePage from "@/components/HomePage/HomePage.vue";
import TheFooter from "@/views/TheFooter.vue";

const home = [
  {
    path: "/home",
    name: "home",
    components: {
      header: HomeHeader,
      default: HomePage,
      footer: TheFooter
    },
  },
]

export default home;