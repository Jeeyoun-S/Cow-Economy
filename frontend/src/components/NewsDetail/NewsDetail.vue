<template>
  <v-sheet
    min-height="100%"
    id="scrolling-techniques-7"
    class="overflow-y-auto"
  >
    <NewsDetailLoading v-if="loading"></NewsDetailLoading>
    <div v-else>
      <NewsDetailContent :newsDetail="newsDetail"></NewsDetailContent>
      <NewsDetailRelation :newsRelated="newsRelated"></NewsDetailRelation>
      <NewsDetailMemo
        :memoMine="newsDetail.userArticleMemoListMine"
        :memoOther="newsDetail.userArticleMemoListOther"
      ></NewsDetailMemo>
      <!-- finish reading snackbar -->
      <v-snackbar
        :timeout="5000"
        class="mb-4"
        v-model="localDone"
        color="var(--main-col-4-1)"
        elevation="10"
        width="90%"
        min-width="0"
        max-width="450"
        height="30"
        bottom
      >
        <v-sheet
          color="transparent"
          class="d-flex flex-row align-center justify-space-between"
        >
          <span class="main-col-1"
            >기사를 읽어, <span class="b-font">경험치 1 EXP 증가</span></span
          ><v-btn icon text>
            <v-icon color="var(--main-col-1)" @click="localDone = false">
              mdi-close-circle
            </v-icon></v-btn
          >
        </v-sheet>
      </v-snackbar>
      <!-- word explain modal -->
      <NewsDetailContentWord></NewsDetailContentWord>
    </div>
    <news-detail-server-error ref="detailerror"></news-detail-server-error>
    <scroll-top-btn v-if="!loading"></scroll-top-btn>
  </v-sheet>
</template>

<script>
import NewsDetailContent from "./NewsDetailContent.vue";
import NewsDetailRelation from "./NewsDetailRelation/NewsDetailRelation.vue";
import NewsDetailMemo from "./NewsDetailMemo/NewsDetailMemo.vue";
import NewsDetailLoading from "./NewsDetailLoading.vue";
import { mapActions, mapState } from "vuex";
import memoStore from "@/store/modules/memoStore";
import { getNewsDetail, updateReading } from "@/api/modules/article.js";
import wordStore from "@/store/modules/wordStore";
import NewsDetailContentWord from "@/components/NewsDetail/NewsDetailContentWord.vue";
import NewsDetailServerError from "./NewsDetailServerError.vue";
import ScrollTopBtn from "@/common/component/ScrollTopBtn.vue";

export default {
  name: "NewsDetail",
  data() {
    return {
      localDone: false, // 기사 읽음 snackbar 활성화
      loading: true, // 로딩 중일 경우 true
      newsDetail: null, // 기사 상세 정보
      newsRelated: null, //관련 기사 리스트
    };
  },
  computed: {
    // vuex에 저장된 기사 읽음 snackbar 활성화
    ...mapState("memoStore", ["done"]),
  },
  components: {
    NewsDetailContent,
    NewsDetailRelation,
    NewsDetailMemo,
    NewsDetailLoading,
    NewsDetailContentWord,
    NewsDetailServerError,
    ScrollTopBtn,
  },
  // data와 vuex 내 기사 읽음 snackbar 활성화 값을 동일하게
  watch: {
    localDone() {
      memoStore.state.done = this.localDone;
    },
    done() {
      this.localDone = this.done;
    },
  },
  methods: {
    ...mapActions("wordStore", ["setWordInfo"]),
    ...mapActions("memoStore", ["updateReading"]),
    ...mapActions("newsStore", ["setCurNews"]),

    addScrollEvent() {
      // content의 아래까지 스크롤이 이동하면 기사 읽음 처리
      var content = document.getElementById("news-content");
      // 목표하는 스크롤 위치 (기사 맨 아래)
      var target = content.offsetTop + content.offsetHeight;
      // params에서 기사 ID 가져오기
      var id = this.$route.params.id;
      // 스크롤 이벤트에 넣을 함수
      function finishReading() {
        // 현재 스크롤 위치
        var now = window.scrollY + document.documentElement.clientHeight * 0.8;
        if (now > target && !memoStore.state.reading) {
          memoStore.state.reading = true;
          // 스크롤 이벤트 삭제
          document.removeEventListener("scroll", finishReading);
          // 기사 읽음 처리 API 요청
          updateReading(id).then((res) => {
            if (res) {
              // vuex의 값을 변경해 snackbar 활성화
              memoStore.state.done = true;
            }
          });
        }
      }
      // 스크롤 이벤트 추가
      window.addEventListener("scroll", finishReading);
    },
  },
  async mounted() {
    function click() {
      wordStore.state.wordModal = this.innerText;
      wordStore.state.isWordModalOpen = true;
    }
    // 기사 상세 정보 요청하는 API
    await getNewsDetail(this.$route.params.id).then((res) => {
      if (res == null) {
        this.$refs.detailerror.openDialog();
      } else if (res) {
        // 받아온 기사 내용은 HTML로 바꾸고 event 추가하기
        var content = document.createElement("div");
        content.setAttribute("id", "article");
        content.innerHTML = res.articleContent;
        var spans = content.querySelectorAll("#article span");
        for (var i = 0; i < spans.length; i++) {
          spans[i].addEventListener("click", click);
        }
        res.articleContent = content;

        // newsDetail에 받아온 상세 정보 넣기
        this.newsDetail = res;
        this.setWordInfo(res.articleWordList);

        const newsTitle = this.newsDetail.articleTitle
        const newsContent = this.newsDetail.articleContent.innerText

        this.setCurNews([newsTitle, newsContent])

        // newsDetail에 받아온 관련 기사 아이디 넣기
        this.newsRelated = this.newsDetail.relatedArticleList;

        // 로딩 상태 변경
        this.loading = false;
      }
    });
    // 기사를 아직 안 읽었다면 읽음 처리 Event 추가
    if (this.newsDetail && !this.newsDetail.reading) {
      this.addScrollEvent();
      this.updateReading(this.newsDetail.reading);
    }
  },
};
</script>

<style>
#bookmark-pointer {
  display: inline;
}
</style>
