<template>
  <v-sheet id="news-content" class="pa-5 d-flex flex-column">
    <!-- 1 : category & press -->
    <div class="pb-2 d-flex">
      <v-chip class="mr-2" color="var(--main-col-2)" outlined>{{
        newsDetail.articleCategory
      }}</v-chip>
      <v-chip color="var(--main-col-2)" outlined>{{
        newsDetail.articlePress
      }}</v-chip>
    </div>

    <!-- 2 : title -->
    <div class="py-2">
      <span class="b-font xxxxl-font">{{ newsDetail.articleTitle }}</span>
    </div>

    <!-- 3 : date & original url -->
    <div class="py-2 d-flex align-center justify-space-between">
      <span class="xsm-font">{{
        newsDetail.articleRegtime.substr(0, 16)
      }}</span>
      <div class="d-flex align-center">
        <v-btn-toggle
          v-model="toggle_exclusive"
          color="var(--main-col-1)"
          mandatory
          dense
          rounded
          class="mr-2"
        >
          <v-btn class="pa-0" text small>작게</v-btn>
          <v-btn class="pa-0" text small>보통</v-btn>
          <v-btn class="pa-0" text small>크게</v-btn>
        </v-btn-toggle>
      </div>
    </div>

    <!-- 4 : content -->
    <div
      id="content"
      class="py-2"
      :class="
        toggle_exclusive == 2
          ? 'lg-font'
          : toggle_exclusive == 0
          ? 'sm-font'
          : ''
      "
    >
      <!-- <div id="article" v-html="newsDetail.articleContent"></div> -->
    </div>

    <!-- 5 : reference plus button -->
    <div class="py-2 d-flex flex-row">
      <div v-if="newsDetail.articleEditor">
        {{ newsDetail.articleEditor }}
      </div>
      <v-btn
        class="ml-auto"
        color="grey darken-1"
        small
        outlined
        rounded
        @click="openOriginalUrl()"
        >원본URL</v-btn
      >
    </div>

    <!-- 6 : add reference -->
    <v-snackbar v-model="memoBtnLocal" color="var(--main-col-2)" rounded="pill">
      <div class="d-flex flex-row align-center">
        <v-icon>mdi-plus-circle</v-icon>
        <span class="ml-2">메모에 인용문 추가하기</span>
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
// import { addSelectionEvent } from "@/common/function/textSelection";
import { mapActions, mapGetters } from "vuex";

const memoStore = "memoStore";

export default {
  name: "NewsDetailContent",
  data() {
    return {
      toggle_exclusive: 1,
      memoBtnLocal: false,
    };
  },
  props: {
    newsDetail: Object,
  },
  computed: {
    // 메모 인용문 추가 버튼 활성화 여부 memoBtn
    // ...mapState(memoStore, ["memoBtn"]),
    ...mapGetters("userStore", ["isLoggedIn"]),
  },
  methods: {
    ...mapActions(memoStore, ["changeMemoBtn", "getSelectionText"]),
    // 인용문 추가하기
    addMemoReference() {
      this.memoBtnLocal = false;
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
    async addSelection() {
      // await addSelectionEvent().then((res) => {
      //   this.memoBtnLocal = res;
      // });
      try {
        var selection = window.getSelection();
        if (!selection) {
          selection = document.getSelection();
        }
        if (this.memoBtnLocal && !selection.getRangeAt(0).toString()) {
          this.memoBtnLocal = false;
          // console.log("여기");
        } else if (!this.memoBtnLocal && selection.getRangeAt(0).toString()) {
          this.memoBtnLocal = true;
          // console.log("여기2");
        }
        // console.log(
        //   "selection",
        //   selection.getRangeAt(0).toString(),
        //   !!selection.getRangeAt(0).toString()
        // );
      } catch (err) {
        err;
        this.memoBtnLocal = false;
      }
    },
  },
  mounted() {
    document
      .getElementById("content")
      .appendChild(this.newsDetail.articleContent);

    // if (this.isLoggedIn) {
      // 텍스트 드래그하면 메모 추가 창이 생기는 event 추가
      // document 전체에 적용 (div#article에만 하면 미작동)
      document.addEventListener("selectionchange", this.addSelection);
    // }
  },
  beforeDestroy() {
    // 텍스트 드래그하면 메모 추가 창이 생기는 event 삭제
    document.removeEventListener("selectionchange", this.addSelection);
    // this.memoBtnLocal = false;
  },
};
</script>

<style></style>
