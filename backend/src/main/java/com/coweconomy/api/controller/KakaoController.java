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
@RestController
@RequestMapping("/auth")
public class KakaoController {

    private static final Logger logger = LoggerFactory.getLogger(KakaoController.class);
    private static String KAKAO_TOKEN_REQUEST_URL;
    private static String KAKAO_USER_INFO_REQUEST_URL;
    private static String CLIENT_ID;
    private static String REDIRECT_URI;

    @Autowired
    UserService userService;

    @Autowired
    public KakaoController(
            @Value("${jwt.kakaoTokenRequestUrl}") String kakaoTokenRequestUrl,
            @Value("${jwt.kakaoUserInfoRequestUrl}") String kakaoUserInfoRequestUrl,
            @Value("${jwt.clientId}") String clientId,
            @Value("${jwt.redirectUri}") String redirectUri
    )
    {
        this.KAKAO_TOKEN_REQUEST_URL = kakaoTokenRequestUrl;
        this.KAKAO_USER_INFO_REQUEST_URL = kakaoUserInfoRequestUrl;
        this.CLIENT_ID = clientId;
        this.REDIRECT_URI = redirectUri;
    }

    /**
     * RedirectUri에서 카카오 토큰을 사용하여 사용자 정보 추출 후
     * AccessToken 및 RefreshToken 발급
     * @param code
     * @return
     */
    @GetMapping("/login/kakao")
    public BaseResponse<?> kakaoLogin(@RequestParam("code") String code) {

        // kakao token 발급을 위한 header 설정
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        
        // kakao token 발급을 위한 params 설정
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", CLIENT_ID);
        params.add("redirect_uri", REDIRECT_URI);
        params.add("code", code);

        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());

        // code를 통해 카카오 엑세스 토큰 받아오기
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<Map> response = restTemplate.exchange(KAKAO_TOKEN_REQUEST_URL, HttpMethod.POST, request, Map.class);
        Map<String, Object> responseBody = response.getBody();

        // 카카오 토큰
        String token = (String) responseBody.get("access_token");

        // 카카오 토큰을 사용하여 유저 정보를 가져오기
        restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> requestUser = new HttpEntity<>(headers);
        ResponseEntity<Map> responseUser = restTemplate.exchange(KAKAO_USER_INFO_REQUEST_URL, HttpMethod.GET, requestUser, Map.class);
        Map<String, Object> responseUserBody = responseUser.getBody();

        // 사용자 정보에서 ID(email)를 가져오기
        Map<String, Object> kakaoAccount = (Map<String, Object>) responseUserBody.get("kakao_account");
        String email = (String) kakaoAccount.get("email");
        Long id;
        
        // 로그인한 유저의 email로 user 객체 가져오기
        User user = userService.getUserByUserEmail(email);
        
        // 회원가입을 하지 않은 경우(db에 유저가 없을 경우)
        if (user==null) {
            //회원가입 진행
            UserRegisterPostReq req = new UserRegisterPostReq();
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

        // user의 email과 id로 accessToken, refreshToken 발급
        String accessToken = JwtTokenUtil.getAccessToken(email, id);
        String refreshToken = JwtTokenUtil.getRefreshToken(email, id);

        // refreshToken은 db에 저장
        boolean isSaved = userService.isTokenSaved(email, refreshToken);
        if(!isSaved) {
            return BaseResponse.fail();
        }

        Map<String, Object> result = new HashMap<>();
        result.put("accessToken", accessToken);
        result.put("refreshToken", refreshToken);

        return BaseResponse.success(result);
    }

    /**
     * 카카오 로그아웃
     * 카카오 연결 끊기 추가 시 로그아웃 성공 처리 작성
     * @param accessToken
     * @return
     */
    @PostMapping("/logout/kakao")
    public BaseResponse<?> kakaoLogout(@RequestParam("accessToken") String accessToken) {
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
            // 로그아웃 실패
            return BaseResponse.fail();
        }
    }
}
