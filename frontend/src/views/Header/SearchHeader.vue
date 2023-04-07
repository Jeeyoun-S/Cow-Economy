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
        @keyup.enter="searchKeyword()"
      ></v-text-field>
    </v-toolbar>
  </v-sheet>
</template>

<script>
import BackIcon from "@/common/component/BackIcon.vue";
import { mapActions, mapState } from "vuex";
export default {
  name: "SearchHeader",
  components: {
    BackIcon,
  },
  data() {
    return { keyword: "", page: "1" };
  },
  computed: {
    ...mapState("newsStore", ["categoryLast"]),
  },
  methods: {
    ...mapActions("newsStore", [
      "init",
      "setSearchText",
      "setSearched",
      "setNews",
    ]),
    async searchKeyword() {
      await this.init();
      this.setSearchText(this.keyword);
      await this.setNews({
        keyword: this.keyword,
        categoryLast: this.categoryLast,
      });
    },
    clearKeyword() {
      this.keyword = "";
      this.setSearchText("");
    },
  },
};
</script>

<style></style>
