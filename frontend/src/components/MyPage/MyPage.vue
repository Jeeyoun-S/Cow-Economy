<template>
  <v-sheet color="transparent">
    <!-- 로그인 -->

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

export default {
  name: "MyPage",
  components: {
    MyPageProfile,
    MyPageLogoutBtn,
    MyPageMemo,
    MyPageInfo,
  },
  data() {
    return {
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
  methods: {},
};
</script>

<style></style>
