<template>
  <div>
    <ReadNews></ReadNews>
    <ReadNewsCategory></ReadNewsCategory>
    <WordCategory></WordCategory>
  </div>
</template>

<script>
import ReadNews from "@/components/MyPage/MyPageInfo/ReadNews.vue"
import ReadNewsCategory from "@/components/MyPage/MyPageInfo/ReadNewsCategory.vue"
import WordCategory from "@/components/MyPage/MyPageInfo/WordCategory.vue"


export default {
  name: "MyPageInfo",
  components: {
    ReadNews,
    ReadNewsCategory,
    WordCategory,
  }
};
</script>

<style></style>
