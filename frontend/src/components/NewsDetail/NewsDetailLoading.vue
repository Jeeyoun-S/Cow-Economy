<template>
  <v-sheet class="pa-7 d-flex flex-column">
    <div class="pb-3 d-flex flex-row">
      <!-- category -->
      <v-skeleton-loader
        class="mr-3 d-flex flex-row"
        type="chip"
        width="70"
      ></v-skeleton-loader>
      <!-- press -->
      <v-skeleton-loader
        class="d-flex flex-row"
        type="chip"
        width="70"
      ></v-skeleton-loader>
    </div>
    <!-- title -->
    <v-skeleton-loader
      type="image"
      height="45"
      width="100%"
    ></v-skeleton-loader>
    <div class="pt-3 pb-1 d-flex flex-row justify-space-between">
      <!-- date -->
      <v-skeleton-loader type="text" width="100"></v-skeleton-loader>
      <!-- url -->
      <v-skeleton-loader type="text" width="50"></v-skeleton-loader>
    </div>
    <!-- image -->
    <v-skeleton-loader class="pb-3" type="image"></v-skeleton-loader>
    <!-- text -->
    <v-skeleton-loader type="paragraph@3"></v-skeleton-loader>
  </v-sheet>
</template>

<script>
export default {
  name: "NewsDetailLoading",
};
</script>

<style></style>
