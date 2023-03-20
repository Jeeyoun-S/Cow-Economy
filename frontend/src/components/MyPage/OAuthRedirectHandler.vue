<template>
  <div>
  </div>
</template>
<script>
import axios from "@/api/index.js";

export default {
    async created() {
        var code = new URL(window.location.href).searchParams.get("code");
        const response = await axios({url: `/kakao/callback?code=${code}`, method: "GET"});
        const ACCESS_TOKEN = response.data.data;

        localStorage.setItem("access-token", ACCESS_TOKEN);
        window.location.replace("/home")
    }
}
</script>