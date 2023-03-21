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
          referenceText: [
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          ],
          memoContent: "와~ 돈이 참 많네요... 진짜 부럽다...",
          regtime: "2023.03.02 18:00",
          userNickname: "경제왕이될거야",
          memoId: 1,
        },
        {
          referenceText: [
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          ],
          memoContent: "와~ 돈이 참 많네요... 진짜 부럽다...",
          regtime: "2023.03.02 18:00",
          userNickname: "경제왕이될거야",
          memoId: 2,
        },
        {
          referenceText: [
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          ],
          memoContent: "와~ 돈이 참 많네요... 진짜 부럽다...",
          regtime: "2023.03.02 18:00",
          userNickname: "경제왕이될거야",
          memoId: 3,
        },
        {
          referenceText: [
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          ],
          memoContent: "와~ 돈이 참 많네요... 진짜 부럽다...",
          regtime: "2023.03.02 18:00",
          userNickname: "경제왕이될거야",
          memoId: 4,
        },
        {
          referenceText: [
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          ],
          memoContent: "와~ 돈이 참 많네요... 진짜 부럽다...",
          regtime: "2023.03.02 18:00",
          userNickname: "경제왕이될거야",
          memoId: 5,
        },
        {
          referenceText: [
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          ],
          memoContent: "와~ 돈이 참 많네요... 진짜 부럽다...",
          regtime: "2023.03.02 18:00",
          userNickname: "경제왕이될거야",
          memoId: 6,
        },
        {
          referenceText: [
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          ],
          memoContent: "와~ 돈이 참 많네요... 진짜 부럽다...",
          regtime: "2023.03.02 18:00",
          userNickname: "경제왕이될거야",
          memoId: 7,
        },
        {
          referenceText: [
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          ],
          memoContent: "와~ 돈이 참 많네요... 진짜 부럽다...",
          regtime: "2023.03.02 18:00",
          userNickname: "경제왕이될거야",
          memoId: 8,
        },
        {
          referenceText: [
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          ],
          memoContent: "와~ 돈이 참 많네요... 진짜 부럽다...",
          regtime: "2023.03.02 18:00",
          userNickname: "경제왕이될거야",
          memoId: 9,
        },
        {
          referenceText: [
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          ],
          memoContent: "와~ 돈이 참 많네요... 진짜 부럽다...",
          regtime: "2023.03.02 18:00",
          userNickname: "경제왕이될거야",
          memoId: 10,
        },
        {
          referenceText: [
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          ],
          memoContent: "와~ 돈이 참 많네요... 진짜 부럽다...",
          regtime: "2023.03.02 18:00",
          userNickname: "경제왕이될거야",
          memoId: 11,
        },
        {
          referenceText: [
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          ],
          memoContent: "와~ 돈이 참 많네요... 진짜 부럽다...",
          regtime: "2023.03.02 18:00",
          userNickname: "경제왕이될거야",
          memoId: 12,
        },
        {
          referenceText: [
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          ],
          memoContent: "와~ 돈이 참 많네요... 진짜 부럽다...",
          regtime: "2023.03.02 18:00",
          userNickname: "경제왕이될거야",
          memoId: 13,
        },
        {
          referenceText: [
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          ],
          memoContent: "와~ 돈이 참 많네요... 진짜 부럽다...",
          regtime: "2023.03.02 18:00",
          userNickname: "경제왕이될거야",
          memoId: 14,
        },
        {
          referenceText: [
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          ],
          memoContent: "와~ 돈이 참 많네요... 진짜 부럽다...",
          regtime: "2023.03.02 18:00",
          userNickname: "경제왕이될거야",
          memoId: 15,
        },
        {
          referenceText: [
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          ],
          memoContent: "와~ 돈이 참 많네요... 진짜 부럽다...",
          regtime: "2023.03.02 18:00",
          userNickname: "경제왕이될거야",
          memoId: 16,
        },
        {
          referenceText: [
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          ],
          memoContent: "와~ 돈이 참 많네요... 진짜 부럽다...",
          regtime: "2023.03.02 18:00",
          userNickname: "경제왕이될거야",
          memoId: 17,
        },
        {
          referenceText: [
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          ],
          memoContent: "와~ 돈이 참 많네요... 진짜 부럽다...",
          date: "2023.03.02 18:00",
          userNickname: "경제왕이될거야",
          memoId: 18,
        },
        {
          referenceText: [
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          ],
          memoContent: "와~ 돈이 참 많네요... 진짜 부럽다...",
          date: "2023.03.02 18:00",
          userNickname: "경제왕이될거야",
          memoId: 19,
        },
        {
          referenceText: [
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          ],
          memoContent: "와~ 돈이 참 많네요... 진짜 부럽다...",
          date: "2023.03.02 18:00",
          userNickname: "경제왕이될거야",
          memoId: 20,
        },
        {
          referenceText: [
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          ],
          memoContent: "와~ 돈이 참 많네요... 진짜 부럽다...",
          date: "2023.03.02 18:00",
          userNickname: "경제왕이될거야",
          memoId: 21,
        },
        {
          referenceText: [
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          ],
          memoContent: "와~ 돈이 참 많네요... 진짜 부럽다...",
          date: "2023.03.02 18:00",
          userNickname: "경제왕이될거야",
          memoId: 22,
        },
        {
          referenceText: [
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          ],
          memoContent: "와~ 돈이 참 많네요... 진짜 부럽다...",
          date: "2023.03.02 18:00",
          userNickname: "경제왕이될거야",
          memoId: 23,
        },
        {
          referenceText: [
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          ],
          memoContent: "와~ 돈이 참 많네요... 진짜 부럽다...",
          date: "2023.03.02 18:00",
          userNickname: "경제왕이될거야",
          memoId: 24,
        },
        {
          referenceText: [
            "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
          ],
          memoContent: "와~ 돈이 참 많네요... 진짜 부럽다...",
          date: "2023.03.02 18:00",
          userNickname: "경제왕이될거야",
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
      if (bottom && this.memos.length < this.memos_all.length) {
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
