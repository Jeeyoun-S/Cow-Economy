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

export default {
  name: "NewsHeader",
  components: {
    BackIcon,
  },
  mounted() {
    this.initKakaoShare();
  },
  methods: {
    initKakaoShare() {
      if (!window.Kakao.isInitialized()) {
        alert("카카오 SDK가 초기화되지 않았습니다.");
        return;
      }

      window.Kakao.Link.createDefaultButton({
        container: this.$refs.kakaoShareButton,
        objectType: "feed",
        // 나중에 기사 완성 되면 제목, 설명, 이미지 변수 넣기
        content: {
          title: "News Title",
          description: "News Description",
          imageUrl: "https://lh3.googleusercontent.com/poWNIQX2ZneVwTfUCFVrsRcE5skKidLAX-12WJ0czkD6-0FOAI_Rc7P-EzgcaWBfedBhj8tz7N_X8rqWK95iYSRpIDQUnDwwbiSZLac",
          link: {
            mobileWebUrl: "http://localhost:3000/news/1",
            webUrl: "http://localhost:3000/news/1",
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
