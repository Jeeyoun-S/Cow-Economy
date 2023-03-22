<template>
  <v-sheet
    min-height="100%"
    id="scrolling-techniques-7"
    class="overflow-y-auto"
  >
    <NewsDetailFloatingBtn v-show="false"></NewsDetailFloatingBtn>
    <NewsDetailContent></NewsDetailContent>
    <NewsDetailRelation></NewsDetailRelation>
    <NewsDetailMemo></NewsDetailMemo>
  </v-sheet>
</template>

<script>
import NewsDetailContent from "./NewsDetailContent.vue";
import NewsDetailRelation from "./NewsDetailRelation/NewsDetailRelation.vue";
import NewsDetailMemo from "./NewsDetailMemo/NewsDetailMemo.vue";
import NewsDetailFloatingBtn from "./NewsDetailFloatingBtn/NewsDetailFloatingBtn.vue";

export default {
  name: "NewsDetail",
  components: {
    NewsDetailContent,
    NewsDetailRelation,
    NewsDetailMemo,
    NewsDetailFloatingBtn,
  },
};
</script>

<style>
#bookmark-pointer {
  display: inline;
}
</style>
