<template>
  <v-sheet color="transparent">
    <v-toolbar flat height="78px" color="transparent">
      <!-- 뒤로가기 아이콘 -->
      <BackIcon></BackIcon>
      <!-- 검색창 -->
      <v-text-field class="px-3" placeholder="검색어를 입력해 주세요." 
        append-icon="mdi-magnify" solo hide-details clearable
        @input="onInput"
      ></v-text-field>
    </v-toolbar>
  </v-sheet>
</template>

<script>
import BackIcon from "@/common/component/BackIcon.vue"
import { mapActions } from 'vuex'

export default {
  name: "SearchHeader",
  components: {
    BackIcon
  },
  methods: {
    ...mapActions("newsStore", ["setSearchText", "setSearched"]),
    onInput(value) {
      this.setSearchText(value);
      this.setSearched(true);
    }
  }
}
</script>

<style>

</style>