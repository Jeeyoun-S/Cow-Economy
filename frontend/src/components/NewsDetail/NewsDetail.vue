<template>
  <v-sheet
    min-height="100%"
    id="scrolling-techniques-7"
    class="overflow-y-auto"
  >
    <NewsDetailContent></NewsDetailContent>
    <NewsDetailRelation></NewsDetailRelation>
    <NewsDetailMemo></NewsDetailMemo>
  </v-sheet>
</template>

<script>
import NewsDetailContent from "@/components/NewsDetail/NewsDetailContent.vue";
import NewsDetailRelation from "@/components/NewsDetail/NewsDetailRelation.vue";
import NewsDetailMemo from "@/components/NewsDetail/NewsDetailMemo/NewsDetailMemo.vue";

export default {
  name: "NewsDetail",
  components: {
    NewsDetailContent,
    NewsDetailRelation,
    NewsDetailMemo,
  },
};
</script>

<style>
#bookmark-pointer {
  display: inline;
}
</style>
