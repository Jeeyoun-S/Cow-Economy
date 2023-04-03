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
          relation.articleTitle.length < 30
            ? relation.articleTitle
            : relation.articleTitle.slice(0, 30) + "···"
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

<style scoped></style>
