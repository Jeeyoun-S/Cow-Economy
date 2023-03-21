<template>
  <v-sheet>
    <!-- register -->
    <NewsDetailMemoRegister
      @addNewMemo="addNewMemo"
      @updateMemo="updateMemo"
    ></NewsDetailMemoRegister>
    <!-- memo boxes -->
    <NewsDetailMemoBox
      :isMine="true"
      v-for="(memo, index) in memos"
      :key="index"
      :memo="memo"
      :index="index"
      @deleteMemoItem="deleteMemoItem"
    ></NewsDetailMemoBox>
    <!-- scroll loading -->
    <v-sheet
      v-if="memos.length === 0 && myMemoList.length > 0"
      class="d-flex justify-center"
    >
      <v-progress-circular indeterminate color="primary" class="bottom" />
    </v-sheet>
  </v-sheet>
</template>

<script>
import NewsDetailMemoRegister from "./NewsDetailMemoRegister.vue";
import NewsDetailMemoBox from "./NewsDetailMemoBox.vue";
import { mapActions, mapState } from "vuex";

const memoStore = "memoStore";

export default {
  name: "NewsDetailMemoMine",
  components: {
    NewsDetailMemoRegister,
    NewsDetailMemoBox,
  },
  props: {
    // 정렬 방식
    sort: String,
  },
  data() {
    return {
      memos: [], // 보여지고 있는 memo 리스트
      memoIndex: 0, // 보여지고 있는 memo의 마지막 index
      bottom: false, // 현재 스크롤의 위치
    };
  },
  computed: {
    ...mapState(memoStore, ["myMemoList"]),
  },
  methods: {
    ...mapActions(memoStore, ["addMyMemo"]),
    deleteMemoItem(index) {
      if (this.memos.length > index) {
        this.memos.splice(index, 1);
      }
    },
    addMemo() {
      if (this.memos.length < this.myMemoList.length) {
        // +10과 최대 Index 중 최솟값 구하기
        const maxIndex = Math.min(this.myMemoList.length, this.memoIndex + 10);
        // 전체 메모 리스트에서 slice해서 memos에 추가
        this.memos.push(...this.myMemoList.slice(this.memoIndex, maxIndex));
        // 이미 보여준 마지막 memoIndex 업데이트
        this.memoIndex = maxIndex;
      }
    },
    bottomVisible() {
      const scrollY = window.scrollY;
      const visible = document.documentElement.clientHeight;
      const pageHeight = document.documentElement.scrollHeight;
      // + 73은 Footer의 높이
      const bottomOfPage = visible + scrollY + 73 >= pageHeight;
      return bottomOfPage || pageHeight < visible;
    },
    sortMemoList() {
      if (this.sort == "최신순") {
        this.myMemoList.sort(function (a, b) {
          return b.regtime - a.regtime;
        });
      } else {
        this.myMemoList.sort(function (a, b) {
          return a.heartNum - b.heartNum;
        });
      }

      // 무한스크롤 초기화하고 다시 넣기
      this.memoIndex = 0;
      this.memos = [];
      this.addMemo();
    },
    addNewMemo(memo) {
      this.addMyMemo({
        memo: memo,
        sort: this.sort,
      });
      if (this.sort == "최신순") {
        this.memos.unshift(memo);
        this.memoIndex += 1;
      } else {
        if (this.myMemoList.length - 1 <= this.memos.length) {
          this.memos.push(memo);
          this.memoIndex += 1;
        }
      }
    },
    updateMemo(memo, index) {
      if (index < this.memos.length) {
        // re-rendering
        var memoList = [...this.memos];
        memoList[index] = memo;
        this.memos = memoList;
      }
    },
  },
  watch: {
    bottom(bottom) {
      // 스크롤이 바닥에 닿은 상태이고, 전체 길이보다 현재 보여주고 있는 길이가 짧은 경우
      if (bottom && this.memos.length < this.myMemoList.length) {
        // 메모 추가
        this.addMemo();
      }
    },
    sort() {
      this.sortMemoList();
    },
  },
  created() {
    // 스크롤 이동할 때 bottom 변화 확인
    window.addEventListener("scroll", () => {
      this.bottom = this.bottomVisible();
    });
    this.sortMemoList();
    this.addMemo();
  },
  beforeDestroy() {
    window.removeEventListener("scroll", () => {
      this.bottom = this.bottomVisible();
    });
  },
};
</script>

<style></style>
