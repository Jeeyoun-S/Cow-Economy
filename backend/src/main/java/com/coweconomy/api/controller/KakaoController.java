package com.coweconomy.api.controller;

import com.coweconomy.api.request.UserRegisterPostReq;
import com.coweconomy.api.response.BaseResponse;
import com.coweconomy.common.jwt.JwtTokenUtil;
import com.coweconomy.domain.user.entity.User;
import com.coweconomy.service.UserService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    private static final Logger logger = LoggerFactory.getLogger(KakaoController.class);
    @Autowired
    UserService userService;
    
    // 이거 다 yml으로 빼기
//    @Value("${jwt.kakaoTokenRequestUrl}")
    private final String KAKAO_TOKEN_REQUEST_URL = "https://kauth.kakao.com/oauth/token";
    private final String KAKAO_USER_INFO_REQUEST_URL = "https://kapi.kakao.com/v2/user/me";
    private final String CLIENT_ID = "a8424f450f05d7160ccc24288e86ec14";
    private final String REDIRECT_URI = "http://localhost:3000/my-page";

    /**
     * RedirectUri에서 사용자 정보 추출
     * @param code
     */
    @GetMapping("/login/kakao")
    public BaseResponse<?> kakaoLogin(@RequestParam("code") String code) {
        logger.info("#[KakaoController]# KakaoLogin 동작 - code: {}", code);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", CLIENT_ID);
        params.add("redirect_uri", REDIRECT_URI);
        params.add("code", code);

        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());

        // 카카오에서 받은 code를 통해 엑세스 토큰 받아오기
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<Map> response = restTemplate.exchange(KAKAO_TOKEN_REQUEST_URL, HttpMethod.POST, request, Map.class);
        Map<String, Object> responseBody = response.getBody();

        String token = (String) responseBody.get("access_token");
        logger.info("#21# Kakao Token 확인: {}", token);

        // 액세스 토큰을 사용하여 유저 정보를 가져오기
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> requestUser = new HttpEntity<>(headers);
        ResponseEntity<Map> responseUser = restTemplate.exchange(KAKAO_USER_INFO_REQUEST_URL, HttpMethod.GET, requestUser, Map.class);
        Map<String, Object> responseUserBody = responseUser.getBody();
        logger.info("#21# Kakao User Info 확인: {}", responseUserBody);

        // 사용자 정보에서 ID(email)를 가져오기
        Map<String, Object> kakaoAccount = (Map<String, Object>) responseUserBody.get("kakao_account");
        String email = (String) kakaoAccount.get("email");
        Long id;
        User user = userService.getUserByUserEmail(email);
        if (user==null) {
            //회원가입
            UserRegisterPostReq req = new UserRegisterPostReq();
            System.out.println(kakaoAccount);

            req.setUserEmail(email);
            Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
            req.setUserNickname((String) profile.get("nickname"));
            req.setUserExperience(0);
            req.setUserLevel(1);
            User newUser = userService.createUser(req);

            id = newUser.getUserId();
        } else {
            id = user.getUserId();
        }


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

        return BaseResponse.success(result);
    }

    @PostMapping("/logout/kakao")
    public BaseResponse<?> kakaoLogout(@RequestParam("accessToken") String accessToken) {
        logger.info("#[KakaoController]# KakaoLogout 동작 - accessToken: {}", accessToken);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<Map> response =restTemplate.exchange("http://kapi.kakao.com/v1/user/logout", HttpMethod.POST, request, Map.class);
        int responseStatusCode = response.getStatusCodeValue();

        if (responseStatusCode == 200) {
            // 로그아웃 성공 처리 하기(토큰 무효화 등등)
            return BaseResponse.success("카카오 로그아웃 성공");
        } else {
            // 로그아웃 실패 처리
            return BaseResponse.fail();
        }
    }
}
