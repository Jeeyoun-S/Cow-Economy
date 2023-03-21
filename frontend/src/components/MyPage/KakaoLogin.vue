<template>
    <div>
      <a id="custom-login-btn" @click="kakaoLogin">
        <v-img src="@/assets/images/login/kakao_login.png"/>
      </a>
    </div>
</template>
<script>
// import { getToken } from "@/api/user";
// await getToken(
// 				async ({ data }) => {
// 					console.log("#21# getToken 실행결과: ", data );
// 					// i) 성공
// 					if (data.statusCode == 200) {
// 						console.log("토큰 발급 성공", data);
// 						const ACCESS_TOKEN = data.data.data.acessToken;
// 						localStorage.setItem("access-token", ACCESS_TOKEN);
// 						window.location.replace("/home");
// 					}
// 				},
// 				// ii) 실패
// 				(error) => {
// 					console.log(error);
// 				}
// 			)

export default {
	name: "kakaoLogin",
	data() {
		return {
				redirectUri: encodeURIComponent(process.env.VUE_APP_KAKAO_REDIRECT_URI),
				oauthUrl: `https://kauth.kakao.com/oauth/authorize?client_id=${process.env.VUE_APP_KAKAO_REST_API_KEY}&redirect_uri=${process.env.VUE_APP_KAKAO_REDIRECT_URI}&response_type=code`, 
		}
	},
	components: {},
	computed: {},
	methods: {
		kakaoLogin() {
			window.location.replace(
				`https://kauth.kakao.com/oauth/authorize?client_id=${process.env.VUE_APP_KAKAO_REST_API_KEY}&redirect_uri=${process.env.VUE_APP_KAKAO_REDIRECT_URI}&response_type=code`
			)
		}
	}
	// async created() {
	// 	this.oauthUrl = `https://kauth.kakao.com/oauth/authorize?client_id=${process.env.VUE_APP_KAKAO_REST_API_KEY}&redirect_uri=${process.env.VUE_APP_KAKAO_REDIRECT_URI}&response_type=code`
	// },
}
</script>
