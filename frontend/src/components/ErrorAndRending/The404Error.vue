<template>
  <div>
    <home-header />
    <!-- <div class="py-2 d-flex align-center"></div> -->
    <div class="d-flex align-center justify-center">
      <img :src="require('@/assets/images/error-404.png')" />
    </div>
    <div class="d-flex align-center justify-center">
      <img :src="require('@/assets/images/cow.svg')" />
    </div>
    <the-footer />
  </div>
</template>

<script>
import HomeHeader from "@/views/Header/HomeHeader.vue";
import TheFooter from "@/views/TheFooter.vue";

export default {
  components: { HomeHeader, TheFooter },
};
</script>

<style>
/* .error-image {
  margin-top: 0%;
  margin-left: 20%;

  width: 80%;
} */
</style>
