<template>
  <div class="pa-1 memo">
    <v-row class="pb-5">
      <v-col cols="4"
        ><v-select
          v-model="range"
          :items="['전체', '기사 제목', '기사 내용', '메모 내용', '인용구']"
          dense
          hide-details
        ></v-select
      ></v-col>
      <v-col
        ><v-text-field
          v-model="keyword"
          placeholder="검색어를 입력해 주세요"
          dense
          hide-details
        ></v-text-field
      ></v-col>
    </v-row>
    <v-expansion-panels class="swiper-item" accordion multiple tile flat>
      <v-expansion-panel v-for="(memoDto, index) in articles" :key="index">
        <v-expansion-panel-header>
          <v-row class="align-center"
            ><v-col cols="10">{{ memoDto.articleTitle }}</v-col
            ><v-col cols="1"
              ><v-btn
                icon
                text
                color="var(--main-col-3)"
                small
                @click="moveNewsDetail(memoDto.articleId)"
                ><v-icon small> mdi-link-variant </v-icon></v-btn
              ></v-col
            >
          </v-row>
        </v-expansion-panel-header>
        <v-expansion-panel-content>
          <v-sheet class="py-1" v-for="(m, i) in memoDto.memoList" :key="i">
            <!-- header -->
            <v-sheet
              class="px-4 sm-font d-flex align-center justify-space-between flex-row"
              color="var(--main-col-3)"
              dark
              small
              block
              rounded="xl"
            >
              <span>{{ m.regtime }}</span>
              <div>
                <NewsDetailMemoBtnLock
                  :memoPublicScope="m.memoPublicScope"
                  :index="index"
                  color="white"
                  :memoId="m.memoId"
                  :isSmall="true"
                  :indexDetail="i"
                  @modifyPublicScope="modifyPublicScope"
                ></NewsDetailMemoBtnLock>
                <NewsDetailMemoBtnDelete
                  :memoId="m.memoId"
                  :index="index"
                  color="white"
                  :isSmall="true"
                  :indexDetail="i"
                  @deleteMemoItem="deleteMemoItem"
                ></NewsDetailMemoBtnDelete>
              </div>
            </v-sheet>
            <!-- reference -->
            <div
              v-if="m.referenceText"
              class="ma-2 pa-2 border-left font-italic xs-font"
            >
              <div>{{ m.referenceText }}</div>
            </div>
            <!-- content -->
            <div class="ma-2 sm-font">{{ m.memoContent }}</div>
          </v-sheet>
          <v-divider class="mt-2"></v-divider>
        </v-expansion-panel-content>
      </v-expansion-panel>
    </v-expansion-panels>
    <!-- scroll loading -->
    <v-sheet
      v-if="articles.length === 0 && articles_all.length > 0"
      class="d-flex justify-center"
    >
      <v-progress-circular indeterminate color="primary" class="bottom" />
    </v-sheet>
  </div>
</template>

<script>
import NewsDetailMemoBtnDelete from "@/components/NewsDetail/NewsDetailMemo/NewsDetailMemoBtnDelete.vue";
import NewsDetailMemoBtnLock from "@/components/NewsDetail/NewsDetailMemo/NewsDetailMemoBtnLock.vue";

export default {
  name: "MyPageMemo",
  components: {
    NewsDetailMemoBtnDelete,
    NewsDetailMemoBtnLock,
  },
  props: {
    memoDtoList: Array,
  },
  data() {
    return {
      range: "전체",
      keyword: "",
      articles: [],
      articles_all: [],
      articles_original: [],
      articleIndex: 0,
      bottom: false,
    };
  },
  methods: {
    moveNewsDetail(articleId) {
      this.$router.push(`/news/${articleId}`);
    },
    // 보여지는 리스트에 메모 추가하기
    addMemo() {
      if (this.articles.length < this.articles_all.length) {
        // +10과 최대 Index 중 최솟값 구하기
        const maxIndex = Math.min(
          this.articles_all.length,
          this.articleIndex + 10
        );
        // 전체 메모 리스트에서 slice해서 memos에 추가
        this.articles.push(
          ...this.articles_all.slice(this.articleIndex, maxIndex)
        );
        // 이미 보여준 마지막 memoIndex 업데이트
        this.articleIndex = maxIndex;
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
    deleteMemoItem(index, indexDetail) {
      if (this.articles.length > index) {
        this.articles[index].memoList.splice(indexDetail, 1);
        if (this.articles[index].memoList.length <= 0) {
          this.articles.splice(index, 1);
        }
      }
      this.articles_all[index].memoList.splice(indexDetail, 1);
      if (this.articles_all[index].memoList.length <= 0) {
        this.articles_all.splice(index, 1);
      }
    },
    modifyPublicScope(index, indexDetail, memoPublicScope) {
      if (this.articles.length > index) {
        this.articles[index].memoList[indexDetail].memoPublicScope =
          memoPublicScope;
      }
      this.articles_all[index].memoList[indexDetail].memoPublicScope =
        memoPublicScope;
    },
  },
  computed: {
    // 미완성
    // articleSort() {
    //   if (this.keyword) {
    //     if (this.range == "기사 제목") {
    //       //['전체', '기사 제목', '기사 내용', '인용구', '메모 내용']
    //       return this.articles.filter((v) =>
    //         v.articleTitle.includes(this.keyword)
    //       );
    //     } else if (this.range == "기사 내용") {
    //       return this.articles.filter((v) =>
    //         v.articleContent.includes(this.keyword)
    //       );
    //     }
    // else if (this.range == "인용구") {
    //   return this.articles.filter((v) =>
    //     v.memoList.filter((vv) => vv.referenceText.includes(this.keyword))
    //   );
    // } else if (this.range == "메모 내용") {
    //   return this.articles.filter((v) =>
    //     v.memoList.filter((vv) => vv.memoContent.includes(this.keyword))
    //   );
    // }
    //   else {
    //     return this.articles.filter(
    //       (v) =>
    //         v.articleTitle.includes(this.keyword) ||
    //         v.articleContent.includes(this.keyword)
    //     );
    //   }
    // } else {
    //   return this.articles;
    // }
    // },
  },
  watch: {
    // 스크롤이 변경되는 것을 감지
    bottom(bottom) {
      // 스크롤이 바닥에 닿은 상태이고, 전체 길이보다 현재 보여주고 있는 길이가 짧은 경우
      if (bottom && this.articles.length < this.articles_all.length) {
        // 메모 추가
        this.addMemo();
      }
    },
  },
  created() {
    // 스크롤 이동할 때 bottom 변화 확인
    window.addEventListener("scroll", () => {
      this.bottom = this.bottomVisible();
    });

    this.articles_all = this.memoDtoList;
    this.articles_original = this.memoDtoList;
    this.addMemo();
  },
};
</script>

<style></style>
