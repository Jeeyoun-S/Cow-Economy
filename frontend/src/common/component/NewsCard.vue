<template>
  <v-card
    max-width="100%"
    class="mx-auto my-2 d-flex flex-row"
    height="120"
    @click="moveDetailArticle(article.articleId)"
  >
    <!-- 이미지 -->
    <div v-if="article.articleThumbnail">
      <v-avatar class="custom-avatar" size="120" tile>
        <v-img
          v-if="article.articleThumbnail"
          :src="this.article.articleThumbnail"
        ></v-img>
      </v-avatar>
    </div>
    <!-- 기사 텍스트 -->
    <div
      class="text-col ma-5"
      style="
        flex: 1;
        display: flex;
        flex-direction: column;
        justify-content: center;
      "
    >
      <!-- 언론사, 날짜 -->
      <div
        class="main-subtitle-font"
        style="display: flex; justify-content: space-between"
      >
        <p class="my-1">{{ this.article.articlePress }}</p>
        <p class="my-1">
          {{
            new Intl.DateTimeFormat("kr").format(
              new Date(this.article.articleRegtime)
            )
          }}
        </p>
      </div>
      <!-- 제목 -->
      <div class="title-row">
        <v-card-title
          class="pa-0 main-category-news-font"
          >{{
            this.article.articleTitle.length > 60
              ? this.article.articleTitle.slice(0, 60)
              : this.article.articleTitle
          }}</v-card-title
        >
      </div>
    </div>
  </v-card>
</template>
<script>
export default {
  name: "NewsCard",
  props: {
    article: Object,
  },
  data() {
    return {};
  },
  methods: {
    // [@Method] 기사 상세페이지로 이동
    moveDetailArticle(articleId) {
      console.log(articleId);
      this.$router.push(`/news/${articleId}`);
    },
  },
};
</script>
