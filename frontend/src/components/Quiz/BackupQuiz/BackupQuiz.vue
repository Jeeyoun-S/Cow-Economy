<template>
  <div>
    quiz
    <div>
      <!-- :steps="['1', '2', '3', '4', '5', '6', '7']" -->
      <v-step-progress
        :steps="['1', '2', '3']"
        passive-color="#b5b5b5"
        active-color="#23d160"
        :passive-thickness="3"
        :active-thickness="3"
        :line-thickness="7"
        :current-step="step - 1"
        icon-class="fa fa-check"
        class="progress"
      ></v-step-progress>
    </div>
  </div>
</template>

<script>
import StepProgress from "vue-step-progress";
import "vue-step-progress/dist/main.css";

export default {
  name: "TodayQuiz",
  components: {
    "v-step-progress": StepProgress,
  },
};
</script>

<style>
.progress {
}
</style>

<!-- <style lang="sass">
.step-progress
  &__wrapper
    width: 80%
    margin: 0 auto
    position: relative

  /* 바 위치
    - top: 바 위치
    - width 바 게이지 */
  &__wrapper-before
    content: ''
    position: absolute
    left: 0
    top: 50%
    height: 12px
    width: 100%
    background-color: grey
    transform: translateY(-50%) perspective(1000px)

  &__wrapper-after
    content: ''
    position: absolute
    left: 0
    top: 50%
    height: 12px
    width: 100%
    background-color: red
    transform: scaleX(0) translateY(-50%) perspective(1000px)
    transform-origin: left center
    transition: transform .5s ease

  /* 프로그레스 위치 */
  &__bar
    width: 100%
    display: flex
    height: 80px
    justify-content: space-between
    align-items: center
    margin-bottom: 40px

  &__step
    z-index: 2
    position: relative
    --activeColor: red
    --passiveColor: grey
    --activeBorder: 5px
    --passiveBorder: 5px
    span
      color: var(--passiveColor)
      transition: .3s ease
      display: block
      font-size: 15px
      transform: translate3d(0,0,0) scale(1) perspective(1000px)
      font-weight: 200
      text-align: center
      opacity: 1
      @media (max-width: 767px)
        font-size: 28px

    &--active
      span
        color: var(--activeColor)
      .step-progress__step-label
        color: var(--activeColor)
      .step-progress__step-icon
        opacity: 1

    &--valid
      .step-progress__step-icon
        opacity: 1
        transform: translate3d(-50%, -50%, 0) scale(1) perspective(1000px)
      span
        color: var(--activeColor)
        opacity: 0
        transform: translate3d(0,0,0) scale(2) perspective(1000px)
      .step-progress__step-label
        color: var(--activeColor)

    /* 숫자를 감싸고 있는 동그라미 */
    &:after
      content: ""
      position: absolute
      z-index: -1
      left: 50%
      top: 45%
      transform: translate(-50%, -50%) perspective(1000px)
      width: 29px
      height: 29px
      background-color: #fff
      border-radius: 50%
      border: var(--passiveBorder) solid var(--passiveColor)
      transition: .3s ease
      @media (max-width: 767px)
        width: 40px
        height: 40px

    &--active:after
      border: var(--activeBorder) solid var(--activeColor)

    &--valid:after
      background-color: var(--activeColor)
      border: var(--activeBorder) solid var(--activeColor)

  &__step-label
    position: absolute
    top: calc(100% + 12px)
    left: 50%
    transform: translateX(-50%) perspective(1000px)
    white-space: nowrap
    font-size: 15px
    font-weight: 600
    color: gray
    transition: .3s ease

  &__step-icon
    font-size: 36px
    color: #fff
    position: absolute
    left: 50%
    top: 50%
    transition: transform .3s ease
    opacity: 0
    transform: translate3d(-50%, -50%, 0) scale(0) perspective(1000px)
    @media (max-width: 767px)
      font-size: 22px 
</style> -->
