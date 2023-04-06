<template>
  <v-card
    width="100%"
    height="100%"
    class="card-gradient mx-auto my-2 d-flex flex-row"
    elevation="0"
    rounded="lg"
    @click="moveDetailArticle(article.articleId)"
  >
    <!-- 이미지 -->
    <v-sheet
      width="100"
      v-if="article.articleThumbnail && article.articleThumbnail != 'NaN'"
      :style="{
        backgroundImage: `url(${article.articleThumbnail})`,
        backgroundSize: 'cover',
        borderTopRightRadius: '0px',
        borderBottomLeftRadius: 'inherit',
        backgroundPosition: 'center',
      }"
    ></v-sheet>
    <!-- <img
      v-if="article.articleThumbnail && article.articleThumbnail != 'NaN'"
      width="100"
      :src="this.article.articleThumbnail"
      style="
        object-fit: cover;
        border-top-right-radius: 0px !important;
        border-bottom-left-radius: inherit !important;
        max-height: 100%;
      "
    /> -->
    <!-- <div v-if="article.articleThumbnail && article.articleThumbnail != 'NaN'">
      <v-avatar class="custom-avatar" size="105" tile>
        <v-img
          v-if="article.articleThumbnail"
          :src="this.article.articleThumbnail"
        ></v-img>
      </v-avatar>
    </div> -->
    <!-- 기사 텍스트 -->
    <div
      class="text-col ma-4 mb-5 d-flex flex-column justify-start"
      style="flex: 1"
    >
      <!-- 언론사, 날짜 -->
      <div class="main-subtitle-font d-flex justify-space-between">
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
        <v-card-title class="pa-0 main-category-news-font">{{
          this.article.articleTitle.length > 60
            ? this.article.articleTitle.slice(0, 60) + "···"
            : this.article.articleTitle
        }}</v-card-title>
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
      this.$router.push(`/news/${articleId}`);
    },
  },
};
</script>
