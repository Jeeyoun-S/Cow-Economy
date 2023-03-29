<template>
  <div class="pa-5">
    <div class="d-flex align-center">
      <span class="xxl-font">읽은 기사의 카테고리</span>
      <div class="horizontal-divider">
        <v-divider class="mx-2"></v-divider>
      </div>
      <span class="xxl-font th-font">02</span>
    </div>
    <div class="th-font">지금까지 읽은 기사의 카테고리 비율을 보여드려요.</div>
    <div class="d-flex justify-space-between align-center pt-7 px-3 xxxl-font">
      <v-btn x-large icon>
        <v-icon x-large style="color: #BDBDBD">mdi-chevron-left</v-icon>
      </v-btn>
      <span style="color: #757575">2023년</span>
      <v-btn x-large icon>
        <v-icon x-large style="color: #BDBDBD">mdi-chevron-right</v-icon>
      </v-btn>
    </div>
    <div class="d-flex justify-center">
      <canvas class="chartjs-render-monitor" ref="barChart" height="400"></canvas>
    </div>
  </div>
</template>

<script>
import { Chart, registerables } from "chart.js";

Chart.register(...registerables);

import { mapState } from "vuex";

export default {
  data: function() {
    return {
      chart: null,
    };
  },
  computed: mapState("newsStore", ["category"]),
  mounted() {
    this.drawChart();
  },
  methods: {
    drawChart() {
      const ctx = this.$refs.barChart.getContext("2d");

      const labels = this.category.map((item) => item[0]);
      const data = this.category.map((item) => item[1]);

      this.chart = new Chart(ctx, {
        type: "doughnut",
        data: {
          labels: labels,
          datasets: [
            {
              data: data,
              backgroundColor: [
                "#cdd2fd",
                "#9ba6fa",
                "#6979f8",
                "#3c4bbf",
                "#23319d",
                "#182271",
                "#000c64",
                "#000000",
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
                  size: 16,
                  family: getComputedStyle(document.documentElement).getPropertyValue('--main-font-3').trim(),
                },
              }
            },
            tooltip: {
              enabled: true,
            },
          },
          cutout: '60%',
          animation: {
            animateScale: true,
            animateRotate: true,
          },
        },
      });
    }
  },
};
</script>

<style>
  .horizontal-divider {
    flex-grow: 1;
    align-self: center;
  }
</style>
