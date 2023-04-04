<template>
  <NewsSearchBeforeSearch class="main-container" v-if="!searched"></NewsSearchBeforeSearch>
  <div :class="{'main-container': isNoResult}" v-else>
    <NewsSearchNoResult v-if="isNoResult"></NewsSearchNoResult>
    <NewsSearchResult v-else :newsList="searchNews"></NewsSearchResult>

  </div>
</template>

<script>
import NewsSearchBeforeSearch from '@/components/NewsSearch/NewsSearchBeforeSearch.vue';
import NewsSearchNoResult from './NewsSearchNoResult.vue';
import NewsSearchResult from "./NewsSearchResult.vue"
import { mapState, mapGetters, mapActions } from "vuex";

export default {
  name: "NewsSearch",
  components: {
    NewsSearchBeforeSearch,
    NewsSearchNoResult,
    NewsSearchResult,
  },
  computed: {
    ...mapState("newsStore", ["searched", "news"]),
    ...mapGetters("newsStore", ["searchNews"]),
    isNoResult() { 
      return this.searchNews.length === 0;
    },
  },
  created(){
    this.initNews();
  },
  methods: {
    ...mapActions("newsStore",["init"]),
    initNews(){
      this.init();
    }
  }
  // methods: {
  //   ...mapState("newsStore", ["setSearched"])
  // },
  // destroyed() {
  //   this.setSearched(false);
  // }
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