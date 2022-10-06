package com.server.javaPortfolio.account.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NaverUser {

    private Response response;

    @Data
    public static class Response {
        private String id;
        private String nickname;
        private String email;
    }
}
