<template>
    <div>
      <a id="custom-login-btn" @click="kakaoLogin">
        <v-img src="@/assets/images/login/kakao_login.png"/>
      </a>
    </div>
</template>
<script>

export default {
	name: "KakaoLogin",
	components: {},
	computed: {},
	methods: {
		// 카카오 인가 코드 받기, redirect는 my-page로
		kakaoLogin() {
			window.location.replace(
				`https://kauth.kakao.com/oauth/authorize?client_id=${process.env.VUE_APP_KAKAO_REST_API_KEY}&redirect_uri=${process.env.VUE_APP_KAKAO_REDIRECT_URI}&response_type=code`
			)
		}
	}
}
</script>
