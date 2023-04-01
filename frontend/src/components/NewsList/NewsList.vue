<template>
  <v-sheet id="news-list">
    <!-- sort select -->
    <v-sheet class="mx-7 d-flex flex-row align-center">
      <!-- i) news sort select -->
      <v-sheet width="160px"
        ><v-select
          v-model="sort"
          :items="['최신순', '인기순']"
          color="var(--main-col-2)"
          dense
          rounded
          outlined
          hide-details
          style="font-size: 15px"
        ></v-select
      ></v-sheet>
      <!-- ii) news category select -->
      <v-select
        v-model="category"
        :items="[
          '전체',
          '금융',
          '부동산',
          '산업/재계',
          '글로벌 경제',
          '증권',
          '중기/벤처',
          '생활경제',
          '경제 일반',
        ]"
        color="var(--main-col-2)"
        dense
        rounded
        outlined
        hide-details
        style="font-size: 15px"
      ></v-select>
    </v-sheet>
    <!-- news card list -->
    <v-sheet>
      <news-card
        v-for="(article, index) in this.news"
        :key="index"
        :article="article"
        style="cursor: pointer"
      ></news-card>
    </v-sheet>
  </v-sheet>
</template>

<script>
import NewsCard from "./element/NewsCard.vue";
import { mapState } from "vuex";

export default {
  name: "NewsList",
  components: {
    NewsCard,
  },
  data() {
    return {
      sort: "", // news 정렬 기준
      category: "", // category 정렬 기준
    };
  },
  computed: {
    ...mapState("newsStore", ["news"]),
  },
  created() {
    this.sort = "인기순";
    this.category = "전체";
    // console.log("#21# news 확인: ", this.news);
  },
};
</script>

<style>
#news-list {
  background-color: var(--main-col-5);
}
</style>
