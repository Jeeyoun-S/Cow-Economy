<template>
  <div class="d-flex flex-column align-center justify-center">
    <!-- <div class="py-2 d-flex flex-row align-center justify-center"> -->
    <img :src="require('@/assets/images/search/disappointed.png')" />
    <!-- </div> -->
    <span class="py-4 text-center main-title-font">
      "<span class="highlighted-text">{{ searchText }}</span
      >" 검색 결과가 없습니다.
    </span>
    <div class="d-flex justify-center main-subtitle-font">
      <ul class="markdown-list">
        <li>검색어를 입력했는지 확인해 주세요</li>
        <li>띄어쓰기를 한번 더 확인하고 검색해 주세요.</li>
        <li>검색어의 철자가 정확한지 확인해 주세요.</li>
        <li>다른 검색어로 재검색을 해주세요.</li>
        <li>특수문자는 제외하고 검색해주세요.</li>
      </ul>
    </div>
  </div>
</template>
<script>
import { mapState } from "vuex";

export default {
  name: "NewsSearchNoResult",
  computed: {
    ...mapState("newsStore", ["searchText"]),
  },
};
</script>
<style>
.markdown-list {
  list-style-type: disc;
  padding-left: 1.5em;
  display: inline-block;
  text-align: left;
  margin: 0 auto;
}
li {
  font-size: 14px;
}
</style>
