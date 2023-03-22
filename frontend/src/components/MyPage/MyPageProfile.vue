<template>
  <v-sheet color="transparent" class="my-6 d-flex flex-row align-center"
    ><img class="mr-4" height="70" src="@/assets/images/level/level_f.png" />
    <v-sheet color="transparent" class="d-flex flex-column" width="63%"
      ><div class="d-flex flex-row align-center justify-space-between">
        <span class="b-font">Level F</span>
        <v-icon>mdi-information-outline</v-icon>
      </div>
      <div class="my-1">
        <v-progress-linear
          background-color="pink lighten-3"
          color="pink lighten-1"
          value="15"
        ></v-progress-linear>
      </div>
      <div class="d-flex flex-row align-center justify-space-between sm-font">
        <span>EXP 40</span><span>다음 레벨까지 -230</span>
      </div>
    </v-sheet>
  </v-sheet>
</template>

<script>
export default {
  name: "MyPageProfile",
};
</script>

<style></style>
