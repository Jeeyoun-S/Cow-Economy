package com.coweconomy.api.controller;

import com.coweconomy.api.request.UserRegisterPostReq;
import com.coweconomy.api.response.BaseResponse;
import com.coweconomy.common.jwt.JwtTokenUtil;
import com.coweconomy.domain.user.entity.User;
import com.coweconomy.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@Api(value = "인증 API", tags = {"Auth."})
@RestController
@RequestMapping("/auth")
public class KakaoController {
    @Autowired
    UserService userService;
    private final String KAKAO_TOKEN_REQUEST_URL = "https://kauth.kakao.com/oauth/token";
    private final String KAKAO_USER_INFO_REQUEST_URL = "https://kapi.kakao.com/v2/user/me";
    private final String CLIENT_ID = "a8424f450f05d7160ccc24288e86ec14";
    private final String REDIRECT_URI = "http://localhost:3000/oauth/callback/kakao";

    /**
     * RedirectUri에서 사용자 정보 추출
     * @param code
     */
    @GetMapping("/login/kakao")
    public BaseResponse<?> kakaoLogin(@RequestParam("code") String code) {
        System.out.println("#1");
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", CLIENT_ID);
        params.add("redirect_uri", REDIRECT_URI);
        params.add("code", code);

        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());

        System.out.println("#2");
        // 카카오에서 받은 code를 통해 엑세스 토큰 받아오기
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<Map> response = restTemplate.exchange(KAKAO_TOKEN_REQUEST_URL, HttpMethod.POST, request, Map.class);
        Map<String, Object> responseBody = response.getBody();

        String token = (String) responseBody.get("access_token");

        /*사용자 정보 받아오기 함수화 필요!!*/
        // 액세스 토큰을 사용하여 유저 정보를 가져오기
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> requestUser = new HttpEntity<>(headers);
        ResponseEntity<Map> responseUser = restTemplate.exchange(KAKAO_USER_INFO_REQUEST_URL, HttpMethod.GET, requestUser, Map.class);
        Map<String, Object> responseUserBody = responseUser.getBody();
        System.out.println("#3");
        // 사용자 정보에서 ID(email)를 가져오기
        Map<String, Object> kakaoAccount = (Map<String, Object>) responseUserBody.get("kakao_account");
        String email = (String) kakaoAccount.get("email");

        System.out.println("#21# Kakao nickname 가져오기: " + kakaoAccount.get("profile"));
        System.out.println("#4");
        User user = userService.getUserByUserEmail(email);
        System.out.println("#5");
        System.out.println("#6");
        if (user==null) {
            //회원가입
            UserRegisterPostReq req = new UserRegisterPostReq();
            System.out.println(kakaoAccount);

            req.setUserEmail(email);
//            req.setUserNickname(kakaoAccount.get("profile").get("nickname").toString());
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            req.setUserNickname((String) profile.get("nickname"));
            req.setUserExperience(0);
            req.setUserLevel(1);
            System.out.println("#7");
            User newUser = userService.createUser(req);
        }
        Long id = user.getUserId();

        System.out.println("#8");
        String accessToken = JwtTokenUtil.getAccessToken(email, id);
        String refreshToken = JwtTokenUtil.getRefreshToken(email, id);

        // refreshToken db 저장
        boolean isSaved = userService.isTokenSaved(email, refreshToken);
        if(!isSaved) {
            return BaseResponse.fail();
        }

        Map<String, Object> result = new HashMap<>();
        result.put("accessToken", accessToken);
        result.put("refreshToken", refreshToken);

//        // 로그인 또는 회원가입 처리
//        ResponseEntity<LoginResponse> responseEntity = (ResponseEntity<LoginResponse>) userController.loginOrRegister(email);
//        UserLoginPostResDto responseDto = responseEntity.getBody().getData();
//
//        // JWT 토큰과 회원 가입 여부를 프론트엔드로 전달하기 위해 리다이렉트 URL에 파라미터를 추가합니다.
//        String jwtToken = responseDto.getToken();

        return BaseResponse.success(result);
    }
}
