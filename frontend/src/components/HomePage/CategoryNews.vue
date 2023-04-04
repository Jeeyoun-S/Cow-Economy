<template>
  <v-sheet class="pa-6" color="transparent">
    <div class="main-title-font align-center">
      <img height="19.47" :src="require('@/assets/images/color-tags.png')" />
      카테고리별 뉴스
    </div>
    <div class="main-subtitle-font">
      최근 24시간 내 카테고리별 뉴스를 확인해 보세요.
    </div>
    <div style="display: flex; align-items: center">
      <span class="pr-3" style="white-space: nowrap">분류</span>
      <v-slide-group>
        <v-chip-group
          v-model="selectedTag"
          selected-class="text-primary"
          column
          @input="onTagChange"
        >
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
    <div style="display: flex; align-items: center">
      <span class="pr-3" style="white-space: nowrap">정렬</span>
      <v-slide-group>
        <v-chip-group
          v-model="selectedSortKey"
          selected-class="text-primary"
          column
          @input="sortNews"
        >
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

    <news-card
      v-for="(article,idx) in filteredArticles"
      :key="idx"
      :article="article"
      class="mx-auto my-2 d-flex flex-row"
      height="130"
    >
    </news-card>
  </v-sheet>
</template>

<script>
import { getTodayAllNews } from "@/api/modules/article.js";
import NewsCard from '@/common/component/NewsCard.vue';
export default {
  name: "CategoryNews",
  components: {
    NewsCard
  },
  data() {
    return {
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
};
</script>
<style scoped></style>
