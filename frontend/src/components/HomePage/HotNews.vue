<template>
  <v-sheet class="pa-6" color="white">
    <div class="main-title-font align-center">
      <img
        height="19.47"
        :src="require('@/assets/images/increase-stats.png')"
      />
      인기 뉴스
    </div>
    <div class="main-subtitle-font">
      24시간 내 경제 분야에서 인기 뉴스를 확인해 보세요.
    </div>
    <swiper class="swiper mt-4" :options="swiperOption">
      <swiper-slide v-for="(article, idx) in news" :key="idx">
        <v-card
          class="news-item"
          rounded="lg"
          elevation="4"
          color="white"
          @click="moveNewsDetail(article.articleId)"
          style="
            background: linear-gradient(
              180deg,
              rgba(0, 0, 0, 0) 51.04%,
              #000000 84.9%
            );
            box-shadow: 0px 0px 5px 1px rgba(0, 0, 0, 0.14);
          "
          position="relative"
        >
          <v-img
            class="trend_img"
            rounded="lg"
            :src="article.articleThumbnail"
            height="300"
            position="relative"
          />
          <div
            class="text-col ma-5 main-hot-news-font"
            style="
              flex: 1;
              display: flex;
              flex-direction: column;
              position: absolute;
              top: 50%;
            "
          >
            <!-- 언론사, 날짜 -->
            <div style="display: flex; justify-content: space-between">
              <p class="my-1">{{ article.articlePress }}</p>
              <p class="my-1">
                {{
                  new Intl.DateTimeFormat("kr").format(
                    new Date(article.articleRegtime)
                  )
                }}
              </p>
            </div>
            <!-- 제목 -->
            <div class="title-row">
              <v-card-title class="pa-0 main-title-font">{{
                article.articleTitle
              }}</v-card-title>
            </div>
          </div>
        </v-card>
      </swiper-slide>
    </swiper>
  </v-sheet>
</template>

<script>
import { getTodayHotNews } from "@/api/modules/article.js";
export default {
  name: "HotNews",
  data() {
    return {
      news: [],
      swiperOption: {
        spaceBetween: 25,
      },
    };
  },
  mounted() {
    getTodayHotNews().then((res) => {
      this.news = res;
    });
  },
  methods: {
    moveNewsDetail(articleId) {
      this.$router.push(`/news/${articleId}`);
    },
  },
};
</script>

<style scoped>
.trend_area {
  text-align: center;
  width: 338;
  height: 229;
}
.trend_img {
  width: 100%;
  height: 100%;
  text-align: center;
  /* object-fit: cover; */
}
</style>
