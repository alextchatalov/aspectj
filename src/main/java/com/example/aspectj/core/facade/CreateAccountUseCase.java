package com.example.aspectj.core.facade;

import com.example.aspectj.core.dto.Account;

public interface CreateAccountUseCase {

    Account execute(Account account);
}
