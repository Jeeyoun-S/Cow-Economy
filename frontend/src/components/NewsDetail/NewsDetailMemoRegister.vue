<template>
  <v-sheet class="pa-5" rounded="lg" outlined>
    <div class="d-flex justify-space-between">
      <v-card
        class="pa-1"
        color="grey lighten-1"
        height="100%"
        elevation="0"
        rounded="pill"
      >
        <v-chip
          class="pa-4 px-5"
          :color="isOpen ? 'white' : 'transparent'"
          :text-color="isOpen ? 'grey darken-1' : 'white'"
          @click="isOpen = true"
          ><v-icon left> mdi-lock-open-outline </v-icon>공개</v-chip
        >
        <v-chip
          class="pa-4 px-5"
          :color="isOpen ? 'transparent' : 'white'"
          :text-color="isOpen ? 'white' : 'grey darken-1'"
          @click="isOpen = false"
          ><v-icon left> mdi-lock-outline </v-icon>비공개</v-chip
        >
      </v-card>
      <v-btn icon text large><v-icon> mdi-plus </v-icon></v-btn>
    </div>
    <v-textarea
      name="input-7-1"
      placeholder="메모 내용을 작성해 주세요.
  기사 일부를 드래그해 인용문을 추가해 보세요."
      hide-details
    ></v-textarea>
  </v-sheet>
</template>

<script>
export default {
  name: "NewsDetailMemoRegister",
  data() {
    return {
      isOpen: true,
    };
  },
};
</script>

<style></style>
