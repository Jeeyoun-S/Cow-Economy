package com.coweconomy.api.controller;

import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
public class KakaoController {

    private final String KAKAO_TOKEN_REQUEST_URL = "https://kauth.kakao.com/oauth/token";
    private final String KAKAO_USER_INFO_REQUEST_URL = "https://kapi.kakao.com/v2/user/me";
    private final String CLIENT_ID = "a8424f450f05d7160ccc24288e86ec14";
    private final String REDIRECT_URI = "http://localhost:8080/kakao/callback";

    @GetMapping("/kakao/callback")
    public void kakaoCallback(@RequestParam("code") String code) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Accept", "application/json");

        Map<String, String> params = new HashMap<>();
        params.put("grant_type", "authorization_code");
        params.put("client_id", CLIENT_ID);
        params.put("redirect_uri", REDIRECT_URI);
        params.put("code", code);

        System.out.println(code);
        System.out.println(code);
        System.out.println(code);
        System.out.println(code);
        System.out.println(code);
        System.out.println(code);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(params, headers);
        System.out.println("안녕하신지");
        ResponseEntity<Map> response = restTemplate.exchange(KAKAO_TOKEN_REQUEST_URL, HttpMethod.POST, request, Map.class);
        System.out.println("안녕못하네");
        Map<String, Object> responseBody = response.getBody();

        String accessToken = (String) responseBody.get("access_token");
        // 액세스 토큰을 사용하여 유저 정보를 가져옵니다.
        getUserInfo(accessToken);
    }

    private void getUserInfo(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);


        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(KAKAO_USER_INFO_REQUEST_URL, HttpMethod.GET, request, Map.class);
        Map<String, Object> responseBody = response.getBody();

        // 사용자 정보에서 ID를 가져옵니다.
        Long userId = (Long) responseBody.get("id");
        System.out.println("Kakao User ID: " + userId);
    }
}
