<template>
  <v-sheet class="mx-7 my-4 pa-5" rounded="lg" outlined>
    <!-- top -->
    <div class="d-flex justify-space-between">
      <!-- check memoPublicScrope -->
      <v-card
        class="pa-1 d-flex flex-row align-center"
        color="grey lighten-1"
        height="100%"
        elevation="0"
        rounded="pill"
      >
        <v-chip
          class="pa-3 px-4"
          :color="publicScope ? 'white' : 'transparent'"
          :text-color="publicScope ? 'grey darken-1' : 'white'"
          @click="publicScope = true"
          small
          ><v-icon left small> mdi-lock-open-outline </v-icon>공개</v-chip
        >
        <v-chip
          class="pa-3 px-4"
          :color="publicScope ? 'transparent' : 'white'"
          :text-color="publicScope ? 'white' : 'grey darken-1'"
          @click="publicScope = false"
          small
          ><v-icon left small> mdi-lock-outline </v-icon>비공개</v-chip
        >
      </v-card>
      <!-- register button -->
      <v-btn @click="registerMemo()" icon text
        ><v-icon> mdi-plus </v-icon></v-btn
      >
    </div>
    <!-- reference -->
    <v-sheet
      v-if="!!selectionText"
      class="mt-2 pa-2 d-flex align-start pointer"
      color="grey lighten-2"
      @click="move()"
    >
      <!-- reference text -->
      <v-hover v-slot="{ hover }">
        <v-sheet
          class="font-italic sm-font"
          color="transparent"
          :class="{ 'on-hover underline': hover }"
        >
          <div
            class="spacing-all"
            v-html="
              selectionText.length > 1
                ? selectionText[0] + (hover ? selectionText[1] : '···')
                : selectionText[0]
            "
          ></div>
        </v-sheet>
      </v-hover>
      <!-- remove reference -->
      <v-btn
        class="ml-1 ml-auto"
        icon
        text
        color="grey darken-1"
        @click="removeReference()"
        ><v-icon> mdi-close-circle </v-icon></v-btn
      >
    </v-sheet>
    <!-- input memoContent -->
    <v-form ref="form">
      <v-textarea
        placeholder="메모 내용을 작성해 주세요.
  기사 일부를 드래그해 인용문을 추가해 보세요."
        maxlength="500"
        v-model="content"
        rows="3"
        :rules="ruleContent"
        clearable
        counter
      ></v-textarea>
    </v-form>
    <!-- success register (keep 2 seconds) -->
    <v-snackbar
      :timeout="2000"
      v-model="successBar"
      color="var(--main-col-2)"
      elevation="10"
      width="90%"
      min-width="0"
      max-width="450"
    >
      <v-sheet color="transparent" class="d-flex flex-row align-center">
        <v-icon class="mr-3"> mdi-check-circle-outline </v-icon>
        새로운 메모를 등록했습니다.
      </v-sheet>
    </v-snackbar>
    <!-- failure register (keep 2 seconds) -->
    <v-snackbar
      :timeout="2000"
      v-model="failureBar"
      color="var(--error-col-1)"
      elevation="10"
      width="90%"
      min-width="0"
      max-width="450"
    >
      <v-sheet color="transparent" class="d-flex flex-row align-center">
        <v-icon class="mr-3"> mdi-close-circle-outline </v-icon>
        <div>메모 등록에 실패했습니다.<br />다시 시도해 주시기 바랍니다.</div>
      </v-sheet>
    </v-snackbar>
  </v-sheet>
</template>

<script>
import { moveReference } from "@/common/function/textSelection";
import { createMemo } from "@/api/modules/memo";
import { mapActions, mapState } from "vuex";

const memoStore = "memoStore";

export default {
  name: "NewsDetailMemoRegister",
  data() {
    return {
      publicScope: false, // 공개 여부
      content: null, // 메모 내용
      ruleContent: [
        (v) =>
          (!!v && v.length > 0 && v.length <= 500) ||
          "500자 이하로 내용을 입력해 주세요.",
      ], // 메모 내용 유효성
      successBar: false,
      failureBar: false,
    };
  },
  computed: {
    ...mapState(memoStore, ["myMemoList", "selectionResult", "selectionText"]),
  },
  methods: {
    ...mapActions(memoStore, ["removeSelectionText", "addMyMemo"]),
    async registerMemo() {
      // 메모 등록하기
      const valid = this.$refs.form.validate();
      if (valid) {
        // 메모 등록 API 실행
        await createMemo(
          this.publicScope,
          this.content,
          this.selectionResult
        ).then((newMemo) => {
          if (newMemo != null) {
            // vuex에 값 추가하기
            this.addMyMemo(newMemo);
            // 등록 완료 snackbar 활성화
            this.successBar = true;
            // 인용문 초기화
            this.removeSelectionText();
            // 입력 값 초기화
            this.memoPublicScope = false;
            this.memoContent = null;
            // 유효성 검사 초기화
            this.$refs.form.reset();
            // 부모 요소(Mine)로 등록된 메모 보내기
            this.$emit("addNewMemo", newMemo);
          } else {
            this.failureBar = true;
          }
        });
      }
    },
    removeReference() {
      // 인용문 삭제하기
      this.removeSelectionText();
    },
    move() {
      // 인용문 위치로 이동하기
      const selectionResult = this.selectionResult;
      moveReference(
        selectionResult.startRange,
        selectionResult.endRange,
        selectionResult.startIndex,
        selectionResult.endIndex
      );
    },
  },
};
</script>

<style></style>
