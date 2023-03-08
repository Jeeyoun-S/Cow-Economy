<template>
  <v-sheet color="transparent">
    <v-toolbar flat height="65px" color="transparent">
      <!-- 로고 (아이콘) -->
      <img height="70%" :src="require('@/assets/images/icon.png')" />
      <v-spacer></v-spacer>
      <!-- 로그인 버튼 -->
      <MainHeaderLogin></MainHeaderLogin>
    </v-toolbar>
  </v-sheet>
</template>

<script>
import MainHeaderLogin from "@/components/User/MainHeaderLogin.vue";
export default {
  components: {
    MainHeaderLogin
  }
}
</script>

<style>

</style>