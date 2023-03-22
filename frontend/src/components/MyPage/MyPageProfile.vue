<template>
  <v-sheet
    id="profile"
    color="transparent"
    class="my-6 d-flex flex-row align-center"
    ><img class="mr-4" height="70" src="@/assets/images/level/level_f.png" />
    <v-sheet color="transparent" class="d-flex flex-column" width="63%"
      ><div class="d-flex flex-row align-center justify-space-between">
        <span class="b-font xl-font main-col-1">LEVEL F</span>
        <v-icon small>mdi-information-outline</v-icon>
      </div>
      <div class="my-1">
        <v-progress-linear
          background-color="white"
          color="pink lighten-1"
          value="80"
          height="12"
          rounded
        ></v-progress-linear>
      </div>
      <div class="d-flex flex-row align-center justify-space-between xs-font">
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
