<template>
  <v-btn icon text :color="color">
    <v-icon v-if="!memoPublicScope" @click="changeOpen()"> mdi-lock </v-icon>
    <v-icon v-else @click="changeOpen()"> mdi-lock-open-outline </v-icon>
  </v-btn>
</template>

<script>
import { updateMemoPublicScope } from "@/api/modules/memo";
import { mapActions } from "vuex";

const memoStore = "memoStore";

export default {
  name: "NewsDetailMemoBtnLock",
  props: {
    memoPublicScope: Boolean,
    index: Number,
    color: String,
    memoId: Number,
  },
  methods: {
    ...mapActions(memoStore, ["updatePublicScope"]),
    // 공개 상태를 변경하는 함수
    changeOpen() {
      // 공개 상태 변경 API 요청
      updateMemoPublicScope(this.memoId).then((res) => {
        if (res != null) {
          // 메모 리스트의 공개 상태 변경하기
          this.$emit("modifyPublicScope", this.index, res);
        }
      });
    },
  },
};
</script>

<style></style>
