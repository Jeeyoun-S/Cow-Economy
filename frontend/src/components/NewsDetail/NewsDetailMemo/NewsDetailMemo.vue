<template>
  <v-sheet class="py-7" id="memo">
    <v-sheet class="mx-7 d-flex flex-row align-center">
      <!-- memo title -->
      <h3 class="mr-auto">메모</h3>
      <!-- memo sort select -->
      <v-sheet width="100px">
        <v-select
          v-model="sort"
          :items="
            isListMine ? ['최신순', '인기순'] : ['최신순', '인기순', '레벨순']
          "
          color="var(--main-col-2)"
          dense
          rounded
          outlined
          hide-details
          style="font-size: 15px"
        ></v-select>
      </v-sheet>
    </v-sheet>
    <!-- list buttons -->
    <v-row class="mx-7 py-5 pt-7">
      <v-col class="pa-0">
        <v-btn
          elevation="0"
          color="var(--main-col-2)"
          @click="isListMine = true"
          :dark="isListMine ? true : false"
          :outlined="isListMine ? false : true"
          block
          tile
          >나의 메모</v-btn
        >
      </v-col>
      <v-col class="pa-0">
        <v-btn
          elevation="0"
          color="var(--main-col-2)"
          @click="isListMine = false"
          :dark="isListMine ? false : true"
          :outlined="isListMine ? true : false"
          block
          tile
          >전체 메모</v-btn
        >
      </v-col>
    </v-row>
    <!-- my memo list -->
    <NewsDetailMemoMine v-if="isListMine" :sort="sort"></NewsDetailMemoMine>
    <!-- others memo list -->
    <NewsDetailMemoOther v-else :sort="sort"></NewsDetailMemoOther>
  </v-sheet>
</template>

<script>
import NewsDetailMemoOther from "./NewsDetailMemoOther.vue";
import NewsDetailMemoMine from "./NewsDetailMemoMine.vue";

export default {
  name: "NewsDetail",
  components: {
    NewsDetailMemoMine,
    NewsDetailMemoOther,
  },
  data() {
    return {
      sort: "최신순",
      isListMine: true,
    };
  },
  watch: {
    isListMine() {
      this.sort = "최신순";
    },
  },
};
</script>

<style></style>
