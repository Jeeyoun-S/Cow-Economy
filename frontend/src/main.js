import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import vuetify from "./plugins/vuetify";
import VueAwesomeSwiper from "vue-awesome-swiper";
import "swiper/css/swiper.min.css";
import "@egjs/vue-flicking/dist/flicking.css";
import "@egjs/vue-flicking/dist/flicking-inline.css";

Vue.config.productionTip = false;
Vue.use(VueAwesomeSwiper);

new Vue({
  router,
  store,
  vuetify,
  VueAwesomeSwiper,
  render: (h) => h(App),
}).$mount("#app");
