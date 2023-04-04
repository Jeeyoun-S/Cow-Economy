<template>
  <v-sheet id="news-list" min-height="100%">
    <!-- sort select -->
    <v-sheet
      class="mx-5 pt-4 pb-2 d-flex flex-row justify-space-between align-center"
      id="list-select"
    >
      <!-- i) news sort select -->
      <v-sheet width="37%">
        <v-select
          v-model="sort"
          :items="['최신순', '인기순']"
          color="var(--main-col-2)"
          dense
          rounded
          outlined
          hide-details
          style="font-size: 15px"
        />
      </v-sheet>
      <!-- ii) news category select -->
      <v-sheet width="60%">
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
    </v-sheet>
    <!-- news card list -->
    <v-sheet>
      <news-card
        v-for="(article, index) in newsList"
        :key="index"
        :article="article"
        style="cursor: pointer"
      ></news-card>
    </v-sheet>
    <!-- go to top button -->
    <ScrollTopBtn></ScrollTopBtn>
  </v-sheet>
</template>

<script>
import NewsCard from "./element/NewsCard.vue";
import { mapActions, mapState } from "vuex";
import { Swiper } from "vue-awesome-swiper";
import ScrollTopBtn from "@/common/component/ScrollTopBtn.vue";

export default {
  name: "NewsList",
  components: {
    NewsCard,
    ScrollTopBtn,
  },
  data() {
    return {
      newsList: [],
      sort: "", // news 정렬 기준
      category: "", // category 정렬 기준
    };
  },
  computed: {
    ...mapState("newsStore", ["news", "categoryNews"]),
  },
  mounted() {
    new Swiper(".swiper-container", {
      // Swiper 옵션 설정
    });
  },
  created() {
    this.sort = "최신순";
    this.category = "전체";
  },
  watch: {
    sort() {
      this.sortNews();
    },
    category() {
      this.filterNews();
    },
  },
  methods: {
    ...mapActions("newsStore", ["setCategoryNews"]),
    // [@Method] 최신 or 인기순 정렬
    sortNews() {
      if (this.sort == "최신순") {
        this.news.sort(function (a, b) {
          return new Date(b.article_regtime) - new Date(a.article_regtime);
        });
      } else if (this.sort == "인기순") {
        this.news.sort(function (a, b) {
          return b.article_hits - a.article_hits;
        });
      }
    },
    // [@Method] 카테고리 별 정렬
    filterNews() {
      if (this.category == "전체") {
        return (this.newsList = this.news);
      } else {
        this.newsList = this.news.filter(
          (article) => article.article_category == this.category
        );
      }
    },
    // [@Method] 맨 위로 가기
    // scrollToTop() {
    //   window.scrollTo({
    //     top: 0,
    //     behavior: "smooth",
    //   });
    // },
  },
};
</script>

<style>
/* #news-list {
  background-color: var(white);
} */

/* .go-to-top {
  position: fixed;
  bottom: 30px;
  right: 30px;

  background-color: var(--main-col-2);
  z-index: 1;
} */
</style>
