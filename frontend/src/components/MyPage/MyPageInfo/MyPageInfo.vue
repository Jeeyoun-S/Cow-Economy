<template>
  <div>
    <ReadNews></ReadNews>
    <ReadNewsCategory></ReadNewsCategory>
  </div>
</template>

<script>
import ReadNews from "@/components/MyPage/MyPageInfo/ReadNews.vue"
import ReadNewsCategory from "@/components/MyPage/MyPageInfo/ReadNewsCategory.vue"

export default {
  name: "MyPageInfo",
  components: {
    ReadNews,
    ReadNewsCategory,
  }
};
</script>

<style></style>
