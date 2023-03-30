<template>
  <v-sheet
    id="profile"
    color="transparent"
    class="py-7 d-flex flex-row align-center"
  >
    <!-- Level Image -->
    <div class="mr-4">
      <img
        height="70"
        :src="require('@/assets/images/level/' + levelImage[user.userLevel])"
      />
    </div>
    <v-sheet color="transparent" class="d-flex flex-column" width="63%"
      ><div class="d-flex flex-row align-center justify-space-between">
        <!-- Level -->
        <span class="b-font xl-font main-col-1"
          >LEVEL {{ String.fromCharCode(user.userLevel) }}</span
        >
        <!-- Level Info -->
        <v-dialog v-model="levelInfoDialog" max-width="370">
          <template v-slot:activator="{ on, attrs }">
            <v-icon color="grey lighten-1" v-bind="attrs" v-on="on"
              >mdi-information-outline</v-icon
            >
          </template>
          <!-- Level Info Modal -->
          <v-sheet class="xs-font pa-8 ma-0">
            <div class="d-flex">
              <div class="mb-3 xxl-font b-font">소귀경의 LEVEL</div>
              <v-btn class="ml-auto" icon @click="levelInfoDialog = false">
                <v-icon>mdi-close</v-icon>
              </v-btn>
            </div>
            <div class="d-flex flex-row flex-wrap justify-space-between">
              <div
                v-for="l in level"
                :key="l"
                class="my-2 mr-4 d-flex flex-row align-center"
              >
                <img
                  class="mr-2"
                  height="45"
                  :src="require('@/assets/images/level/' + levelImage[l])"
                />
                <div>EXP {{ minExperience[l] }}<br />이상 자동 승급</div>
              </div>
            </div>
            <v-btn
              class="mt-5 blue-shadow"
              block
              dark
              color="var(--main-col-2)"
              @click="levelInfoDialog = false"
              >닫기</v-btn
            >
          </v-sheet>
        </v-dialog>
      </div>
      <div class="my-1">
        <!-- Experience Bar -->
        <v-progress-linear
          background-color="white"
          color="pink lighten-1"
          :value="(100 / maxExperience[user.userLevel]) * user.userExperience"
          height="12"
          rounded
        ></v-progress-linear>
      </div>
      <div class="d-flex flex-row align-center justify-space-between xs-font">
        <!-- Experience -->
        <span>EXP {{ user.userExperience }}</span>
        <!-- Next Level Experience -->
        <span
          >다음 레벨까지 -
          {{ maxExperience[user.userLevel] - user.userExperience }}</span
        >
      </div>
    </v-sheet>
  </v-sheet>
</template>

<script>
export default {
  name: "MyPageProfile",
  data() {
    return {
      levelInfoDialog: false,
      level: [83, 67, 65, 68, 66, 70],
      maxExperience: {
        70: 500,
        68: 1000,
        67: 1500,
        66: 2500,
        65: 4000,
        83: 6000,
      },
      minExperience: {
        70: 0,
        68: 501,
        67: 1001,
        66: 1501,
        65: 2501,
        83: 4001,
      },
      levelImage: {
        70: "level_f.png",
        68: "level_d.png",
        67: "level_c.png",
        66: "level_b.png",
        65: "level_a.png",
        83: "level_s.png",
      },
    };
  },
  props: {
    user: Object,
  },
};
</script>

<style></style>
