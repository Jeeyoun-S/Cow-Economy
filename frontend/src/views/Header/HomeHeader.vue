<template>
  <v-sheet id="home-header">
    <!-- 상단 헤더 -->
    <v-toolbar flat height="65px">
      <!-- 로고 (글씨) -->
      <v-sheet class="pt-1" height="45px">
        <img height="100%" :src="require('@/assets/images/logo/logo.png')" />
      </v-sheet>
      <v-spacer></v-spacer>
      <!-- 검색 아이콘 -->
      <v-btn class="mr-0" icon color="grey darken-1" to="/search">
        <v-icon large>mdi-magnify</v-icon>
      </v-btn>
    </v-toolbar>

    <!-- 하단 메뉴 -->
    <v-tabs class="b-font" color="var(--main-col-2)" grow>
      <v-tab to="/home">Home</v-tab>
      <v-tab to="/news">News</v-tab>
      <v-tab to="/quiz">Quiz</v-tab>
      <v-tab to="/my-page">My Page</v-tab>
    </v-tabs>
  </v-sheet>
</template>

<script>
export default {
  name: "HomeHeader",
};
</script>

<style></style>
