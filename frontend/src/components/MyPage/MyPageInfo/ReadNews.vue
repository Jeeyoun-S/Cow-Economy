<template>
  <div class="px-5">
    <div class="d-flex align-center">
      <span class="xxl-font">읽은 기사 수</span>
      <div class="horizontal-divider">
        <v-divider class="mx-2"></v-divider>
      </div>
      <span class="xxl-font th-font">01</span>
    </div>
    <div class="th-font">최근 6개월 동안 읽은 기사 수를 보여드려요.</div>
    <div>
      <canvas ref="barChart" height="300"></canvas>
    </div>
  </div>
</template>

<script>
import { Chart, registerables } from "chart.js";
Chart.register(...registerables);
import { mapGetters, mapState } from "vuex";

export default {
  computed: {
    ...mapGetters({
      getLastSixMonthsReadNews: "userStore/getLastSixMonthsReadNews",
    }),
    ...mapState("newsStore", ["articleList"]),
  },
  mounted() {
    this.createChart();
  },
  // watch: {
  //   // Watch for changes in readCategoryList
  //   readCategoryList: {
  //     handler() {
  //       this.$nextTick(() => {
  //         if (this.$refs.barChart) {
  //           this.createChart();
  //         }
  //       });
  //     },
  //     immediate: true, // Run the handler immediately after the watcher is created
  //   },
  // },
  methods: {
    createChart() {
      // console.log("##1 ", this.articleCntList);
      const chartData = this.articleList.articleCntList;
      const labels = chartData.map(([month]) => {
        const date = new Date(`${month}-01`);
        return `${date.getMonth() + 1}월`;
      });
      const data = chartData.map(([, readNewsCount]) => readNewsCount);
      const colors = [
        ["rgba(102, 134, 250, 1)", "rgba(102, 134, 250, 0.8)"],
        ["rgba(128, 136, 251, 1)", "rgba(128, 136, 251, 0.8)"],
        ["rgba(136, 125, 250, 1)", "rgba(136, 125, 250, 0.8)"],
        ["rgba(157, 126, 251, 1)", "rgba(157, 126, 251, 0.8)"],
        ["rgba(163, 103, 250, 1)", "rgba(163, 103, 250, 0.8)"],
        ["rgba(201, 116, 242, 1)", "rgba(201, 116, 242, 0.8)"],
      ];
      const bg_colors = [
        ["rgba(102, 134, 250, 0.1)", "rgba(102, 134, 250, 0.2)"],
        ["rgba(128, 136, 251, 0.1)", "rgba(128, 136, 251, 0.2)"],
        ["rgba(136, 125, 250, 0.1)", "rgba(136, 125, 250, 0.2)"],
        ["rgba(157, 126, 251, 0.1)", "rgba(157, 126, 251, 0.2)"],
        ["rgba(163, 103, 250, 0.1)", "rgba(163, 103, 250, 0.2)"],
        ["rgba(201, 116, 242, 0.1)", "rgba(201, 116, 242, 0.2)"],
      ];

      new Chart(this.$refs.barChart, {
        type: "bar",
        data: {
          labels,
          datasets: [
            {
              label: "읽은 기사 수",
              data,
              backgroundColor: (context) => {
                const index = context.dataIndex;
                const chart = context.chart;
                const gradient = chart.ctx.createLinearGradient(
                  0,
                  chart.height,
                  0,
                  0
                );
                gradient.addColorStop(0, colors[index][0]);
                gradient.addColorStop(1, colors[index][1]);
                return gradient;
              },
              borderWidth: 0,
              borderRadius: {
                topLeft: 15,
                topRight: 15,
                bottomLeft: 15,
                bottomRight: 15,
              },
              borderSkipped: false,
              categoryPercentage: 1,
              barPercentage: 1,
            },
            {
              data: chartData.map(() => Math.max(...data)),
              backgroundColor: (context) => {
                const index = context.dataIndex;
                const chart = context.chart;
                const gradient = chart.ctx.createLinearGradient(
                  0,
                  chart.height,
                  0,
                  0
                );
                gradient.addColorStop(0, bg_colors[index][0]);
                gradient.addColorStop(1, bg_colors[index][1]);
                return gradient;
              },
              borderRadius: {
                topLeft: 15,
                topRight: 15,
                bottomLeft: 15,
                bottomRight: 15,
              },
              borderWidth: 0,
              categoryPercentage: 1,
              barPercentage: 1,
            },
          ],
        },
        options: {
          maxBarThickness: 35,
          plugins: {
            legend: {
              display: false,
            },
          },
          scales: {
            y: {
              display: false,
              beginAtZero: true,
              grid: {
                display: false,
              },
              ticks: {
                display: false,
                padding: 10,
                color: "transparent",
              },
            },
            x: {
              grid: {
                display: false,
              },
              stacked: true,
              ticks: {
                font: {
                  size: 20,
                },
                color: "rgba(0, 0, 0, 0.8)",
                padding: 10,
              },
              color: "transparent",
              borderColor: "transparent",
              borderWidth: 0,
            },
          },
          elements: {
            bar: {
              barPercentage: 0.5,
            },
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
