<template>
  <v-btn icon text color="var(--main-col-3)">
    <v-icon v-if="!isOpen" @click="changeOpen()"> mdi-lock </v-icon>
    <v-icon v-else @click="changeOpen()"> mdi-lock-open-outline </v-icon>
  </v-btn>
</template>

<script>
export default {
  name: "NewsDetailMemoBtnLock",
  data() {
    return {
      isOpen: false,
    };
  },
  methods: {
    changeOpen() {
      alert("공개 여부 변경");
      this.isOpen = !this.isOpen;
    },
  },
};
</script>

<style></style>
