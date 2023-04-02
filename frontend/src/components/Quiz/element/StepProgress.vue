<template>
  <div id="progress" class="px-7">
    <div>
      <v-step-progress
        :steps="this.steps"
        :current-step="this.index"
        icon-class="mdi-check"
        :active-color="this.activeColor"
        :passive-color="this.passiveColor"
        :line-thickness="this.lineThickness"
        :active-thickness="this.activeThickness"
        :passive-thickness="this.passiveThickness"
      ></v-step-progress>
    </div>
  </div>
</template>

<script>
import StepProgress from "vue-step-progress";
import { mapState } from "vuex";

const quizStore = "quizStore";

export default {
  name: "TodayQuiz",
  components: {
    "v-step-progress": StepProgress,
  },
  data() {
    return {
      steps: ["1", "2", "3", "4", "5", "6", "7"],
      activeColor: "var(--main-col-2)", // #5176FA
      passiveColor: "var(--main-col-4)", //  #FFFFFF
      lineThickness: 4,
      activeThickness: 4,
      passiveThickness: 2,
    };
  },
  computed: {
    ...mapState(quizStore, ["index"]),
  },
};
</script>

<style lang="sass">
.step-progress
  &__wrapper
    width: 100%
    margin: 0 auto
    position: relative

  &__wrapper-before
    content: '1'
    position: absolute
    left: 0
    top: 46%
    height: 12px
    width: 100%
    background-color: gray
    transform: translateY(-50%) perspective(1000px)

  &__wrapper-after
    content: ''
    position: absolute
    left: 0
    top: 46%
    height: 12px
    width: 100%
    background-color: red
    transform: scaleX(0) translateY(-50%) perspective(1000px)
    transform-origin: left center
    transition: transform .5s ease

  &__bar
    width: 100%
    display: flex
    height: 80px
    justify-content: space-between
    align-items: center

  &__step
    z-index: 2
    position: relative
    --activeColor: red
    --passiveColor: gray
    --activeBorder: 5px
    --passiveBorder: 5px
    span
      color: var(--passiveColor)
      transition: .3s ease
      display: block
      font-size: 18px
      transform: translate3d(0,0,0) scale(1) perspective(1000px)
      font-weight: 200
      text-align: center
      opacity: 1
      margin: 0.7px 1px 3px 0px
      padding: 2px
    .step-progress__step-icon
      display: none

    &--active /* 활성화 현재 step font */
      span
        color: var(--activeColor)
        font-family: var(--main-font-5)
      .step-progress__step-label
        color: var(--activeColor)

    &--valid /* 지나간 step font */
      span
        color: transparent
        background-image: url("@/assets/images/icon/check-solid.svg")
        background-position: center center
        background-size: 15px
      .step-progress__step-label
        color: var(--activeColor)

    &:after /* step 동그라미 */
      content: ""
      position: absolute
      z-index: -1
      left: 50%
      top: 40%
      transform: translate(-50%, -50%) perspective(1000px)
      width: 32px
      height: 32px
      background-color: #FFFFFF
      border-radius: 50%
      border: var(--passiveBorder) solid var(--passiveColor)
      transition: .3s ease

    &--active:after /* 현재 step 동그라미 */
      border: var(--activeBorder) solid var(--activeColor)
    &--valid:after /* 지나간 step 동그라미 */
      background-color: var(--activeColor)
      border: var(--activeBorder) solid var(--activeColor)

  &__step-label /* step 라벨 */
    position: absolute
    top: calc(100%)
    left: 50%
    transform: translateX(-50%) perspective(1000px)
    white-space: nowrap
    font-size: 0px
    transition: .3s ease

    &__step-icon
      font-size: 36px
      color: #fff
      position: absolute
      left: 50%
      top: 50%
      transition: transform .3s ease
      opacity: 1
      transform: translate3d(-50%, -50%, 0) scale(0) perspective(1000px)
</style>
