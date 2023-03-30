<template>
  <v-card
    class="blue-shadow"
    width="100%"
    :height="cardHeight"
    rounded="default"
  >
    <!-- thumbnail -->
    <img
      class="swiper-lazy"
      :data-src="relation.thumbnails"
      ref="imgEle"
      height="120"
      width="100%"
    />
    <!-- title -->
    <div class="pt-1 pa-2 sm-font">
      {{
        relation.title.length < 30
          ? relation.title
          : relation.title.slice(0, 30) + "···"
      }}
    </div>
  </v-card>
</template>

<script>
export default {
  name: "NewsDetailRelationItem",
  computed: {
    cardHeight() {
      return document.clientWidth * 0.5;
    },
  },
  props: {
    relation: Object,
    index: Number,
  },
  mounted() {
    this.$refs.imgEle.src = this.relation.thumbnails;
  },
};
</script>

<style></style>
