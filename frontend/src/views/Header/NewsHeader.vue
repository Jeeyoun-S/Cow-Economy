<template>
  <v-sheet>
    <v-toolbar flat height="65px">
      <!-- 뒤로가기 아이콘 -->
      <BackIcon></BackIcon>
      <v-spacer></v-spacer>
      <!-- 제목 -->
      <v-toolbar-title>News</v-toolbar-title>
      <v-spacer></v-spacer>
      <!-- 공유 아이콘 -->
      <v-btn class="mr-0" icon color="grey darken-1" @click="shareKakao">
        <v-icon>mdi-share-variant</v-icon>
      </v-btn>
      <!-- 숨겨진 공유 버튼 -->
      <button ref="kakaoShareButton" style="display: none;"></button>
    </v-toolbar>
    <v-divider></v-divider>
  </v-sheet>
</template>

<script>
import BackIcon from "@/common/component/BackIcon.vue";
import { mapState } from 'vuex';


export default {
  name: "NewsHeader",
  components: {
    BackIcon,
  },
  mounted() {
    this.initKakaoShare();
  },
  computed: {
    ...mapState('newsStore', ['cur']),

  },
  methods: {
    initKakaoShare() {
      if (!window.Kakao.isInitialized()) {
        alert("카카오 SDK가 초기화되지 않았습니다.");
        return;
      }
      const newsTitle = this.cur
      console.log(newsTitle)

      window.Kakao.Link.createDefaultButton({
        container: this.$refs.kakaoShareButton,
        objectType: "feed",
        content: {
          title: newsTitle,
          imageUrl: "@/assets/images/logo/logo_full.png",
          link: {
            mobileWebUrl: window.location.href,
            webUrl: window.location.href,
          },
        },
      });
    },
    shareKakao() {
      this.$refs.kakaoShareButton.click();
    },
  },
};
</script>

<style></style>
