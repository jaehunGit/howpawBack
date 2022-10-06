package com.server.javaPortfolio.product.entity;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResponseMessage {

    private HttpStatus statusCode;
    private String message;
    private Integer code;
    private String id;
    private String nickName;
}
