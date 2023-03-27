<template>
	<div class="pa-10 main-container">
		<div class="py-2 d-flex flex-row align-center justify-center">
			<img
				class="mr-2"
				:src="require('@/assets/images/search/disappointed.png')"
			/>
		</div>  
		<p class="pa-3 text-title text-center search-text">
			"<span class="highlighted-text">{{ searchText }}</span>" 검색 결과가 없습니다.
		</p>
		<div class="d-flex justify-center">
			<ul class="markdown-list">
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
			...mapState("newsStore", ["searchText"])
		}
}
</script>
<style>
.markdown-list {
  list-style-type: disc;
  padding-left: 1.5em;
	display: inline-block;
	text-align: left;
	margin: 0 auto;
}

.search-text {
	font-family: var(--main-font-3);
	color: black;
	font-size: 20px;
}

.main-container {
	height: 100%;
	display: flex;
	flex-direction: column;
	justify-content: center;
}



li {
	font-size: 14px;
}
</style>