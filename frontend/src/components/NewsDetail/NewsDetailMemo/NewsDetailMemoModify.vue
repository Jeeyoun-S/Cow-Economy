<template>
  <v-sheet
    class="mt-3 pa-5 d-flex flex-column"
    color="var(--main-col-5)"
    rounded="lg"
  >
    <v-sheet v-if="!!referenceM" class="pa-3 d-flex align-center" rounded="">
      <div class="font-italic sm-font">
        {{ referenceM }}
      </div>
      <v-btn
        class="ml-1"
        v-bind="attrs"
        v-on="on"
        icon
        text
        color="var(--main-col-3)"
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
    <div class="d-flex justify-end">
      <v-btn
        class="mr-3"
        color="var(--main-col-1)"
        elevation="0"
        dark
        @click="modifyMemo()"
        >수정</v-btn
      >
      <v-btn color="white" elevation="0" @click="goBack()">취소</v-btn>
    </div>
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
