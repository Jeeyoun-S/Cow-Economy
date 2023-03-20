<template>
    <div>
      <a id="custom-login-btn" :href='this.oauthUrl'>
        <v-img src="@/assets/images/login/kakao_login.png"/>
      </a>
    </div>
</template>
<script>
export default {
    name: "kakaoLogin",
    data() {
        return {
            redirectUri: encodeURIComponent(process.env.VUE_APP_KAKAO_REDIRECT_URI),
            oauthUrl: null, 
        }
    },
    created() {
        this.oauthUrl = `https://kauth.kakao.com/oauth/authorize?client_id=${process.env.VUE_APP_KAKAO_REST_API_KEY}&redirect_uri=http://localhost:3000/oauth/callback/kakao&response_type=code`
    },
}
</script>
