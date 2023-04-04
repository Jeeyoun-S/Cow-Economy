<template>
  <div>
    <landing v-if="isLoggedIn"></landing>
    <info-detail v-else></info-detail>
  </div>
</template>

<script>
import InfoDetail from "./InfoDetail.vue";
import Landing from "./Landing.vue";
import { mapGetters } from "vuex";

export default {
  name: "InfoPage",
  components: {
    InfoDetail,
    Landing,
  },
  created() {
    // if (this.isLoggedIn) {
    //   this.$router.push("/home");
    // }
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
