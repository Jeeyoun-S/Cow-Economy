<template>
  <v-dialog v-if="wordModal" v-model="dialog" max-width="380" scrollable
    ><v-card>
      <v-card-title class="pb-2">
        <v-sheet
          width="100%"
          class="d-flex flex-row align-center justify-space-between"
        >
          <div class="d-flex flex-row flex-wrap align-end">
            <span class="point-md xl-font pr-2">{{ wordModal }}</span>
            <span
              v-if="wordInfo[wordModal].subword"
              class="main-col-2 point-th xs-font"
            >
              [ {{ wordInfo[wordModal].subword }} ]
            </span>
          </div>
          <v-btn icon @click="dialog = false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-sheet>
      </v-card-title>
      <v-divider></v-divider>
      <v-card-text class="black-col-1" style="max-height: 400px">
        <div class="pb-2 d-flex flex-row align-center"></div>
        <div class="d-flex flex-column">
          <div>{{ wordInfo[wordModal].wordExpl }}</div>
        </div>
      </v-card-text>
    </v-card></v-dialog
  >
</template>

<script>
import { mapActions, mapState } from "vuex";

export default {
  name: "NewsDetailContentWord",
  computed: {
    ...mapState("wordStore", ["wordModal", "isWordModalOpen", "wordInfo"]),
  },
  data() {
    return {
      dialog: false,
    };
  },
  methods: {
    ...mapActions("wordStore", ["setIsWordModal"]),
  },
  watch: {
    isWordModalOpen() {
      this.dialog = this.isWordModalOpen;
    },
    dialog() {
      this.setIsWordModal(this.dialog);
    },
  },
};
</script>

<style></style>
