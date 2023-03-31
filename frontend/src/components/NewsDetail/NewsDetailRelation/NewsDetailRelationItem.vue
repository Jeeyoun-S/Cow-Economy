<template>
  <v-sheet @click="goNewsDetail" width="150" height="100%">
    <v-card
      v-if="relation.articleThumbnail != 'NaN'"
      class="blue-shadow"
      width="100%"
      height="100%"
      rounded="default"
    >
      <!-- thumbnail -->
      <v-img
        class="swiper-lazy img-card"
        :src="relation.articleThumbnail"
        height="150"
        width="100%"
        cover
      >
        <div class="pa-2 sm-font text-in-image">
          {{
            relation.articleTitle.length < 25
              ? relation.articleTitle
              : relation.articleTitle.slice(0, 25) + "···"
          }}
        </div>
      </v-img>

      <!-- title -->
    </v-card>
    <v-card height="100%" class="pt-1 pa-2 sm-font" v-else>
      <p class="pt-3">{{ relation.articleTitle }}</p>
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
.no-image-card {
  /* background: linear-gradient(180deg, rgba(63, 88, 178, 0.5) 0%, #3f58b3 100%); */
  box-shadow: 0px 0px 5px 1px rgba(0, 0, 0, 0.14);
  /* color: white; */
}
.text-in-image {
  position: absolute;
  width: 100%;
  bottom: 0;
  color: white;
  background: linear-gradient(180deg, rgba(0, 0, 0, 0) 0%, #000000 84.9%);
}
</style>
