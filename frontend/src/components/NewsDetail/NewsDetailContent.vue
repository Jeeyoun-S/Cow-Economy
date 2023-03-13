<template>
  <v-sheet class="pa-7 d-flex flex-column">
    <div class="pb-2">
      <v-chip class="mr-2" color="var(--main-col-2)" outlined>{{
        category
      }}</v-chip>
      <v-chip color="var(--main-col-2)" outlined>{{ press }}</v-chip>
    </div>
    <div class="py-2">
      <h2>비트코인, 빅스텝 우려에 주춤…3100만원도 '아슬'</h2>
    </div>
    <div class="py-2 d-flex align-center">
      <span>{{ date }}</span>
      <v-btn class="ml-auto" color="grey darken-1" small outlined rounded
        >원본 보기</v-btn
      >
    </div>
    <div class="py-2" v-html="content"></div>
    <div class="py-2 ml-auto">{{ editor }}</div>
  </v-sheet>
</template>

<script>
export default {
  name: "NewsDetailContent",
  data() {
    return {
      category: "금융",
      press: "뉴시스",
      date: "2023.03.02",
      content:
        "인천공항을 출발해 필리핀 마닐라로 가려던 대한항공 여객기 좌석 밑에서 실탄 2발이 발견돼 승객 218명이 대피했다. 이 항공기는 애초보다 3시간 늦게 출발했다 인천공항경찰단은 10일 오전 8시쯤 인천공항 제2여객터미널 대한항공 여객기에서 실탄 2발이 발견돼 기내 반입 여부를 조사하고 있다고 밝혔다. 당시 한 승객은 좌석 밑에 떨어져 있는 실탄을 발견해 승무원에게 알려고, 승무원은 112에 신고했다. 이 항공기는 이날 오전 7시 45분 마닐라로 출발할 예정이었으나, 이륙 직전 터미널로 되돌아왔다. 항공기 탄 승객 218명과 승무원 12명 등 230명은 비행기에서 내려 대피했다. 경찰은 실탄이 기내에 유입된 경위 등을 조사하고 있다.<br/> 경찰 대테러 기동팀과 폭발물처리반(EOD)이 투입돼 기내를 수색, 테러 위협이 없는 것으로 판단되자 대한항공은 애초보다 3시간 늦은 오전 11시 5분쯤 여객기를 마닐라로 출발시켰다.인천공항을 출발해 필리핀 마닐라로 가려던 대한항공 여객기 좌석 밑에서 실탄 2발이 발견돼 승객 218명이 대피했다. <br/>이 항공기는 애초보다 3시간 늦게 출발했다 인천공항경찰단은 10일 오전 8시쯤 인천공항 제2여객터미널 대한항공 여객기에서 실탄 2발이 발견돼 기내 반입 여부를 조사하고 있다고 밝혔다. 당시 한 승객은 좌석 밑에 떨어져 있는 실탄을 발견해 승무원에게 알려고, 승무원은 112에 신고했다. 이 항공기는 이날 오전 7시 45분 마닐라로 출발할 예정이었으나, 이륙 직전 터미널로 되돌아왔다. 항공기 탄 승객 218명과 승무원 12명 등 230명은 비행기에서 내려 대피했다. </p><p>경찰은 실탄이 기내에 유입된 경위 등을 조사하고 있다. 경찰 대테러 기동팀과 폭발물처리반(EOD)이 투입돼 기내를 수색, 테러 위협이 없는 것으로 판단되자 대한항공은 애초보다 3시간 늦은 오전 11시 5분쯤 여객기를 마닐라로 출발시켰다.</p>",
      editor: "홍길동 기자",
    };
  },
};
</script>

<style></style>
