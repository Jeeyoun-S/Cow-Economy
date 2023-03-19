package com.coweconomy.api.controller;

import com.coweconomy.api.response.LoginResponse;
import com.coweconomy.api.response.UserLoginResponseDto;
import com.coweconomy.domain.user.entity.User;
import com.coweconomy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.xml.ws.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class KakaoController {

    private final UserService userService;

    private final String KAKAO_TOKEN_REQUEST_URL = "https://kauth.kakao.com/oauth/token";
    private final String KAKAO_USER_INFO_REQUEST_URL = "https://kapi.kakao.com/v2/user/me";
    private final String CLIENT_ID = "a8424f450f05d7160ccc24288e86ec14";
    private final String REDIRECT_URI = "http://localhost:8080/kakao/callback";

    private final String FRONTEND_REDIRECT_URI = "http://localhost:3000/home";


    public KakaoController(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private UserController userController;
    /**
     * RedirectUri에서 사용자 정보 추출
     * @param code
     */
    @GetMapping("/kakao/callback")
    public ResponseEntity<?> kakaoCallback(@RequestParam("code") String code) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new FormHttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", CLIENT_ID);
        params.add("redirect_uri", REDIRECT_URI);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<Map> response = restTemplate.exchange(KAKAO_TOKEN_REQUEST_URL, HttpMethod.POST, request, Map.class);
        Map<String, Object> responseBody = response.getBody();

        String accessToken = (String) responseBody.get("access_token");
        System.out.println("여기까지는 오나?");
        // 액세스 토큰을 사용하여 유저 정보를 가져오기
        return getUserInfo(accessToken);
    }

    /**
     * 카카오 토큰을 사용하여 유저 정보 가져오기
     * @param accessToken : 카카오에서 받은 code를 기반으로 api 요청을 사용해 제공받은 accessToken
     */
    private ResponseEntity<?> getUserInfo(String accessToken) throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(KAKAO_USER_INFO_REQUEST_URL, HttpMethod.GET, request, Map.class);
        Map<String, Object> responseBody = response.getBody();

        // 사용자 정보에서 ID(email)를 가져오기
        Map<String, Object> kakaoAccount = (Map<String, Object>) responseBody.get("kakao_account");
        String email = (String) kakaoAccount.get("email");
        System.out.println("email : " + email);

        // 로그인 또는 회원가입 처리
        ResponseEntity<LoginResponse> responseEntity = (ResponseEntity<LoginResponse>) userController.loginOrRegister(email);
        UserLoginResponseDto responseDto = responseEntity.getBody().getData();

        // JWT 토큰과 회원 가입 여부를 프론트엔드로 전달하기 위해 리다이렉트 URL에 파라미터를 추가합니다.
        String jwtToken = responseDto.getToken();
        boolean isRegistered = responseDto.isRegistered();
        String redirectUrl = FRONTEND_REDIRECT_URI + "?token=" + jwtToken + "&registered=" + isRegistered;

        // 프론트엔드로 리다이렉트합니다.
        HttpHeaders redirectHeaders = new HttpHeaders();
        redirectHeaders.setLocation(new URI(redirectUrl));
        return new ResponseEntity<>(redirectHeaders, HttpStatus.FOUND);

//        return responseEntity;
    }
}
