<template>
  <v-sheet color="transparent">
    <v-toolbar flat height="78px" color="transparent">
      <!-- 뒤로가기 아이콘 -->
      <BackIcon></BackIcon>
      <!-- 검색창 -->
      <v-text-field
        v-model="keyword"
        class="px-3"
        placeholder="검색어를 입력해 주세요."
        append-icon="mdi-magnify"
        clear-icon="mdi-close-circle"
        solo
        hide-details
        clearable
        @click:append="searchKeyword()"
        @click:clear="clearKeyword()"
      ></v-text-field>
    </v-toolbar>
  </v-sheet>
</template>

<script>
import BackIcon from "@/common/component/BackIcon.vue";
import { mapActions } from "vuex";
export default {
  name: "SearchHeader",
  components: {
    BackIcon,
  },
  data() {
    return { keyword: "", page: "1" };
  },
  methods: {
    ...mapActions("newsStore", ["init", "setSearchText", "setSearched", "setNews"]),
    // onInput(value) {
    //   this.setSearchText(value);
    //   this.setSearched(true);
    // },
    async searchKeyword() {
      await this.init();
      this.setSearchText(this.keyword);
      await this.setNews({"keyword": this.keyword, "lastArticleId": Number.MAX_SAFE_INTEGER + 1});
      // this.setSearched(true);
    },
    clearKeyword() {
      this.keyword = "";
      this.setSearchText("");
    },
  },
};
</script>

<style></style>
