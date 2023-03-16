<template>
  <v-sheet color="var(--main-col-5)" class="px-7 mt-3 py-4 d-flex flex-column">
    <v-sheet v-if="!!referenceM" class="pa-3 d-flex align-center">
      <div class="font-italic sm-font spacing-all">
        {{ referenceM }}
      </div>
      <v-btn
        class="ml-1"
        icon
        text
        color="var(--main-col-4)"
        @click="referenceM = null"
        ><v-icon> mdi-close-circle </v-icon></v-btn
      >
    </v-sheet>
    <v-textarea
      class="pa-2"
      placeholder="메모 내용을 작성해 주세요.
  기사 일부를 드래그해 인용문을 추가해 보세요."
      maxlength="500"
      v-model="contentM"
      rows="3"
      :rules="ruleContent"
      clearable
      counter
    ></v-textarea>
    <v-row class="px-2 py-2">
      <v-col class="pa-1"
        ><v-btn
          color="var(--main-col-2)"
          elevation="0"
          dark
          @click="modifyMemo()"
          rounded
          block
          >수정</v-btn
        ></v-col
      >
      <v-col class="pa-1"
        ><v-btn
          color="var(--main-col-2)"
          elevation="0"
          @click="goBack()"
          rounded
          outlined
          block
          >취소</v-btn
        ></v-col
      >
    </v-row>
  </v-sheet>
</template>

<script>
export default {
  name: "NewsDetailMemoModify",
  props: {
    reference: String,
    memoId: Number,
    content: String,
  },
  data() {
    return {
      referenceM: null,
      contentM: null,
    };
  },
  methods: {
    goBack() {
      this.$emit("changeMode");
    },
    modifyMemo() {
      alert("메모 수정 로직 구현");
    },
  },
  created() {
    this.referenceM = this.reference;
    this.contentM = this.content;
  },
};
</script>

<style></style>
