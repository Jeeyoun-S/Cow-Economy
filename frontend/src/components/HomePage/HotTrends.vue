<template>
  <v-sheet class="pa-6" color="transparent">
    <div class="d-flex flex-row align-start">
      <img
        height="22"
        class="mr-1"
        :src="require('@/assets/images/emoji _fire.png')"
      />
      <span class="main-title-font">최신 트렌드</span>
    </div>
    <div class="main-subtitle-font">
      최근 경제 뉴스에서 다루고 있는 핵심 키워드를 확인해 보세요.
    </div>
    <v-sheet
      v-if="words == null"
      class="mt-4 trend_area pa-1"
      rounded="lg"
      elevation="3"
      color="white"
    >
      <v-img class="trend_img" :src="`${imgPath}`"></v-img>
    </v-sheet>
    <v-sheet class="mt-4 pa-0" v-else rounded="lg" elevation="3" color="white">
      <wordcloud
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
      words: [],
      rotate: { from: 0, to: 0 },
    };
  },
  components: {
    // VueWordCloud,
    wordcloud,
  },
  async created() {
    let result = await getWordCloud();
    if (result != null) {
      result.data.forEach((el) => {
        this.words.push({ name: el.name, value: el.value });
      });
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
}
</style>
