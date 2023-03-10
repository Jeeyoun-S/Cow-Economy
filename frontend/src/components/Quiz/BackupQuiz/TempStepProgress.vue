<template>
  <div>
    <div>
      <step-progress
        :steps="this.mySteps"
        :current-step="this.currentStep"
        icon-class="fa fa-check"
      ></step-progress>
      <v-btn @click="preStep">이전</v-btn>
      <v-btn @click="nextStep">다음</v-btn>
    </div>
  </div>
</template>

<script>
import StepProgress from "vue-step-progress";

// import the css (OPTIONAL - you can provide your own design)
import "vue-step-progress/dist/main.css";

export default {
  name: "TodayQuiz",
  components: {
    "step-progress": StepProgress,
  },
  data() {
    return {
      mySteps: ["1", "2", "3", "4", "5", "6", "7"],
      currentStep: 0,
    };
  },
  methods: {
    nextStep() {
      this.currentStep++;
    },
    preStep() {
      this.currentStep--;
    },
  },
};
// ...
// register the component in your Vue instance
</script>
