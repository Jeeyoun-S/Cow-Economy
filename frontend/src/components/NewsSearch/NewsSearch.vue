<template>
  <NewsSearchBeforeSearch class="main-container" v-if="!searched"></NewsSearchBeforeSearch>
  <div :class="{'main-container': isNoResult}" v-else>
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