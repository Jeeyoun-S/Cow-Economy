<template>
  <div>
    <v-tabs class="menu b-font main-col-3" background-color="transparent" grow>
      <v-tab
        :class="{ 'selected-category': !selectedCategory }"
        @click="resetFilter"
        >모두보기</v-tab
      >
      <v-tab
        :class="{ 'selected-category': selectedCategory === '금융' }"
        @click="filterByCategory('금융')"
        >금융</v-tab
      >
      <v-tab
        :class="{ 'selected-category': selectedCategory === '증권' }"
        @click="filterByCategory('증권')"
        >증권</v-tab
      >
      <v-tab
        :class="{ 'selected-category': selectedCategory === '산업/재계' }"
        @click="filterByCategory('산업/재계')"
        >산업/재계</v-tab
      >
      <v-tab
        :class="{ 'selected-category': selectedCategory === '중기/벤처' }"
        @click="filterByCategory('중기/벤처')"
        >중기/벤처</v-tab
      >
      <v-tab
        :class="{ 'selected-category': selectedCategory === '부동산' }"
        @click="filterByCategory('부동산')"
        >부동산</v-tab
      >
      <v-tab
        :class="{ 'selected-category': selectedCategory === '글로벌 경제' }"
        @click="filterByCategory('글로벌 경제')"
        >글로벌경제</v-tab
      >
      <v-tab
        :class="{ 'selected-category': selectedCategory === '생활경제' }"
        @click="filterByCategory('생활경제')"
        >생활경제</v-tab
      >
      <v-tab
        :class="{ 'selected-category': selectedCategory === '경제 일반' }"
        @click="filterByCategory('경제 일반')"
        >경제일반</v-tab
      >
      
    </v-tabs>

    <v-sheet
      class="mx-7 my-5 d-flex flex-row align-center"
      style="background-color: transparent"
    >
      <h3 class="mr-auto main-title-font grey--text">
        {{ filteredNews.length }}개의 검색 결과
      </h3>
      <v-sheet
        width="110px"
        class="md-r-font"
        style="background-color: transparent"
      >
        <!-- 지윤님 토글 물어봐서 바꾸기 -->
        <v-select
          class="md-r-font main-col-3"
          :value="sortKey"
          :items="['최신순', '인기순']"
          dense
          rounded
          outlined
          hide-details
          @change="sortNews($event)"
        ></v-select>
      </v-sheet>
    </v-sheet>
    <v-sheet class="pa-6" color="transparent">
      <news-card
        v-for="(article, idx) in filteredNews"
        :key="idx"
        :article="article"
        class="mx-auto my-2 d-flex flex-row"
        height="130"
      >
      </news-card>
      <infinite-loading @infinite="infiniteHandler"></infinite-loading>
    </v-sheet>
  </div>
</template>

<script>
import NewsCard from "@/common/component/NewsCard.vue";
import InfiniteLoading from "vue-infinite-loading";
import { mapActions, mapState } from 'vuex';
export default {
  name: "NewsSearchResult",
  components: {
    NewsCard,
    InfiniteLoading
  },
  props: {
    newsList: Array,
  },
  data() {
    return {
      page: 0,
      items: this.newsList,
      selectedCategory: null,
      sortKey: "최신순",
    };
  },
  computed: {
    ...mapState("newsStore", ["searchText","categoryLast"]),
    filteredNews() {
      // console.log("마지막 기사: "+this.items[this.items.length-1].articleId);
      let filtered = this.selectedCategory
        ? this.items.filter(
            (news) => news.articleCategory === this.selectedCategory
          )
        : this.items;

      if (this.sortKey === "최신순") {
        filtered.sort(
          (a, b) => new Date(b.articleRegtime) - new Date(a.articleRegtime)
        );
      } else if (this.sortKey === "인기순") {
        filtered.sort((a, b) => b.articleHits - a.articleHits);
      }
      return filtered;
    },
  },
  methods: {
    ...mapActions("newsStore", ["setNews"]),
    async infiniteHandler($state) {
      this.page = this.newsList[this.newsList.length-1].articleId
      await this.setNews({"keyword": this.searchText, "categoryLast": this.categoryLast});
      if (this.newsList.length>0){
        await setTimeout(() => {
          this.items = this.items.concat(this.newsList);
          this.page = this.items[this.items.length-1].articleId;
          $state.loaded();
        }, 1000);
      }else{
        $state.complete();
      }
    },
    filterByCategory(category) {
      this.selectedCategory = category;
    },
    resetFilter() {
      this.selectedCategory = null;
    },
    sortNews(newSortKey) {
      this.sortKey = newSortKey;
    },
  },
};
</script>

<style></style>
