<template>
  <v-sheet class="py-5" id="memo">
    <v-sheet class="mx-5 d-flex flex-row align-center">
      <!-- memo title -->
      <span class="mr-auto b-font lg-font">메모</span>
      <!-- memo sort select -->
      <v-sheet width="100px">
        <v-select
          v-model="sort"
          :disabled="isListMine ? true : false"
          :items="isListMine ? ['최신순'] : ['최신순', '레벨순']"
          color="var(--main-col-2)"
          dense
          rounded
          outlined
          hide-details
          style="font-size: 15px"
        ></v-select>
      </v-sheet>
    </v-sheet>
    <!-- list buttons -->
    <!-- <v-row v-if="isLoggedIn" class="mx-5 py-5 pt-7"> -->
    <v-col class="pa-0">
      <v-btn
        elevation="0"
        color="var(--main-col-2)"
        @click="isListMine = true"
        :dark="isListMine ? true : false"
        :outlined="isListMine ? false : true"
        block
        tile
        >나의 메모</v-btn
      >
    </v-col>
    <v-col class="pa-0">
      <v-btn
        elevation="0"
        color="var(--main-col-2)"
        @click="isListMine = false"
        :dark="isListMine ? false : true"
        :outlined="isListMine ? true : false"
        block
        tile
        >전체 메모</v-btn
      >
    </v-col>
    <!-- </v-row> -->
    <!-- Login Guide -->
    <!-- <v-row v-else class="mx-6 py-5 pt-7"> -->
    <v-sheet
      class="pa-1 px-5 d-flex flex-row align-center"
      color="var(--main-col-2)"
      dark
      width="100%"
      rounded="xl"
      ><span class="sm-font">메모 작성은 회원만 가능합니다.</span>
      <v-btn
        class="my-1 ml-auto"
        @click="moveLogin()"
        elevation="0"
        color="var(--main-col-2)"
        rounded
        text
        style="background-color: white"
        small
        ><v-icon small>mdi-account</v-icon>로그인</v-btn
      ></v-sheet
    >
    <!-- </v-row> -->
    <!-- my memo list -->
    <NewsDetailMemoMine
      v-if="isListMine"
      :sort="sort"
      :memoMine="memoMine"
    ></NewsDetailMemoMine>
    <!-- others memo list -->
    <NewsDetailMemoOther
      v-else
      :sort="sort"
      :memoOther="memoOther"
    ></NewsDetailMemoOther>
  </v-sheet>
</template>

<script>
import NewsDetailMemoOther from "./NewsDetailMemoOther.vue";
import NewsDetailMemoMine from "./NewsDetailMemoMine.vue";
import { mapGetters } from "vuex";

export default {
  name: "NewsDetail",
  props: {
    memoMine: Array,
    memoOther: Array,
  },
  components: {
    NewsDetailMemoMine,
    NewsDetailMemoOther,
  },
  computed: {
    ...mapGetters("userStore", ["isLoggedIn"]),
  },
  data() {
    return {
      sort: "최신순", // 정렬 방식
      isListMine: true, // true면 나의 메모, false면 전체 메모
    };
  },
  methods: {
    moveLogin() {
      this.$router.push("/my-page");
    },
  },
  // 나의 메모 or 전체 메모로 바꾸면 sort도 함께 초기화
  watch: {
    isListMine() {
      this.sort = "최신순";
    },
  },
  created() {
    // if (!this.isLoggedIn) this.isListMine = false;
  },
};
</script>

<style></style>
