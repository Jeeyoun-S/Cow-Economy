<template>
  <v-sheet>
    <NewsDetailMemoRegister></NewsDetailMemoRegister>
    <NewsDetailMemoBox
      :isMine="true"
      v-for="n in 5"
      :key="n"
    ></NewsDetailMemoBox>
  </v-sheet>
</template>

<script>
import NewsDetailMemoRegister from "./NewsDetailMemoRegister.vue";
import NewsDetailMemoBox from "./NewsDetailMemoBox.vue";

export default {
  name: "NewsDetailMemoMine",
  components: {
    NewsDetailMemoRegister,
    NewsDetailMemoBox,
  },
};
</script>

<style></style>
