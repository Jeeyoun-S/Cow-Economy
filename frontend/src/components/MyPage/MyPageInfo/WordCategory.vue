<template>
  <div class="px-1 mt-6">
    <div class="d-flex align-center">
      <span class="xl-font">경제 용어의 카테고리</span>
      <!-- <div class="horizontal-divider"> -->
      <v-divider class="mx-2"></v-divider>
      <!-- </div> -->
      <span class="xl-font th-font">03</span>
    </div>
    <div class="mb-3 th-font sm-font">
      오늘의 Quiz에서 맞춘 용어의 카테고리를 보여드립니다.
    </div>
    <div v-if="hasData">
      <div
        class="d-flex justify-space-between align-center pt-2 px-3 xxxl-font"
      >
        <div x-large icon></div>
        <span class="xxl-font" style="color: #757575"
          >{{ this.currentYear }}년 {{ this.currentMonth }}월</span
        >
        <div x-large icon></div>
      </div>
      <canvas ref="radarChart"></canvas>
    </div>
    <InfoNoData v-else v-bind:child-value="msg"></InfoNoData>
  </div>
</template>

<script>
import { mapState } from "vuex";
import { Chart, registerables } from "chart.js";
import InfoNoData from "@/components/MyPage/MyPageInfo/InfoNoData.vue";

Chart.register(...registerables);
Chart.defaults.font.family = "MinSans-Regular";

export default {
  components: {
    InfoNoData,
  },
  data: function () {
    return {
      currentYear: null,
      currentMonth: null,
      msg: "퀴즈를 풀어주세요",
    };
  },
  computed: {
    ...mapState("userStore", ["articleList"]),
    hasData() {
      const wordCategoryList = this.articleList.wordCategoryList;
      // console.log(wordCategoryList)
      return wordCategoryList.some((value) => value[1] !== 0);
    },
  },
  created() {
    this.currentYear = new Date().getFullYear();
    this.currentMonth = new Date().getMonth() + 1;
  },
  mounted() {
    if (this.hasData) {
      this.drawRadarChart();
    }
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
              label: "",
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
                  // family: getComputedStyle(document.documentElement)
                  //   .getPropertyValue("--main-font-1")
                  //   .trim(),
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
