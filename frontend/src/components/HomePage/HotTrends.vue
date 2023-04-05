<template>
  <v-sheet class="pa-6" color="transparent">
    <div class="main-title-font align-center">
      <img height="19.47" :src="require('@/assets/images/emoji _fire.png')" />
      최신 트렌드
    </div>
    <div class="main-subtitle-font">
      최근 경제 뉴스에서 다루고 있는 핵심 키워드를 확인해 보세요.
    </div>
    <v-skeleton-loader
      class="mt-4"
      v-if="words == null"
      type="image"
    ></v-skeleton-loader>
    <v-sheet class="mt-4 pa-0" v-else rounded="lg" elevation="3" color="white">
      <v-img v-if="!flag" class="trend_img" :src="`${imgPath}`"></v-img>
      <wordcloud
        v-else
        style="width 100%; height: 300px;"
        font="GongGothicBold"
        :data="words"
        nameKey="name"
        valueKey="value"
        :color="colors"
        spiral="rectangular"
        :fontSize="[10, 40]"
        :rotate="rotate"
        :showTooltip="false"
      >
      </wordcloud>
    </v-sheet>
  </v-sheet>
</template>

<script>
import { getWordCloud } from "@/api/modules/wordcloud";
import wordcloud from "vue-wordcloud";

export default {
  name: "HotTrends",
  data() {
    return {
      imgPath: process.env.VUE_APP_WORD_CLOUD_URL,
      colors: ["#5aa9e6", "#7fc8f8", "#ffe45e", "#ff6392", "#dab6fc"],
      words: null,
      rotate: { from: 0, to: 0 },
      flag: true,
    };
  },
  components: {
    wordcloud,
  },
  async mounted() {
    let result = await getWordCloud();
    this.words = [];
    if (result != null) {
      result.data.forEach((el) => {
        this.words.push({ name: el.name, value: el.value });
      });
    } else {
      //이미지로 대체
      this.flag = false;
    }
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
  border-radius: 0.5rem;
}
</style>
