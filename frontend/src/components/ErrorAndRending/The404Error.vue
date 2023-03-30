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
<template>
  <v-sheet>
    <home-header />
    <div class="img">
      <img :src="require('@/assets/images/error-404.gif')" />
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
.img {
  margin-left: 15%;
}
</style>
