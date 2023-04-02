<template>
  <v-sheet id="relation-article" class="pa-7" color="var(--main-col-5)">
    <h3 class="mb-4">관련 기사</h3>
    <v-slide-group class="pt-10 pa-1">
      <v-slide-item
        class="mx-2"
        style="height: auto"
        v-for="(relation, index) in newsRelated"
        :key="index"
      >
        <NewsDetailRelationItem
          :index="index"
          :relation="relation"
        ></NewsDetailRelationItem>
      </v-slide-item>
    </v-slide-group>
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
    return {};
  },
  methods: {},
};
</script>

<style></style>
