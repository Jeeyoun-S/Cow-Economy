<template>
  <v-sheet class="pa-6" color="transparent">
    <div class="main-title-font align-center">
      <img height="19.47" :src="require('@/assets/images/emoji _fire.png')" />
      최신 트렌트
    </div>
    <div class="main-subtitle-font">
      최근 경제 뉴스에서 다루고 있는 핵심 키워드를 확인해 보세요.
    </div>
    <v-sheet class="mt-4" rounded color="white" width="100%" height="229">
      <p>word cloud 이미지 영역</p>
    </v-sheet>
  </v-sheet>
</template>

<script>
export default {
  name: "HotTrends",
  data() {
    return {};
  },
};
</script>

<style></style>
