<template>
  <v-btn icon text color="var(--main-col-3)">
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
  },
  methods: {
    ...mapActions(memoStore, ["updatePublicScope"]),
    changeOpen() {
      updateMemoPublicScope().then((res) => {
        if (res) {
          // 공개 상태 변경 완료
          this.updatePublicScope(this.index);
        } else {
          // 공개 상태 변경 실패
        }
      });
    },
  },
};
</script>

<style></style>
