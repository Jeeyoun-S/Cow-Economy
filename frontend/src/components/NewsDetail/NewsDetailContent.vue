<template>
  <v-sheet id="news-content" class="pa-5 d-flex flex-column">
    <!-- 1 : category & press -->
    <div class="pb-2">
      <v-chip class="mr-2" color="var(--main-col-2)" outlined>{{
        newsDetail.articleCategory
      }}</v-chip>
      <v-chip color="var(--main-col-2)" outlined>{{
        newsDetail.articlePress
      }}</v-chip>
    </div>

    <!-- 2 : title -->
    <div class="py-2">
      <h2>{{ newsDetail.articleTitle }}</h2>
    </div>

    <!-- 3 : date & original url -->
    <div class="py-2 d-flex align-center">
      <span class="sm-font">{{ newsDetail.articleRegtime }}</span>
      <v-btn
        class="ml-auto"
        color="grey darken-1"
        small
        outlined
        rounded
        @click="openOriginalUrl()"
        >원본 보기</v-btn
      >
    </div>

    <!-- 4 : content -->
    <div id="content" class="py-2">
      <!-- <div id="article" v-html="newsDetail.articleContent"></div> -->
    </div>

    <!-- 5 : reference plus button -->
    <div v-if="newsDetail.articleEditor" class="py-2 ml-auto">
      {{ newsDetail.articleEditor }}
    </div>

    <!-- 6 : add reference -->
    <v-snackbar v-model="memoBtn" color="var(--main-col-2)" rounded="pill">
      <div class="d-flex flex-row align-center">
        <v-icon>mdi-plus-circle</v-icon>
        <span class="ml-2">메모에 인용문으로 추가하기</span>
      </div>
      <template v-slot:action="{ attrs }">
        <v-btn
          color="white"
          text
          v-bind="attrs"
          @click="addMemoReference()"
          rounded
        >
          추가
        </v-btn>
        <v-btn
          color="white"
          text
          v-bind="attrs"
          @click="changeMemoBtn()"
          rounded
        >
          닫기
        </v-btn>
      </template>
    </v-snackbar>
  </v-sheet>
</template>

<script>
import { addSelectionEvent } from "@/common/function/textSelection";
import { mapActions, mapState } from "vuex";

const memoStore = "memoStore";

export default {
  name: "NewsDetailContent",
  props: {
    newsDetail: Object,
  },
  computed: {
    // 메모 인용문 추가 버튼 활성화 여부 memoBtn
    ...mapState(memoStore, ["memoBtn"]),
  },
  methods: {
    ...mapActions(memoStore, ["changeMemoBtn", "getSelectionText"]),
    // 인용문 추가하기
    addMemoReference() {
      // 메모 작성란으로 스크롤 이동
      document.getElementById("memo").scrollIntoView(true);
      // 선택한 인용문 정보 vuex에 저장하기
      this.getSelectionText();
    },
    // 원본 URL 열기
    openOriginalUrl() {
      const tab = window.open(this.newsDetail.articleUrl, "_blank");
      tab.focus();
    },
  },
  mounted() {
    document
      .getElementById("content")
      .appendChild(this.newsDetail.articleContent);

    // 텍스트 드래그하면 메모 추가 창이 생기는 event 추가
    // document 전체에 적용 (div#article에만 하면 미작동)
    document.addEventListener("selectionchange", addSelectionEvent);
  },
  destroyed() {
    // 텍스트 드래그하면 메모 추가 창이 생기는 event 삭제
    document.removeEventListener("selectionchange", addSelectionEvent);
  },
};
</script>

<style></style>
