package com.server.javaPortfolio.account.controller;

import com.server.javaPortfolio.account.entity.AccountEntity;
import com.server.javaPortfolio.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "localhost:3000")
@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/api/signUpUser")
    public ResponseEntity SignUpUser(@RequestBody AccountEntity accountEntity, Error error) {


        return ResponseEntity.status(HttpStatus.OK).body(accountService.signUpUserService(accountEntity));
    }

    @GetMapping("/api/UserIdConfirm")
    public ResponseEntity UserIdConfirm(@RequestParam String userId) {

        return ResponseEntity.status(HttpStatus.OK).body(accountService.UserIdConfirmService(userId));
    }

    @PostMapping("/api/Login")
    public ResponseEntity UserLogin(@RequestBody AccountEntity accountEntity) {

        return ResponseEntity.status(HttpStatus.OK).body(accountService.UserLoginService(accountEntity));
    }


    @PostMapping("/api/AddFavoriteProduct")
    public ResponseEntity addFavoriteProduct(@RequestBody Map<String,String> map) {

        return ResponseEntity.status(HttpStatus.OK).body(accountService.addFavoriteProductService(map.get("pdcNumber"), map.get("userId")));
    }

    @GetMapping("/api/getFavoriteProduct")
    public ResponseEntity getFavoriteProduct(@RequestParam String pdcNumber, String userId) {

        return ResponseEntity.status(HttpStatus.OK).body(accountService.getFavoriteProductService(pdcNumber, userId));
    }


    @PostMapping("/api/removeFavoriteProduct")
    public ResponseEntity removeFavoriteProduct(@RequestBody Map<String,String> map) {

        return ResponseEntity.status(HttpStatus.OK).body(accountService.removeFavoriteProductService(map.get("pdcNumber"), map.get("userId")));
    }
}
