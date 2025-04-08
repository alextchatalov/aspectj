package com.example.aspectj.entrypoint;

import com.example.aspectj.core.facade.CreateAccountUseCase;
import com.example.aspectj.core.facade.DeleteAccountUseCase;
import com.example.aspectj.core.facade.GetAccountUseCase;
import com.example.aspectj.entrypoint.dto.AccountDTO;
import com.example.aspectj.log.annotation.Loggable;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountRequest {

    private final GetAccountUseCase getAccountUseCase;
    private final CreateAccountUseCase createAccountUseCase;
    private final DeleteAccountUseCase deleteAccountUseCase;

    @GetMapping("/{id}")
    @Loggable
    public ResponseEntity<AccountDTO> account(@PathVariable UUID id) {
        return new ResponseEntity<>(AccountDTO.toReponse(getAccountUseCase.execute(id)), HttpStatus.OK);
    }

    @PostMapping
    @Loggable
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO) {
                return new ResponseEntity<>(AccountDTO.toReponse(createAccountUseCase.execute(accountDTO.toDomain())), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Loggable
    public ResponseEntity<Void> deleteAccount(@PathVariable String id) {
        deleteAccountUseCase.execute(UUID.fromString(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
