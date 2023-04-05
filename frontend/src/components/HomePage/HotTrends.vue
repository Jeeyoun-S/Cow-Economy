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
    <v-skeleton-loader
      class="mt-4"
      v-if="words == null"
      type="image"
    ></v-skeleton-loader>
    <v-sheet class="mt-4 pa-0" elevation="2" v-else rounded="lg" color="white">
      <!-- <v-img v-if="!wordsFlag" class="trend_img" :src="`${imgPath}`"></v-img> -->
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
import wordcloud from "vue-wordcloud";
import { mapActions, mapState } from "vuex";

const mainStore = "mainStore";

export default {
  name: "HotTrends",
  data() {
    return {
      imgPath: process.env.VUE_APP_WORD_CLOUD_URL,
      colors: ["#5aa9e6", "#7fc8f8", "#ffe45e", "#ff6392", "#dab6fc"],
      rotate: { from: 0, to: 0 },
    };
  },
  components: {
    wordcloud,
  },
  computed: {
    ...mapState(mainStore, ["words", "wordsFlag"]),
  },
  mounted() {
    if (!this.wordsFlag) {
      //워드 클라우드가 없을 때 새로 갱신
      this.updateWordCloud();
    }
  },
  methods: {
    ...mapActions(mainStore, ["updateWordCloud"]),
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
