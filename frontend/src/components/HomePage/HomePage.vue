<template>
  <div>
    <hot-trends></hot-trends>
    <hot-news></hot-news>
    <category-news></category-news>
  </div>
</template>

<script>
import HotTrends from "./HotTrends.vue";
import HotNews from "./HotNews.vue";
import CategoryNews from './CategoryNews.vue';
export default {
  components: { HotTrends, HotNews, CategoryNews },
};
</script>

<style></style>

HotTrends
