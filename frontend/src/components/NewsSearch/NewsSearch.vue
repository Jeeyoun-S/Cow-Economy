<template>
  <NewsSearchBeforeSearch v-if="!searched"></NewsSearchBeforeSearch>
  <div v-else>
    <NewsSearchNoResult v-if="isNoResult"></NewsSearchNoResult>
    <NewsSearchResult v-else :newsList="news"></NewsSearchResult>
  </div>
</template>

<script>
import NewsSearchBeforeSearch from '@/components/NewsSearch/NewsSearchBeforeSearch.vue';
import NewsSearchNoResult from './NewsSearchNoResult.vue';
import NewsSearchResult from "./NewsSearchResult.vue"
import { mapState } from "vuex";

export default {
  name: "NewsSearch",
  computed: {
    ...mapState("newsStore", ["searched", "news"]),
    isNoResult() {
      return this.news.length === 0;
    }
  },
  components: {
    NewsSearchBeforeSearch,
    NewsSearchNoResult,
    NewsSearchResult,
  }
}
</script>

<style>

</style>