<template>
  <div class="pa-5">
    <div class="d-flex align-center">
      <span class="xxl-font">경제 용어의 카테고리</span>
      <div class="horizontal-divider">
        <v-divider class="mx-2"></v-divider>
      </div>
      <span class="xxl-font th-font">03</span>
    </div>
    <div class="th-font">
      오늘의 Quiz에서 맞췄던 용어의 카테고리를 보여드립니다.
    </div>
    <div class="d-flex justify-space-between align-center pt-7 px-3 xxxl-font">
      <v-btn x-large icon>
        <v-icon x-large style="color: #bdbdbd">mdi-chevron-left</v-icon>
      </v-btn>
      <span style="color: #757575">2023년 03월</span>
      <v-btn x-large icon>
        <v-icon x-large style="color: #bdbdbd">mdi-chevron-right</v-icon>
      </v-btn>
    </div>
    <canvas ref="radarChart"></canvas>
  </div>
</template>

<script>
import { mapState } from "vuex";
import { Chart, registerables } from "chart.js";

Chart.register(...registerables);

export default {
  computed: {
    ...mapState("userStore", ["articleList"]),
  },
  mounted() {
    this.drawRadarChart();
  },
  methods: {
    drawRadarChart() {
      const ctx = this.$refs.radarChart.getContext("2d");
      this.category = this.articleList.wordCategoryList;
      const labels = this.category.map((item) => item[0]);
      const data = this.category.map((item) => item[1]);

      new Chart(ctx, {
        type: "radar",
        data: {
          labels: labels,
          datasets: [
            {
              label: "Categories",
              data: data,
              backgroundColor: "rgba(229, 232, 253, 0.8)",
              borderColor: "rgba(81, 118, 250, 1)",
              borderWidth: 1,
              pointBackgroundColor: "rgba(81, 118, 250, 1)",
            },
          ],
        },
        options: {
          plugins: {
            legend: {
              display: false,
              position: "bottom",
            },
            tooltip: {
              enabled: true,
            },
          },
          scales: {
            r: {
              beginAtZero: true,
              ticks: {
                stepSize: 1,
                display: false,
              },
              grid: {
                color: function (context) {
                  return context.tick.value === 0
                    ? "rgba(0, 0, 0, 0)"
                    : "rgba(0, 0, 0, 0.1)";
                },
              },
              angleLines: {
                display: false,
              },
              pointLabels: {
                color: "rgb(0, 0, 0)", // 항목 글씨 색상 변경
                font: {
                  size: 14, // 항목 글씨 크기 변경
                  family: getComputedStyle(document.documentElement)
                    .getPropertyValue("--main-font-3")
                    .trim(),
                },
              },
            },
          },
        },
      });
    },
  },
};
</script>
