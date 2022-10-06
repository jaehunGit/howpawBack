package com.server.javaPortfolio.account.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class KakaoUser {

    private String id; // 카카오 계정 고유 번호
    private Properties properties;
    private LocalDateTime connected_at;
    private KakaoAccount kakao_account;

    @Data
    public static class Properties {
        private String nickname;
    }
    @Data
    public static class KakaoAccount {

        private String email;
    }
}