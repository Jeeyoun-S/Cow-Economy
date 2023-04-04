<template>
  <div class="py-5 d-flex flex-column justify-center align-center">
    <img
      width="150"
      src="@/assets/images/illustration/empty.png"
      alt="#"
      class="responsive-image"
    />
    <v-card
      width="100%"
      height="100"
      class="point-th mb-4 pt-3 d-flex flex-column justify-center align-center"
      rounded="xl"
      elevation="0"
    >
      <v-card-text class="pa-0 text-center">
        <div class="lg-font">보여 드릴 데이터가 없어요</div>
        <div class="lg-font">
          {{ this.childValue }}
        </div>
      </v-card-text>
      <v-card-actions class="d-flex justify-center">
        <v-btn
          outlined
          rounded
          color="var(--main-col-3)"
          class="md-font"
          @click="goToNewsList"
          height="30"
        >
          기사 읽으러 가기
        </v-btn>
      </v-card-actions>
    </v-card>
  </div>
</template>

<script>
export default {
  props: ["childValue"],
  methods: {
    goToNewsList() {
      this.$router.push("/news"); // '/news' 경로로 이동하는 코드를 추가합니다.
    },
  },
};
</script>

<style>
.emoji {
  font-size: 3rem;
  margin-bottom: 10px;
}
</style>
