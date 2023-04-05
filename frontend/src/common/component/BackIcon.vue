<template>
  <v-btn class="ml-0" icon color="grey darken-1" @click="back()">
    <v-icon>mdi-arrow-left</v-icon>
  </v-btn>
</template>

<script>
export default {
  name: "BackIcon",
  methods: {
    back() {
      if (this.$router.history.length > 1) {
        this.$router.go(-1);
      } else {
        this.$router.push('/home')
      }
    }
  }
}
</script>

<style>

</style>