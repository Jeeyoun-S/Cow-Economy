import HomeHeader from "@/views/Header/HomeHeader.vue";
import NewsList from "@/components/NewsList/NewsList.vue";
import TheFooter from "@/views/TheFooter.vue";
import NewsHeader from "@/views/Header/NewsHeader.vue";
import NewsDetail from "@/components/NewsDetail/NewsDetail.vue";

const news = [
  {
    path: "/news",
    name: "newsList",
    components: {
      header: HomeHeader,
      default: NewsList,
      footer: TheFooter
    },
    meta: { requireAuth: true },
  },
  {
    path: "/news/:id",
    name: "newsDetail",
    components: {
      header: NewsHeader,
      default: NewsDetail,
      footer: TheFooter
    },
    meta: { requireAuth: true },
  },
]

export default news;