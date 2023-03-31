<template>
  <v-sheet class="pa-6" color="transparent">
    <div class="main-title-font align-center">
      <img height="19.47" :src="require('@/assets/images/color-tags.png')" />
      카테고리별 뉴스
    </div>
    <div class="main-subtitle-font">
      최근 24시간 내 카테고리별 뉴스를 확인해 보세요.
    </div>
    <div style="display: flex; align-items: center;">
      <span class="pr-3" style="white-space: nowrap">분류</span>
      <v-slide-group>
        <v-chip-group v-model="selectedTag" selected-class="text-primary" column @input="onTagChange">
          <v-chip
            filter
            style="background-color: white !important"
            v-for="tag in tags"
            :key="tag.category"
            color="var(--main-col-2)"
            outlined
            :value="tag"
          >
            {{ tag.name }}
          </v-chip>
        </v-chip-group>
      </v-slide-group>
    </div>
    <div style="display: flex; align-items: center;">
      <span class="pr-3" style="white-space: nowrap">정렬</span>
      <v-slide-group>
        <v-chip-group v-model="selectedSortKey" selected-class="text-primary" column @input="sortNews">
          <v-chip
            filter
            style="background-color: white !important"
            v-for="sortKey in sortKeys"
            :key="sortKey.value"
            color="var(--main-col-2)"
            outlined
            :value="sortKey.value"
          >
            {{ sortKey.label }}
          </v-chip>
        </v-chip-group>
      </v-slide-group>
    </div>
    
    <v-card
    v-for="news in filteredArticles"
    :key="news.articleId"
    class="news-item my-2 d-flex flex-row"
    elevation="0"
    >
      <!-- 이미지 -->
      <v-img v-if="news.article_thumbnail" width="5%" max-width="100" :src="news.article_thumbnail"></v-img>
      <!-- 기사 텍스트 -->
      <div
        class="text-col ma-5 main-subtitle-font"
        style="flex: 1; display: flex; flex-direction: column"
      >
        <!-- 언론사, 날짜 -->
        <div
          class="main-subtitle-font"
          style="display: flex; justify-content: space-between"
        >
          <p class="my-1">{{ news.article_press }}</p>
          <p class="my-1">{{ news.article_regtime }}</p>
        </div>
        <!-- 제목 -->
        <div class="title-row">
          <v-card-title class="pa-0 main-title-font">{{
            news.article_title
          }}</v-card-title>
        </div>
      </div>
    </v-card>
  </v-sheet>
</template>

<script>
import newsStore from "@/store/modules/newsStore.js"

export default {
  name: "CategoryNews",
  data() {
    return {
      tags: [
        { name: "금융", category: "금융" },
        { name: "증권", category: "증권" },
        { name: "산업/재계", category: "산업/재계" },
        { name: "중기/벤처", category: "중기/벤처" },
        { name: "부동산", category: "부동산" },
        { name: "글로벌경제", category: "글로벌경제" },
        { name: "생활경제", category: "생활경제" },
        { name: "경제일반", category: "경제일반" },
      ],
      selectedTag: { name: "금융", category: "금융" },
      sortKeys: [
        { label: "최신순", value: "최신순" },
        { label: "인기순", value: "인기순" },
      ],
      selectedSortKey: '최신순',
    };
  },
  computed: {
    allNews() {
      console.log(newsStore.state.news)
      return newsStore.state.news;
    },
    filteredArticles() {
      if (!this.selectedTag) {
        return [];
      }
      let filtered = this.allNews.filter(
        (article) => article.article_category === this.selectedTag.category
      );

      if (this.selectedSortKey === '최신순') {
        filtered.sort((a, b) => new Date(b.article_regtime) - new Date(a.article_regtime));
      } else if (this.selectedSortKey === '인기순') {
        filtered.sort((a, b) => b.article_hits - a.article_hits);
      }

      return filtered;
    },
  },
  methods: {
    onTagChange(value) {
      console.log("onTagChange: ", value);
      this.selectedTag = value;
    },
    sortNews(value) {
      this.selectedSortKey = value;
    },
  }
};
</script>
<style scoped></style>
