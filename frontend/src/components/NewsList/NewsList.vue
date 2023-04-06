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
          v-model="sortKey"
          :items="['최신순', '인기순']"
          color="var(--main-col-2)"
          dense
          rounded
          outlined
          hide-details
          style="font-size: 15px"
          @change="sortNews($event)"
        />
      </v-sheet>
      <!-- ii) news category select -->
      <v-sheet width="60%">
        <v-select
          v-model="selectedCategory"
          :items="[
            '전체',
            '금융',
            '증권',
            '산업/재계',
            '중기/벤처',
            '부동산',
            '글로벌 경제',
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
        v-for="(article, index) in filteredNews"
        :key="index"
        :article="article"
        style="cursor: pointer"
      ></news-card>
      <infinite-loading ref="infiniteLoading" @infinite="infiniteHandler" spinner = "spiral"></infinite-loading>
    </v-sheet>
    <!-- go to top button -->
    <ScrollTopBtn></ScrollTopBtn>
  </v-sheet>
</template>

<script>
import NewsCard from "./element/NewsCard.vue";
import { getAllNews } from "@/api/modules/article.js";
import { mapActions, mapState } from "vuex";
import { Swiper } from "vue-awesome-swiper";
import ScrollTopBtn from "@/common/component/ScrollTopBtn.vue";
import InfiniteLoading from "vue-infinite-loading";
const maxArticleId = Number.MAX_SAFE_INTEGER + 1;
export default {
  name: "NewsList",
  components: {
    NewsCard,
    ScrollTopBtn,
    InfiniteLoading,
  },
  data() {
    return {
      news: [],
      hotNews: [],
      recentNews: [],
      items: [],
      hotCategoryLast: {
        finance: maxArticleId,
        stock: maxArticleId,
        industry: maxArticleId,
        venture: maxArticleId,
        estate: maxArticleId,
        worldwide: maxArticleId,
        life: maxArticleId,
        common: maxArticleId,
      },
      recentCategoryLast: {
        finance: maxArticleId,
        stock: maxArticleId,
        industry: maxArticleId,
        venture: maxArticleId,
        estate: maxArticleId,
        worldwide: maxArticleId,
        life: maxArticleId,
        common: maxArticleId,
      },
      sortKey: "최신순",
      selectedCategory: "전체",
    };
  },
  computed: {
    ...mapState("newsStore", ["searchText"]),
    filteredNews() {
      if (!this.sortKey) {
        console.log("sortKey")
        return [];
      }
      if (this.news.length == 0) {
        return [];
      }
      
      let articles = this.sortKey === "최신순" ? this.news[1] : this.news[0];
      let filtered=(this.selectedCategory==="전체"?articles:(articles.filter(
        (article) => article.articleCategory === this.selectedCategory
      )));

      if (this.sortKey === "최신순") {
        filtered.sort(
          (a, b) => new Date(b.articleRegtime) - new Date(a.articleRegtime)
        );
      } else if (this.sortKey === "인기순") {
        filtered.sort((a, b) => b.articleHits - a.articleHits);
      }
      return filtered;
    },
  },
  mounted() {
    new Swiper(".swiper-container", {
      // Swiper 옵션 설정
    });
  },
  created() {
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
    ...mapActions("newsStore", ["init", "setNews"]),
    async infiniteHandler($state) {
      await getAllNews({"hot":this.hotCategoryLast,"recent":this.recentCategoryLast}).then((res) => {
        this.news = [];
        this.hotCategoryLast = this.setCategoryLast(res.categoryLast[0]);
        this.recentCategoryLast = this.setCategoryLast(res.categoryLast[1]);
        this.hotNews = this.hotNews.concat(res.articles[0]);
        this.recentNews = this.recentNews.concat(res.articles[1]);
        this.news.push(this.hotNews);
        this.news.push(this.recentNews);
        if (this.hotNews.length > 0 || this.recentNews.length > 0 ) {
          setTimeout(() => {
            $state.loaded();
          }, 1000);
        } else {
          $state.complete();
        }
      }) 
    },
    setCategoryLast(category){
      return {finance: category[0],
        stock: category[1],
        industry: category[2],
        venture: category[3],
        estate: category[4],
        worldwide: category[5],
        life: category[6],
        common: category[7]}
    },
    resetFilter() {
      this.selectedCategory = null;
    },
    sortNews(newSortKey) {
      this.sortKey = newSortKey;
    },
    // [@Method] 맨 위로 가기
    scrollToTop() {
      window.scrollTo({
        top: 0,
        behavior: "smooth",
      });
    },
  },
};
</script>

<style>
.go-to-top {
  position: fixed;
  bottom: 30px;
  right: 30px;

  background-color: var(--main-col-2);
  z-index: 1;
}
</style>
