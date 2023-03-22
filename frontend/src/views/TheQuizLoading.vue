<!-- # for. quiz Loading 창 -->
<!-- <template>
  <div class="modal-mask" v-if="loading">
    <div class="lds-facebook">
      <div></div>
      <div></div>
      <div></div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    loading: {
      type: Boolean,
      required: true,
    },
  },
};
</script>

<style>
.lds-facebook {
  display: inline-block;
  position: absolute;
  position: fixed;
  width: 64px;
  height: 64px;
  top: 50%;
  left: 50%;
}

.lds-facebook div {
  display: inline-block;
  position: absolute;
  left: 6px;
  width: 13px;
  background: #5176fa;
  animation: lds-facebook 1.2s cubic-bezier(0, 0.5, 0.5, 1) infinite;
}

.lds-facebook div:nth-child(1) {
  left: 6px;
  animation-delay: -0.24s;
}

.lds-facebook div:nth-child(2) {
  left: 26px;
  animation-delay: -0.12s;
}

.lds-facebook div:nth-child(3) {
  left: 45px;
  animation-delay: 0;
}

@keyframes lds-facebook {
  0% {
    top: 6px;
    height: 51px;
  }

  50%,
  100% {
    top: 19px;
    height: 26px;
  }
}
</style> -->
<!-- <template>
  <div class="modal-mask" v-if="loading">
    <vue-circle
      :progress="50"
      :size="100"
      :reverse="false"
      line-cap="round"
      :fill="fill"
      empty-fill="rgba(0, 0, 0, .1)"
      :animation-start-value="0.0"
      :start-angle="0"
      insert-mode="append"
      :thickness="5"
      :show-percent="true"
      @vue-circle-progress="progress"
      @vue-circle-end="progress_end"
    >
      <p>로오딩</p>
    </vue-circle>
  </div>
</template> -->
<template>
  <div class="modal-mask" v-if="loading">
    <div><img :src="require('@/assets/images/mypage/quiz/fail.gif')" /></div>
  </div>
</template>

<script>
export default {
  props: {
    loading: {
      type: Boolean,
      required: true,
    },
  },
};
</script>

<style></style>
