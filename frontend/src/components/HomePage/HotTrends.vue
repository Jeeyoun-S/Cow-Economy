<template>
  <v-sheet class="pa-6" color="transparent">
    <div class="main-title-font align-center">
      <img height="19.47" :src="require('@/assets/images/emoji _fire.png')" />
      최신 트렌드
    </div>
    <div class="main-subtitle-font">
      최근 경제 뉴스에서 다루고 있는 핵심 키워드를 확인해 보세요.
    </div>
    <v-sheet class="mt-4 trend_area pa-4"  rounded="lg" elevation="12" color="white">
      <img class="trend_img" :src="`${imgPath}`" alt="word cloud 이미지" />
    </v-sheet>
  </v-sheet>
</template>

<script>
export default {
  name: "HotTrends",
  data() {
    return {
      imgPath: process.env.VUE_APP_WORD_CLOUD_URL,
    };
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
  object-fit: cover;
}
</style>
