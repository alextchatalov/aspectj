package com.example.aspectj.core.usecase;

import com.example.aspectj.core.dto.Account;
import com.example.aspectj.core.facade.CreateAccountUseCase;
import com.example.aspectj.core.facade.GetAccountUseCase;
import com.example.aspectj.log.annotation.Loggable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateAccountUseCaseImpl implements CreateAccountUseCase {

    @Override
    @Loggable
    public Account execute(Account account) {
        return account;
    }
}
