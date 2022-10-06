package com.server.javaPortfolio.account.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Entity(name = "account")
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(50) comment 'userID'", nullable = true)
    private String userId;

    @Column(columnDefinition = "VARCHAR(255) comment 'user_password'", nullable = true)
    private String userPassword;

    @Column(columnDefinition = "VARCHAR(50) comment 'nick_name'", nullable = true)
    private String nickName;

    @Column(columnDefinition = "VARCHAR(100) comment 'user_email'", nullable = true)
    private String userEmail;

    @Column(columnDefinition = "VARCHAR(255) comment 'favorite_product'", nullable = true)
    private String favoriteProduct;

    @Column(columnDefinition = "VARCHAR(255) comment 'SNS타입'", nullable = true)
    private String snsType;

    @Column(columnDefinition = "DATETIME comment '로그인 접속시간'", nullable = true)
    private LocalDateTime lastConnectedDateTime;
}
