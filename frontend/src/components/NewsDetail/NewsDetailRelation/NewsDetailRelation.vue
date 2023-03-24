<template>
  <v-sheet id="relation-article" class="pa-7" color="var(--main-col-5)">
    <h3 class="mb-4">관련 기사</h3>
    <swiper class="swiper pt-10 pa-1" :options="swiperOption">
      <div class="swiper-pagination" slot="pagination"></div>
      <swiper-slide v-for="(relation, index) in relations" :key="index">
        <NewsDetailRelationItem
          :index="index"
          :relation="relation"
        ></NewsDetailRelationItem>
      </swiper-slide>
    </swiper>
  </v-sheet>
</template>

<script>
import NewsDetailRelationItem from "./NewsDetailRelationItem.vue";

export default {
  name: "NewsDetailRelation",
  components: {
    NewsDetailRelationItem,
  },
  data() {
    return {
      relations: [
        {
          title: "비트코인, 연준 '비둘기' 발언에도 3100만원대 횡보",
          thumbnails:
            "https://imgnews.pstatic.net/image/005/2023/03/13/2023020520421142396_1675597331_0924291337_20230313000404714.jpg?type=w647",
        },
        {
          title: "비트코인, 연준 '비둘기' 발언에도 3100만원대 횡보",
          thumbnails:
            "https://imgnews.pstatic.net/image/005/2023/03/13/2023020520421142396_1675597331_0924291337_20230313000404714.jpg?type=w647",
        },
        {
          title: "비트코인, 연준 '비둘기' 발언에도 3100만원대 횡보",
          thumbnails:
            "https://imgnews.pstatic.net/image/005/2023/03/13/2023020520421142396_1675597331_0924291337_20230313000404714.jpg?type=w647",
        },
        {
          title: "비트코인, 연준 '비둘기' 발언에도 3100만원대 횡보",
          thumbnails:
            "https://imgnews.pstatic.net/image/005/2023/03/13/2023020520421142396_1675597331_0924291337_20230313000404714.jpg?type=w647",
        },
        {
          title: "비트코인, 연준 '비둘기' 발언에도 3100만원대 횡보",
          thumbnails:
            "https://imgnews.pstatic.net/image/005/2023/03/13/2023020520421142396_1675597331_0924291337_20230313000404714.jpg?type=w647",
        },
        {
          title: "비트코인, 연준 '비둘기' 발언에도 3100만원대 횡보",
          thumbnails:
            "https://imgnews.pstatic.net/image/005/2023/03/13/2023020520421142396_1675597331_0924291337_20230313000404714.jpg?type=w647",
        },
        {
          title: "비트코인, 연준 '비둘기' 발언에도 3100만원대 횡보",
          thumbnails:
            "https://imgnews.pstatic.net/image/005/2023/03/13/2023020520421142396_1675597331_0924291337_20230313000404714.jpg?type=w647",
        },
        {
          title: "비트코인, 연준 '비둘기' 발언에도 3100만원대 횡보",
          thumbnails:
            "https://imgnews.pstatic.net/image/005/2023/03/13/2023020520421142396_1675597331_0924291337_20230313000404714.jpg?type=w647",
        },
        {
          title: "비트코인, 연준 '비둘기' 발언에도 3100만원대 횡보",
          thumbnails:
            "https://imgnews.pstatic.net/image/005/2023/03/13/2023020520421142396_1675597331_0924291337_20230313000404714.jpg?type=w647",
        },
        {
          title: "비트코인, 연준 '비둘기' 발언에도 3100만원대 횡보",
          thumbnails:
            "https://imgnews.pstatic.net/image/005/2023/03/13/2023020520421142396_1675597331_0924291337_20230313000404714.jpg?type=w647",
        },
      ],
      swiperOption: {
        slidesPerView: 2,
        spaceBetween: 25,
        slidesPerGroup: 2,
        loop: true,
        pagination: {
          el: ".swiper-pagination",
          clickable: true,
        },
        lazy: {
          loadPrevNext: true,
        },
      },
    };
  },
};
</script>

<style></style>
