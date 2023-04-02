<template>
  <div>
    <div class="pagination black">
      <div class="dot selected"></div>
      <div class="dot"></div>
      <div class="dot"></div>
      <div class="dot"></div>
    </div>
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
              <img height="60px" :src="require('@/assets/images/icon.png')" />
              <h1>소 귀<span>에</span> 경<span>제읽기</span></h1>
              <span class="description">경제 기사를 읽으며</span>
              <span class="description">경제에 대해 공부하는 서비스</span>
            </div>
          </div>
          <div class="page features">
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
          </div>
        </div>
      </div>
      <div class="imac">
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
      </div>
      <div class="macbook">
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
      </div>
      <div class="ipad ratio13">
        <div class="container">
          <Flicking class="panels" :options="gap5Options">
            <div class="panel"></div>
            <div class="panel"></div>
            <div class="panel"></div>
            <div class="panel"></div>
          </Flicking>
        </div>
      </div>
      <div class="iphone">
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
    </Flicking>
  </div>
</template>

<script>
import { Flicking } from "@egjs/vue-flicking";
import Scene from "scenejs";

export default {
  name: "FlickingTest",
  components: {
    Flicking: Flicking,
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
      gap5Options: {
        circular: true,
        gap: 10,
      },
    };
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

      // if (index === 0 || index === 3) {
      //   pagination.classList.add("black");
      // } else {
      //   pagination.classList.remove("black");
      // }

      if (index) dots[flicking.index].classList.remove("selected");
      dots[index].classList.add("selected");
    });

    const scene = new Scene(
      {
        ".c1": {
          0: {
            right: "45%",
            transform: "translate(0%, 0vh) translateY(0%)",
          },
          1: {
            right: "60%",
            transform: "translate(0%, 50vh) translateY(90%)",
          },
          2: {
            right: "85%",
            transform:
              "translate(40%, 20vh) translateX(0vw) translateY(0%) scale(1)",
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
            transform: "translate(0%, -50vh) translateY(-60%)",
            left: "60%",
          },
          2: {
            transform: "translate(0%, -100vh) translateY(-100%)  scale(1)",
            left: "85%",
          },
          3: {
            left: "57%",
            transform: "translate(-50%, 0vh) translateY(-60%) scale(0.27)",
          },
        },
        ".c5": {
          0: {
            transform: "translate(0, 0%)",
          },
          1: {
            transform: "translate(0, -100%)",
          },
        },
        ".c6": {
          0: {
            transform: "translate(0, 0%) translate2(0vw, 0vh)",
          },
          1: {
            transform: "translate(0, -100%) translate2(0vw, -100vh)",
          },
        },
        ".iphone": {
          0: {
            transform: "translate(-50%, -90px) translateY(0vh) translateY2(0%)",
          },
          1: {
            transform:
              "translate(-50%, 0px) translateY(-50vh) translateY2(-50%) translateX(0px) translateY3(0vh) scale(1)",
            background: "#eee",
          },
          2: {
            transform:
              "translate(-50%, -30px) translateX(120px) translateY2(-50%) translateY3(0vh) scale(0.3)",
            background: "#444",
          },
          3: {
            transform: "translateY3(-50vh) translateY2(-100%)",
          },
        },
        ".imac": {
          1: {
            transform:
              "translate(-50%) translate2(0px, 170px) translateY(50vh)",
            opacity: 0,
          },
          2: {
            transform: "translateY(0vh) translateY2(0%)",
            opacity: 1,
          },
          3: {
            transform: "translateY(-50vh) translateY2(-100%)",
          },
        },
        ".macbook": {
          1: {
            transform:
              "translate(-50%) translate2(-200px, 170px) translateY(70vh)",
            opacity: 0,
          },
          2: {
            transform:
              "translateY(0vh) translate2(-200px, 170px) translateY2(0%)",
            opacity: 1,
          },
          3: {
            transform:
              "translateY(-50vh) translate2(-200px, 0px) translateY2(-150%)",
          },
        },
        ".ipad": {
          1: {
            transform:
              "translate(-50%) translate2(200px, 170px) translateY(70vh)",
          },
          1.3: {
            opacity: 0,
          },
          2: {
            transform:
              "translateY(0vh) translate2(200px, 170px) translateY2(0%)",
            opacity: 1,
          },
          3: {
            transform:
              "translateY(-50vh) translate2(200px, 50px) translateY2(-100%)",
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
        ".iphone .container": {
          0.9: {
            opacity: 0,
          },
          1: {
            opacity: 1,
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
        ".panel.slogan h2": {
          1.7: {
            opacity: 0,
          },
          2: {
            opacity: 1,
          },
        },
        ".c7": {
          1: {
            transform: "translate(0%, 0%)",
            top: "100%",
          },
          2: {
            transform: "translate(0%, 0%) scale(1)",
            top: "80%",
          },
          3: {
            top: "50%",
            transform: "translate(-60%, -60%) scale(0.3)",
          },
        },
      },
      {
        selector: true,
      }
    ).setTime(0);

    this.$refs.flicking.on("move", () => {
      scene.setTime(flicking.camera.progress);
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
          if (delta > 0 && flicking.index < 3) {
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
@import url("https://fonts.googleapis.com/css?family=Open+Sans:400,700,800&display=swap");

/* * {
  font-family: "Open Sans", sans-serif !important;
  letter-spacing: 0.5px !important;
} */
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
  background: var(--graph-1-col-4);
  z-index: 1;
}
/* 가운데 동그라미의 테두리 동그라미 */
.c2 {
  width: 85%;
  max-width: 380px;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  border: 5px solid var(--main-col-1);
  z-index: 0;
}
/* 첫 페이지 가운데 동그라미 */
.c3 {
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background: var(--main-col-2);
  z-index: 0;
}
.c4 {
  width: 60vmax !important;
  min-width: 1000px !important;
  top: 50% !important;
  left: 45% !important;
  background: #30b78e !important;
}
.c5 {
  width: 20% !important;
  min-width: 300px !important;
  bottom: 60% !important;
  left: 70% !important;
  border: 40px solid #ea905d !important;
}
.c6 {
  width: 20% !important;
  min-width: 300px !important;
  top: 60% !important;
  right: 70% !important;
  border: 40px solid #5d95ea !important;
}
.c7 {
  width: 60vmax !important;
  min-width: 1000px !important;
  left: 50% !important;
  top: 80% !important;
  background: #6692ca !important;
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
.iphone,
.ipad,
.macbook,
.imac {
  z-index: 2050 !important;
  transform: translateY(100vh) !important;
}
.iphone {
  position: absolute !important;
  width: 200px !important;
  height: 400px !important;
  border-radius: 20px !important;
  background: #eee !important;
  box-shadow: rgba(100, 100, 100, 0.4) 5px 5px !important;
  left: 50% !important;
  top: 100% !important;
  transform: translate(-50%, -90px) !important;
  margin: auto !important;
  transform-origin: bottom !important;
}
.iphone .head {
  position: absolute !important;
  width: 36px !important;
  height: 8px !important;
  top: 22px !important;
  left: 0 !important;
  right: 0 !important;
  border-radius: 4px !important;
  margin: auto !important;
  background: #ddd !important;
}
.iphone .container {
  position: absolute !important;
  width: calc(100% - 16px) !important;
  height: calc(100% - 100px) !important;
  background: transparent !important;
  border: 2px solid #ddd !important;
  background: #eee !important;
  position: absolute !important;
  border-radius: 5px !important;
  left: 0 !important;
  right: 0 !important;
  margin: auto !important;
  bottom: 52px !important;
}
.iphone .home {
  position: absolute !important;
  width: 36px !important;
  height: 36px !important;
  border: 2px solid #ccc !important;
  background: #eee !important;
  border-radius: 50% !important;
  bottom: 6px !important;
  left: 0 !important;
  right: 0 !important;
  margin: auto !important;
}

.panels {
  position: absolute !important;
  top: 50% !important;
  transform: translateY(-50%) !important;
  width: 100% !important;
  height: 150px !important;
}
.panels .panel {
  position: relative !important;
  width: 70% !important;
  height: 100% !important;
  border-radius: 10px !important;
  background: #f55 !important;
}
.panels .panel:nth-child(4n + 1) {
  background-color: #78caff !important;
}
.panels .panel:nth-child(4n + 2) {
  background-color: #ffd99d !important;
}
.panels .panel:nth-child(4n + 3) {
  background-color: #ffb0b9 !important;
}
.panels .panel:nth-child(4n) {
  background-color: #b5f6b9 !important;
}
.ipad {
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
}
.imac {
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
}
.macbook {
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
}

.background2 {
  background: #9c5dea !important;
  z-index: -1 !important;
  transform: translateY(100vh) !important;
}
.flicking .eg-flick-viewport,
.flicking .eg-flick-camera,
.flicking .page {
  position: relative !important;
  width: 100% !important;
  height: 100% !important;
}
.flicking .page .container {
  position: absolute !important;
  left: 50% !important;
  top: 50% !important;
  transform: translate(-50%, -50%) !important;
}
/* .flicking .page.main h1,
.flicking .page.main .description {
  text-align: center !important;
} */
/* 타이틀 - 소귀경 */
.flicking .page.main h1 {
  font-size: 33px;
  color: white;
  font-family: var(--main-font-3);
}
/* 타이틀 속 설명 - 에, 제읽기 */
.flicking .page.main h1 span {
  font-family: var(--main-font-0);
}

.flicking .page.main .description {
  /* font-size: 16px !important; */
  /* max-width: 600px !important; */
  margin: 0px;
  /* padding: 5px !important; */
  /* box-sizing: border-box !important; */
  color: white;
}

.flicking .page.features h3,
.flicking .page.features .description {
  color: #fff !important;
}
.flicking .page.features .feature {
  position: absolute !important;
  width: 200px !important;
  top: 50% !important;
  left: 50% !important;
}

.flicking .page.slogan h2 span[data-text]:before {
  content: attr(data-text) !important;
}
.flicking .page.slogan .container {
  min-width: 600px !important;
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
}

.pagination {
  /* position: relative; */
  position: absolute !important;
  right: 0 !important;
  top: 50% !important;
  transform: translateY(-50%) !important;
  z-index: 2080 !important;
}
.pagination .dot {
  position: relative !important;
  width: 10px !important;
  height: 10px !important;
  border-radius: 50% !important;
  background: rgba(255, 255, 255, 0.4) !important;
  margin: 10px 10px !important;
  transition: background-color ease 0.5s !important;
  cursor: pointer !important;
}
.pagination.black .dot.selected {
  background: #333 !important;
}
.pagination.black .dot {
  background: rgba(100, 100, 100, 0.4) !important;
}
.pagination .dot.selected {
  background: white !important;
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
