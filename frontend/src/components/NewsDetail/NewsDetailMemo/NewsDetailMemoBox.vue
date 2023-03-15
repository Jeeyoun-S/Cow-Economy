<template>
  <div>
    <v-hover v-if="!modifyMode" v-slot="{ hover }" open-delay="200">
      <!-- memo box -->
      <v-sheet
        :color="hover ? 'var(--main-col-5)' : 'transparent'"
        :class="{ 'on-hover': hover }"
        class="px-8 py-3 d-flex flex-column"
      >
        <!-- memo header -->
        <v-sheet class="mb-3 d-flex align-center" color="transparent">
          <!-- memo editor -->
          <v-chip class="mr-3" color="var(--main-col-3)" label dark small>{{
            nickname
          }}</v-chip>
          <v-divider></v-divider>
          <!-- memo date -->
          <span class="mx-1 ml-2 sm-font">{{ date }}</span>
          <!-- memo update -->
          <div v-if="isMine">
            <!-- memo open status button -->
            <NewsDetailMemoBtnLock></NewsDetailMemoBtnLock>
            <!-- memo modify button -->
            <v-btn
              icon
              text
              color="var(--main-col-3)"
              @click="modifyMode = true"
              ><v-icon> mdi-pencil </v-icon></v-btn
            >
            <!-- memo delete button -->
            <NewsDetailMemoBtnDelete :memoId="memoId"></NewsDetailMemoBtnDelete>
          </div>
        </v-sheet>
        <!-- memo reference box -->
        <NewsDetailMemoReference
          :reference="reference"
        ></NewsDetailMemoReference>
        <!-- memo content -->
        <v-sheet color="transparent">
          {{ content }}
        </v-sheet>
      </v-sheet>
    </v-hover>
    <!-- memo modify version -->
    <NewsDetailMemoModify
      v-else
      @changeMode="modifyMode = false"
      :reference="reference"
      :memoId="memoId"
      :content="content"
    ></NewsDetailMemoModify>
    <!-- <v-divider></v-divider> -->
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
  props: {
    isMine: Boolean,
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
  created() {
    if (this.isMine) {
      this.nickname = "MY";
    }
  },
};
</script>

<style></style>
