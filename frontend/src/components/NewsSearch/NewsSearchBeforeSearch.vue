<template>
	<div class="pa-10 main-container">
		<div class="py-2 d-flex flex-row align-center justify-center">
			<img
				class="mr-2"
				:src="require('@/assets/images/search/search.png')"
			/>
		</div>  
		<p class="pa-3 text-title text-center search-text">
			다양한 경제 뉴스를<br>검색해 보세요.
		</p>
	</div>
</template>
  
<script>
export default {
	name: "NewsSearchBeforeSearch",
}
</script>

<style>
.search-text {
	font-family: var(--main-font-3);
	color: var(--main-col-4);
	font-size: 24px;
}
.main-container {
	height: 100%;
	display: flex;
	flex-direction: column;
	justify-content: center;
}
</style>
