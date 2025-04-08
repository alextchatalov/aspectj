package com.example.aspectj.core.facade;

import com.example.aspectj.core.dto.Account;

import java.util.UUID;

public interface GetAccountUseCase {

    Account execute(UUID id);
}
