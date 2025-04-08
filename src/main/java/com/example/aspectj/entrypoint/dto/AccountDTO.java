package com.example.aspectj.entrypoint.dto;

import com.example.aspectj.core.dto.Account;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class AccountDTO {
    private String id;
    private String username;
    private String password;

    public static AccountDTO toReponse(Account account) {
        return AccountDTO.builder()
                .id(account.getId().toString())
                .username(account.getUsername())
                .build();
    }

    public Account toDomain() {
        return Account.builder()
                .id(UUID.fromString(id))
                .username(username)
                .password(password)
                .build();
    }
}
