<template>
  <v-sheet
    min-height="100%"
    id="scrolling-techniques-7"
    class="overflow-y-auto"
  >
    <NewsDetailFloatingBtn v-show="false"></NewsDetailFloatingBtn>
    <NewsDetailContent></NewsDetailContent>
    <NewsDetailRelation></NewsDetailRelation>
    <NewsDetailMemo></NewsDetailMemo>
    <!-- complete reading -->
    <v-snackbar
      :timeout="2000"
      v-model="localDone"
      color="var(--main-col-4-1)"
      elevation="10"
      width="90%"
      min-width="0"
      max-width="450"
    >
      <v-sheet
        color="transparent"
        class="d-flex flex-row align-center justify-space-between"
      >
        <span class="main-col-1"
          >기사를 읽어,
          <span class="b-font">경험치가 1 EXP 증가</span>했습니다.</span
        ><v-btn icon text>
          <v-icon color="var(--main-col-1)" @click="localDone = false">
            mdi-close-circle
          </v-icon></v-btn
        >
      </v-sheet>
    </v-snackbar>
  </v-sheet>
</template>

<script>
import NewsDetailContent from "./NewsDetailContent.vue";
import NewsDetailRelation from "./NewsDetailRelation/NewsDetailRelation.vue";
import NewsDetailMemo from "./NewsDetailMemo/NewsDetailMemo.vue";
import NewsDetailFloatingBtn from "./NewsDetailFloatingBtn/NewsDetailFloatingBtn.vue";
import { mapState } from "vuex";
import memoStore from "@/store/modules/memoStore";

export default {
  name: "NewsDetail",
  data() {
    return {
      localDone: false,
    };
  },
  computed: {
    ...mapState("memoStore", ["done"]),
  },
  components: {
    NewsDetailContent,
    NewsDetailRelation,
    NewsDetailMemo,
    NewsDetailFloatingBtn,
  },
  watch: {
    localDone() {
      memoStore.state.done = this.localDone;
    },
    done() {
      this.localDone = this.done;
    },
  },
  mounted() {
    var content = document.getElementById("news-content");
    var target = content.offsetTop + content.offsetHeight;
    document.addEventListener("scroll", function finishReading() {
      var now = window.scrollY + document.documentElement.clientHeight * 0.8;
      if (now > target) {
        memoStore.state.done = true;
        document.removeEventListener("scroll", finishReading);
      }
    });
  },
};
</script>

<style>
#bookmark-pointer {
  display: inline;
}
</style>
