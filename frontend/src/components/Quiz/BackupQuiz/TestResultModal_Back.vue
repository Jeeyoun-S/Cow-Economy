<!-- <template>
  <div>
    <div class="modal-mask">
      <div class="modal-wrapper">
        <div class="modal-container">
          <h5>최종 스코어</h5>
          <h3>Modal 입니당</h3>
          <div>
            <slot name="body"></slot>
          </div>
          <footer>
            <button class="btn-close" v-on:click="closeModal">
              퀴즈 종료하기
            </button>
          </footer>
        </div>
      </div>
    </div>
  </div>
</template> -->
<template>
  <div class="modal-mask">
    <v-card class="modal-wrapper">
      <v-card-text>
        <div>Word of the Day</div>
        <p class="text-h4 text--primary">be•nev•o•lent</p>
        <p>adjective</p>
        <div class="text--primary">
          well meaning and kindly.<br />
          "a benevolent smile"
        </div>
      </v-card-text>
      <v-card-actions>
        <v-btn text color="deep-purple accent-4"> Learn More </v-btn>
      </v-card-actions>
    </v-card>
  </div>
</template>

<script>
export default {
  name: "TestResultModal",
  data() {
    return {};
  },
  methods: {
    // [@Method] Modal 닫고 마이페이지로 이동
    closeModal() {
      console.log("#21# 모달 닫음");
    },
  },
};
</script>

<style>
.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: table;
  transition: opacity 0.3s ease;
}

/* .modal-wrapper {
  display: table-cell;
  vertical-align: middle;
} */
.modal-wrapper {
  /* display: table-cell;
  vertical-align: middle; */

  width: 70%;
  margin-left: 0%;
}

.modal-container {
  width: 70%;
  margin: 0px auto;
  padding: 20px 30px;
  text-align: center;
  font-size: 1.4em;
  background-color: #fff;
  border-radius: 2px;
  -webkit-box-shadow: 2px 1px 5px 0px rgba(0, 0, 0, 0.75);
  -moz-box-shadow: 2px 1px 5px 0px rgba(0, 0, 0, 0.75);
  box-shadow: 2px 1px 5px 0px rgba(0, 0, 0, 0.75);
  transition: all 0.3s ease;
}
.dog {
  width: 50%;
  max-width: 200px;
}
footer {
  text-align: center;
}
.btn-close {
  cursor: pointer;
  padding: 10px 50px;
  font-size: 0.9em;
  background-color: #e53935;
  border: 0;
  color: #fff;
  border-radius: 10px;
  -webkit-box-shadow: 2px 1px 5px 0px rgba(0, 0, 0, 0.75);
  -moz-box-shadow: 2px 1px 5px 0px rgba(0, 0, 0, 0.75);
  box-shadow: 2px 1px 5px 0px rgba(0, 0, 0, 0.75);
}
.btn-close:hover {
  background-color: #d32f2f;
  transition: opacity 0.5s ease;
}
</style>
