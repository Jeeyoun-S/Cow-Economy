<template>
  <div>
    <home-header />
    <div class="error-image">
      <img :src="require('@/assets/images/error-404.gif')" />
    </div>
  </div>
</template>

<script>
import HomeHeader from "@/views/Header/HomeHeader.vue";

export default {
  components: { HomeHeader },
};
</script>

<style>
.error-image {
  margin-top: 0%;
  margin-left: 20%;
}
</style>
