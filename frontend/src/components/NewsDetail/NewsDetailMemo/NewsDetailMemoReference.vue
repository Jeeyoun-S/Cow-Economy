<template>
  <v-hover v-slot="{ hover }">
    <div
      :class="{ 'on-hover underline pointer': hover }"
      class="mb-2 pa-1 pl-3 border-left font-italic xs-font"
      @click="moveScroll()"
    >
      <div
        v-html="
          referenceList.length > 1
            ? referenceList[0] + (hover ? referenceList[1] : '···')
            : referenceList[0]
        "
      ></div>
    </div>
  </v-hover>
</template>

<script>
import { moveReference } from "@/common/function/textSelection";

export default {
  name: "NewsDetailMemoReference",
  data() {
    return {
      referenceList: null,
    };
  },
  props: {
    referenceText: String,
    startIndex: Number,
    endIndex: Number,
    startRange: Number,
    endRange: Number,
  },
  methods: {
    moveScroll() {
      moveReference(
        this.startRange,
        this.endRange,
        this.startIndex,
        this.endIndex
      );
    },
  },
  created() {
    this.referenceList = this.referenceText.split("@@@");
  },
};
</script>

<style></style>
