<template>
  <!-- <v-card class="overflow-hidden" color="transparent" elevation="0">
    <InfoHeader></InfoHeader>
    <v-sheet
      id="scrolling-techniques-3"
      class="scroll overflow-auto"
      max-height="100vh"
      color="transparent"
    >
      <v-container>
        <info-page-first></info-page-first> -->
  <flicking-test></flicking-test>
  <!-- </v-container>
    </v-sheet>
  </v-card> -->
</template>

<script>
// import InfoHeader from "@/views/Header/InfoHeader.vue";
// import InfoPageFirst from "./InfoPageFirst.vue";
import FlickingTest from "./FlickingTest.vue";
import { mapGetters } from "vuex";

export default {
  name: "InfoPage",
  components: {
    // InfoHeader,
    // InfoPageFirst,
    FlickingTest,
  },
  created() {
    if (this.isLoggedIn) {
      this.$router.push("/home");
    }
  },
  computed: {
    ...mapGetters("userStore", ["isLoggedIn"]),
  },
};
</script>

<style scoped>
.scroll::-webkit-scrollbar {
  display: none !important;
}
.scroll {
  -ms-overflow-style: none !important; /* 인터넷 익스플로러 */
  scrollbar-width: none !important; /* 파이어폭스 */
}
</style>
