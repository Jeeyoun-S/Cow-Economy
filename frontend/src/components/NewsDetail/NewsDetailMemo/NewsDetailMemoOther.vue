<template>
  <v-sheet>
    <v-sheet
      class="pt-2 d-flex justify-center sm-font"
      v-if="memoOther.length < 1"
      >등록된 메모가 없습니다.</v-sheet
    >
    <NewsDetailMemoBox
      :isMine="false"
      v-for="(memo, index) in memos"
      :key="index"
      :memo="memo"
    ></NewsDetailMemoBox>
  </v-sheet>
</template>

<script>
import NewsDetailMemoBox from "./NewsDetailMemoBox.vue";

export default {
  name: "NewsDetailMemoOther",
  components: {
    NewsDetailMemoBox,
  },
  props: {
    // 정렬 방식
    memoOther: Array,
    sort: String,
  },
  data() {
    return {
      memos: [],
      memos_all: [],
      memoIndex: 0,
      bottom: false,
    };
  },
  methods: {
    addMemo() {
      if (this.memos.length < this.memos_all.length) {
        // +10과 최대 Index 중 최솟값 구하기
        const maxIndex = Math.min(this.memos_all.length, this.memoIndex + 10);
        // 전체 메모 리스트에서 slice해서 memos에 추가
        this.memos.push(...this.memos_all.slice(this.memoIndex, maxIndex));
        // 이미 보여준 마지막 memoIndex 업데이트
        this.memoIndex = maxIndex;
      }
    },
    sortMemoList() {
      if (this.sort == "레벨순") {
        this.memos_all.sort(function (a, b) {
          return b.userExperience - a.userExperience;
        });
      } else {
        this.memos_all.sort(function (a, b) {
          return new Date(b.regtime) - new Date(a.regtime);
        });
      }

      // 무한스크롤 초기화하고 다시 넣기
      this.memoIndex = 0;
      this.memos = [];
      this.addMemo();
    },
    bottomVisible() {
      const scrollY = window.scrollY;
      const visible = document.documentElement.clientHeight;
      const pageHeight = document.documentElement.scrollHeight;
      // + 73은 Footer의 높이
      const bottomOfPage = visible + scrollY + 73 >= pageHeight;
      return bottomOfPage || pageHeight < visible;
    },
    onTheBottom() {
      this.bottom = this.bottomVisible();
    },
  },
  watch: {
    bottom(bottom) {
      if (bottom && this.memos.length < this.memos_all.length) {
        this.addMemo();
      }
    },
    sort() {
      this.sortMemoList();
    },
  },
  created() {
    window.addEventListener("scroll", this.onTheBottom);
    this.memos_all = this.memoOther;
    this.sortMemoList();
  },
  beforeDestroy() {
    window.removeEventListener("scroll", this.onTheBottom);
  },
};
</script>

<style></style>
