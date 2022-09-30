package com.server.javaPortfolio.email.entity;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class EmailRequest {

    @Email
    @NotBlank(message = "이메일은 필수")
    private String email;
}
