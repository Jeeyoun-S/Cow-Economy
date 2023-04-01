import InfoHeader from "@/views/Header/InfoHeader.vue";
import InfoPage from "@/components/MainPage/InfoPage.vue";

const main = [
  {
    path: "/",
    name: "main",
    components: {
      header: InfoHeader,
      default: InfoPage,
    },
  },
];

export default main;
