import SearchHeader from "@/views/Header/SearchHeader.vue";
import NewsSearch from "@/components/NewsSearch/NewsSearch.vue";

const search = [
  {
    path: "/search",
    name: "search",
    components: {
      header: SearchHeader,
      default: NewsSearch
    },
  },
]

export default search;