package com.test.service;

import com.test.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ScheduledBalanceUpdateService {
    private final AccountRepository accountRepository;

    @Scheduled(fixedDelay = 30, timeUnit = TimeUnit.SECONDS)
    public void scheduledBalanceUpdate() {
        accountRepository.updateBalanceForAllAccounts();
    }
}
