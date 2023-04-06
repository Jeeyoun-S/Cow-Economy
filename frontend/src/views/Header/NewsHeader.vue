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
    if (this.cur) {
      this.initKakaoShare();
    }
  },
  computed: {
    ...mapState('newsStore', ['cur']),
  },
  watch: {
    cur() {
      this.initKakaoShare();
    },
  },
  methods: {
    initKakaoShare() {
      if (!window.Kakao.isInitialized()) {
        alert("카카오 SDK가 초기화되지 않았습니다.");
        return;
      }

      const templateId = 91976
      this.$refs.kakaoShareButton.onclick = () => {
        window.Kakao.Link.sendCustom({
          templateId: templateId,
          templateArgs: {
            'title': this.cur[0],
            'content': this.cur[1],
            'imageUrl': this.cur[2],
            'linkMobile': `https://j8a509.p.ssafy.io${window.location.pathname}`,
            'linkWeb': `https://j8a509.p.ssafy.io${window.location.pathname}`,
          }
        });
      };
    },
    shareKakao() {
      this.$refs.kakaoShareButton.click();
    },
  },
};
</script>

<style></style>
