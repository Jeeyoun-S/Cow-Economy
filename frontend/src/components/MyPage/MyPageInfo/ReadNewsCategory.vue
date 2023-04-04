<template>
  <div class="px-1 mt-6">
    <div class="d-flex align-center">
      <span class="xl-font">읽은 기사의 카테고리</span>
      <!-- <div class="horizontal-divider"> -->
      <v-divider class="mx-2"></v-divider>
      <!-- </div> -->
      <span class="xl-font th-font">02</span>
    </div>
    <div class="mb-3 th-font sm-font">
      지금까지 읽은 기사의 카테고리 비율을 보여드려요.
    </div>
    <div v-if="hasData">
      <div
        class="d-flex justify-space-between align-center py-2 px-3 xxxl-font"
      >
        <div x-large icon></div>
        <span class="xxl-font" style="color: #757575"
          >{{ this.currentYear }}년</span
        >
        <div x-large icon></div>
      </div>
      <div class="d-flex justify-center">
        <canvas
          class="chartjs-render-monitor"
          ref="barChart"
          height="350"
        ></canvas>
      </div>
    </div>
    <InfoNoData v-else v-bind:childValue="msg"></InfoNoData>
  </div>
</template>

<script>
import { Chart, registerables } from "chart.js";

Chart.register(...registerables);
Chart.defaults.font.family = "MinSans-Regular";

import { mapState } from "vuex";
import InfoNoData from "@/components/MyPage/MyPageInfo/InfoNoData.vue";

export default {
  components: {
    InfoNoData,
  },
  data: function () {
    return {
      chart: null,
      currentYear: null,
      msg: "기사를 읽어주세요",
    };
  },
  computed: {
    ...mapState("userStore", ["articleList"]),
    hasData() {
      const wordCategoryList = this.articleList.readCategoryList;
      // console.log(wordCategoryList)
      return wordCategoryList.some((value) => value[1] !== 0);
    },
  },
  created() {
    this.currentYear = new Date().getFullYear();
  },
  mounted() {
    if (this.hasData) {
      this.drawChart();
    }
  },
  watch: {
    // Watch for changes in readCategoryList
    readCategoryList: {
      handler() {
        this.$nextTick(() => {
          if (this.$refs.barChart) {
            this.drawChart();
          }
        });
      },
      immediate: true, // Run the handler immediately after the watcher is created
    },
  },
  methods: {
    drawChart() {
      if (this.chart) {
        this.chart.destroy();
      }
      const ctx = this.$refs.barChart.getContext("2d");
      // console.log("readNewsCategory", this.articleList.readCategoryList)

      const labels = this.articleList.readCategoryList.map((item) => item[0]);
      const data = this.articleList.readCategoryList.map((item) => item[1]);
      // console.log("##23 ", data)
      this.chart = new Chart(ctx, {
        type: "doughnut",
        data: {
          labels: labels,
          datasets: [
            {
              data: data,
              backgroundColor: [
                "#cdd2fd",
                "#a7b0f8",
                "#8996f9",
                "#5764c9",
                "#3846b3",
                "#2c378b",
                "#040f65",
                "#000c64",
              ],
              borderWidth: 1,
            },
          ],
        },
        options: {
          responsive: false,
          plugins: {
            legend: {
              position: "bottom",
              boxWidth: 20,
              align: "center",

              labels: {
                usePointStyle: true,
                pointStyle: "circle",
                borderWidth: 3,
                useBorderRadius: true,
                borderRadius: "3",
                font: {
                  size: 14,
                  // family: getComputedStyle(document.documentElement)
                  //   .getPropertyValue("--main-font-1")
                  //   .trim(),
                },
                textAlign: "left",
              },
            },
            tooltip: {
              enabled: true,
            },
          },
          cutout: "60%",
          animation: {
            animateScale: true,
            animateRotate: true,
          },
        },
      });
    },
  },
};
</script>

<style>
.horizontal-divider {
  flex-grow: 1;
  align-self: center;
}
</style>
