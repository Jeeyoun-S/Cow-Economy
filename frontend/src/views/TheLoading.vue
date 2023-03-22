<!-- # for. axios 통신 시 Loading 창 -->
<!-- <template>
  <div class="lds-facebook" v-if="loading">
    <div></div>
    <div></div>
    <div></div>
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
  background: #42b883;
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
<template>
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
      <p>Slot!</p>
    </vue-circle>
  </div>
  <!-- <div class="loading" v-if="loading">
    <v-progress-circular
      :size="70"
      :width="7"
      color="primary"
      indeterminate
    ></v-progress-circular>
  </div> -->
  <!-- <div class="loading" v-if="loading">
    <v-progress-circular
      :size="70"
      :width="7"
      color="primary"
      indeterminate
    ></v-progress-circular>
  </div> -->
</template>

<script>
export default {
  name: "NowLoading",
};
</script>

<style scoped>
.loading {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 600px;
  padding: 20px;
}
.loading span {
  font-size: 50px;
  font-weight: 800;
}
</style>
