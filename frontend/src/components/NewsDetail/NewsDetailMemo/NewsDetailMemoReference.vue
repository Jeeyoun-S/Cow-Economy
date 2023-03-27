<template>
  <v-hover v-slot="{ hover }">
    <div
      :class="{ 'on-hover underline pointer': hover }"
      class="mb-2 pa-1 pl-3 border-left font-italic xs-font"
      @click="moveScroll()"
    >
      <div
        v-html="
          referenceText.length > 1
            ? referenceText[0] + (hover ? referenceText[1] : '···')
            : referenceText[0]
        "
      ></div>
    </div>
  </v-hover>
</template>

<script>
import { moveReference } from "@/common/function/textSelection";

export default {
  name: "NewsDetailMemoReference",
  props: {
    referenceText: Array,
    startIndex: Number,
    endIndex: Number,
    startRange: Number,
    endRange: Number,
  },
  methods: {
    moveScroll() {
      // 인용문 위치로 스크롤 이동 & 인용문 형광펜 표시
      moveReference(
        this.startRange,
        this.endRange,
        this.startIndex,
        this.endIndex
      );
    },
  },
};
</script>

<style></style>
