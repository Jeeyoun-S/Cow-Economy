<template>
  <v-sheet>
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
  data() {
    return {
      memos: [],
      memos_all: [
        {
          reference:
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          content: "와~ 돈이 참 많네요... 진짜 부럽다...",
          date: "2023.03.02 18:00",
          nickname: "경제왕이될거야",
          memoId: 1,
        },
        {
          reference:
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          content: "와~ 돈이 참 많네요... 진짜 부럽다...",
          date: "2023.03.02 18:00",
          nickname: "경제왕이될거야",
          memoId: 2,
        },
        {
          reference:
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          content: "와~ 돈이 참 많네요... 진짜 부럽다...",
          date: "2023.03.02 18:00",
          nickname: "경제왕이될거야",
          memoId: 3,
        },
        {
          reference:
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          content: "와~ 돈이 참 많네요... 진짜 부럽다...",
          date: "2023.03.02 18:00",
          nickname: "경제왕이될거야",
          memoId: 4,
        },
        {
          reference:
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          content: "와~ 돈이 참 많네요... 진짜 부럽다...",
          date: "2023.03.02 18:00",
          nickname: "경제왕이될거야",
          memoId: 5,
        },
        {
          reference:
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          content: "와~ 돈이 참 많네요... 진짜 부럽다...",
          date: "2023.03.02 18:00",
          nickname: "경제왕이될거야",
          memoId: 6,
        },
        {
          reference:
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          content: "와~ 돈이 참 많네요... 진짜 부럽다...",
          date: "2023.03.02 18:00",
          nickname: "경제왕이될거야",
          memoId: 7,
        },
        {
          reference:
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          content: "와~ 돈이 참 많네요... 진짜 부럽다...",
          date: "2023.03.02 18:00",
          nickname: "경제왕이될거야",
          memoId: 8,
        },
        {
          reference:
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          content: "와~ 돈이 참 많네요... 진짜 부럽다...",
          date: "2023.03.02 18:00",
          nickname: "경제왕이될거야",
          memoId: 9,
        },
        {
          reference:
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          content: "와~ 돈이 참 많네요... 진짜 부럽다...",
          date: "2023.03.02 18:00",
          nickname: "경제왕이될거야",
          memoId: 10,
        },
        {
          reference:
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          content: "와~ 돈이 참 많네요... 진짜 부럽다...",
          date: "2023.03.02 18:00",
          nickname: "경제왕이될거야",
          memoId: 11,
        },
        {
          reference:
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          content: "와~ 돈이 참 많네요... 진짜 부럽다...",
          date: "2023.03.02 18:00",
          nickname: "경제왕이될거야",
          memoId: 12,
        },
        {
          reference:
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          content: "와~ 돈이 참 많네요... 진짜 부럽다...",
          date: "2023.03.02 18:00",
          nickname: "경제왕이될거야",
          memoId: 13,
        },
        {
          reference:
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          content: "와~ 돈이 참 많네요... 진짜 부럽다...",
          date: "2023.03.02 18:00",
          nickname: "경제왕이될거야",
          memoId: 14,
        },
        {
          reference:
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          content: "와~ 돈이 참 많네요... 진짜 부럽다...",
          date: "2023.03.02 18:00",
          nickname: "경제왕이될거야",
          memoId: 15,
        },
        {
          reference:
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          content: "와~ 돈이 참 많네요... 진짜 부럽다...",
          date: "2023.03.02 18:00",
          nickname: "경제왕이될거야",
          memoId: 16,
        },
        {
          reference:
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          content: "와~ 돈이 참 많네요... 진짜 부럽다...",
          date: "2023.03.02 18:00",
          nickname: "경제왕이될거야",
          memoId: 17,
        },
        {
          reference:
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          content: "와~ 돈이 참 많네요... 진짜 부럽다...",
          date: "2023.03.02 18:00",
          nickname: "경제왕이될거야",
          memoId: 18,
        },
        {
          reference:
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          content: "와~ 돈이 참 많네요... 진짜 부럽다...",
          date: "2023.03.02 18:00",
          nickname: "경제왕이될거야",
          memoId: 19,
        },
        {
          reference:
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          content: "와~ 돈이 참 많네요... 진짜 부럽다...",
          date: "2023.03.02 18:00",
          nickname: "경제왕이될거야",
          memoId: 20,
        },
        {
          reference:
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          content: "와~ 돈이 참 많네요... 진짜 부럽다...",
          date: "2023.03.02 18:00",
          nickname: "경제왕이될거야",
          memoId: 21,
        },
        {
          reference:
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          content: "와~ 돈이 참 많네요... 진짜 부럽다...",
          date: "2023.03.02 18:00",
          nickname: "경제왕이될거야",
          memoId: 22,
        },
        {
          reference:
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          content: "와~ 돈이 참 많네요... 진짜 부럽다...",
          date: "2023.03.02 18:00",
          nickname: "경제왕이될거야",
          memoId: 23,
        },
        {
          reference:
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          content: "와~ 돈이 참 많네요... 진짜 부럽다...",
          date: "2023.03.02 18:00",
          nickname: "경제왕이될거야",
          memoId: 24,
        },
        {
          reference:
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          content: "와~ 돈이 참 많네요... 진짜 부럽다...",
          date: "2023.03.02 18:00",
          nickname: "경제왕이될거야",
          memoId: 25,
        },
      ],
      memoIndex: 0,
      bottom: false,
    };
  },
  methods: {
    addMemo() {
      this.memos.push(
        ...this.memos_all.slice(this.memoIndex, this.memoIndex + 10)
      );
      this.memoIndex += 10;
    },
    bottomVisible() {
      const scrollY = window.scrollY;
      const visible = document.documentElement.clientHeight;
      const pageHeight = document.documentElement.scrollHeight;
      // + 73은 Footer의 높이
      const bottomOfPage = visible + scrollY + 73 >= pageHeight;
      return bottomOfPage || pageHeight < visible;
    },
  },
  watch: {
    bottom(bottom) {
      if (bottom) {
        this.addMemo();
      }
    },
  },
  created() {
    window.addEventListener("scroll", () => {
      this.bottom = this.bottomVisible();
    });
    this.addMemo();
  },
};
</script>

<style></style>
