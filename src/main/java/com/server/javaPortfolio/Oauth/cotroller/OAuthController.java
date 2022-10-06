package com.server.javaPortfolio.Oauth.cotroller;

import com.server.javaPortfolio.Oauth.service.OAuthService;
import com.server.javaPortfolio.account.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthService oAuthService;

    @GetMapping("/api/getKakaoToken")
    public ResponseEntity kakaoTokenController(@RequestParam String code) {

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        String reqUrl = "https://kauth.kakao.com/oauth/token";
        RestTemplate restTemplate = new RestTemplate();

        parameters.add("grant_type", "authorization_code");
        parameters.add("client_id", "0647469b8a94c9ade88ef28aafddec73");
        parameters.add("redirect_uri", "http://localhost:3000/login/oauth2/code/kakao");
        parameters.add("code", code);

        ResponseEntity<KakaoUserInfo> responseEntity = restTemplate.postForEntity(reqUrl, parameters, KakaoUserInfo.class);

        parameters.clear();
        parameters.add("type", responseEntity.getBody().getToken_type());
        parameters.add("token", responseEntity.getBody().getAccess_token());

        return ResponseEntity.status(HttpStatus.OK).body(oAuthService.createKakaoUser(parameters));
    }

    @PostMapping("/api/GoogleLogin")
    public ResponseEntity GoogleLogin(@RequestBody Map<String, String> code) {


        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("client_id", "564148449868-pcan78kdgjib2rcu67173qiaa8ov6u8a.apps.googleusercontent.com");
        parameters.add("client_secret", "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");
        parameters.add("code", code.get("code"));
        parameters.add("grant_type", "authorization_code");
        parameters.add("redirect_uri", "http://localhost:3000/login/oauth2/code/google");

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<GoogleUserInfo> responseEntity = restTemplate.postForEntity("https://oauth2.googleapis.com/token", parameters, GoogleUserInfo.class);

        parameters.clear();
        parameters.add("alt", "json");
        parameters.add("access_token", responseEntity.getBody().getAccess_token());

        return ResponseEntity.status(HttpStatus.OK).body(oAuthService.GoogleInfoSave(parameters));
    }

    @PostMapping("/api/NaverLogin")
    public ResponseEntity NaverLogin(@RequestBody Map<String, String> code) {

        String reqUrl = "https://nid.naver.com/oauth2.0/token";

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("client_id", "xyGdQbw5wmMiIquTq6jP");
        parameters.add("client_secret", "vxd_E6jAqm");
        parameters.add("code", code.get("code"));
        parameters.add("grant_type", "authorization_code");
        parameters.add("redirect_uri", "http://localhost:3000/login/oauth2/code/naver");

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<NaverUserInfo> responseEntity = restTemplate.postForEntity(reqUrl, parameters, NaverUserInfo.class);


        parameters.clear();
        parameters.add("Authorization", "Bearer");
        parameters.add("access_token", responseEntity.getBody().getAccess_token());

        return ResponseEntity.status(HttpStatus.OK).body(oAuthService.NaverInfoSave(parameters));
    }
}

