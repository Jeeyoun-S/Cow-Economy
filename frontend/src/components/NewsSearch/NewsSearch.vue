<template>
  <NewsSearchBeforeSearch
    class="main-container"
    v-if="!beforeSearch"
  ></NewsSearchBeforeSearch>
  <div :class="{ 'main-container': isNoResult }" v-else>
    <NewsSearchNoResult v-if="isNoResult"></NewsSearchNoResult>
    <NewsSearchResult v-else :newsList="searchNews"></NewsSearchResult>
  </div>
</template>

<script>
import NewsSearchBeforeSearch from "@/components/NewsSearch/NewsSearchBeforeSearch.vue";
import NewsSearchNoResult from "./NewsSearchNoResult.vue";
import NewsSearchResult from "./NewsSearchResult.vue";
import { mapState, mapGetters } from "vuex";

export default {
  name: "NewsSearch",
  components: {
    NewsSearchBeforeSearch,
    NewsSearchNoResult,
    NewsSearchResult,
  },
  computed: {
    ...mapState("newsStore", ["beforeSearch", "searched", "news"]),
    ...mapGetters("newsStore", ["searchNews"]),
    isNoResult() {
      // console.log(this.searched + " " + this.searchNews.length);
      if (this.searched === false && this.searchNews.length === 0) {
        // console.log("결과없음")
        return true;
      } else return false;
    },
  },
};
</script>

<style>
.main-container {
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
.no-result-container {
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
}
</style>
