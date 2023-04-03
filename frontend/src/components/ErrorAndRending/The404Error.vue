<template>
  <div>
    <home-header />
    <div class="error-image">
      <img :src="require('@/assets/images/error-404.svg')" />
    </div>
  </div>
  <!-- <v-sheet>
    <home-header />
    <v-sheet class="">
      <div><img :src="require('@/assets/images/error-404.svg')" /></div>
    </v-sheet>
    <the-footer />
  </v-sheet> -->
</template>

<script>
import HomeHeader from "@/views/Header/HomeHeader.vue";
import TheFooter from "@/views/TheFooter.vue";

export default {
  components: { HomeHeader, TheFooter },
};
</script>

<style>
.error-image {
  margin-top: 0%;
  margin-left: 20%;

  width: 80%;
}
</style>
