<template>
  <v-sheet class="py-6" color="transparent">
    <div class="px-6 d-flex flex-row align-start">
      <img
        height="22"
        class="mr-1"
        :src="require('@/assets/images/color-tags.png')"
      />
      <span class="main-title-font">카테고리별 뉴스</span>
    </div>
    <div class="px-6 main-subtitle-font">
      오늘의 카테고리별 뉴스를 확인해 보세요.
    </div>
    <div
      class="pl-6 overflow-hidden"
      style="display: flex; align-items: center"
    >
      <!-- <v-slide-group> -->
      <span class="pr-3" style="white-space: nowrap">분류</span>
      <v-chip-group
        v-model="selectedTag"
        selected-class="text-primary"
        @input="onTagChange"
        mandatory
      >
        <div class="swiper-container" style="max-width: 450px">
          <div class="swiper-wrapper">
            <!-- <swiper-slide> -->
            <v-chip
              class="swiper-slide"
              style="width: auto !important"
              v-for="tag in tags"
              :key="tag.category"
              filter
              color="var(--main-col-2)"
              :style="
                selectedTag.name == tag.name
                  ? ''
                  : 'background-color: white !important'
              "
              :outlined="selectedTag.name == tag.name ? false : true"
              :value="tag"
              active-class="active-category"
            >
              {{ tag.name }}
            </v-chip>
            <!-- </swiper-slide> -->
          </div>
        </div>
      </v-chip-group>

      <!-- <div > -->

      <!-- </div> -->
      <!-- </v-slide-group> -->
    </div>
    <div class="pl-6" style="display: flex; align-items: center">
      <span class="pr-3" style="white-space: nowrap">정렬</span>
      <v-slide-group>
        <v-chip-group
          v-model="selectedSortKey"
          selected-class="text-primary"
          column
          @input="sortNews"
          mandatory
        >
          <v-chip
            filter
            :style="
              selectedSortKey == sortKey.label
                ? ''
                : 'background-color: white !important'
            "
            v-for="sortKey in sortKeys"
            :key="sortKey.value"
            color="var(--main-col-2)"
            :value="sortKey.value"
            :outlined="selectedSortKey == sortKey.label ? false : true"
            active-class="active-category"
          >
            {{ sortKey.label }}
          </v-chip>
        </v-chip-group>
      </v-slide-group>
    </div>

    <!-- <news-card
      v-for="(article, idx) in filteredArticles"
      :key="idx"
      :article="article"
      class="px-6 mx-auto my-2 d-flex flex-row"
      height="130"
    >
    </news-card> -->
    <div class="px-6 mx-auto my-2 d-flex flex-column">
      <news-card
        v-for="(article, idx) in filteredArticles"
        :key="idx"
        :article="article"
        height="130"
      >
      </news-card>
    </div>
  </v-sheet>
</template>

<script>
import Swiper from "swiper";
import { getTodayAllNews } from "@/api/modules/article.js";
import NewsCard from "@/common/component/NewsCard.vue";

export default {
  name: "CategoryNews",
  components: {
    NewsCard,
  },
  data() {
    return {
      swiperOption: {
        slidesPerView: "auto",
        spaceBetween: 0,
      },
      news: [],
      tags: [
        { name: "금융", category: "금융" },
        { name: "증권", category: "증권" },
        { name: "산업/재계", category: "산업/재계" },
        { name: "중기/벤처", category: "중기/벤처" },
        { name: "부동산", category: "부동산" },
        { name: "글로벌경제", category: "글로벌 경제" },
        { name: "생활경제", category: "생활경제" },
        { name: "경제일반", category: "경제 일반" },
      ],
      selectedTag: { name: "금융", category: "금융" },
      sortKeys: [
        { label: "최신순", value: "최신순" },
        { label: "인기순", value: "인기순" },
      ],
      selectedSortKey: "최신순",
    };
  },
  computed: {
    filteredArticles() {
      if (!this.selectedTag) {
        return [];
      }
      if (this.news.length == 0) {
        // console.log(this.news.length)
        return [];
      }
      let filtered = (
        this.selectedSortKey === "최신순" ? this.news[1] : this.news[0]
      ).filter(
        (article) => article.articleCategory === this.selectedTag.category
      );

      if (this.selectedSortKey === "최신순") {
        filtered.sort(
          (a, b) => new Date(b.articleRegtime) - new Date(a.articleRegtime)
        );
      } else if (this.selectedSortKey === "인기순") {
        filtered.sort((a, b) => b.articleHits - a.articleHits);
      }
      return filtered;
    },
  },
  created() {
    getTodayAllNews().then((res) => {
      this.news = res;
    });
  },
  methods: {
    onTagChange(value) {
      console.log("onTagChange: ", value);
      this.selectedTag = value;
    },
    sortNews(value) {
      this.selectedSortKey = value;
    },
    moveNewsDetail(articleId) {
      this.$router.push(`/news/${articleId}`);
    },
  },
  mounted() {
    new Swiper(".swiper-container", {
      // Add the clickable option
      clickable: true,
      slidesPerView: "auto",
      spaceBetween: 7,
    });
  },
};
</script>
<style scoped>
.active-category {
  background-color: var(--main-col-2);
  color: white;
}
</style>
