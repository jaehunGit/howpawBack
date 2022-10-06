package com.server.javaPortfolio.account.entity;

import lombok.Data;

@Data
public class GoogleUser {

    private String sub; // 구글 계정 고유 번호
    private String name; // FirstName + LastName
    private String given_name; // FirstName
    private String family_name; // LastName
    private String email; // 이메일
    private String locale; // 지역
}
