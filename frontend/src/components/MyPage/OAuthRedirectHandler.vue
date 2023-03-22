<template>
  <div>
  </div>
</template>
<script>
import { getToken } from "@/api/user";

// 1. kakao redirect uri 를 mypage 페이지에서 code를 받을 수 있도록
// 2. mypage에서 code를 받고 
// 3. code를 가지고 userStore에 getToken 함수를 호출 
//    ! getToken 함수는 지금 이 페이지에 있는 getToken 함수 
// 4. user.api에 있는 getToken 함수 실행
// 5. 그 결과값이 userStore에 있는 getToken으로 올 것이고
// 6. 필요한 token이나 변수 상태?를 userStore에 state에 저장
// 7. mypage에 userStore의 state를 가져와서 state가 로그인되어 있다면 kakaologin 컴포넌트가 안보이도록 설정
// 
// vue에서 store에 있는 getToken1을 호출 -> store에 있는 getToken1이 동작되면서 - api에 있는 getToken 함수를 호출 -> 
// api에 있는 getToken 함수가 동작 -> 결과가 store에 있는 getToken1 함수에 저장됨 ->
// 필요한 상태값들은 store의 state(isLogin, token 등등 변수)에 저장 (! 저장할 땐 꼭 SET_TOKEN 같은 mutation으로 해야함)
// ! 주의사항 
// vue에서 부르는 함수 이름은 store에 있는 함수 이름이랑 동일해야함 getToken({ commit }, parameter) {  await getTokenAPI }
// > vue에서 ...mapActions(store이름, ["함수 이름"]) 선언하고 this.함수이름(); 이때 함수 이름 동일해야댐
//
// store에서 부르는 api 함수는 api함수 이름이랑 동일해야함
// api에서 함수를 export, store에서 해당 함수를 import해서 사용 

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
</script>