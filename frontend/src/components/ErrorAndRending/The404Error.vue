<!-- <template>
  <v-app>
    <router-view name="header" />
    <div>
      <p>이거 맞아?</p>
      <img :src="require('@/assets/images/error-404.gif')" />
    </div>
    <router-view name="footer" />
  </v-app>
</template> -->
<!-- <template>
  <v-sheet>
    <home-header />
    <div class="img">
      <img :src="require('@/assets/images/error-404.gif')" />
    </div>
  </v-sheet>
</template> -->
<template>
  <v-sheet>
    <home-header />
    <div class="error-page">
      <h1 data-h1="404">404</h1>
      <p data-p="NOT FOUND">NOT FOUND</p>
    </div>
  </v-sheet>
</template>

<script>
import HomeHeader from "@/views/Header/HomeHeader.vue";

export default {
  components: { HomeHeader },
  name: "The404Error",
  data: () => ({
    isShowHeader: true,
    scrollPosition: 0,
  }),
};
</script>

<style>
.error-page {
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  height: 100%;
  /* font-family: Arial,"Helvetica Neue",Helvetica,sans-serif; */
}
</style>
