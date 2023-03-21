<template>
  <v-sheet class="pa-7 d-flex flex-column">
    <div class="pb-2">
      <v-chip class="mr-2" color="var(--main-col-2)" outlined>{{
        category
      }}</v-chip>
      <v-chip color="var(--main-col-2)" outlined>{{ press }}</v-chip>
    </div>
    <div class="py-2">
      <h2>{{ title }}</h2>
    </div>
    <div class="py-2 d-flex align-center">
      <span class="sm-font">{{ date }}</span>
      <v-btn
        class="ml-auto"
        color="grey darken-1"
        small
        outlined
        rounded
        @click="openOriginalUrl()"
        >원본 보기</v-btn
      >
    </div>
    <div id="content" class="py-2">
      <div id="article" v-html="content"></div>
    </div>
    <div class="py-2 ml-auto">{{ editor }}</div>
    <v-snackbar v-model="memoBtn" color="var(--main-col-2)" rounded="pill">
      <span>메모에 인용문으로 추가하기</span>
      <template v-slot:action="{ attrs }">
        <v-btn
          color="white"
          text
          v-bind="attrs"
          @click="addMemoReference()"
          rounded
        >
          추가
        </v-btn>
        <v-btn
          color="white"
          text
          v-bind="attrs"
          @click="changeMemoBtn()"
          rounded
        >
          닫기
        </v-btn>
      </template>
    </v-snackbar>
  </v-sheet>
</template>

<script>
import { addSelectionEvent } from "@/common/function/textSelection";
import { mapActions, mapState } from "vuex";

const memoStore = "memoStore";

export default {
  name: "NewsDetailContent",
  data() {
    return {
      category: "금융",
      press: "뉴시스",
      date: "2023.03.02",
      content:
        "@@strong[비즈 이슈] “상속 과정 절차상 문제 있어” 주장@@br장례 당시 가족간 합의 여부 쟁점@@endstrong@@imghttps://imgnews.pstatic.net/image/005/2023/03/13/2023020520421142396_1675597331_0924291337_20230313000404714.jpg?type=w647@@endimg@@imgdesc사진=연합뉴스@@endimgdesc@@br고(故) 구본무 전 LG그룹 회장의 상속 지분을 둘러싼 법적 공방에 재계 안팎의 이목이 쏠리고 있다. 창립 75년을 맞은 LG그룹에서 재산 분할분쟁이 일어나기는 처음이다. 특히 2018년 구 전 회장의 별세 이후 4년이 지난 시점에서 갑작스레 소송이 불거진 배경, 진행 과정 등을 놓고 다양한 해석이 나온다. 소송 결과에 따라 LG그룹 지배구조에 큰 변화가 발생할 수 있다는 시각도 있다.@@br@@br12일 재계와 법조계에 따르면 구광모 LG그룹 회장의 모친 김영식 여사와 여동생 구연경 LG복지재단 대표, 구연수씨는 서울서부지법에 구 회장을 상대로 ‘상속회복청구’ 소송을 제기했다. 구 회장은 구 전 회장의동생인 구본능 희성그룹 회장의 큰아들이다. ‘장자(長子) 승계’ 원칙에 따라 지난 2004년 구 전 회장의 양자로 입적했다.@@br@@br법조계는 김 여사 모녀가 상속회복청구 소송을 제기한 지점에 주목한다. 구 회장의 상속자격 자체를 문제삼고 있어서다. 부장판사 출신의 한 변호사는 “상속회복은 상속인을 참칭해 유산을 가져갔거나,선순위 상속인이 뒤늦게 확인된 경우에 벌어지는 소송”이라며 “상속 비율이 문제라면 유류분(법적 상속 비율) 반환 소송을 제기할 텐데, 구 회장의 상속자격 자체를 다투는 건 이례적”이라고 말했다.@@br@@br구 회장은 구 전 회장의 별세 이후 지주회사인 ㈜LG 주식 11.28%(1945만8169주) 가운데 8.76%(1512만2169주)를 상속받아 대표이사 회장에 올랐다. 구 대표는 2.01%(346만4000주), 연수씨는 0.51%(87만2000주)를 분할 상속받았다. 김 여사는 1주도 받지 않았다. 유류분 기준에 따르면 구 전 회장 보유지분의 상속 비율은 ‘1.5(배우자) 대 1(자녀 1인당)’이다.@@br@@br때문에 구 전 회장 별세 당시 구 회장과 김 여사 모녀 사이에 어떤 합의가 이뤄졌는지가 쟁점으로 떠오른다. 김 여사 측은 “상속 과정에서 여러 절차상 문제가 있었다”고 주장하는 것으로 알려졌다. 이들은 상속 재산 분할을 합의하면서 구 전 회장이 남긴 유언장이 있는 것으로 알았지만, 뒤늦게 유언장이 없다는 걸 알게 됐다는 입장이다.@@br@@br반면 LG그룹 측은 “김 여사 모녀는 유언장이 없다는 것을 이미 알고 있었다”고 반박한다. 일부에선 상속회복청구 기간이 지난 상황에서 시효를 인정받으려는 시도로 보기도 한다. 상속회복청구는 ‘침해를 안 날’로부터 3년, ‘침해행위가 있은 날’로부터 10년 안에 행사해야 한다. 유류분 권리 행사는 피상속자의 사망 사실을 안 시점에서 1년 내 가능하다. 한 법조인은 “유류분 반환청구가 시효 문제에 막힌다면, 상속회복청구로 ‘침해 사실을 뒤늦게 알았다’고 주장해 법적 판단을받아볼 수 있을 것”이라고 설명했다.@@br@@br김 여사 측 주장이 받아들여지면, LG그룹 지분 구조에 변화는 불가피할 전망이다. 유류분 기준으로 재산정하면 김 여사가 3.75%를받고 구 회장과 구 대표, 연수씨가 각각 2.51%를 받게 된다. 구 회장의 ㈜LG 지분은 현재 15.95%에서 9.7%로 낮아진다. 김 여사는 지분 4.20%에서 7.95%로 뛰어 2대 주주로 오른다. 구 대표와 연수씨 지분도 각각 3.42%, 2.72%로 높아진다. 세 모녀의 지분을 합하면 14.09%로 구 회장 지분을 웃돌게 된다.",
      editor: "홍길동 기자",
      title: "비트코인, 빅스텝 우려에 주춤…3100만원도 '아슬'",
      url: "https://mobile.newsis.com/view.html?ar_id=NISX20230302_0002210849#_enliple",
    };
  },
  computed: {
    ...mapState(memoStore, ["memoBtn"]),
  },
  methods: {
    ...mapActions(memoStore, ["changeMemoBtn", "getSelectionText"]),
    addMemoReference() {
      document.getElementById("memo").scrollIntoView(true);
      this.getSelectionText();
    },
    openOriginalUrl() {
      const tab = window.open(this.url, "_blank");
      tab.focus();
    },
  },
  created() {
    this.content = this.content
      .replaceAll("@@imgdesc", "<em>")
      .replaceAll("@@endimgdesc", "</em>")
      .replaceAll("@@img", "<img src='")
      .replaceAll("@@endimg", "' />")
      .replaceAll("@@strong", "<div><strong>")
      .replaceAll("@@endstrong", "</strong></div>")
      .replaceAll("@@br", "<br />");
  },
  mounted() {
    // 텍스트 드래그하면 메모 추가 창이 생기는 event 추가
    // document 전체에 적용 (div#article에만 하면 미작동)
    document.addEventListener("selectionchange", addSelectionEvent);
  },
  destroyed() {
    // 텍스트 드래그하면 메모 추가 창이 생기는 event 삭제
    document.removeEventListener("selectionchange", addSelectionEvent);
  },
};
</script>

<style></style>
