<template>
  <v-sheet class="mx-7 my-4 pa-5" rounded="lg" outlined>
    <div class="d-flex justify-space-between">
      <v-card
        class="pa-1"
        color="grey lighten-1"
        height="100%"
        elevation="0"
        rounded="pill"
      >
        <v-chip
          class="pa-4 px-5"
          :color="memoScrollPoint ? 'white' : 'transparent'"
          :text-color="memoScrollPoint ? 'grey darken-1' : 'white'"
          @click="memoScrollPoint = true"
          ><v-icon left> mdi-lock-open-outline </v-icon>공개</v-chip
        >
        <v-chip
          class="pa-4 px-5"
          :color="memoScrollPoint ? 'transparent' : 'white'"
          :text-color="memoScrollPoint ? 'white' : 'grey darken-1'"
          @click="memoScrollPoint = false"
          ><v-icon left> mdi-lock-outline </v-icon>비공개</v-chip
        >
      </v-card>
      <v-btn @click="registerMemo()" icon text large
        ><v-icon> mdi-plus </v-icon></v-btn
      >
    </div>
    <v-sheet
      v-if="!!getSelectioNText"
      class="mt-2 pa-2 d-flex align-center"
      color="grey lighten-2"
      @click="move()"
    >
      <v-sheet class="font-italic sm-font" color="transparent">
        <div class="spacing-all" v-html="getSelectioNText"></div>
      </v-sheet>
      <v-btn
        class="ml-1 ml-auto"
        icon
        text
        color="grey darken-1"
        @click="removeReference()"
        ><v-icon> mdi-close-circle </v-icon></v-btn
      >
    </v-sheet>
    <v-form ref="form">
      <v-textarea
        placeholder="메모 내용을 작성해 주세요.
  기사 일부를 드래그해 인용문을 추가해 보세요."
        maxlength="500"
        v-model="memoContent"
        rows="3"
        :rules="ruleContent"
        clearable
        counter
      ></v-textarea>
    </v-form>
  </v-sheet>
</template>

<script>
import { moveReference } from "@/common/function/textSelection";

export default {
  name: "NewsDetailMemoRegister",
  data() {
    return {
      memoScrollPoint: false, // 공개 여부
      memoContent: null, // 메모 내용
      ruleContent: [
        (v) =>
          (!!v && v.length > 0 && v.length <= 500) ||
          "500자 이하로 내용을 입력해 주세요.",
      ], // 메모 내용 유효성
    };
  },
  computed: {
    getSelectioNText() {
      return this.$store.getters.getSelectioNText;
    },
  },
  methods: {
    registerMemo() {
      const valid = this.$refs.form.validate();
      if (valid) {
        alert("등록 진행");
      }
    },
    removeReference() {
      this.$store.dispatch("removeSelectionText");
    },
    move() {
      const selectionResult = this.$store.getters.getSelectionResult;
      console.log(selectionResult);
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
