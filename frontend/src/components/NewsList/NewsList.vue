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
      sortKey: "최신순",
      selectedCategory: "전체",
    };
  },
  computed: {
    ...mapState("newsStore", ["searchText"]),
    filteredNews() {
      // console.log("마지막 기사: "+this.items[this.items.length-1].articleId);
      let filtered = this.selectedCategory
        ? this.items.filter(
            (news) => news.articleCategory === this.selectedCategory
          )
        : this.items;

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
  // created() {
  //   console.log("created");
  // },
  watch: {
    sort() {
      this.sortNews();
    },
    category() {
      this.filterNews();
    },
  },
  methods: {
    ...mapActions("newsStore", ["setNews"]),
    async infiniteHandler($state) {
      this.page = this.newsList[this.newsList.length - 1].articleId;
      await this.setNews({
        keyword: this.searchText,
        lastArticleId: this.page,
      });
      if (this.newsList.length > 0) {
        await setTimeout(() => {
          this.items = this.items.concat(this.newsList);
          // for (let index = 0; index < this.items.length; index++) {
          //   console.log(this.items[index].articleTitle);
          // }
          this.page = this.items[this.items.length - 1].articleId;
          $state.loaded();
        }, 1000);
      } else {
        $state.complete();
      }
    },
    filterByCategory(category) {
      this.selectedCategory = category;
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
/* #news-list {
  background-color: var(white);
} */

.go-to-top {
  position: fixed;
  bottom: 30px;
  right: 30px;

  background-color: var(--main-col-2);
  z-index: 1;
}
</style>
