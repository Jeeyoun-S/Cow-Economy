<template>
  <v-sheet id="memo-register" class="mx-7 my-4 pa-5" rounded="lg" outlined>
    <!-- top -->
    <div class="d-flex justify-space-between align-center">
      <!-- left : check memoPublicScrope -->
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
          ><v-icon left small> mdi-lock </v-icon>비공개</v-chip
        >
      </v-card>
      <!-- right -->
      <div class="d-flex align-center">
        <!-- is modifying -->
        <v-btn
          class="mr-1 sm-font"
          v-if="newMemo.isModify"
          @click="deleteMemoInfo()"
          elevation="0"
          rounded
          small
          dark
          outlined
          color="grey darken-1"
          >수정 취소</v-btn
        >
        <!-- register button -->
        <v-btn @click="registerMemo()" icon text
          ><v-icon> mdi-plus </v-icon></v-btn
        >
      </div>
    </div>
    <!-- reference -->
    <NewsDetailMemoReferenceBtn
      :text="selectionText"
      :startRange="selectionResult.startRange"
      :endRange="selectionResult.endRange"
      :startIndex="selectionResult.startIndex"
      :endIndex="selectionResult.endIndex"
      @removeReference="removeSelectionText"
      color="grey"
    ></NewsDetailMemoReferenceBtn>
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
        <span v-if="barOption">메모를 수정했습니다.</span>
        <span v-else>새로운 메모를 등록했습니다.</span>
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
        <div>
          메모 <span v-if="barOption">수정</span><span v-else>등록</span>에
          실패했습니다.<br />다시 시도해 주시기 바랍니다.
        </div>
      </v-sheet>
    </v-snackbar>
  </v-sheet>
</template>

<script>
import NewsDetailMemoReferenceBtn from "./NewDetailMemoReferenceBtn.vue";
import { updateMemo } from "@/api/modules/memo";
import { mapActions, mapState } from "vuex";

const memoStore = "memoStore";

export default {
  name: "NewsDetailMemoRegister",
  components: {
    NewsDetailMemoReferenceBtn,
  },
  data() {
    return {
      publicScope: false, // 공개 여부
      content: null, // 메모 내용
      ruleContent: [
        (v) =>
          (!!v && v.length > 0 && v.length <= 500) ||
          "500자 이하로 내용을 입력해 주세요.",
      ], // 메모 내용 유효성
      successBar: false, // 메모 등록 또는 수정 성공
      failureBar: false, // 메모 등록 또는 수정 실패
      barOption: false, // true이면 수정, false면 등록
    };
  },
  computed: {
    ...mapState(memoStore, ["selectionResult", "selectionText", "newMemo"]),
  },
  methods: {
    ...mapActions(memoStore, ["removeSelectionText", "updateNewMemo"]),
    async registerMemo() {
      // 메모 등록하기
      const valid = this.$refs.form.validate();
      if (valid) {
        // 메모 등록 API 실행
        await updateMemo(
          this.newMemo,
          this.selectionResult,
          this.selectionText,
          this.$route.params.id
        ).then((res) => {
          if (res != null) {
            new Promise(() => {
              // 등록 완료 snackbar 활성화
              this.successBar = true;
              this.barOption = this.newMemo.isModify;
              // 유효성 검사 초기화
              this.$refs.form.reset();
              // 수정인 경우
              if (this.newMemo.isModify) {
                // 지금 보여지고 있는 메모 리스트에서 수정
                this.$emit("updateMemo", res, this.newMemo.index);
              }
              // 등록인 경우
              else {
                // 부모 요소(NewsDetailMemoMine)로 등록된 메모 보내기
                this.$emit("addNewMemo", res);
              }
            }).then(
              // 등록 완료된 메모 정보 모두 삭제
              this.deleteMemoInfo()
            );
          } else {
            // 등록 실패 snackbar 활성화
            this.failureBar = true;
          }
        });
      }
    },
    // 작성 중인 메모 정보 모두 삭제
    deleteMemoInfo() {
      // 인용문 초기화
      this.removeSelectionText();
      // vuex 값 초기화
      this.updateNewMemo({
        isModify: false,
        memoId: null,
        memoContnet: null,
        memoPublicScope: false,
        index: null,
      });
      // 유효성 검사 초기화
      this.$refs.form.reset();
    },
  },
  watch: {
    // vuex와 data가 똑같이 변경되도록 설정
    publicScope() {
      this.newMemo.memoPublicScope = this.publicScope;
    },
    content() {
      this.newMemo.memoContent = this.content;
    },
    newMemo: {
      handler() {
        this.content = this.newMemo.memoContent;
        this.publicScope = this.newMemo.memoPublicScope;
      },
      deep: true,
    },
  },
};
</script>

<style></style>
