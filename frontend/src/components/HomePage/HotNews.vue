<template>
  <v-sheet class="pa-6" color="white">
    <div class="main-title-font align-center">
      <img height="19.47" :src="require('@/assets/images/increase-stats.png')" />
      인기 뉴스
    </div>
    <div class="main-subtitle-font">
      24시간 내 경제 분야에서 인기 뉴스를 확인해 보세요.
    </div>
    <v-sheet class="mt-4 trend_area" rounded color="white">
      <div id="article">
        <img>
      </div>
    </v-sheet>
  </v-sheet>
</template>

<script>
export default {
  name: "HotTrends",
  data() {
    return {
      
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
