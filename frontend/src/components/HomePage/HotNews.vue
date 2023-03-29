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
      <swiper-slide v-for="tag in model" :key="tag">
        <v-card class="trend_area" rounded="lg" elevation="4" color="white">
          <v-img
            class="trend_img"
            rounded="lg"
            src="https://imgnews.pstatic.net/image/009/2023/03/28/0005107713_001_20230328114304617.jpg?type=w647"
            alt="word cloud 이미지"
          />
        </v-card>
      </swiper-slide>
    </swiper>
  </v-sheet>
</template>

<script>
export default {
  name: "HotTrends",
  data() {
    return {
      imgPath: process.env.VUE_APP_WORD_CLOUD_URL,
      model: ["1", "2", "3"],
      swiperOption: {
        spaceBetween: 25,
      },
    };
  },
};
</script>

<style scoped>
.trend_area {
  text-align: center;
  /* width: 338;
  height: 229; */
}
.trend_img {
  width: 100%;
  height: 100%;
  text-align: center;
  /* object-fit: cover; */
}
</style>
