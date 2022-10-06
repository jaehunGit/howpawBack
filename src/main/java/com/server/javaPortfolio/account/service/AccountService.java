package com.server.javaPortfolio.account.service;


import com.server.javaPortfolio.account.entity.AccountEntity;
import com.server.javaPortfolio.account.repository.AccountRepository;
import com.server.javaPortfolio.product.entity.ResponseMessage;
import javassist.tools.web.BadHttpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.management.BadAttributeValueExpException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    private final PasswordEncoder passwordEncoder;

    public ResponseMessage signUpUserService(AccountEntity accountEntity) {

        AccountEntity check = accountRepository.findByUserId(accountEntity.getUserId());

        String encodedPassword = passwordEncoder.encode(accountEntity.getUserPassword());


        if (check == null) {
            check = AccountEntity.builder()
                    .userId(accountEntity.getUserId())
                    .userPassword(encodedPassword)
                    .userEmail(accountEntity.getUserEmail())
                    .nickName(accountEntity.getNickName())
                    .snsType("none")
                    .lastConnectedDateTime(LocalDateTime.now())
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "중복된 ID 입니다.");
        }

        accountRepository.save(check);

        return ResponseMessage.builder().statusCode(HttpStatus.OK).message("회원가입 성공 !").build();
    }

    public ResponseMessage UserIdConfirmService(String userId) {
        AccountEntity check = accountRepository.findByUserId(userId);

        if (check != null) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "중복된 ID 입니다");
        }

        return ResponseMessage.builder().statusCode(HttpStatus.OK).message("사용할 수 있는 ID입니다.").build();
    }


    public ResponseMessage UserLoginService(AccountEntity accountEntity) {
        AccountEntity check = accountRepository.findByUserId(accountEntity.getUserId());

        String inputPassword = accountEntity.getUserPassword();

        if (check != null) {

            if (passwordEncoder.matches(inputPassword, check.getUserPassword())) {

                check.setLastConnectedDateTime(LocalDateTime.now());

                accountRepository.save(check);

                return ResponseMessage.builder().statusCode(HttpStatus.OK).message("로그인 성공!").nickName(check.getNickName()).build();
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 아이디 입니다.");
        }
    }

    public ResponseMessage addFavoriteProductService(String pdcNumber, String userId) {

        AccountEntity dbTable = accountRepository.findByUserId(userId);

        String temp = dbTable.getFavoriteProduct();


        if (temp == null) {
            temp = pdcNumber;
        } else {
            temp += "," + pdcNumber;
        }

        dbTable.setFavoriteProduct(temp);
        accountRepository.save(dbTable);

        return ResponseMessage.builder().statusCode(HttpStatus.OK).message("관심 상품 추가 완료!").build();
    }

    public ResponseMessage getFavoriteProductService(String pdcNumber, String userId) {

        AccountEntity dbTable = accountRepository.findByUserId(userId);

        String productList = dbTable.getFavoriteProduct();

        String[] temp = productList.split(",");

        String result = Arrays.stream(temp)
                .filter(p -> p.equals(pdcNumber))
                .findAny()
                .orElse(null);

        if (result == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } else {
            return ResponseMessage.builder().statusCode(HttpStatus.OK).build();
        }
    }

    public ResponseMessage removeFavoriteProductService(String pdcNumber, String userId) {
        AccountEntity dbTable = accountRepository.findByUserId(userId);

        String productList = dbTable.getFavoriteProduct();

        String[] temp = productList.split(",");

        temp = Arrays.stream(temp)
                .filter(p -> !p.equals(pdcNumber))
                .toArray(String[]::new);

        String result = String.join(",", temp);

        dbTable.setFavoriteProduct(result);

        accountRepository.save(dbTable);

        return ResponseMessage.builder().statusCode(HttpStatus.OK).build();
    }


}
