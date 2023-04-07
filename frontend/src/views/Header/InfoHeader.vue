<template>
  <v-app-bar
    absolute
    color="var(--main-col-5)"
    dark
    scroll-target="#scrolling-techniques-3"
    elevation="0"
  >
    <!-- 로고 (아이콘) -->
    <img height="70%" :src="require('@/assets/images/icon.png')" />

    <v-spacer></v-spacer>

    <!-- 로그인 버튼 -->
    <MainHeaderLogin></MainHeaderLogin>
  </v-app-bar>
</template>

<script>
import MainHeaderLogin from "@/components/User/MainHeaderLogin.vue";
export default {
  components: {
    MainHeaderLogin,
  },
};
</script>

<style></style>
