<template>
  <div>
    <v-tabs class="menu b-font" color="var(--main-col-2)" background-color="transparent" grow>
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

    <v-sheet>

    </v-sheet>
    
    <v-card v-for="news in filteredNews" :key="news.articleId" class="news-item">
      <v-card-title>{{ news.article_title }}</v-card-title>
      <v-card-text>{{ news.article_content }}</v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <p class="mb-0">{{ news.article_regtime }}</p>
      </v-card-actions>
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
    };
  },
  computed: {
    filteredNews() {
      if (this.selectedCategory) {
        return this.newsList.filter((news) => news.article_category === this.selectedCategory);
      }
      return this.newsList;
    }
  },
  methods: {
    filterByCategory(category) {
      this.selectedCategory = category;
    },
    resetFilter() {
      this.selectedCategory = null;
    }
  }
};
</script>
  
<style>
</style>