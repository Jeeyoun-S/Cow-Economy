<template>
  <v-sheet id="relation-article" class="pa-7" color="var(--main-col-5)">
    <h3 class="mb-4">관련 기사</h3>
    <swiper class="swiper pt-10 pa-1" :options="swiperOption">
      <div class="swiper-pagination" slot="pagination"></div>
      <swiper-slide
        style="height: auto"
        v-for="(relation, index) in newsRelated"
        :key="index"
      >
        <NewsDetailRelationItem
          :index="index"
          :relation="relation"
        ></NewsDetailRelationItem>
      </swiper-slide>
    </swiper>
  </v-sheet>
</template>

<script>
import NewsDetailRelationItem from "./NewsDetailRelationItem.vue";
export default {
  name: "NewsDetailRelation",
  props: {
    newsRelated: Array,
  },
  components: {
    NewsDetailRelationItem,
  },
  data() {
    return {
      swiperOption: {
        slidesPerView: 2,
        spaceBetween: 25,
        slidesPerGroup: 2,
        loop: true,
        pagination: {
          el: ".swiper-pagination",
          clickable: true,
        },
        lazy: {
          loadPrevNext: true,
        },
      },
    };
  },
};
</script>

<style></style>
