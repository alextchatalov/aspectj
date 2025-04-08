package com.example.aspectj.core.usecase;

import com.example.aspectj.core.facade.DeleteAccountUseCase;
import com.example.aspectj.log.annotation.Loggable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeletetAccountUseCaseImpl implements DeleteAccountUseCase {
    @Override
    @Loggable
    public void execute(UUID id) {
        System.out.println("Deleting account " + id);
    }
}
