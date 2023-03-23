<template>
  <v-btn class="px-6" elevation="0" color="grey lighten-1" rounded dark @click="kakaoLogOut"
    >로그아웃</v-btn
  >
</template>

<script>
import { mapGetters, mapActions } from "vuex";

export default {
  name: "MyPageLogoutBtn",
  computed: {
    ...mapGetters("userStore", ["isLoggedIn"])
  },
  methods: {
    
    ...mapActions("userStore", ["logout"]),
    kakaoLogOut() {
      this.logout();
    }
  }
};
</script>

<style></style>
