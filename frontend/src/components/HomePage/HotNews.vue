<template>
  <v-sheet class="py-6" color="white">
    <div class="px-6 main-title-font align-center">
      <img
        height="19.47"
        :src="require('@/assets/images/increase-stats.png')"
      />
      인기 뉴스
    </div>
    <div class="px-6 main-subtitle-font">
      24시간 내 경제 분야에서 인기 뉴스를 확인해 보세요.
    </div>
    <swiper class="swiper mt-4 ml-6" :options="swiperOption">
      <swiper-slide v-for="(article, idx) in news" :key="idx">
        <div>
          <v-card
            rounded="lg"
            elevation="4"
            class="ma-0"
            @click="moveNewsDetail(article.articleId)"
            height="200px"
          >
            <v-img
              :src="article.articleThumbnail"
              class="align-end"
              gradient="to bottom, rgba(0,0,0,.1), rgba(0,0,0,.5)"
              height="200px"
            >
              <v-sheet width="91%" class="white-col-1 pa-4" color="transparent">
                <div class="sm-font d-flex flex-row justify-space-between">
                  <span>{{ article.articlePress }}</span>
                  <span>{{
                    new Intl.DateTimeFormat("kr").format(
                      new Date(article.articleRegtime)
                    )
                  }}</span>
                </div>
                <span class="lg-font b-font">{{ article.articleTitle }}</span>
              </v-sheet>
            </v-img>
            <!-- <v-img
          class="trend_img"
          rounded="lg"
          :src="article.articleThumbnail"
          height="300"
          position="relative"
        /> -->
            <!-- <div
          class="text-col ma-5"
          style="
            flex: 1;
            display: flex;
            flex-direction: column;
            position: absolute;
            top: 50%;
          "
        > -->
            <!-- 언론사, 날짜 -->
            <!-- <div style="display: flex; justify-content: space-between">
            <p class="my-1">{{ article.articlePress }}</p>
            <p class="my-1">
              {{
                new Intl.DateTimeFormat("kr").format(
                  new Date(article.articleRegtime)
                )
              }}
            </p>
          </div> -->
            <!-- 제목 -->
            <!-- <div class="title-row">
            <v-card-title class="pa-0 main-title-font">{{
              article.articleTitle
            }}</v-card-title>
          </div> -->
            <!-- </div> -->
          </v-card>
        </div>
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
        slidesPerView: "auto",
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
.swiper-slide {
  width: 80%;
}
</style>
