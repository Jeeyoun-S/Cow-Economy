<template>
  <v-sheet @scroll="this.bottom = this.bottomVisible()">
    <!-- register -->
    <NewsDetailMemoRegister
      @addNewMemo="addNewMemo"
      @updateMemo="updateMemo"
    ></NewsDetailMemoRegister>
    <!-- memo boxes -->
    <NewsDetailMemoBox
      v-for="(memo, index) in memos"
      :isMine="true"
      :key="index"
      :memo="memo"
      :index="index"
      @deleteMemoItem="deleteMemoItem"
      @modifyPublicScope="modifyPublicScope"
    ></NewsDetailMemoBox>
    <!-- scroll loading -->
    <v-sheet
      v-if="memos.length === 0 && memos_all.length > 0"
      class="d-flex justify-center"
    >
      <v-progress-circular indeterminate color="primary" class="bottom" />
    </v-sheet>
  </v-sheet>
</template>

<script>
import NewsDetailMemoRegister from "./NewsDetailMemoRegister.vue";
import NewsDetailMemoBox from "./NewsDetailMemoBox.vue";

export default {
  name: "NewsDetailMemoMine",
  components: {
    NewsDetailMemoRegister,
    NewsDetailMemoBox,
  },
  props: {
    memoMine: Array, // 나의 메모 리스트
    sort: String, // 정렬 방식
  },
  data() {
    return {
      memos: [], // 보여지고 있는 memo 리스트
      memos_all: [], // 전체 memo 리스트
      memoIndex: 0, // 보여지고 있는 memo의 마지막 index
      bottom: false, // 현재 스크롤의 위치
    };
  },
  methods: {
    // index 위치의 publicScope 변경
    modifyPublicScope(index, publicScope) {
      if (this.memos.length > index) {
        this.memos[index].memoPublicScope = publicScope;
      }
      this.memos_all[index].memoPublicScope = publicScope;
    },
    // index에 해당하는 메모 지우기
    deleteMemoItem(index) {
      if (this.memos.length > index) {
        this.memos.splice(index, 1);
      }
      this.memos_all.splice(index, 1);
    },
    // 보여지는 리스트에 메모 추가하기
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
    // 스크롤이 Footer 제외 아래에 닿았는지
    bottomVisible() {
      const scrollY = window.scrollY;
      const visible = document.documentElement.clientHeight;
      const pageHeight = document.documentElement.scrollHeight;
      // + 73은 Footer의 높이
      const bottomOfPage = visible + scrollY + 73 >= pageHeight;
      return bottomOfPage || pageHeight < visible;
    },
    // memolist를 정렬
    sortMemoList() {
      if (this.sort == "최신순") {
        this.memos_all.sort(function (a, b) {
          return new Date(b.regtime) - new Date(a.regtime);
        });
      } else {
        this.memos_all.sort(function (a, b) {
          return a.heartNum - b.heartNum;
        });
      }

      // 무한스크롤 초기화하고 다시 넣기
      this.memoIndex = 0;
      this.memos = [];
      this.addMemo();
    },
    // 새로운 메모 추가하기
    addNewMemo(memo) {
      if (this.sort == "최신순") {
        this.memos.unshift(memo);
        this.memos_all.unshift(memo);
        this.memoIndex += 1;
      } else {
        if (this.memos_all.length - 1 <= this.memos.length) {
          this.memos.push(memo);
          this.memoIndex += 1;
        }
        this.memos_all.push(memo);
      }
    },
    // 기존 메모 내용 변경하기
    updateMemo(memo, index) {
      if (index < this.memos.length) {
        // re-rendering을 위한 작업
        var memoList = [...this.memos];
        memoList[index] = memo;
        this.memos = memoList;
      }
      this.memos_all[index] = memo;
    },
  },
  watch: {
    // 스크롤이 변경되는 것을 감지
    bottom(bottom) {
      // 스크롤이 바닥에 닿은 상태이고, 전체 길이보다 현재 보여주고 있는 길이가 짧은 경우
      if (bottom && this.memos.length < this.memos_all.length) {
        // 메모 추가
        this.addMemo();
      }
    },
    // 정렬 방식이 변경되는지 감지
    sort() {
      this.sortMemoList();
    },
  },
  created() {
    // 스크롤 이동할 때 bottom 변화 확인
    // window.addEventListener("scroll", () => {
    //   this.bottom = this.bottomVisible();
    // });
    // memo 리스트 초기화
    this.memos_all = this.memoMine;
    this.sortMemoList();
  },
  // destroy 시 scroll 이벤트 삭제
  beforeDestroy() {
    window.removeEventListener("scroll", () => {
      this.bottom = this.bottomVisible();
    });
  },
};
</script>

<style></style>
