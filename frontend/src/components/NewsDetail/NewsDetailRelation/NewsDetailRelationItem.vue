<template>
  <v-sheet height="100%">
    <v-card
      v-if="relation.articleThumbnail != 'NaN'"
      class="blue-shadow"
      width="100%"
      height="100%"
      rounded="default"
    >
      <!-- thumbnail -->
      <div>
        <img
          class="swiper-lazy"
          :src="relation.articleThumbnail"
          height="120"
          width="100%"
        />
        <!-- title -->
        <div class="pt-1 pa-2 sm-font">
          {{
            relation.articleTitle.length < 30
              ? relation.articleTitle
              : relation.articleTitle.slice(0, 30) + "···"
          }}
        </div>
      </div> </v-card
    ><v-sheet height="100%" v-else>
      <div class="pt-1 pa-2 sm-font">
        {{ relation.articleTitle }}
      </div>
    </v-sheet>
  </v-sheet>
</template>

<script>
export default {
  name: "NewsDetailRelationItem",
  props: {
    relation: Object,
    index: Number,
  },
};
</script>

<style></style>
