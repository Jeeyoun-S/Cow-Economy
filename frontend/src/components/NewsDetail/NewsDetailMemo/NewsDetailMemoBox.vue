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
          <div v-if="!isMine" class="d-flex flex-row align-end">
            <img
              class="mr-1"
              :src="
                require('@/assets/images/level/' + levelImage[memo.userLevel])
              "
              height="25"
            />
            <v-chip color="var(--main-col-3)" label dark small>{{
              memo.userNickname
            }}</v-chip>
          </div>
          <v-divider v-if="!isMine" class="mx-2"></v-divider>
          <!-- memo date -->
          <v-chip color="var(--main-col-3)" dark small>{{
            memo.regtime
          }}</v-chip>
          <v-divider v-if="isMine" class="mx-2"></v-divider>
          <!-- memo update -->
          <div v-if="isMine">
            <!-- memo open status button -->
            <NewsDetailMemoBtnLock
              :memoPublicScope="memo.memoPublicScope"
              :index="index"
              color="var(--main-col-3)"
              :memoId="memo.memoId"
              :isSmall="false"
              @modifyPublicScope="modifyPublicScope"
            ></NewsDetailMemoBtnLock>
            <!-- memo modify button -->
            <v-btn
              icon
              text
              color="var(--main-col-3)"
              @click="checkRegisterContent()"
              ><v-icon> mdi-pencil </v-icon></v-btn
            >
            <!-- memo delete button -->
            <NewsDetailMemoBtnDelete
              :memoId="memo.memoId"
              :index="index"
              color="var(--main-col-3)"
              :isSmall="false"
              @deleteMemoItem="deleteMemoItem"
            ></NewsDetailMemoBtnDelete>
          </div>
          <!-- memo like button -->
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
import { likeMemo, unlikeMemo } from "@/api/modules/memo";

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
      levelImage: {
        1: "level_f.png",
        2: "level_d.png",
        3: "level_c.png",
        4: "level_b.png",
        5: "level_a.png",
        6: "level_s.png",
      },
    };
  },
  computed: {
    ...mapState(memoStore, ["newMemo", "selectionText"]),
  },
  methods: {
    ...mapActions(memoStore, ["updateNewMemo", "updateSelectionText"]),
    async likeMemo() {
      try {
        const result = await likeMemo(this.memo.memoId);
        if (result) {
          // Update the local like count or use a mutation to update the store
        }
      } catch (error) {
        console.error(error);
      }
    },
    async unlikeMemo() {
      try {
        const result = await unlikeMemo(this.memo.memoId);
        if (result) {
          // Update the local like count or use a mutation to update the store
        }
      } catch (error) {
        console.error(error);
      }
    },
    // 메모 삭제하기
    deleteMemoItem() {
      this.$emit("deleteMemoItem", this.index);
    },
    // 메모 수정하기
    modifyPublicScope(index, res) {
      this.$emit("modifyPublicScope", index, res);
    },
    // 메모 등록창에 내용이 있는지 확인
    checkRegisterContent() {
      // 내용이 있거나, 인용문이 있다면
      if (this.newMemo.memoContent != null || this.selectionText != null) {
        this.dialog = true;
      }
      // 등록창에 내용이 없다면 메모 등록창을 수정 창으로 변경
      else {
        this.modifyMemo();
      }
    },
    // 메모 등록창을 수정창으로 변경하기
    modifyMemo() {
      // 메모 등록창 데이터에 수정할 기존 메모 넣기
      this.updateNewMemo({
        memoContent: this.memo.memoContent,
        memoPublicScope: this.memo.memoPublicScope,
        isModify: true,
        memoId: this.memo.memoId,
        index: this.index,
      });
      // 메모 등록창 데이터에 수정할 기존 인용문 넣기
      this.updateSelectionText({
        text: this.memo.referenceText,
        startIndex: this.memo.memoStartIndex,
        endIndex: this.memo.memoEndIndex,
        startRange: this.memo.memoStartRange,
        endRange: this.memo.memoEndRange,
      });
      // 메모 등록창으로 스크롤 이동
      scrollTo(0, document.getElementById("memo-register").offsetTop);
      // 경고문 닫기
      this.dialog = false;
    },
  },
};
</script>

<style></style>
