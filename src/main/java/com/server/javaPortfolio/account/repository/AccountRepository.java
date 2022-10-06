package com.server.javaPortfolio.account.repository;

import com.server.javaPortfolio.account.entity.AccountEntity;
import com.server.javaPortfolio.account.service.AccountService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    AccountEntity findByUserId(String user_id);

    AccountEntity findByUserEmail(String email);

    AccountEntity findByNickName(String nickName);
}
