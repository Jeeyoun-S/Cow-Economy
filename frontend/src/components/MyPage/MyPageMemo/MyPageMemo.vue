<template>
  <div>
    <div class="pa-1 memo d-flex flex-column">
      <v-row class="pb-3">
        <v-col cols="4"
          ><v-select
            v-model="range"
            :items="['전체', '기사 제목', '기사 내용', '메모 내용', '인용구']"
            dense
            hide-details
          ></v-select
        ></v-col>
        <v-col>
          <v-text-field
            v-model="keyword"
            placeholder="검색어를 입력해 주세요"
            dense
            hide-details
            append-icon="mdi-magnify"
          ></v-text-field
        ></v-col>
      </v-row>
      <div class="pb-1 ml-auto" v-if="keyword">
        <v-btn
          color="var(--main-col-2)"
          elevation="0"
          small
          dark
          rounded
          outlined
          @click="refreshSearch()"
          ><v-icon class="mr-1" small>mdi-refresh</v-icon>검색 초기화</v-btn
        >
      </div>
      <MyPageMemoList
        :keyword="keyword"
        :range="range"
        :memoDtoList="memoDtoList"
      ></MyPageMemoList>
    </div>
  </div>
</template>

<script>
import MyPageMemoList from "@/components/MyPage/MyPageMemo/MyPageMemoList.vue";

export default {
  name: "MyPageMemo",
  components: {
    MyPageMemoList,
  },
  props: {
    memoDtoList: Array,
  },
  data() {
    return {
      range: "전체",
      keyword: "",
    };
  },
  methods: {
    refreshSearch() {
      this.keyword = "";
      this.range = "전체";
    },
  },
};
</script>

<style></style>
