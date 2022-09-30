package com.server.javaPortfolio.email.service;

import com.server.javaPortfolio.email.entity.EmailRequest;
import com.server.javaPortfolio.product.entity.ResponseMessage;
import com.server.javaPortfolio.redis.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class VerifyEmailService {

    private final JavaMailSender javaMailSender;
    private final RedisUtil redisUtil;


    @Transactional
    public ResponseEntity authEmail(EmailRequest request ) {
        Random random =new Random();
        String authKey = String.valueOf(random.nextInt(888888) + 111111);

        return ResponseEntity.status(HttpStatus.OK).body(sendAuthEmail(request.getEmail(), authKey));
    }

    public ResponseMessage sendAuthEmail(String email, String authKey) {

        String subject = "회원가입을 위한 인증번호입니다.";
        String text = "회원 가입을 위한 인증번호는 " + authKey + "입니다. <br/>";

        try {
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
                helper.setTo(email);
                helper.setSubject(subject);
                helper.setText(text, true);     //포함된 텍스트가 HTML이라는 의미로 true.
                javaMailSender.send(mimeMessage);

            } catch (MessagingException e) {
                e.printStackTrace();
            }

            // 유효 시간(5분)동안 {email, authKey} 저장
            redisUtil.setDataExpire(authKey, email, 60 * 5L);


        return ResponseMessage.builder().statusCode(HttpStatus.OK).message("인증번호를 보냈습니다.").build();
    }

    public ResponseMessage emailVerification( String requestCode, String email ) {

        if ( redisUtil.hasKey(requestCode) && redisUtil.getData(requestCode).equals(email) ) {

            redisUtil.deleteData( requestCode );

            return ResponseMessage.builder().statusCode(HttpStatus.OK).message("인증이 완료되었습니다.").build();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "인증 실패.");
        }
    }
}
