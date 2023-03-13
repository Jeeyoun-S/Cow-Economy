<template>
  <v-sheet class="pa-4 d-flex flex-column" elevation="1" rounded="lg">
    <v-chip class="ma-2" color="var(--main-col-3)" label dark>MY</v-chip>
    <v-sheet>{{ reference }}</v-sheet>
    {{ content }}
  </v-sheet>
</template>

<script>
export default {
  name: "NewsDetailMemoItem",
  data() {
    return {
      reference:
        "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
      content: "와~ 돈이 참 많네요... 진짜 부럽다...",
    };
  },
};
</script>

<style></style>
