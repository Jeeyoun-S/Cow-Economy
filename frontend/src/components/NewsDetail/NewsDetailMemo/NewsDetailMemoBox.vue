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
        <v-sheet class="mb-2 d-flex align-center" color="transparent">
          <!-- memo editor -->
          <v-chip v-if="!isMine" color="var(--main-col-3)" label dark small>{{
            memo.userNickname
          }}</v-chip>
          <v-divider v-if="!isMine" class="mx-2"></v-divider>
          <!-- memo date -->
          <span class="sm-font">{{ memo.regtime }}</span>
          <v-divider v-if="isMine" class="mx-2"></v-divider>
          <!-- memo update -->
          <div v-if="isMine">
            <!-- memo open status button -->
            <NewsDetailMemoBtnLock
              :memoPublicScope="memo.memoPublicScope"
              :index="index"
            ></NewsDetailMemoBtnLock>
            <!-- memo modify button : 보류 -->
            <v-btn
              icon
              text
              color="var(--main-col-3)"
              @click="modifyMode = true"
              ><v-icon> mdi-pencil </v-icon></v-btn
            >
            <!-- memo delete button -->
            <NewsDetailMemoBtnDelete
              :memoId="memo.memoId"
              :index="index"
              @deleteMemoItem="deleteMemoItem"
            ></NewsDetailMemoBtnDelete>
          </div>
          <div class="ml-1" v-else>
            <v-btn small icon text color="var(--main-col-3)"
              ><v-icon small> mdi-thumb-up </v-icon></v-btn
            >
          </div>
        </v-sheet>
        <!-- memo reference box -->
        <NewsDetailMemoReference
          v-if="!!memo.referenceText"
          :referenceText="memo.referenceText"
          :startIndex="memo.memoStartIndex"
          :endIndex="memo.memoEndIndex"
          :startRange="memo.memoStartRange"
          :endRange="memo.memoEndRange"
        ></NewsDetailMemoReference>
        <!-- memo content -->
        <v-sheet color="transparent" class="sm-font">
          {{ memo.memoContent }}
        </v-sheet>
      </v-sheet>
    </v-hover>
    <!-- memo modify version -->
    <NewsDetailMemoModify
      v-else
      @changeMode="modifyMode = false"
      :reference="memo.referenceText"
      :memoId="memo.memoId"
      :content="memo.memoContent"
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
  props: {
    isMine: Boolean,
    memo: Object,
    index: Number,
  },
  data() {
    return {
      modifyMode: false,
    };
  },
  methods: {
    deleteMemoItem() {
      this.$emit("deleteMemoItem", this.index);
    },
  },
};
</script>

<style></style>
