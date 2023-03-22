<template>
  <div>
    <v-hover v-slot="{ hover }" open-delay="200">
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
              color="var(--main-col-3)"
            ></NewsDetailMemoBtnLock>
            <!-- memo modify button : 보류 -->
            <v-btn icon text color="var(--main-col-3)" @click="checkRegister()"
              ><v-icon> mdi-pencil </v-icon></v-btn
            >
            <!-- memo delete button -->
            <NewsDetailMemoBtnDelete
              :memoId="memo.memoId"
              :index="index"
              color="var(--main-col-3)"
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
    <v-dialog v-model="dialog" max-width="300">
      <template v-slot:default="dialog">
        <v-card class="pb-3">
          <v-card-title>메모 수정하기</v-card-title>
          <v-card-text
            >등록 또는 수정 중이던 메모가 있습니다.<br />
            이를 무시하고, 수정을 진행하시겠습니까?</v-card-text
          >
          <v-card-actions class="justify-center">
            <v-btn
              elevation="0"
              color="var(--error-col-1)"
              @click="modifyMemo()"
              dark
              >수정</v-btn
            >
            <v-btn elevation="0" @click="dialog.value = false">취소</v-btn>
          </v-card-actions>
        </v-card>
      </template>
    </v-dialog>
  </div>
</template>

<script>
import NewsDetailMemoReference from "./NewsDetailMemoReference.vue";
import NewsDetailMemoBtnDelete from "./NewsDetailMemoBtnDelete.vue";
import NewsDetailMemoBtnLock from "./NewsDetailMemoBtnLock.vue";
import { mapActions, mapState } from "vuex";

const memoStore = "memoStore";

export default {
  name: "NewsDetailMemoItem",
  components: {
    NewsDetailMemoReference,
    NewsDetailMemoBtnDelete,
    NewsDetailMemoBtnLock,
  },
  props: {
    isMine: Boolean,
    memo: Object,
    index: Number,
  },
  data() {
    return {
      dialog: false,
    };
  },
  computed: {
    ...mapState(memoStore, ["newMemo", "selectionText"]),
  },
  methods: {
    ...mapActions(memoStore, ["updateNewMemo", "updateSelectionText"]),
    deleteMemoItem() {
      this.$emit("deleteMemoItem", this.index);
    },
    checkRegister() {
      if (this.newMemo.memoContent != null || this.selectionText != null) {
        this.dialog = true;
      } else {
        this.modifyMemo();
      }
    },
    modifyMemo() {
      this.updateNewMemo({
        memoContent: this.memo.memoContent,
        memoPublicScope: this.memo.memoPublicScope,
        isModify: true,
        memoId: this.memo.memoId,
        index: this.index,
      });
      this.updateSelectionText({
        text: this.memo.referenceText,
        startIndex: this.memo.memoStartIndex,
        endIndex: this.memo.memoEndIndex,
        startRange: this.memo.memoStartRange,
        endRange: this.memo.memoEndRange,
      });
      scrollTo(0, document.getElementById("memo-register").offsetTop);
      this.dialog = false;
    },
  },
};
</script>

<style></style>
