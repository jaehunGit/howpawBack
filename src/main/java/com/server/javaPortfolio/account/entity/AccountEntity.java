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

    @Column(columnDefinition = "VARCHAR(50) comment 'userID'", nullable = false)
    private String userId;

    @Column(columnDefinition = "VARCHAR(50) comment 'user_password'", nullable = false)
    private String userPassword;

    @Column(columnDefinition = "VARCHAR(50) comment 'nick_name'", nullable = false)
    private String nickName;

    @Column(columnDefinition = "VARCHAR(100) comment 'user_email'", nullable = false)
    private String userEmail;

    @Column(columnDefinition = "VARCHAR(255) comment 'favorite_product'", nullable = false)
    private String favoriteProduct;

    @Column(columnDefinition = "VARCHAR(255) comment 'SNS타입'", nullable = false)
    private String snsType;

    @Column(columnDefinition = "DATETIME comment '로그인 접속시간'", nullable = false)
    private LocalDateTime lastConnectedDateTime;
}
