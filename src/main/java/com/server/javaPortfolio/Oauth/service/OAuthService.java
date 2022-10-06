
package com.server.javaPortfolio.Oauth.service;

import com.server.javaPortfolio.account.entity.*;
import com.server.javaPortfolio.account.repository.AccountRepository;
import com.server.javaPortfolio.product.entity.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OAuthService {

    private final AccountRepository accountRepository;

    public ResponseMessage createKakaoUser(MultiValueMap<String, String> parameters) {

        String reqURL = "https://kapi.kakao.com/v2/user/me";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "bearer " + parameters.get("token"));
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");


        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest =
                new HttpEntity<>(headers);


        ResponseEntity<KakaoUser> response = restTemplate.exchange(
                reqURL,
                HttpMethod.POST,
                kakaoProfileRequest,
                KakaoUser.class
        );

        AccountEntity account = accountRepository.findByUserId(response.getBody().getId());
        if (account == null) {
            account = AccountEntity.builder()
                    .userId(response.getBody().getId())
                    .nickName(response.getBody().getProperties().getNickname())
                    .snsType("kakao")
                    .lastConnectedDateTime(response.getBody().getConnected_at())
                    .userEmail(response.getBody().getKakao_account().getEmail())
                    .build();
        } else {
            account.setLastConnectedDateTime(LocalDateTime.now());
        }

        accountRepository.save(account);

        return ResponseMessage.builder().statusCode(HttpStatus.OK).message("good").id(response.getBody().getId()).nickName(response.getBody().getProperties().getNickname()).build();
    }

    public ResponseMessage GoogleInfoSave(MultiValueMap<String, String> parameters) {

        String reqUrl = "https://www.googleapis.com/oauth2/v3/userinfo";
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<GoogleUser> responseEntity = restTemplate.postForEntity(reqUrl, parameters, GoogleUser.class); // 여기서 에러가나더라구요 v1 v3 이거때문에 그런건가요..? 띠용

        AccountEntity account = accountRepository.findByUserId(responseEntity.getBody().getSub());

        if (account == null) {
            account = AccountEntity.builder().userEmail(responseEntity.getBody().getEmail())
                    .lastConnectedDateTime(LocalDateTime.now())
                    .userId(responseEntity.getBody().getSub())
                    .snsType("google")
                    .nickName(responseEntity.getBody().getName())
                    .build();
        } else {
            account.setLastConnectedDateTime(LocalDateTime.now());
        }

        accountRepository.save(account);

        return ResponseMessage.builder().statusCode(HttpStatus.OK).code(200).message("good").id(responseEntity.getBody().getSub()).nickName(responseEntity.getBody().getName()).build();
    }

    public ResponseMessage NaverInfoSave(MultiValueMap<String, String> parameters) {

        String testUrl = "https://openapi.naver.com/v1/nid/me";
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<NaverUser> responseEntity = restTemplate.postForEntity(testUrl, parameters, NaverUser.class);

        AccountEntity account = accountRepository.findByUserId(responseEntity.getBody().getResponse().getId());

        if (account == null) {
            account = AccountEntity.builder().userEmail(responseEntity.getBody().getResponse().getEmail())
                    .lastConnectedDateTime(LocalDateTime.now())
                    .userId(responseEntity.getBody().getResponse().getId())
                    .snsType("NAVER")
                    .nickName(responseEntity.getBody().getResponse().getNickname())
                    .build();
        } else {
            account.setLastConnectedDateTime(LocalDateTime.now());
        }

        accountRepository.save(account);

        return ResponseMessage.builder().statusCode(HttpStatus.OK).code(200).message("good").id(responseEntity.getBody().getResponse().getId()).nickName(responseEntity.getBody().getResponse().getNickname()).build();
    }
}
