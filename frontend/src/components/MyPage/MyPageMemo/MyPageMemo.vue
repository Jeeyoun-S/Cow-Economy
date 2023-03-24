<template>
  <div class="pa-1 memo">
    <v-row class="pb-5">
      <v-col cols="4"
        ><v-select
          v-model="range"
          :items="['전체', '기사 제목', '인용구', '메모 내용']"
          dense
          hide-details
        ></v-select
      ></v-col>
      <v-col
        ><v-text-field
          placeholder="검색어를 입력해 주세요"
          dense
          hide-details
        ></v-text-field
      ></v-col>
    </v-row>
    <v-expansion-panels class="swiper-item" accordion multiple tile flat>
      <v-expansion-panel v-for="(article, i) in articles" :key="i">
        <v-expansion-panel-header>
          <v-row class="align-center"
            ><v-col cols="10">{{ article.title }}</v-col
            ><v-col cols="1"
              ><v-btn icon text color="var(--main-col-3)" small
                ><v-icon small> mdi-link-variant </v-icon></v-btn
              ></v-col
            >
          </v-row>
        </v-expansion-panel-header>
        <v-expansion-panel-content>
          <v-sheet class="py-1" v-for="(m, i) in article.memo" :key="i">
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
                <NewsDetailMemoBtnLock color="white"></NewsDetailMemoBtnLock>
                <NewsDetailMemoBtnDelete
                  color="white"
                ></NewsDetailMemoBtnDelete>
              </div>
            </v-sheet>
            <!-- reference -->
            <div class="ma-2 pa-2 border-left font-italic xs-font">
              <div>{{ m.referenceText }}</div>
            </div>
            <!-- content -->
            <div class="ma-2 sm-font">{{ m.memoContent }}</div>
          </v-sheet>
          <v-divider class="mt-2"></v-divider>
        </v-expansion-panel-content>
      </v-expansion-panel>
    </v-expansion-panels>
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
  data() {
    return {
      range: "전체",
      articles: [
        {
          title:
            "비트코인, 빅스텝 우려에 주춤…3100만원도 '아슬'비트코인, 빅스텝 우려에 주춤…3100만원도 '아슬'",
          memo: [
            {
              memoId: 1,
              memoContent: "와~ 돈이 참 많네요... 진짜 부럽다...",
              regtime: "2023.03.02 20:00",
              referenceText:
                "“코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.”",
            },
            {
              memoId: 1,
              memoContent: "와~ 돈이 참 많네요... 진짜 부럽다...",
              regtime: "2023.03.02 20:00",
              referenceText:
                "“코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.”",
            },
          ],
        },
        { title: "비트코인, 빅스텝 우려에 주춤…3100만원도 '아슬'", memo: [] },
        { title: "비트코인, 빅스텝 우려에 주춤…3100만원도 '아슬'", memo: [] },
      ],
    };
  },
};
</script>

<style></style>
