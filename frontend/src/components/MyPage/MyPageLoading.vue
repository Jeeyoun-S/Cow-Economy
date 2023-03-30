<template>
  <div>
    <!-- profile -->
    <v-sheet
      color="transparent"
      class="py-7 d-flex flex-row justify-center align-center"
    >
      <v-skeleton-loader
        class="mr-4"
        type="image"
        height="70"
        width="70"
      ></v-skeleton-loader>
      <v-sheet color="transparent" class="d-flex flex-column" width="63%">
        <div class="pb-2 d-flex flex-row justify-space-between">
          <v-skeleton-loader type="heading" width="200"></v-skeleton-loader>
        </div>
        <div>
          <v-skeleton-loader type="text" width="100%"></v-skeleton-loader>
        </div>
        <div class="d-flex flex-row justify-space-between">
          <v-skeleton-loader type="text" width="15%"></v-skeleton-loader>
          <v-skeleton-loader type="text" width="20%"></v-skeleton-loader>
        </div>
      </v-sheet>
    </v-sheet>
    <v-sheet class="pa-7" rounded="t-xl" elevation="5">
      <v-sheet class="pa-1 pb-5 d-flex flex-row justify-space-between">
        <div>
          <v-skeleton-loader
            class="mb-2"
            type="heading"
            width="200"
          ></v-skeleton-loader>
          <v-skeleton-loader type="heading" width="250"></v-skeleton-loader>
        </div>
        <v-skeleton-loader type="chip"></v-skeleton-loader>
      </v-sheet>
      <div class="d-flex flex-row justify-space-between">
        <v-skeleton-loader
          type="image"
          width="47%"
          height="30"
        ></v-skeleton-loader>
        <v-skeleton-loader
          type="image"
          width="47%"
          height="30"
        ></v-skeleton-loader>
      </div>
      <div v-for="index in 3" :key="index" class="my-6">
        <v-skeleton-loader class="mb-2" type="heading"></v-skeleton-loader>
        <v-skeleton-loader type="text"></v-skeleton-loader>
        <v-skeleton-loader type="image"></v-skeleton-loader>
      </div>
    </v-sheet>
  </div>
</template>

<script>
export default {
  name: "MyPageLoading",
};
</script>

<style></style>
