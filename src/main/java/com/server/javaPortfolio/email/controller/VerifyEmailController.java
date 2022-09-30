package com.server.javaPortfolio.email.controller;

import com.server.javaPortfolio.email.entity.EmailRequest;
import com.server.javaPortfolio.email.service.VerifyEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class VerifyEmailController {

    private final VerifyEmailService verifyEmailService;

    @PostMapping("/api/sendEmail")
    public ResponseEntity authEmail(@RequestBody @Valid EmailRequest request) {

        return ResponseEntity.status(HttpStatus.OK).body(verifyEmailService.authEmail(request));
    }

    @GetMapping("/api/checkAuthCode")
    public ResponseEntity getAuthCode(@RequestParam String requestCode, @RequestParam String email) {

        return ResponseEntity.status(HttpStatus.OK).body(verifyEmailService.emailVerification( requestCode, email ));
    }
}
