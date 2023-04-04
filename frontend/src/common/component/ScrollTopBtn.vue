<template>
  <!-- <v-btn class="go-to-top" icon color="white" @click="scrollToTop">
    <v-icon>mdi-arrow-up</v-icon>
  </v-btn> -->
  <v-btn
    v-scroll="onScroll"
    v-show="fab"
    fab
    dark
    fixed
    bottom
    right
    color="var(--main-col-1)"
    @click="toTop"
    small
    class="mr-2"
  >
    <v-icon>mdi-arrow-up</v-icon>
  </v-btn>
</template>

<script>
export default {
  name: "ScrollTopBtn",
  data() {
    return {
      fab: false,
    };
  },
  methods: {
    // [@Method] 맨 위로 가기
    // scrollToTop() {
    //   window.scrollTo({
    //     top: 0,
    //     behavior: "smooth",
    //   });
    // },
    onScroll(e) {
      if (typeof window === "undefined") return;
      const top = window.pageYOffset || e.target.scrollTop || 0;
      this.fab = top > 20;
    },
    // [@Method] 맨 위로 가기
    toTop() {
      this.$vuetify.goTo(0);
    },
  },
};
</script>

<style>
.go-to-top {
  position: fixed;
  bottom: 30px;
  right: 30px;

  background-color: var(--main-col-2);
  z-index: 1;
}
</style>
