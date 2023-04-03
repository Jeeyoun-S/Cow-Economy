<template>
  <v-sheet @click="goNewsDetail" width="100%" height="100%">
    <v-card class="blue-shadow" width="100%" height="100%" rounded="default">
      <!-- thumbnail -->
      <v-img
        v-if="relation.articleThumbnail != 'NaN'"
        class="swiper-lazy"
        :src="relation.articleThumbnail"
        height="120"
        cover
      ></v-img>
      <v-img
        v-else
        class="swiper-lazy"
        src="@/assets/images/news_background.jpg"
        height="120"
        cover
      ></v-img>
      <!-- title -->
      <v-card-text
        class="pa-2 sm-font"
        style="width: 90%; !important; color:black"
      >
        {{
          relation.articleTitle.length < 28
            ? relation.articleTitle
            : relation.articleTitle.slice(0, 28) + "···"
        }}
      </v-card-text>
    </v-card>
  </v-sheet>
</template>

<script>
export default {
  name: "NewsDetailRelationItem",
  props: {
    relation: Object,
    index: Number,
  },
  methods: {
    goNewsDetail() {
      // this.$router.replace(`/news/${this.relation.articleId}`);
      location.href = `/news/${this.relation.articleId}`;
    },
  },
};
</script>

<style scoped>
.text_in_img {
  position: absolute;
  bottom: 0;
  color: white;
  width: 95%;
  background: linear-gradient(180deg, rgba(0, 0, 0, 0) 0%, #000000 84.9%);
}
.non_image {
  background-color: var(--main-col-4-1);
}
</style>
