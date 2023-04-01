<!-- <template>
  <v-card>
    <div class="d-flex flex-nowrap justify-space-between">
      <div>
        <v-card-actions>
          <v-img
            v-if="this.article.article_thumbnail"
            class="ml-2 mt-3"
            fab
            height="40px"
            right
            width="40px"
          ></v-img>
        </v-card-actions>
        <v-card-title classd="text-h7">{{
          this.article.article_title
        }}</v-card-title>
      </div>
    </div>
  </v-card>
</template> -->
<template>
  <v-card max-width="400" class="mx-auto">
    <v-container>
      <v-row dense>
        <v-col v-for="(item, i) in items" :key="i" cols="12">
          <v-card>
            <div class="d-flex flex-no-wrap">
              <v-avatar class="ma-0" size="125" tile>
                <v-img :src="item.src"></v-img>
              </v-avatar>
              <div>
                <v-card-title
                  class="text-h7 mt-5"
                  v-text="item.title"
                ></v-card-title>
                <div class="d-flex ml-3">
                  <v-chip outlined>언론사</v-chip>
                  <v-chip outlined>날짜</v-chip>
                </div>
              </div>
            </div>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-card>
</template>

<!-- <script>
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
</script> -->
<script>
export default {
  data: () => ({
    items: [
      {
        color: "#1F7087",
        src: "https://cdn.vuetifyjs.com/images/cards/foster.jpg",
        title: "Supermodel",
        artist: "Foster the People",
      },
      {
        color: "#952175",
        src: "https://cdn.vuetifyjs.com/images/cards/halcyon.png",
        title: "Halcyon Days",
        artist: "Ellie Goulding",
      },
    ],
  }),
};
</script>

<style></style>
