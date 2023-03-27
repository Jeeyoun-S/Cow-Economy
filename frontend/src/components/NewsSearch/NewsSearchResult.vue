<template>
  <div>
    <v-tabs class="menu b-font main-col-3" background-color="transparent" grow>
      <v-tab :class="{ 'selected-category': !selectedCategory }"
        @click="resetFilter">모두보기</v-tab>
      <v-tab :class="{ 'selected-category': selectedCategory === '금융' }"
        @click="filterByCategory('금융')">금융</v-tab>
      <v-tab :class="{ 'selected-category': selectedCategory === '증권' }"
        @click="filterByCategory('증권')">증권</v-tab>
      <v-tab :class="{ 'selected-category': selectedCategory === '산업/재계' }"
        @click="filterByCategory('산업/재계')">산업/재계</v-tab>
      <v-tab :class="{ 'selected-category': selectedCategory === '중기/벤처' }"
        @click="filterByCategory('중기/벤처')">중기/벤처</v-tab>
      <v-tab :class="{ 'selected-category': selectedCategory === '부동산' }"
        @click="filterByCategory('부동산')">부동산</v-tab>
    </v-tabs>

    <v-sheet class="mx-7 my-5 d-flex flex-row align-center" style="background-color: transparent">
      <h3 class="mr-auto main-title-font grey--text">{{ filteredNews.length }}개의 검색 결과</h3>
      <v-sheet width="110px" class="md-r-font" style="background-color: transparent">
        <!-- 지윤님 토글 물어봐서 바꾸기 -->
        <v-select
          class="md-r-font main-col-3"
          v-model="sort"
          :items="['정렬', '최신순', '인기순']"
          dense
          rounded
          outlined
          hide-details
          @change="sortNews"
        ></v-select>
      </v-sheet>
    </v-sheet>

    <v-card v-for="news in filteredNews" :key="news.articleId" class="news-item mx-7 my-2" outlined style="border-radius: 8px; height: 120px; overflow: hidden;">
      <div class="news-content" style="display: flex; height: 100%;">
        <!-- 이미지 -->
        <div class="image-col" v-if="news.article_thumbnail" style="width: 100px; height: 100%; overflow: hidden; display: flex; align-items: center;">
          <v-img :src="news.article_thumbnail" cover></v-img>
        </div>
        <!-- 기사 텍스트 -->
        <div class="text-col ma-5 main-subtitle-font" style="flex: 1; display: flex; flex-direction: column;">
          <!-- 언론사, 날짜 -->
          <div class="main-subtitle-font" style="display: flex; justify-content: space-between;">
            <p class="my-1">{{ news.article_press }}</p>
            <p class="my-1">{{ news.article_regtime }}</p>
          </div>
          <!-- 제목 -->
          <div class="title-row">
            <v-card-title class="pa-0 main-title-font">{{ news.article_title }}</v-card-title>
          </div>
        </div>
      </div>
    </v-card>
  </div>
</template>
  
<script>
export default {
  name: "NewsSearchResult",
  props: {
    newsList: Array,
  },
  data() {
    return {
      selectedCategory: null,
      sort: '정렬',
    };
  },
  computed: {
    filteredNews() {
      let filtered = this.selectedCategory
        ? this.newsList.filter((news) => news.article_category === this.selectedCategory)
        : this.newsList;

      if (this.sort === '최신순') {
        filtered.sort((a, b) => new Date(b.article_regtime) - new Date(a.article_regtime));
      } else if (this.sort === '인기순') {
        filtered.sort((a, b) => b.article_hits - a.article_hits);
      }

      return filtered;
    },
  },
  methods: {
    filterByCategory(category) {
      this.selectedCategory = category;
    },
    resetFilter() {
      this.selectedCategory = null;
    },
    sortNews() {
      // 필터링된 뉴스를 정렬하면 filteredNews computed 속성이 다시 실행됩니다.
    },
  },
};
</script>
  
<style>
</style>