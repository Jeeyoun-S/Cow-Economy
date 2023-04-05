<template>
  <div>
    <!-- 오른쪽 페이지 버튼 -->
    <div class="pagination">
      <div class="dot selected"></div>
      <div class="dot"></div>
      <div class="dot"></div>
      <div class="dot"></div>
      <div class="dot"></div>
    </div>
    <!-- 화면 중앙 아래 다음 페이지로 이동하는 버튼 -->
    <v-btn
      class="next-icon"
      icon
      large
      color="var(--main-col-1)"
      @click="nextFlicking()"
    >
      <v-icon x-large>mdi-chevron-down</v-icon>
    </v-btn>
    <!-- 왼쪽 상단 페이지로 이동하는 로고 (첫 페이지 제외) -->
    <img
      style="cursor: pointer"
      @click="moveToHome()"
      class="top-logo"
      src="@/assets/images/logo/logo.png"
    />
    <!-- 배경에서 움직이는 동그라미들 -->
    <div class="background">
      <div class="shapes">
        <div class="circle c1"></div>
        <div class="circle c2"></div>
        <div class="circle c3"></div>
        <div class="circle c4"></div>
        <div class="circle c5"></div>
        <div class="circle c6"></div>
        <div class="circle c7"></div>
      </div>
      <div class="background background2"></div>
    </div>
    <!-- 5개의 페이지 -->
    <Flicking class="flicking" ref="flicking" :options="flickingOptions">
      <!-- page 1 -->
      <div class="eg-flick-viewport">
        <div class="eg-flick-camera">
          <div class="page main">
            <div class="wheel"></div>
            <div
              class="container d-flex flex-column justify-center align-center"
            >
              <!-- 첫 페이지 등장 효과 -->
              <div class="line-top"></div>
              <div class="motion">
                <div class="glitter glitter1"></div>
                <div class="glitter glitter2"></div>
                <div class="glitter glitter3"></div>
                <div class="glitter glitter4"></div>
                <div class="glitter glitter5"></div>
                <div class="glitter glitter6"></div>
                <div class="glitter glitter7"></div>
                <div class="glitter glitter8"></div>
                <div class="circle circle1"></div>
                <div class="circle circle2"></div>
              </div>

              <!-- 첫 페이지 로고 및 내용 -->
              <img
                class="logo"
                height="120px"
                :src="require('@/assets/images/logo/logo_full.png')"
              />
              <div class="detail d-flex flex-column align-center">
                <div class="title">
                  <span>소 귀에 경제 읽기</span>
                  <!-- <div class="cursor"></div> -->
                </div>
                <span class="description">경제 기사를 읽으며</span>
                <span class="description">경제에 대해 공부하는 서비스</span>
                <MainHeaderLogin class="point-th mt-3"></MainHeaderLogin>
                <v-btn
                  class="point-th px-10 mt-3"
                  elevation="0"
                  color="var(--main-col-1)"
                  to="/home"
                  dark
                  rounded
                  outlined
                  style="background-color: white"
                >
                  로그인 없이 시작하기
                </v-btn>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- page 2 -->
      <div class="eg-flick-viewport">
        <div class="eg-flick-camera">
          <div class="page main news">
            <div class="wheel"></div>
            <div
              class="container d-flex flex-column justify-center align-center"
            >
              <div
                class="func-1 point-md main-col-1 xxxxl-font"
                style="height: 40px"
              ></div>
              <div class="d-flex flex-column align-center">
                <div class="d-flex flex-wrap align-center justify-center">
                  <v-chip
                    class="chip-1 mr-1 my-1 px-4"
                    color="var(--graph-1-col-3)"
                    outlined
                    style="background-color: white !important"
                    >인기 뉴스</v-chip
                  >
                  <v-chip
                    class="chip-1 mr-1 my-1 px-4"
                    color="var(--graph-1-col-1)"
                    outlined
                    style="background-color: white !important"
                    >최신 트렌드</v-chip
                  ><v-chip
                    class="chip-1 mr-1 my-1 px-4"
                    color="var(--graph-1-col-5)"
                    outlined
                    style="background-color: white !important"
                    >최신 뉴스</v-chip
                  >
                </div>
                <div class="d-flex flex-wrap align-center justify-center">
                  <v-chip
                    class="chip-1 mr-1 my-1 px-4"
                    color="var(--graph-1-col-4)"
                    outlined
                    style="background-color: white !important"
                    >카테고리별 뉴스</v-chip
                  >

                  <v-chip
                    class="chip-1 px-4 my-1"
                    color="var(--graph-1-col-6)"
                    outlined
                    style="background-color: white !important"
                    >키워드 검색</v-chip
                  >
                </div>
              </div>
              <v-sheet height="55vh"></v-sheet>
              <div class="phone">
                <div class="head"></div>
                <div class="container">
                  <Flicking
                    class="panels"
                    :options="gap10Options"
                    :plugins="plugins"
                  >
                    <div class="panel">
                      2월 회사채 발행 19% 증가한 20조원…주식 발행은 84% 급감
                    </div>
                    <div class="panel">
                      “주식 사라” vs “아직 아냐”…증권사 애널리스트 격돌
                    </div>
                    <div class="panel">
                      비트코인, 바이낸스 악재에 하락..."2만5천달러까지 떨어질
                      수도" [코인브리핑]
                    </div>
                    <div class="panel">
                      비트코인 1분기 72% 급등…2년 만에 최대 상승폭
                    </div>
                    <div class="panel">
                      "예비신랑, 삼성전자에 물렸습니다"…결혼 앞둔 여성들 '멘붕'
                      [박의명의 불개미 구조대]
                    </div>
                    <div class="panel">
                      주총 온라인 생중계...주주가치 제고 나선 기업은
                    </div>
                  </Flicking>
                </div>
                <div class="home"></div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- page 3 -->
      <div class="eg-flick-viewport">
        <div class="eg-flick-camera">
          <div class="page main">
            <div
              class="container d-flex flex-column justify-center align-center"
            >
              <v-sheet
                height="70"
                width="90%"
                color="transparent"
                class="mb-2 d-flex flex-column justify-end align-start"
              >
                <span class="white-col-1 point-md xxxxl-font pink-gradient"
                  >단어</span
                >
                <span class="graph-2-col-4 md-font"
                  >기사 속 밑줄 친 경제 용어를 클릭해보세요!</span
                >
                <span class="graph-2-col-4 md-font"
                  >해당 용어에 대한 설명을 볼 수 있습니다.</span
                >
              </v-sheet>
              <v-sheet class="sample pa-4 sm-font" rounded="lg" width="90%">
                남양유업은 지난 2013년 대리점 물량 밀어내기 갑질 논란, 2021년
                불가리스 사태, 창업주 3세들의 마약 투약과 최근 한앤컴퍼니와의
                경영권 분쟁 등의 사건으로 인해 주가가 지속적으로 하락세다.
                실적도 부진하다.
                <span style="position: relative">
                  <v-icon
                    style="position: absolute; left: 70%; top: 60%"
                    color="black"
                    large
                    >mdi-cursor-default</v-icon
                  >
                  <span class="underline">영업손실</span>
                </span>
                은 868억원을 내,
                <span class="memo-sample">적자폭을 11.5%</span> 늘렸고
                당기순손실은 781억원을 냈다.
              </v-sheet>
              <v-sheet
                height="70"
                width="90%"
                color="transparent"
                class="mt-2 d-flex flex-column justify-start align-end"
              >
                <span class="white-col-1 point-md xxxxl-font pink-gradient"
                  >메모</span
                >
                <span class="graph-2-col-4 md-font"
                  >기사 내용을 드래그해 메모를 써보세요!</span
                >
                <span class="graph-2-col-4 md-font"
                  >선택한 내용을 메모에 추가하는 창이 하단에 뜹니다.</span
                >
              </v-sheet>
            </div>
          </div>
        </div>
      </div>
      <!-- page 4 -->
      <div class="eg-flick-viewport">
        <div class="eg-flick-camera">
          <div class="page main">
            <div
              class="container d-flex flex-column justify-center align-center"
            >
              <v-sheet
                class="mb-10 d-flex flex-column justify-center align-center"
                color="transparent"
                width="80%"
              >
                <span
                  class="graph-1-col-4 point-md xxxxl-font main-col-1 white-2-gradient"
                  >오늘의 Quiz</span
                >
                <span class="sm-font graph-1-col-4-1 md-font white-2-gradient"
                  >읽었던 기사 속 단어를 맞혀보세요.</span
                >
                <div class="d-flex flex-column justify-center align-center">
                  <v-chip
                    class="px-7 my-1 white-2-gradient"
                    color="var(--graph-1-col-4)"
                    outlined
                    style="background-color: white !important"
                    >15초 내에 보기 4개 중 답을 선택</v-chip
                  >
                  <v-chip
                    class="px-7 my-1 white-2-gradient"
                    color="var(--graph-1-col-4)"
                    outlined
                    style="background-color: white !important"
                    >7문제 중 5문제 이상 맞히면 경험치 획득</v-chip
                  >
                </div>
              </v-sheet>
              <v-sheet
                class="d-flex flex-column justify-space-between align-center"
                color="transparent"
                width="80%"
              >
                <span class="white-2-gradient point-md xxxxl-font graph-1-col-2"
                  >나의 활동 그래프</span
                >
                <span class="sm-font graph-1-col-2-1 md-font"
                  >내가 읽은 기사를 통계로 알아볼 수 있습니다.</span
                >
                <div class="pb-3 d-flex flex-row">
                  <Flicking class="charts" :options="options">
                    <v-sheet
                      rounded="lg"
                      width="180"
                      class="graph-gradient my-2 mr-3 pa-2 d-flex flex-column align-center justify-space-between"
                    >
                      <span class="my-2 point-th">읽은 기사 수</span>
                      <canvas ref="barChart"></canvas>
                    </v-sheet>
                    <v-sheet
                      rounded="lg"
                      width="180"
                      class="graph-gradient my-2 mr-3 pa-2 d-flex flex-column align-center justify-space-between"
                    >
                      <span class="my-2 point-th">읽은 기사의 카테고리</span>
                      <canvas
                        ref="doughnutChart"
                        style="
                          height: 180px !important;
                          width: 180px !important;
                        "
                      ></canvas>
                    </v-sheet>
                    <v-sheet
                      rounded="lg"
                      width="180"
                      class="graph-gradient mr-3 my-2 pa-2 d-flex flex-column align-center justify-start"
                    >
                      <span class="my-2 point-th">경제 용어의 카테고리</span>
                      <canvas ref="radarChart"></canvas>
                    </v-sheet>
                  </Flicking>
                </div>
              </v-sheet>
            </div>
          </div>
        </div>
      </div>
      <!-- page 5 -->
      <div class="eg-flick-viewport">
        <div class="eg-flick-camera">
          <div class="page main">
            <div
              class="container d-flex flex-column justify-center align-center"
            >
              <div class="d-flex flex-column point-th align-center white-col-1">
                <span class="xxl-font pink-gradient">지금 바로</span>
                <span class="xxl-font pink-gradient"
                  ><span class="point-md xxl-font">소귀경</span>에 접속해</span
                >
                <span class="xxl-font pink-gradient"
                  >경제 공부를 시작해보세요!</span
                >
                <MainHeaderLogin class="mt-3"></MainHeaderLogin>
                <v-btn
                  class="px-10 mt-3"
                  elevation="0"
                  color="var(--main-col-1)"
                  to="/home"
                  dark
                  rounded
                  outlined
                  style="background-color: white"
                >
                  로그인 없이 시작하기
                </v-btn>
              </div>
            </div>
          </div>
        </div>
      </div>
    </Flicking>
    <!-- 맨 마지막 페이지에 들어가는 팀 설명 -->
    <div
      class="team d-flex flex-column justify-center align-center main-col-1 white-gradient sm-font"
    >
      <div class="point-th">SSAFY 8기 A509 오난쥉이조</div>
      <div class="point-th">엄희정 민동주 서지윤 손승환 신도연</div>
    </div>
  </div>
</template>

<script>
import { Flicking } from "@egjs/vue-flicking";
import Scene from "scenejs";
import { typing } from "@scenejs/effects";
import MainHeaderLogin from "@/components/User/MainHeaderLogin.vue";
import { AutoPlay } from "@egjs/flicking-plugins";
import { Chart } from "chart.js";

export default {
  name: "FlickingTest",
  components: {
    Flicking,
    MainHeaderLogin,
  },
  data() {
    return {
      flickingOptions: {
        horizontal: false,
        autoResize: true,
        duration: 700,
      },
      gap10Options: {
        circular: true,
        gap: 10,
      },
      plugins: [
        new AutoPlay({ duration: 1500, direction: "NEXT", stopOnHover: false }),
      ],
      options: {},
      snackbarSample: true,
    };
  },
  methods: {
    nextFlicking() {
      this.$refs.flicking.next();
    },
    moveToHome() {
      this.$router.push("/home");
    },
  },
  mounted() {
    const flicking = this.$refs.flicking;
    const pagination = document.querySelector(".pagination");
    const dots = [].slice.call(pagination.querySelectorAll(".dot"));

    dots.forEach((dot, i) => {
      dot.addEventListener("click", () => {
        this.$refs.flicking.stopAnimation();
        this.$refs.flicking.moveTo(i);
      });
    });

    this.$refs.flicking.on("willChange", (e) => {
      const index = e.index;

      dots[flicking.index].classList.remove("selected");
      dots[index].classList.add("selected");
    });

    const scene = new Scene(
      {
        ".c1": {
          0: {
            right: "45%",
            transform: "translate(0%, 0vh) translateY(0%) scale(1)",
          },
          1: {
            right: "60%",
            transform: "translate(0%, 50vh) translateY(100%) scale(1)",
          },
          1.5: {
            transform:
              "translate(40%, 20vh) translateX(0vw) translateY(0%) scale(10)",
          },
          1.8: {
            transform:
              "translate(40%, 20vh) translateX(0vw) translateY(0%) scale(50)",
          },
          2: {
            right: "85%",
            transform:
              "translate(40%, 20vh) translateX(0vw) translateY(0%) scale(100)",
          },
          2.3: {
            right: "10%",
            transform:
              "translateX(7vw) translate(40%, 68vh) translateY(50%) scale(50)",
          },
          2.8: {
            right: "10%",
            transform:
              "translateX(7vw) translate(40%, 68vh) translateY(50%) scale(5)",
          },
          3: {
            right: "10%",
            transform:
              "translateX(7vw) translate(40%, 68vh) translateY(50%) scale(0.5)",
          },
          3.5: {
            transform:
              "translate(40%, 20vh) translateX(0vw) translateY(0%) scale(10)",
          },
          3.8: {
            transform:
              "translate(40%, 20vh) translateX(0vw) translateY(0%) scale(50)",
          },
          4: {
            right: "85%",
            transform:
              "translate(40%, 20vh) translateX(0vw) translateY(0%) scale(100)",
          },
        },
        ".c2": {
          0.4: {
            transform: "translate(-50%, -50%) scale(1)",
          },
          1: {
            transform: "scale(0)",
          },
        },
        ".c3": {
          0.5: {
            width: "78vw",
            "max-width": "350px",
          },
          0.8: {
            "border-radius": "50%",
            "max-width": "120vmax",
          },
          1: {
            width: "120vmax",
            "max-width": "120vmax",
            "border-radius": "0%",
            transform: "translate(-50%, -50%) translateY(0vh)",
          },
          2: {
            transform: "translateY(-100vh) translate(-50%, -50%) scale(1)",
            "border-radius": "0%",
          },
          2.2: {
            "border-radius": "50%",
          },
          3: {
            width: "40vmax",
            transform: "translate(-65%, -40%) translateY(0vh) scale(0.5)",
          },
        },
        ".c4": {
          0: {
            transform: "translate(0%, 0vh) translateY(0%)",
            left: "45%",
          },
          1: {
            transform: "translate(0%, -50vh) translateY(-80%)",
            left: "60%",
          },
          2: {
            transform: "translate(0%, -100vh) translateY(-100%)  scale(1)",
            left: "85%",
          },
          3: {
            left: "57%",
            transform: "translate(-35%, 0vh) translateY(10%) scale(0.27)",
          },
        },
        ".c5": {
          0: {
            transform: "translate(0, 0%)",
            opacity: 1,
            "border-color": "var(--quiz-1-col-4)",
          },
          1: {
            transform: "translate(0, -150%)",
            "border-color": "var(--main-col-5)",
          },
          1.5: {
            transform: "translate(0, -20%)",
            "z-index": 1,
          },
          3: {
            transform: "translate(0, -20%) scale(1)",
          },
          4: {
            transform: "translate(0, -20%) scale(2)",
          },
        },
        ".c6": {
          0: {
            transform: "translate(0, 0%) translate2(0vw, 0vh)",
            "border-color": "var(--quiz-1-col-6)",
            opacity: 1,
          },
          1: {
            opacity: 0,
            "border-color": "var(--main-col-5)",
          },
          1.5: {
            transform: "translate(0, 45%) translate2(0vw, 0vh) scale(1)",
            opacity: 1,
            "z-index": 1,
          },
          3: {
            transform: "translate(0, 45%) translate2(0vw, 0vh) scale(1)",
          },
          4: {
            transform: "translate(0, 45%) translate2(0vw, 0vh) scale(2)",
          },
        },
        ".background2": {
          1: {
            transform: "translateY(100vh)",
          },
          2: {
            transform: "translateY(0vh)",
          },
          3: {
            transform: "translateY(-100vh)",
          },
        },
        ".feature": (i) => ({
          0.9: {
            transform: "translate(-50%, -50%) translate2(0px, 0px)",
            opacity: 0,
          },
          1: {
            transform: `translate2(${(i % 2 ? 1 : -1) * 220}px, ${
              (Math.floor(i / 2) - 1) * 120
            }px)`,
            opacity: 1,
          },
          2: {
            opacity: 0,
          },
        }),
        ".c7": {
          1: {
            transform: "translate(0%, 0%)",
            top: "100%",
          },
          2: {
            transform: "translate(0%, 0%) scale(1)",
            top: "70%",
          },
          3: {
            top: "40%",
            transform: "translate(-65%, -70%) scale(0.3)",
          },
          // 4: {},
        },
        ".next-icon": {
          0: {
            opacity: 1,
            "z-index": 2080,
          },
          3.5: {
            opacity: 1,
            "z-index": -100,
          },
        },
        ".top-logo": {
          0: {
            transform: "scale(0)",
          },
          0.8: {
            transform: "scale(1)",
          },
        },
        ".chip-1": {
          0: {
            opacity: 0,
          },
          1: {
            opacity: 1,
          },
          2: {
            opacity: 0,
          },
        },
        ".team": {
          3: {
            transform: "scale(0)",
          },
          4: {
            transform: "scale(1)",
          },
        },
      },
      {
        selector: true,
      }
    ).setTime(0);

    this.$refs.flicking.on("move", () => {
      scene.setTime(flicking.camera.progress);
      if (flicking.camera.progress == 1) {
        new Scene(
          {
            ".func-1": typing({
              text: "다양한 경제 뉴스 보기.",
              duration: 1,
              end: 13,
            }),
          },
          {
            direction: "alternate",
            selector: true,
          }
        ).play();
      } else if (flicking.camera.progress == 2) {
        new Scene(
          {
            ".underline": {
              0: {
                color: "rgba(0, 0, 0, 0.87)",
                "font-family": "var(--main-font-2)",
              },
              0.8: {
                color: "var(--main-col-1)",
                "font-family": "var(--main-font-5)",
              },
              1.4: {
                color: "var(--main-col-1)",
                "font-family": "var(--main-font-5)",
              },
            },
            ".memo-sample": {
              0: {
                "background-color": "transparent",
                color: "black",
              },
              0.8: {
                "background-color": "var(--main-col-2)",
                color: "white",
              },
              1.4: {
                "background-color": "var(--main-col-2)",
                color: "white",
              },
            },
          },
          {
            iterationCount: "infinite",
            direction: "alternate",
            selector: true,
          }
        ).playCSS();
      }
    });

    new Scene(
      {
        ".wheel": {
          0: {
            transform: "translate(-50%, -90px)",
            height: "0px",
          },
          0.5: {
            height: "30px",
          },
          1: {
            height: "0px",
            transform: "translate(-50%, 0px)",
          },
        },
      },
      {
        selector: true,
        easing: "ease-in-out",
        iterationCount: "infinite",
      }
    ).playCSS();

    new Scene(
      {
        ".line-top": {
          0: {
            transform: "translateY(-100%) scaleY(1)",
          },
          0.3: {
            transform: "translateY(0%) scaleY(0)",
          },
          options: {
            easing: "ease-in",
          },
        },
        ".circle1": {
          0: {
            "border-width": "100px",
            transform: "scale(0)",
          },
          0.7: {
            opacity: 1,
          },
          0.9: {
            "border-width": "0px",
            transform: "scale(1)",
            opacity: 0,
          },
          options: {
            delay: 0.5,
          },
        },
        ".circle2": {
          0: {
            "border-width": "200px",
            transform: "scale(0)",
          },
          0.1: {
            opacity: 1,
          },
          0.7: {
            "border-width": "0px",
            transform: "scale(1)",
            opacity: 0,
          },
          1.8: 1,
          options: {
            delay: 1.1,
          },
        },
        ".glitter": (i) => ({
          0: {
            width: "0px",
            transform: {
              rotate: `${(360 / 8) * i}deg`,
              translate: "0px",
              scaleX: 1,
            },
            opacity: 0,
          },
          0.3: {
            width: "50px",
            opacity: 1,
          },
          0.8: {
            width: "0px",
            transform: {
              translate: "100px",
              scaleX: 0,
            },
            opacity: 0,
          },
          options: {
            delay: 0.5,
          },
        }),
        ".logo": {
          0: {
            opacity: 0,
            transform: "scale(0.3)",
            top: "-100px",
            bottom: "-100px",
          },
          0.1: {
            opacity: 1,
          },
          0.8: {
            opacity: 1,
          },
          1: {
            transform: "translateY(0%) scale(1)",
            opacity: 1,
          },
          2: {
            transform: "translateY(-105%)  scale(1)",
          },
          options: {
            delay: 1.3,
          },
        },
        ".detail": {
          0: {
            opacity: 0,
          },
          2.3: {
            opacity: 1,
          },
          options: {
            delay: 2.3,
          },
        },
      },
      {
        easing: "ease-out",
        selector: true,
        iterationCount: "1",
      }
    ).playCSS();

    new Scene(
      {
        ".next-icon": {
          0: {
            transform: "translateY(100%)",
          },
          1: {
            transform: "translateY(-100%)",
          },
        },
      },
      {
        selector: true,
        iterationCount: "infinite",
      }
    ).play();

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
        labels: ["1월", "2월", "3월", "4월", "5월", "6월"],
        datasets: [
          {
            label: "읽은 기사 수",
            data: [5, 2, 7, 1, 3, 8],
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
            data: [8, 8, 8, 8, 8, 8],
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
            borderSkipped: false,
            categoryPercentage: 1,
            barPercentage: 1,
          },
        ],
      },
      options: {
        aspectRatio: 1,
        maxBarThickness: 20,
        plugins: {
          legend: {
            display: false,
          },
          tooltip: {
            enabled: true,
            callbacks: {
              label: function (context) {
                if (context.datasetIndex === 0) {
                  return context.dataset.label + ": " + context.parsed.y;
                } else if (context.datasetIndex === 1) {
                  return null;
                }
              },
            },
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
                size: 13,
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

    new Chart(this.$refs.doughnutChart, {
      type: "doughnut",
      data: {
        labels: [
          "금융",
          "증권",
          "산업/재계",
          "중기/벤처",
          "부동산",
          "글로벌 경제",
          "생활경제",
          "경제 일반",
        ],
        datasets: [
          {
            data: [2, 3, 4, 5, 6, 7, 8, 9],
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
        maintainAspectRatio: false,
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
                size: 10,
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

    new Chart(this.$refs.radarChart, {
      type: "radar",
      data: {
        labels: [
          "금융",
          "증권",
          "산업/재계",
          "중기/벤처",
          "부동산",
          "글로벌 경제",
          "생활경제",
          "경제 일반",
        ],
        datasets: [
          {
            label: "",
            data: [5, 2, 4, 6, 7, 5, 8, 9],
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
                size: 10, // 항목 글씨 크기 변경
                // family: getComputedStyle(document.documentElement)
                //   .getPropertyValue("--main-font-1")
                //   .trim(),
              },
            },
          },
        },
      },
    });

    let isEnableScroll = true;
    let timerId = 0;

    function setScrollTimer() {
      isEnableScroll = false;
      if (timerId) {
        clearTimeout(timerId);
      }
      timerId = setTimeout(function () {
        isEnableScroll = true;
      }, 600);
    }
    function moveFlickingPage(e) {
      var link = document.location.href;
      if (link != "http://localhost:3000/") {
        window.removeEventListener("wheel", moveFlickingPage);
        return;
      }

      if (!isEnableScroll || flicking.animating) {
        return;
      }
      e.preventDefault();
      var delta = e.deltaY;

      if (Math.abs(delta) > 40) {
        if (delta > 0 && flicking.index < 4) {
          setScrollTimer();
          console.log("여기 후보 2", flicking.panelCount);
          flicking.next();
        } else if (delta < 0 && flicking.index > 0) {
          flicking.prev();
        }
      }
    }
    window.addEventListener("wheel", moveFlickingPage, {
      passive: false,
    });
  },
};
</script>

<style scoped>
/* 첫 페이지 서비스 제목 */
.title {
  margin: 10px;
}
.team {
  position: absolute;
  bottom: 5%;
  width: 300px;
  z-index: 2080;
  left: 50%;
  margin-left: -150px;
}
.top-logo {
  position: absolute;
  margin: 12px;
  height: 42px;
  z-index: 2080;
}
.next-icon {
  width: 44px;
  position: absolute;
  bottom: 5%;
  left: 50%;
  margin-left: -22px; /* width의 50% */
  z-index: 2080;
}
.title span {
  font-size: 30px;
  color: var(--main-col-1);
  font-family: var(--point-font-2);
}
.word {
  position: absolute;
  top: -29%;
  left: 28%;
  transform: "translate(0%, 0%) scale(0)";
}
/* 첫 페이지 서비스 로고 등장 효과 */
.line-top {
  position: absolute;
  bottom: 50%;
  left: 0;
  right: 0;
  width: 2px;
  height: 70%;
  transform-origin: 50% 100%;
  background: var(--main-col-3);
  margin: auto;
  transform: translateY(-100%);
}
.motion {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  margin: auto;
  width: 100%;
  height: 50%;
  max-width: 600px;
  max-height: 600px;
}
.motion .circle {
  position: absolute;
  width: 140px;
  height: 140px;
  border-radius: 50%;
  box-sizing: border-box;
  border: 70px solid var(--main-col-3);
  left: -100px;
  right: -100px;
  top: -100px;
  bottom: -100px;
  margin: auto;
  opacity: 0;
}
.motion .circle2 {
  width: 300px;
  height: 300px;
}
.motion .glitter {
  position: absolute;
  left: 50%;
  top: 50%;
  width: 100px;
  height: 2px;
  background: var(--main-col-3);
  transform-origin: 0px 1px;
}
.detail {
  margin-top: 60px;
}
.logo {
  position: absolute;
  left: -100px;
  right: -100px;
  top: -100px;
  bottom: -100px;
  margin: auto;
}
.ratio13:before {
  content: "" !important;
  position: relative !important;
  display: block !important;
  width: 100% !important;
  padding-top: 130% !important;
}
.ratio062:before {
  content: "" !important;
  position: relative !important;
  display: block !important;
  width: 100% !important;
  padding-top: 66% !important;
}
html,
body,
.flicking,
.background {
  position: relative !important;
  width: 100% !important;
  height: 100% !important;
  margin: 0 !important;
  padding: 0 !important;
}
.flicking,
.background {
  position: absolute !important;
  overflow: hidden !important;
}

.background .rect,
.background .circle,
.background .star,
.background .triangle {
  position: absolute !important;
}
.background .circle {
  border-radius: 50%;
}
.background .circle:before {
  content: "" !important;
  position: relative !important;
  display: block !important;
  width: 100% !important;
  padding-top: 100% !important;
}
/* 메인페이지 왼쪽 위 */
.c1 {
  width: 60vmax;
  /* min-width: 1000px; */
  bottom: 80%;
  right: 45%;
  background: var(--quiz-1-col-1);
  z-index: 1;
}
/* 메인페이지 오른쪽 아래 */
.c4 {
  width: 60vmax;
  min-width: 1000px;
  top: 65%;
  left: 45%;
  background: var(--quiz-1-col-2);
  z-index: 1;
}
/* 메인페이지 오른쪽 위 */
.c5 {
  width: 200px;
  bottom: 72%;
  left: 75%;
  border: 30px solid var(--quiz-1-col-4);
}
/* 메인페이지 왼쪽 아래 */
.c6 {
  width: 200px;
  top: 70%;
  right: 70%;
  border: 40px solid var(--quiz-1-col-6);
}
.c7 {
  width: 60vmax;
  min-width: 1000px;
  left: 50%;
  top: 80%;
  background: var(--quiz-1-col-3);
}
.r1 {
  width: 20px !important;
  height: 20px !important;
  top: 90% !important;
  right: 65% !important;
  border: 3px solid #7095dd !important;
  border-radius: 2px !important;
}
.t1 {
  width: 22px !important;
  bottom: 90% !important;
  left: 65% !important;
}
.phone {
  position: absolute;
  width: 60vw;
  max-width: 250px;
  height: 50vh;
  border-radius: 20px;
  background: white;
  box-shadow: 0px 0px 8px 0px rgba(100, 100, 100, 0.4);
  left: 50%;
  transform: translate(-50%, 60px);
  margin: auto;
  transform-origin: bottom;
}
.phone .head {
  position: absolute;
  width: 36px;
  height: 8px;
  top: 22px;
  left: 0;
  right: 0;
  border-radius: 4px;
  margin: auto;
  background: #ddd;
}
.phone .container {
  position: absolute;
  width: calc(100% - 16px);
  height: calc(100% - 100px);
  background: transparent;
  border: 2px solid #ddd;
  background: white;
  position: absolute;
  border-radius: 5px;
  left: 0;
  right: 0;
  margin: auto;
  top: 10px;
  padding: 0px;
}
.phone .home {
  position: absolute;
  width: 33px;
  height: 33px;
  border: 2px solid #ccc;
  background: white;
  border-radius: 50%;
  bottom: 6px;
  left: 0;
  right: 0;
  margin: auto;
}
.panels {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  width: 100%;
  height: 180px !important;
}
.panels .panel {
  display: flex;
  align-items: flex-end;
  width: 75%;
  height: 100%;
  border-radius: 10px;
  margin-right: 8px;
  background-size: cover;
  padding: 10px;
  font-size: 12px;
  color: white;
  background-image: linear-gradient(to left, red, yellow);
}
.panels .panel:nth-child(6n + 1) {
  background-image: linear-gradient(to bottom, transparent, black),
    url("https://imgnews.pstatic.net/image/032/2023/03/28/0003213554_001_20230328074601106.jpg?type=w647");
}
.panels .panel:nth-child(6n + 2) {
  background-image: linear-gradient(to bottom, transparent, black),
    url("https://imgnews.pstatic.net/image/018/2023/03/28/0005450748_001_20230328074402156.jpg?type=w647");
}
.panels .panel:nth-child(6n + 3) {
  background-image: linear-gradient(to bottom, transparent, black),
    url("https://imgnews.pstatic.net/image/014/2023/03/28/0004987742_001_20230328101902374.jpg?type=w647");
}
.panels .panel:nth-child(6n + 4) {
  background-image: linear-gradient(to bottom, transparent, black),
    url("https://imgnews.pstatic.net/image/277/2023/04/02/0005239612_001_20230402082201352.jpg?type=w647");
}
.panels .panel:nth-child(6n + 5) {
  background-image: linear-gradient(to bottom, transparent, black),
    url("https://imgnews.pstatic.net/image/015/2023/04/01/0004827547_001_20230401134201025.jpg?type=w647");
}
.panels .panel:nth-child(6n) {
  background-image: linear-gradient(to bottom, transparent, black),
    url("https://imgnews.pstatic.net/image/014/2023/03/28/0004987841_001_20230328115902063.jpg?type=w647");
}
.background2 {
  background: #9c5dea !important;
  z-index: -1 !important;
  transform: translateY(100vh) !important;
}
.flicking .eg-flick-viewport,
.flicking .eg-flick-camera,
.flicking .page {
  position: relative;
  width: 100%;
  height: 100%;
}
.flicking .page.main .container {
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}
.flicking .page.main .description {
  margin: 0px;
  color: var(--main-col-1);
}
.pagination {
  position: absolute;
  right: 0;
  top: 50%;
  transform: translateY(-50%);
  z-index: 2080;
}
.pagination .dot {
  position: relative;
  width: 10px;
  height: 10px;
  border-radius: 50%;
  margin: 10px 10px;
  transition: background-color ease 0.5s;
  cursor: pointer;
}
.pagination .dot {
  background: var(--main-col-4);
}
.pagination .dot.selected {
  background: var(--main-col-1);
}
.wheel {
  position: absolute;
  bottom: 110px;
  width: 2px;
  height: 10px;
  left: 50%;
  transform: translate(-50%, 0);
  background: #eee;
}
</style>
