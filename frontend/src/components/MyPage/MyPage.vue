<template>
  <v-sheet color="transparent">
    <!-- 로그인 -->
    <div v-if="loaded">
      <div v-if="isLoggedIn">마이페이지</div>
      <div v-else>
        <kakaoLogin></kakaoLogin>
        로그인 페이지
      </div>
    </div>
    <div v-else>Loading...</div>
    <!-- level profile -->
    <MyPageProfile class="justify-center"></MyPageProfile>
    <!-- username & logout btn -->
    <v-sheet class="pa-7" rounded="t-xl" elevation="5">
      <!-- hello & logout -->
      <v-sheet class="pa-1 d-flex flex-row justify-space-between pb-5">
        <div class="xl-font">
          <div>안녕하세요</div>
          <div class="d-flex align-center">
            <span class="b-font">홍길동</span>님<img
              height="25"
              class="pl-1"
              src="@/assets/images/emoji/waving_hand.png"
            />
          </div>
        </div>
        <MyPageLogoutBtn></MyPageLogoutBtn>
      </v-sheet>
      <!-- info & memo -->
      <div>
        <v-row class="px-2 mt-1 mb-3">
          <v-col class="pa-1"
            ><v-btn
              class="swiper-menu my-info"
              slot="button-prev"
              elevation="0"
              block
              >나의 정보</v-btn
            ></v-col
          ><v-col class="pa-1">
            <v-btn
              class="swiper-menu my-memo"
              slot="button-next"
              elevation="0"
              block
              >나의 메모</v-btn
            >
          </v-col>
        </v-row>

        <swiper class="swiper" :options="swiperOptionMain" ref="swiperMain">
          <swiper-slide><MyPageInfo></MyPageInfo></swiper-slide>
          <swiper-slide><MyPageMemo></MyPageMemo></swiper-slide>
        </swiper>
      </div>
    </v-sheet>
  </v-sheet>
</template>

<script>
import MyPageProfile from "./MyPageProfile.vue";
import MyPageLogoutBtn from "./MyPageLogoutBtn.vue";
import MyPageInfo from "./MyPageInfo/MyPageInfo.vue";
import MyPageMemo from "./MyPageMemo/MyPageMemo.vue";

import KakaoLogin from "@/components/MyPage/KakaoLogin.vue";
import { mapGetters, mapActions } from "vuex";

export default {
  name: "MyPage",
  data() {
    return {
      kakaoCode: null,
      loaded: false,
      swiperOptionMain: {
        spaceBetween: 10,
        navigation: {
          nextEl: ".my-memo",
          prevEl: ".my-info",
        },
      },
      selectedBtn: "my-memo",
    };
  },
  watch: {
    isLoggedIn() {
      this.loaded = true;
    },
  },
  created() {
    this.loaded = false;
    // 인가 코드 추출
    this.kakaoCode = this.$route.query.code;
    if (this.kakaoCode != null) {
      this.kakao();
    }
    this.loaded = true;
  },
  components: {
    MyPageProfile,
    MyPageLogoutBtn,
    MyPageMemo,
    MyPageInfo,
    KakaoLogin,
  },
  computed: {
    ...mapGetters("userStore", ["isLoggedIn"]),
  },
  methods: {
    ...mapActions("userStore", ["executeToken"]),
    // 받은 인가 코드를 사용하여 Kakao Token 발급 요청
    async kakao() {
      await this.executeToken();
      // 받은 인가 코드 초기화
      this.kakaoCode = null;
    },
  },
};
</script>

<style></style>
