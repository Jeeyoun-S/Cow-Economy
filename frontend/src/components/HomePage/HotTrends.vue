<template>
  <v-sheet class="pa-6" color="transparent">
    <div class="main-title-font align-center">
      <img height="19.47" :src="require('@/assets/images/emoji _fire.png')" />
      최신 트렌드
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
      <v-img
        class="trend_img"
        :src="`${imgPath}`"
        alt="word cloud 이미지"
      ></v-img>
    </v-sheet>
    <v-sheet class="mt-4 pa-0" v-else rounded="lg" elevation="3" color="white">
      <wordcloud
        style="margin: auto 0"
        font="GongGothicBold"
        :data="words"
        nameKey="name"
        valueKey="value"
        :color="colors"
        spiral="rectangular"
        :fontSize="[10, 35]"
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
      colors: [
        "#8E96DF",
        "#A5D9F0",
        "#A1ABC7",
        "#76B0DC",
        "#6F83E9",
        "#BDE0FE",
      ],
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
        // this.words.push([el.name, el.value]);
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
