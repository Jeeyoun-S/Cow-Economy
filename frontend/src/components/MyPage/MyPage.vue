<template>
  <v-sheet color="transparent">
    <!-- level profile -->
    <MyPageProfile class="justify-center"></MyPageProfile>
    <!-- username & logout btn -->
    <v-sheet
      class="pa-8 d-flex flex-row justify-space-between"
      rounded="t-xl"
      elevation="5"
      min-height="2000px"
      ><div>
        <div>안녕하세요</div>
        <div>홍길동님</div>
      </div>
      <MyPageLogoutBtn></MyPageLogoutBtn>
    </v-sheet>
    <!-- select -->
    <div></div>
  </v-sheet>
</template>

<script>
import MyPageProfile from "./MyPageProfile.vue";
import MyPageLogoutBtn from "./MyPageLogoutBtn.vue";

export default {
  name: "MyPage",
  components: {
    MyPageProfile,
    MyPageLogoutBtn,
  },
};
</script>

<style></style>
