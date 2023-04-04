<template>
  <v-card
    max-width="100%"
    class="d-flex flex-column"
    tile
    elevation="0"
    @click="moveDetailArticle(article.articleId)"
  >
    <!-- <v-card>  -->
    <v-sheet class="px-5 py-3 d-flex flex-row align-center">
      <!-- article thumbnail -->
      <!-- <div v-if="this.article.article_thumbnail"> -->
      <v-avatar
        class="mr-3"
        size="90"
        tile
        v-if="this.article.article_thumbnail"
      >
        <v-img
          :src="this.article.article_thumbnail"
          :aspect-ratio="1000 / 1000"
        />
      </v-avatar>
      <!-- </div> -->
      <div>
        <!-- article title -->
        <!-- - 글자수가 60 이상일 경우 ... 처리 (2줄)-->
        <div class="py-1" v-if="this.article.article_title.length > 65">
          {{ this.article.article_title.slice(0, 65) }} ...
        </div>
        <div class="py-1" v-else>{{ this.article.article_title }}</div>
        <!-- article press & regtime -->
        <div class="d-flex flex-row py-1">
          <v-chip class="mr-2" outlined small>{{
            this.article.article_press
          }}</v-chip>
          <v-chip outlined small>{{
            this.article.article_regtime.slice(0, 10)
          }}</v-chip>
        </div>
      </div>
    </v-sheet>
    <!-- </v-card> -->
    <v-divider></v-divider>
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

<style>
/* .no-shadow {
  box-shadow: none;
} */
</style>
