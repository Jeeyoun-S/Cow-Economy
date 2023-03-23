<template>
	<v-container class="login">
		<div class="login-title">LOGIN</div>
			<div class="login-text">로그인 후 이용 가능한 페이지입니다.<br />로그인 하시겠습니까?</div>
			<div class="social-login">
			<v-flex class="d-flex flex-clumn align-center justify-center ma-3">
				<a id="custom-login-btn" @click="kakaoLogin">
					<v-img src="@/assets/images/login/kakao_login.png"/>
				</a>
			</v-flex>
			<v-flex class="d-flex flex-column align-center justify-center"></v-flex>
		</div>
	</v-container>
</template>

<script>
export default {
	name: "kakaoLogin",
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

<style>
.login {
  background-color: #ffffff;
  margin-top: 10%;
  padding-top: 30%;
}

.login-title {
  font-family: var(--main-font-3);
  text-align: center;
  font-size: 30px;

  padding-top: 10px;
  padding-bottom: 10px;
}

.login-text {
  font-family: var(--main-font-2);
  color: var(--graph-2-col-1);
  text-align: center;

  margin-top: 10%;
}

.blur-on-hover:hover {
  transform: scale(1.05);
  filter: brightness(0.9);
  cursor: pointer;
}

.container {
  background-color: "#ffffff";
  max-width: 20rem;
  height: 40rem;
}

.social-login {
  margin-top: 10%;
}
</style>
