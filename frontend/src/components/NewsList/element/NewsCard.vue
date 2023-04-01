<template>
  <v-card max-width="100%" class="mx-auto mt-3 rounded">
    <v-card>
      <div class="d-flex flex-nowrap">
        <!-- article thumbnail -->
        <div v-if="this.article.article_thumbnail">
          <v-avatar class="custom-avatar" size="125" tile>
            <v-img :src="this.article.article_thumbnail" />
          </v-avatar>
        </div>
        <div class="ml-3 mt-5 mr-2">
          <!-- article title -->
          <!-- - 글자수가 60 이상일 경우 ... 처리 (2줄)-->
          <v-card-content
            v-if="this.article.article_title.length > 65"
            class="text-h7"
            >{{ this.article.article_title.slice(0, 65) }} ...
          </v-card-content>
          <v-card-content v-else class="text-h7">{{
            this.article.article_title
          }}</v-card-content>
          <!-- article press & regtime -->
          <div class="d-flex mt-4">
            <v-chip outlined>{{ this.article.article_press }}</v-chip>
            <v-chip outlined>{{
              this.article.article_regtime.slice(0, 10)
            }}</v-chip>
          </div>
        </div>
      </div>
    </v-card>
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
  created() {
    console.log("#21# 가져온 news 확인: ", this.article);
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
.custom-avatar {
  border-top-left-radius: 3;
  border-bottom-left-radius: 3;
}
</style>
