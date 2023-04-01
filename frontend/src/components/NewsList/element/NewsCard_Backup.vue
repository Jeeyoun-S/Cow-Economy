<template>
  <v-sheet>
    <v-hover v-slot="{ hover }">
      <v-sheet
        :color="hover ? 'var(--main-col-5)' : 'transparent'"
        :class="{ 'on-hover': hover }"
        class="px-8 py-3 d-flex flex-column sm-font"
      >
        <v-card class="mx-auto" max-width="100%">
          <v-list-item three-line>
            <v-list-item-content>
              <!-- article thumbnail -->
              <div
                class="article-thumbnail"
                v-if="article.article_thumbnail"
                style="
                  width: 100px;
                  height: 100%;
                  overflow: hidden;
                  display: flex;
                  align-items: center;
                "
              >
                <v-img :src="article.article_thumbnail" cover />
              </div>
              <!-- article title -->
              <v-list-item-title
                class="text-h7 mb-1"
                v-if="article.article_title.length > 30"
                >{{ article.article_title }}
              </v-list-item-title>
              <v-list-item-title class="text-h7 mb-1" v-else
                >{{ article.article_title }}
              </v-list-item-title>
            </v-list-item-content>
          </v-list-item>
          <!-- <div
                v-if="article.article_thumbnail"
                style="
                  width: 84px;
                  height: 84px;
                  overflow: hidden;
                  display: flex;
                  align-items: center;
                "
              >
                <v-img :src="article.article_thumbnail" cover />
              </div>
              <div>
                <v-card-title
                  v-if="article.article_title.length > 30"
                  class="text-h7"
                >
                  {{ article.article_title.substr(0, 30) }} ...
                </v-card-title>
                <v-card-title v-else class="text-h7">
                  {{ article.article_title }}
                </v-card-title>
              </div>
  
              <div>
                <v-chip outlined>{{ article.article_press }}</v-chip>
                <v-chip outlined>
                  {{ article.article_regtime.slice(0, 10) }}
                </v-chip>
              </div>
            </div> -->
        </v-card>
      </v-sheet>
    </v-hover>
  </v-sheet>
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

<style></style>
