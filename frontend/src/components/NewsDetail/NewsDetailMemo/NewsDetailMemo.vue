<template>
  <v-sheet class="py-7" id="memo">
    <v-sheet class="mx-7 d-flex flex-row align-center">
      <h3 class="mr-auto">메모</h3>
      <v-sheet width="100px">
        <v-select
          v-model="sort"
          :items="['최신순', '인기순', '레벨순']"
          color="var(--main-col-2)"
          dense
          rounded
          outlined
          hide-details
          style="font-size: 15px"
        ></v-select>
      </v-sheet>
    </v-sheet>
    <!-- <v-btn
      elevation="0"
      color="var(--main-col-1)"
      @click="moveIndex()"
      dark
      block
      >이동</v-btn
    > -->
    <v-row class="mx-7 py-5 pt-7">
      <v-col class="pa-0">
        <v-btn
          elevation="0"
          color="var(--main-col-2)"
          @click="isListMine = true"
          :dark="isListMine ? true : false"
          :outlined="isListMine ? false : true"
          block
          tile
          >나의 메모</v-btn
        >
      </v-col>
      <v-col class="pa-0">
        <v-btn
          elevation="0"
          color="var(--main-col-2)"
          @click="isListMine = false"
          :dark="isListMine ? false : true"
          :outlined="isListMine ? true : false"
          block
          tile
          >전체 메모</v-btn
        >
      </v-col>
    </v-row>
    <NewsDetailMemoOther v-if="!isListMine"></NewsDetailMemoOther>
    <NewsDetailMemoMine v-else></NewsDetailMemoMine>
    <!-- <div id="article" @mouseup="getSelectedText()">
      <div v-html="html"></div>
    </div> -->
  </v-sheet>
</template>

<script>
import NewsDetailMemoOther from "./NewsDetailMemoOther.vue";
import NewsDetailMemoMine from "./NewsDetailMemoMine.vue";

export default {
  name: "NewsDetail",
  components: {
    NewsDetailMemoMine,
    NewsDetailMemoOther,
  },
  data() {
    return {
      html: "인천공항을 출발해 필리핀 마닐라로 가려던 대한항공 여객기 좌석 밑에서 실탄 2발이 발견돼 승객 218명이 대피했다. 이 항공기는 애초보다 3시간 늦게 출발했다 인천공항경찰단은 10일 오전 8시쯤 인천공항 제2여객터미널 대한항공 여객기에서 실탄 2발이 발견돼 기내 반입 여부를 조사하고 있다고 밝혔다. 당시 한 승객은 좌석 밑에 떨어져 있는 실탄을 발견해 승무원에게 알려고, 승무원은 112에 신고했다. 이 항공기는 이날 오전 7시 45분 마닐라로 출발할 예정이었으나, 이륙 직전 터미널로 되돌아왔다. 항공기 탄 승객 218명과 승무원 12명 등 230명은 비행기에서 내려 대피했다. 경찰은 실탄이 기내에 유입된 경위 등을 조사하고 있다.<br/> 경찰 대테러 기동팀과 폭발물처리반(EOD)이 투입돼 기내를 수색, 테러 위협이 없는 것으로 판단되자 대한항공은 애초보다 3시간 늦은 오전 11시 5분쯤 여객기를 마닐라로 출발시켰다.인천공항을 출발해 필리핀 마닐라로 가려던 대한항공 여객기 좌석 밑에서 실탄 2발이 발견돼 승객 218명이 대피했다. <br/>이 항공기는 애초보다 3시간 늦게 출발했다 인천공항경찰단은 10일 오전 8시쯤 인천공항 제2여객터미널 대한항공 여객기에서 실탄 2발이 발견돼 기내 반입 여부를 조사하고 있다고 밝혔다. 당시 한 승객은 좌석 밑에 떨어져 있는 실탄을 발견해 승무원에게 알려고, 승무원은 112에 신고했다. 이 항공기는 이날 오전 7시 45분 마닐라로 출발할 예정이었으나, 이륙 직전 터미널로 되돌아왔다. 항공기 탄 승객 218명과 승무원 12명 등 230명은 비행기에서 내려 대피했다. </p><p>경찰은 실탄이 기내에 유입된 경위 등을 조사하고 있다. 경찰 대테러 기동팀과 폭발물처리반(EOD)이 투입돼 기내를 수색, 테러 위협이 없는 것으로 판단되자 대한항공은 애초보다 3시간 늦은 오전 11시 5분쯤 여객기를 마닐라로 출발시켰다.</p>",
      html2: "인<br/>천<br/>공항",
      start: 0,
      sort: "최신순",
      isListMine: true,
    };
  },
};
</script>

<style></style>
