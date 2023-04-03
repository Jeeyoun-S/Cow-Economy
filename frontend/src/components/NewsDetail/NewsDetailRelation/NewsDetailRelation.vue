<template>
  <v-sheet id="relation-article" class="pa-5" color="var(--main-col-5)">
    <div class="swiper-container">
      <span class="b-font lg-font">관련 기사</span>
      <div class="swiper-pagination" slot="pagination"></div>
      <div class="pt-3 swiper-wrapper">
        <div
          class="swiper-slide"
          v-for="(relation, index) in newsRelated"
          :key="index"
          style="height: auto; cursor: pointer"
        >
          <NewsDetailRelationItem
            :index="index"
            :relation="relation"
          ></NewsDetailRelationItem>
        </div>
      </div>
    </div>
  </v-sheet>
</template>

<script>
import Swiper from "swiper";
import NewsDetailRelationItem from "./NewsDetailRelationItem.vue";

export default {
  name: "NewsDetailRelation",
  props: {
    newsRelated: Array,
  },
  components: {
    NewsDetailRelationItem,
  },
  mounted() {
    // Initialize the Swiper instance
    new Swiper(".swiper-container", {
      // Add the clickable option
      clickable: true,
      slidesPerView: 2,
      spaceBetween: 20,
      pagination: {
        el: ".swiper-pagination",
      },
    });
  },
};
</script>

<style></style>
