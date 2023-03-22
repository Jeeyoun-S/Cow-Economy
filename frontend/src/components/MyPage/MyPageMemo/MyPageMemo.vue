<template>
  <div class="pa-1">
    <v-row class="pb-7">
      <v-col cols="3"
        ><v-select
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
    <v-expansion-panels class="swiper-item" hover accordion multiple tile>
      <v-expansion-panel v-for="(article, i) in articles" :key="i">
        <v-expansion-panel-header>{{ article.title }}</v-expansion-panel-header>
        <v-expansion-panel-content>
          Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do
          eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad
          minim veniam, quis nostrud exercitation ullamco laboris nisi ut
          aliquip ex ea commodo consequat.
        </v-expansion-panel-content>
      </v-expansion-panel>
    </v-expansion-panels>
  </div>
</template>

<script>
export default {
  name: "MyPageMemo",
  data() {
    return {
      articles: [
        {
          title: "비트코인, 빅스텝 우려에 주춤…3100만원도 '아슬'",
          memo: [
            {
              memoId: 0,
              memoContent:
                "와~ 돈이 참 많네요... 진짜 다시 한번 너무 부럽다...",
            },
            { memoId: 1, memoContent: "와~ 돈이 참 많네요... 진짜 부럽다..." },
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
