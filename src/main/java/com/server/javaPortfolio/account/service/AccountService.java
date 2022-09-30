package com.server.javaPortfolio.account.service;


import com.server.javaPortfolio.account.entity.AccountEntity;
import com.server.javaPortfolio.account.repository.AccountRepository;
import com.server.javaPortfolio.product.entity.ResponseMessage;
import javassist.tools.web.BadHttpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.management.BadAttributeValueExpException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public ResponseMessage signUpUserService(AccountEntity accountEntity) {

        AccountEntity check = accountRepository.findByUserId(accountEntity.getUserId());

        if (check == null) {
            check = AccountEntity.builder()
                    .userId(accountEntity.getUserId())
                    .userPassword(accountEntity.getUserPassword())
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

            if (inputPassword.equals(check.getUserPassword())) {

                check.setLastConnectedDateTime(LocalDateTime.now());

                accountRepository.save(check);

                return ResponseMessage.builder().statusCode(HttpStatus.OK).message("로그인 성공!").build();
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 아이디 입니다.");
        }
    }
}
