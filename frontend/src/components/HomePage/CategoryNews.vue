<template>
  <v-sheet class="pa-6" color="white">
    <div class="main-title-font align-center">
      <img height="19.47" :src="require('@/assets/images/color-tags.png')" />
      카테고리별 뉴스
    </div>
    <div class="main-subtitle-font">
      최근 24시간 내 카테고리별 뉴스를 확인해 보세요.
    </div>
    <div class="pa-4">
      <span>분류</span>
      <v-slide-group>
            <v-chip-group
              selected-class="text-primary"
              column
            >
              <v-chip filter
                v-for="tag in tags"
                :key="tag"
                color="var(--main-col-2)" outlined>
                {{ tag }}
              </v-chip>
            </v-chip-group>
          </v-slide-group>
          </div>
  </v-sheet>
</template>

<script>
export default {
  name: "HotTrends",
  data() {
    return {
      tags: [
      '금융',
      '증권',
      '산업/재계',
      '중기/벤처',
      '부동산',
      '글로벌경제',
      '생활경제',
      '경제일반'
    ],
    };
  },
};
</script>

<style scoped>

</style>
