<template>
  <div>
    <div class="pagination">
      <div class="dot selected"></div>
      <div class="dot"></div>
      <div class="dot"></div>
      <div class="dot"></div>
      <div class="dot"></div>
    </div>
    <v-btn
      class="next-icon"
      icon
      large
      color="var(--main-col-1)"
      @click="nextFlicking()"
    >
      <v-icon x-large>mdi-chevron-down</v-icon>
    </v-btn>
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
    <Flicking class="flicking" ref="flicking" :options="flickingOptions">
      <div class="eg-flick-viewport">
        <div class="eg-flick-camera">
          <div class="page main">
            <div class="wheel"></div>
            <div
              class="container d-flex flex-column justify-center align-center"
            >
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
                <!-- <h1>소 귀<span>에</span> 경<span>제읽기</span></h1> -->
                <span class="description">경제 기사를 읽으며</span>
                <span class="description">경제에 대해 공부하는 서비스</span>
                <MainHeaderLogin class="mt-3"></MainHeaderLogin>
                <v-btn
                  class="px-10 mt-3"
                  elevation="0"
                  color="var(--main-col-1)"
                  to="/home"
                  dark
                  rounded
                  outlined
                >
                  로그인 없이 시작하기
                </v-btn>
              </div>
            </div>
          </div>
          <!-- <div class="page news">
            <div class="number"></div>
            <div class="feature">
              <h3>Infinite Flicking</h3>
              <p class="description">
                You can dynamically add panels to the flicking.
              </p>
            </div>
            <div class="feature">
              <h3>Free Scroll</h3>
              <p class="description">The panels are freely scrollable..</p>
            </div>
            <div class="feature">
              <h3>Variable Size</h3>
              <p class="description">
                You can place multiple panels and specify various sizes for each
                panel.
              </p>
            </div>
            <div class="feature">
              <h3>Flexible Align</h3>
              <p class="description">
                Use Hanger and Anchor to provide customizable alignment.
              </p>
            </div>
            <div class="feature">
              <h3>Progress</h3>
              <p class="description">
                Indicates the overall progress of the Flicking.
              </p>
            </div>
            <div class="feature">
              <h3>Bound</h3>
              <p class="description">
                Bound the panels so that they are not out of the flicking area.
              </p>
            </div>
          </div>
          <div class="page slogan">
            <div class="container">
              <h2>
                Everyday <span data-text="30"></span> million people experience.
                <br />It's reliable, flexible and extendable carousel.
              </h2>
            </div>
          </div>
          <div class="page more">
            <div class="container">
              <a href="https://github.com/naver/egjs-flicking" target="_blank"
                ><h2>LEARN&nbsp;<br />MORE&nbsp;</h2></a
              >
            </div>
          </div> -->
        </div>
      </div>
      <!-- <div class="iphone">
        <div class="head"></div>
        <div class="container">
          <Flicking class="panels" :options="gap10Options">
            <div class="panel">dd</div>
            <div class="panel"></div>
            <div class="panel"></div>
            <div class="panel"></div>
          </Flicking>
        </div>
        <div class="home"></div>
      </div> -->
      <div class="eg-flick-viewport">
        <div class="eg-flick-camera">
          <div class="page main news">
            <div class="wheel"></div>
            <div
              class="container d-flex flex-column justify-center align-center"
            >
              <!-- <div class="func-1 point-b main-col-1 xxxxl-font">
                다양한 경제 뉴스 보기
              </div> -->
              <div
                class="func-1 point-b main-col-1 xxxxl-font"
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
              <v-sheet height="450px"></v-sheet>
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
      <div class="eg-flick-viewport">
        <div class="eg-flick-camera">
          <div class="page main">
            <!-- <div class="wheel"></div> -->
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
                <span class="main-col-1 md-font"
                  >기사 속 밑줄 친 경제 용어를 클릭해보세요!</span
                >
                <span class="main-col-1 md-font"
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
                <span class="main-col-1 md-font"
                  >기사 내용을 드래그해 메모를 써보세요!</span
                >
                <span class="main-col-1 md-font"
                  >선택한 내용을 메모에 추가하는 창이 뜹니다</span
                >
              </v-sheet>
              <!-- <div>뉴스 상세보기</div>
              <div>단어 뜻 보기</div>
              <div>메모 남기기</div>
              <div>카카오톡 공유</div> -->
            </div>
          </div>
        </div>
      </div>
      <div class="eg-flick-viewport">
        <div class="eg-flick-camera">
          <div class="page main">
            <!-- <div class="wheel"></div> -->
            <div
              class="container d-flex flex-column justify-center align-center"
            >
              <div>퀴즈</div>
              <div>마이페이지 그래프</div>
            </div>
          </div>
        </div>
      </div>
      <div class="eg-flick-viewport">
        <div class="eg-flick-camera">
          <div class="page main">
            <!-- <div class="wheel"></div> -->
            <div
              class="container d-flex flex-column justify-center align-center"
            >
              <div class="detail d-flex flex-column align-center">
                <div class="title">
                  <span>소 귀에 경제 읽기</span>
                  <!-- <div class="cursor"></div> -->
                </div>
                <!-- <h1>소 귀<span>에</span> 경<span>제읽기</span></h1> -->
                <span class="description">지금 바로</span>
                <span class="description">경제 공부를 시작해보세요!</span>
                <MainHeaderLogin class="mt-3"></MainHeaderLogin>
                <v-btn
                  class="px-10 mt-3"
                  elevation="0"
                  color="var(--main-col-1)"
                  to="/home"
                  dark
                  rounded
                  outlined
                >
                  로그인 없이 시작하기
                </v-btn>
                <div>SSAFY 8기 A509 오난쥉이조</div>
                <div>엄희정 민동주 서지윤 손승환 신도연</div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- <div class="imac">
        <div class="screen">
          <div class="container">
            <Flicking class="panels" :options="gap5Options">
              <div class="panel"></div>
              <div class="panel"></div>
              <div class="panel"></div>
              <div class="panel"></div>
            </Flicking>
          </div>
        </div>
        <div class="bottom"></div>
        <div class="stand"></div>
        <div class="stand-bottom"></div>
      </div> -->
      <!-- <div class="macbook">
        <div class="screen ratio062">
          <div class="container">
            <Flicking class="panels" :options="gap5Options">
              <div class="panel"></div>
              <div class="panel"></div>
              <div class="panel"></div>
              <div class="panel"></div>
            </Flicking>
          </div>
        </div>
        <div class="bottom"></div>
      </div> -->
      <!-- <div class="ipad ratio13">
        <div class="container">
          <Flicking class="panels" :options="gap5Options">
            <div class="panel"></div>
            <div class="panel"></div>
            <div class="panel"></div>
            <div class="panel"></div>
          </Flicking>
        </div>
      </div> -->
      <!-- <div class="eg-flick-viewport">
        <div class="eg-flick-camera">
          <div class="page main">
            <div class="wheel"></div>
            <div
              class="container d-flex flex-column justify-center align-center"
            > -->
      <!-- </div>
          </div>
        </div>
      </div> -->

      <!-- <div>
        <div class="head"></div>
        <div class="container">
          <Flicking class="panels" :options="gap10Options">
            <div class="panel"></div>
            <div class="panel"></div>
            <div class="panel"></div>
            <div class="panel"></div>
          </Flicking>
        </div>
        <div class="home"></div>
      </div>
      <div>
        <div class="head"></div>
        <div class="container">
          <Flicking class="panels" :options="gap10Options">
            <div class="panel"></div>
            <div class="panel"></div>
            <div class="panel"></div>
            <div class="panel"></div>
          </Flicking>
        </div>
        <div class="home"></div>
      </div> -->
    </Flicking>
  </div>
</template>

<script>
import { Flicking } from "@egjs/vue-flicking";
import Scene from "scenejs";
import { typing } from "@scenejs/effects";
import MainHeaderLogin from "@/components/User/MainHeaderLogin.vue";
// import { shakeY } from "@scenejs/effects";
import { AutoPlay } from "@egjs/flicking-plugins";

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
      // gap5Options: {
      //   circular: true,
      //   gap: 10,
      // },
      plugins: [
        new AutoPlay({ duration: 1000, direction: "NEXT", stopOnHover: false }),
      ],
      snackbarSample: true,
    };
  },
  methods: {
    nextFlicking() {
      this.$refs.flicking.next();
    },
  },
  mounted() {
    const flicking = this.$refs.flicking;
    // console.log(flicking.panels);
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

      // if (index === 0 || index === 3) {
      //   pagination.classList.add("black");
      // } else {
      //   pagination.classList.remove("black");
      // }

      // console.log(flicking.index, index);

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
          3: {
            right: "10%",
            transform:
              "translateX(7vw) translate(50%, 8vh) translateY(50%) scale(0)",
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
            // width: "120vmax",
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
            transform: "translate(-35%, 0vh) translateY(-30%) scale(0.27)",
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
            // "z-index": 0,
          },
          1.5: {
            transform: "translate(0, -20%)",
            "z-index": 1,
            "border-color": "var(--main-col-5)",
          },
        },
        ".c6": {
          0: {
            transform: "translate(0, 0%) translate2(0vw, 0vh)",
            "border-color": "var(--quiz-1-col-6)",
          },
          1: {
            transform: "translate(0, -100%) translate2(0vw, -100vh)",
          },
          1.5: {
            transform: "translate(0, 45%) translate2(0vw, 0vh)",
            "z-index": 1,
            "border-color": "var(--main-col-5)",
          },
        },
        // ".iphone": {
        //   0: {
        //     transform: "translate(-50%, -90px) translateY(0vh) translateY2(0%)",
        //     opacity: 2,
        //   },
        //   1: {
        //     transform:
        //       "translate(-60%, 0px) translateY(-50vh) translateY2(-50%) translateX(0px) translateY3(0vh) scale(1)",
        //     background: "#eee",
        //   },
        //   2: {
        //     transform:
        //       "translate(-50%, -30px) translateX(120px) translateY2(-50%) translateY3(0vh) scale(0.3)",
        //     background: "#444",
        //     opacity: 0,
        //   },
        //   3: {
        //     transform: "translateY3(-50vh) translateY2(-100%)",
        //   },
        // },
        // ".imac": {
        //   1: {
        //     transform:
        //       "translate(-50%) translate2(0px, 170px) translateY(50vh)",
        //     opacity: 0,
        //   },
        //   2: {
        //     transform: "translateY(0vh) translateY2(0%)",
        //     opacity: 1,
        //   },
        //   3: {
        //     transform: "translateY(-50vh) translateY2(-100%)",
        //   },
        // },
        // ".macbook": {
        //   1: {
        //     transform:
        //       "translate(-50%) translate2(-200px, 170px) translateY(70vh)",
        //     opacity: 0,
        //   },
        //   2: {
        //     transform:
        //       "translateY(0vh) translate2(-200px, 170px) translateY2(0%)",
        //     opacity: 1,
        //   },
        //   3: {
        //     transform:
        //       "translateY(-50vh) translate2(-200px, 0px) translateY2(-150%)",
        //   },
        // },
        // ".ipad": {
        //   1: {
        //     transform:
        //       "translate(-50%) translate2(200px, 170px) translateY(70vh)",
        //   },
        //   1.3: {
        //     opacity: 0,
        //   },
        //   2: {
        //     transform:
        //       "translateY(0vh) translate2(200px, 170px) translateY2(0%)",
        //     opacity: 1,
        //   },
        //   3: {
        //     transform:
        //       "translateY(-50vh) translate2(200px, 50px) translateY2(-100%)",
        //   },
        // },
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
        // ".iphone .container": {
        //   0.9: {
        //     opacity: 0,
        //   },
        //   1: {
        //     opacity: 1,
        //   },
        // },
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
        // ".panel.slogan h2": {
        //   1.7: {
        //     opacity: 0,
        //   },
        //   2: {
        //     opacity: 1,
        //   },
        // },
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
            top: "50%",
            transform: "translate(-60%, -70%) scale(0.3)",
          },
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
        // "func-1": {
        //   1: typing({
        //     text: "다양한 경제 뉴스 읽기",
        //     duration: 1,
        //     delay: 0.5,
        //     end: 11,
        //   }),
        // },
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
      },
      {
        selector: true,
      }
    )
      // .on("update", ({ progress }) => {
      //   if (progress >= 2) {
      //     new Scene(
      //       {
      //         "func-1": typing({
      //           text: "다양한 경제 뉴스 읽기",
      //           duration: 1,
      //           delay: 0.5,
      //           end: 11,
      //         }),
      //       },
      //       {
      //         iterationCount: "infinite",
      //         direction: "alternate",
      //         selector: true,
      //       }
      //     ).play();
      //   }
      // })
      .setTime(0);

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
            // iterationCount: "1",
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
              1: {
                color: "var(--main-col-1)",
                "font-family": "var(--main-font-5)",
              },
            },
            ".word": {
              0: {
                transform: "translate(0%, 0%) scale(0)",
              },
              1: {
                transform: "translate(0%, 0%) scale(0)",
              },
              2.5: {
                transform: "translate(70%, -250%) scale(1)",
              },
              4: {
                transform: "translate(70%, -250%) scale(1)",
              },
            },
            ".memo-sample": {
              0: {
                "background-color": "transparent",
                color: "black",
              },
              1: {
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
        // 매출총이익에서 판매비와 일반관리비를 차감한 금액으로, 총이익보다 판매비와 일반관리비가 클 경우에 나타난다. 영업이익과 함께 영업손실은, 기업의 정상적인 영업활동이 아닌 활동에서 발생한 수익과 비용을 포함하지 않는다. 즉 영업에 직접 관련되지 않은 영업외수익·비용, 특별수익·손실, 법인세 등은 제외된다.
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

    // new Scene(
    //   {
    //     ".title span": typing({
    //       text: "소 귀에 경제 읽기.",
    //       duration: 1,
    //       delay: 0.5,
    //       end: 11,
    //     }),
    //     ".title .cursor": {
    //       0: { opacity: 0 },
    //       0.5: { opacity: 0 },
    //       "0.5>": { opacity: 1 },
    //       1: { opacity: 0 },
    //       options: {
    //         iterationCount: 3,
    //       },
    //     },
    //   },
    //   {
    //     // iterationCount: "infinite",
    //     direction: "alternate",
    //     selector: true,
    //   }
    // ).play();

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

    window.addEventListener(
      "wheel",
      function (e) {
        if (!isEnableScroll || flicking.animating) {
          return;
        }
        e.preventDefault();
        var delta = e.deltaY;

        if (Math.abs(delta) > 40) {
          if (delta > 0 && flicking.index < 4) {
            setScrollTimer();
            flicking.next();
          } else if (delta < 0 && flicking.index > 0) {
            flicking.prev();
          }
        }
      },
      {
        passive: false,
      }
    );
  },
};
</script>

<style scoped>
/* @import url("https://fonts.googleapis.com/css?family=Open+Sans:400,700,800&display=swap"); */

/* 첫 페이지 서비스 제목 */
.title {
  margin: 10px;
}
.next-icon {
  /* position: fixed; */
  /* bottom: 5%; */
  /* left: 45.5%; */
  /* left: 50%; */
  width: 44px;
  position: absolute;
  bottom: 5%;
  left: 50%;
  margin-left: -22px; /* width의 50% */
  /* margin-top: -50px; height의 50% */
  /* transform: translateY(-50%); */
  z-index: 2080;
  /* z-index: 2080; */
}
/* .title .cursor {
  display: inline-block;
  width: 2px;
  height: 30px;
  background: white;
} */
.title span {
  font-size: 30px;
  color: var(--main-col-1);
  font-family: var(--logo-font-2);
}
/* * {
  font-family: "Open Sans", sans-serif !important;
  letter-spacing: 0.5px !important;
} */
.word {
  position: absolute;
  top: -29%;
  left: 28%;
  /* opacity: 0; */
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
/* 가운데 동그라미의 테두리 동그라미 */
/* .c2 {
  width: 85%;
  max-width: 380px;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  border: 5px solid var(--main-col-1);
  z-index: 0;
} */
/* 첫 페이지 가운데 동그라미 */
/* .c3 {
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background: var(--main-col-2);
  z-index: 0;
} */
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
/* .iphone,
.ipad,
.macbook, */
/* .imac {
  z-index: 2050 !important;
  transform: translateY(100vh) !important;
} */
.phone {
  position: absolute;
  width: 210px;
  height: 360px;
  border-radius: 20px;
  background: #eee;
  box-shadow: rgba(100, 100, 100, 0.4) 5px 5px;
  left: 50%;
  transform: translate(-50%, 40px);
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
  background: #eee;
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
  background: #eee;
  border-radius: 50%;
  bottom: 6px;
  left: 0;
  right: 0;
  margin: auto;
}

/* .iphone {
  position: absolute;
  width: 200px;
  height: 400px;
  border-radius: 20px;
  background: #eee;
  box-shadow: rgba(100, 100, 100, 0.4) 5px 5px;
  left: 50%;
  top: 100%;
  transform: translate(-50%, -90px);
  margin: auto;
  transform-origin: bottom;
}
.iphone .head {
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
.iphone .container {
  position: absolute;
  width: calc(100% - 16px);
  height: calc(100% - 100px);
  background: transparent;
  border: 2px solid #ddd;
  background: #eee;
  position: absolute;
  border-radius: 5px;
  left: 0;
  right: 0;
  margin: auto;
  bottom: 52px;
}
.iphone .home {
  position: absolute;
  width: 36px;
  height: 36px;
  border: 2px solid #ccc;
  background: #eee;
  border-radius: 50% !important;
  bottom: 6px !important;
  left: 0 !important;
  right: 0 !important;
  margin: auto !important;
} */

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
  /* background: #f55; */
  margin-right: 8px;
  background-size: cover;
  padding: 10px;
  font-size: 12px;
  color: white;
  background-image: linear-gradient(to left, red, yellow);
}
.panels .panel:nth-child(6n + 1) {
  /* background-color: #78caff; */
  background-image: linear-gradient(to bottom, transparent, black),
    url("https://imgnews.pstatic.net/image/032/2023/03/28/0003213554_001_20230328074601106.jpg?type=w647");
}
.panels .panel:nth-child(6n + 2) {
  /* background-color: #ffd99d; */
  background-image: linear-gradient(to bottom, transparent, black),
    url("https://imgnews.pstatic.net/image/018/2023/03/28/0005450748_001_20230328074402156.jpg?type=w647");
}
.panels .panel:nth-child(6n + 3) {
  /* background-color: #ffb0b9; */
  background-image: linear-gradient(to bottom, transparent, black),
    url("https://imgnews.pstatic.net/image/014/2023/03/28/0004987742_001_20230328101902374.jpg?type=w647");
}
.panels .panel:nth-child(6n + 4) {
  /* background-color: #b5f6b9; */
  background-image: linear-gradient(to bottom, transparent, black),
    url("https://imgnews.pstatic.net/image/277/2023/04/02/0005239612_001_20230402082201352.jpg?type=w647");
}
.panels .panel:nth-child(6n + 5) {
  /* background-color: #b5f6b9; */
  background-image: linear-gradient(to bottom, transparent, black),
    url("https://imgnews.pstatic.net/image/015/2023/04/01/0004827547_001_20230401134201025.jpg?type=w647");
}
.panels .panel:nth-child(6n) {
  /* background-color: #b5f6b9; */
  background-image: linear-gradient(to bottom, transparent, black),
    url("https://imgnews.pstatic.net/image/014/2023/03/28/0004987841_001_20230328115902063.jpg?type=w647");
}
/* .ipad {
  position: absolute !important;
  width: 150px !important;
  background: #444 !important;
  border-radius: 5px !important;
  left: 50% !important;
  bottom: 50% !important;
}
.ipad .container {
  position: absolute !important;
  left: 6px !important;
  top: 6px !important;
  bottom: 6px !important;
  right: 6px !important;
  background: #eee !important;
  border-radius: 3px !important;
}
.ipad .panels {
  height: 80px !important;
}
.ipad .panels .panel {
  width: 40% !important;
} */
/* .imac {
  position: absolute;
  width: 280px !important;
  left: 50% !important;
  bottom: 50% !important;
}
.imac .screen {
  position: relative !important;
  width: 100% !important;
  height: 170px !important;
  margin: auto !important;
  background: #444 !important;
  border-radius: 5px 5px 0px 0px !important;
}
.imac .screen .container {
  position: absolute !important;
  left: 8px !important;
  right: 8px !important;
  top: 12px !important;
  bottom: 12px !important;
  background: #eee !important;
}
.imac .panels {
  height: 60px !important;
}
.imac .panels .panel {
  width: 50px !important;
}

.imac .bottom {
  position: relative !important;
  width: 100% !important;
  height: 26px !important;
  margin: auto !important;
  background: #eee !important;
  border-radius: 0px 0px 5px 5px !important;
}
.imac .stand {
  position: relative !important;
  margin: auto !important;
  width: 40px !important;
  border-bottom: 35px solid #eee !important;
  border-left: 15px solid transparent !important;
  border-right: 15px solid transparent !important;
}
.imac .stand-bottom {
  position: relative !important;
  margin: auto !important;
  width: 100px !important;
  height: 6px !important;
  border-radius: 3px !important;
  background: #e5e5e5 !important;
} */
/* .macbook {
  position: absolute !important;
  width: 240px !important;
  left: 50% !important;
  bottom: 50% !important;
}
.macbook .screen {
  position: relative !important;
  width: 200px !important;
  margin: auto !important;
  background: #444 !important;
  border-radius: 5px !important;
}
.macbook .screen .container {
  position: absolute !important;
  left: 6px !important;
  top: 6px !important;
  bottom: 6px !important;
  right: 6px !important;
  background: #eee !important;
  border-radius: 3px !important;
}
.macbook .panels {
  height: 50px !important;
}
.macbook .panels .panel {
  width: 40px !important;
}
.macbook .bottom {
  position: relative !important;
  width: 240px !important;
  height: 10px !important;
  background: #eee !important;
  margin: 3px auto 0px !important;
  border-radius: 5px !important;
} */

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
.flicking .page.news .container {
  /* position: absolute;
  left: 50%;
  bottom: 30%;
  transform: translate(-50%, -50%); */
}
/* .flicking .page.main h1,
.flicking .page.main .description {
  text-align: center !important;
} */
/* 타이틀 - 소귀경 */
/* .flicking .page.main h1 {
  font-size: 33px;
  color: white;
  font-family: var(--main-font-3);
} */
/* 타이틀 속 설명 - 에, 제읽기 */
/* .flicking .page.main h1 span {
  font-family: var(--main-font-0);
} */

.flicking .page.main .description {
  /* font-size: 16px !important; */
  /* max-width: 600px !important; */
  margin: 0px;
  /* padding: 5px !important; */
  /* box-sizing: border-box !important; */
  color: var(--main-col-1);
}

/* .flicking .page.features h3,
.flicking .page.features .description {
  color: #fff !important;
}
.flicking .page.features .feature {
  position: absolute !important;
  width: 200px !important;
  top: 50% !important;
  left: 50% !important;
} */

/* .flicking .page.slogan h2 span[data-text]:before {
  content: attr(data-text) !important;
}
.flicking .page.slogan .container {
  min-width: 600px;
  height: 350px !important;
  text-align: center !important;
  color: #eee !important;
}
.flicking .page.more a {
  text-decoration: underline !important;
  color: #eee !important;
}
.flicking .page.more h2 {
  width: 110px !important;
  padding: 5px 10px 5px 10px !important;
  border: 3px solid #eee !important;
  color: #eee !important;
} */

.pagination {
  /* position: relative; */
  position: absolute;
  right: 0;
  top: 50%;
  transform: translateY(-50%);
  z-index: 2080;
}
.pagination .dot {
  position: relative !important;
  width: 10px !important;
  height: 10px !important;
  border-radius: 50% !important;
  /* background: rgba(255, 255, 255, 0.4) !important; */
  margin: 10px 10px !important;
  transition: background-color ease 0.5s !important;
  cursor: pointer !important;
}
.pagination .dot {
  background: var(--main-col-4);
}
.pagination .dot.selected {
  background: var(--main-col-1);
}

.wheel {
  position: absolute !important;
  bottom: 110px !important;
  width: 2px !important;
  height: 10px !important;
  left: 50% !important;
  transform: translate(-50%, 0) !important;
  background: #eee !important;
}
</style>
