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
      <v-btn class="mr-0" icon color="grey darken-1">
        <v-icon>mdi-share-variant</v-icon>
      </v-btn>
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
};
</script>

<style></style>
