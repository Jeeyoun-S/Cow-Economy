<template>
  <div>
    <!-- memo box -->
    <v-sheet
      v-if="!modifyMode"
      class="mt-3 pa-5 d-flex flex-column"
      elevation="1"
      rounded="lg"
    >
      <!-- memo header -->
      <v-sheet class="mb-3 d-flex align-center">
        <!-- memo editor -->
        <v-chip class="mr-auto" color="var(--main-col-3)" label dark>{{
          nickname
        }}</v-chip>
        <!-- memo date -->
        <span class="mx-1 sm-font">{{ date }}</span>
        <!-- memo open status button -->
        <NewsDetailMemoBtnLock></NewsDetailMemoBtnLock>
        <!-- memo modify button -->
        <v-btn icon text color="var(--main-col-3)" @click="modifyMode = true"
          ><v-icon> mdi-pencil </v-icon></v-btn
        >
        <!-- memo delete button -->
        <NewsDetailMemoBtnDelete :memoId="memoId"></NewsDetailMemoBtnDelete>
      </v-sheet>
      <!-- memo reference box -->
      <NewsDetailMemoReference :reference="reference"></NewsDetailMemoReference>
      <!-- memo content -->
      <v-sheet>
        {{ content }}
      </v-sheet>
    </v-sheet>
    <!-- memo modify version -->
    <NewsDetailMemoModify
      v-else
      @changeMode="modifyMode = false"
      :reference="reference"
      :memoId="memoId"
      :content="content"
    ></NewsDetailMemoModify>
  </div>
</template>

<script>
import NewsDetailMemoReference from "./NewsDetailMemoReference.vue";
import NewsDetailMemoBtnDelete from "./NewsDetailMemoBtnDelete.vue";
import NewsDetailMemoBtnLock from "./NewsDetailMemoBtnLock.vue";
import NewsDetailMemoModify from "./NewsDetailMemoModify.vue";

export default {
  name: "NewsDetailMemoItem",
  components: {
    NewsDetailMemoReference,
    NewsDetailMemoBtnDelete,
    NewsDetailMemoBtnLock,
    NewsDetailMemoModify,
  },
  data() {
    return {
      reference:
        "코인마켓캡에서는 24시간 전보다 2.09% 상승한 2만3623달러를 나타냈다.",
      content: "와~ 돈이 참 많네요... 진짜 부럽다...",
      date: "2023.03.02 18:00",
      nickname: "경제왕이될거야",
      memoId: 1,
      modifyMode: false,
    };
  },
};
</script>

<style></style>
