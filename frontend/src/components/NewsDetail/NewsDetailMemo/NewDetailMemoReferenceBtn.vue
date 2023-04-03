<template>
  <v-sheet
    v-if="!!text"
    class="mt-2 pa-2 d-flex align-start pointer"
    :color="colorSheet"
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
            text.length > 1 ? text[0] + (hover ? text[1] : '···') : text[0]
          "
        ></div>
      </v-sheet>
    </v-hover>
    <!-- remove reference -->
    <v-btn class="ml-1 ml-auto" icon text :color="colorBtn" @click="remove()"
      ><v-icon> mdi-close-circle </v-icon></v-btn
    >
  </v-sheet>
</template>

<script>
import { moveReference } from "@/common/function/textSelection";

export default {
  name: "NewsDetailMemoReferenceBtn",
  props: {
    text: Array,
    startRange: Number,
    endRange: Number,
    startIndex: Number,
    endIndex: Number,
    color: String,
  },
  data() {
    return {
      colorSheet: "grey lighten-2",
      colorBtn: "grey darken-1",
    };
  },
  methods: {
    move() {
      // 인용문 위치로 이동하기
      moveReference(
        this.startRange,
        this.endRange,
        this.startIndex,
        this.endIndex
      );
    },
    remove() {
      // 인용문 삭제하기
      this.$emit("removeReference");
    },
  },
  created() {
    if (this.color == "blue") {
      this.colorSheet = "white";
      this.colorBtn = "var(--main-col-2)";
    }
  },
};
</script>

<style></style>
