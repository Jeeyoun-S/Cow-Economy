import { getToken } from "@/api/user"

export default {
    async created() {
			await getToken(
				async ({ data }) => {
				console.log("#21# getToken 실행결과: ", data );
				// i) 성공
				if (data.statusCode == 200) {
						console.log("토큰 발급 성공", data);
						const ACCESS_TOKEN = data.data.data.acessToken;
						localStorage.setItem("access-token", ACCESS_TOKEN);
						window.location.replace("/home");
				}
			},
			// ii) 실패
			(error) => {
			console.log(error);
			}
		)
	}
}