<template>
  <v-sheet class="pa-6" color="transparent">
    <div class="main-title-font align-center">
      <img height="19.47" :src="require('@/assets/images/color-tags.png')" />
      카테고리별 뉴스
    </div>
    <div class="main-subtitle-font">
      최근 24시간 내 카테고리별 뉴스를 확인해 보세요.
    </div>
    <div class="pa-4">
      <span>분류</span>
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
      <div>
        <ul>
          <li v-for="article in filteredArticles" :key="article.articleId">
            {{ article.article_title }}
          </li>
        </ul>
        <div>
          hi
        </div>
      </div>
    </div>
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
      return this.allNews.filter(
        (article) => article.article_category === this.selectedTag.category
      );
    },
  },
  methods: {
    onTagChange(value) {
      console.log("onTagChange: ", value);
      this.selectedTag = value;
    }
  }
};
</script>
<style scoped></style>
