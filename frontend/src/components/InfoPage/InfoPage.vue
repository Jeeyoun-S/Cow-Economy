<template>
  <div>
    <landing v-if="isLoggedIn"></landing>
    <info-detail v-else></info-detail>
  </div>
</template>

<script>
import InfoDetail from "./InfoDetail.vue";
import Landing from "./Landing.vue";
import { mapGetters, mapState } from "vuex";

const mainStore = "mainStore";

export default {
  name: "InfoPage",
  components: {
    InfoDetail,
    Landing,
  },
  data() {
    return {
      isAll: 0,
    };
  },
  watch: {
    isAll() {
      if (this.isAll == 3 && this.isLoggedIn) {
        this.$router.push("/home");
      }
    },
    wordsFlag() {
      if (this.wordsFlag) {
        this.isAll += 1;
      }
    },
    hotNewsFlag() {
      if (this.hotNewsFlag) {
        this.isAll += 1;
      }
    },
    categoryNewsFlag() {
      if (this.categoryNewsFlag) {
        this.isAll += 1;
      }
    },
  },
  created() {
    // if (this.isLoggedIn) {
    //   this.$router.push("/home");
    // }
    this.isAll = 0; //초기화
  },
  computed: {
    ...mapGetters("userStore", ["isLoggedIn"]),
    ...mapState(mainStore, ["wordsFlag", "hotNewsFlag", "categoryNewsFlag"]),
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
