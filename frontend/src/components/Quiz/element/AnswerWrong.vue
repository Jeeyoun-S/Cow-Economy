<template>
  <v-sheet
    rounded="xl"
    elevation="10"
    class="pa-5 d-flex flex-column justify-space-between flex-wrap align-center"
  >
    <!-- <img width="45%" src="@/assets/images/mypage/quiz/wrong.png" /> -->
    <v-sheet width="100%" class="px-2">
      <v-sheet
        width="100%"
        class="pb-1 d-flex flex-row justify-space-between align-center"
      >
        <!-- 문제 번호 -->
        <div class="logo-b xxxl-font">{{ this.index + 1 }}. 정답</div>
        <v-btn
          class="point-md"
          color="var(--graph-1-col-4)"
          rounded
          elevation="0"
          outlined
          v-if="questions[this.index]['question'].split('.').length > 2"
          @click="dialog = true"
        >
          더보기
        </v-btn>
      </v-sheet>
      <v-dialog max-width="380" v-model="dialog" scrollable>
        <v-card>
          <v-card-text class="pt-5 black-col-1" style="max-height: 400px">
            {{ questions[this.index]["question"] }}
          </v-card-text>
          <v-card-actions class="align-self-end">
            <v-btn
              color="var(--main-col-1)"
              @click="dialog = false"
              text
              rounded
              elevation="0"
            >
              닫기
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
      <div class="my-2 b-font lg-font">
        {{ questions[this.index]["question"].split(".")[0] }}
      </div>
    </v-sheet>
    <v-sheet
      width="100%"
      color="transparent"
      v-for="(answer, key) in questions[index]['answers']"
      :key="key"
      class="py-2"
    >
      <v-btn
        class="d-flex justify-start px-8"
        :color="
          key.charCodeAt() == questions[index]['correctAnswer']
            ? 'var(--main-col-1)'
            : key == selectedAnswer
            ? 'var(--error-col-1)'
            : ''
        "
        :dark="
          key.charCodeAt() == questions[index]['correctAnswer'] ||
          key == selectedAnswer
            ? true
            : false
        "
        :outlined="
          key.charCodeAt() == questions[index]['correctAnswer'] ||
          key == selectedAnswer
            ? false
            : true
        "
        elevation="0"
        width="100%"
        rounded
        large
      >
        <div class="mr-2 d-flex align-center md-font">
          <v-icon v-if="key.charCodeAt() == questions[index]['correctAnswer']">
            mdi-circle-outline
          </v-icon>
          <v-icon v-else-if="key == selectedAnswer">mdi-close</v-icon>
          <span v-else class="black-font">{{ key }}.</span>
        </div>
        <span class="md-font">{{ answer }}</span>
      </v-btn>
    </v-sheet>
    <v-btn
      class="px-6 mt-2 gradient-2 align-self-end"
      rounded
      elevation="0"
      dark
      @click="nextQuestion"
    >
      <span v-if="index < 6">다음</span>
      <span v-else>결과보기</span>
    </v-btn>
  </v-sheet>
</template>

<script>
import { mapState } from "vuex";

export default {
  data() {
    return {
      dialog: false,
    };
  },
  computed: {
    ...mapState("quizStore", ["index", "questions"]),
  },
  props: {
    selectedAnswer: String,
  },
  methods: {
    nextQuestion() {
      this.$emit("nextQuestion");
    },
  },
};
</script>

<style></style>
