package com.example.aspectj.core.usecase;

import com.example.aspectj.core.dto.Account;
import com.example.aspectj.core.facade.GetAccountUseCase;
import com.example.aspectj.log.annotation.Loggable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class GetAccountUseCaseImpl implements GetAccountUseCase {
    @Override
    @Loggable
    public Account execute(UUID id) {
        return Account.builder()
                .id(id)
                .username("Test")
                .password("123")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .active(true)
                .build();
    }
}
