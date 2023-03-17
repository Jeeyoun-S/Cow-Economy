<template>
  <div
    class="mb-2 pa-1 pl-3 border-left font-italic sm-font"
    @click="moveScroll()"
  >
    {{ reference }}
  </div>
</template>

<script>
import { moveReference } from "@/common/function/textSelection";

export default {
  name: "NewsDetailMemoReference",
  props: {
    reference: String,
  },
  methods: {
    moveScroll() {
      moveReference(4, 22, 58, 148);
    },
  },
};
</script>

<style></style>
