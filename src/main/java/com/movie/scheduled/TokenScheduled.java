package com.movie.scheduled;

import com.movie.entity.Token;
import com.movie.repo.TokenRepository;
import com.movie.service.TokenService;
import com.movie.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
@EnableAsync
public class TokenScheduled {

    @Transactional
    @Async
    @Scheduled(cron = "0 0 5 * * *",zone = "Europe/Istanbul")
    @Autowired
    public void resetDailyRegisterToken(TokenService tokenService, UserService userService){
        tokenService.getAllByRegisterToken().stream()
                .filter(token -> token.getExDate().isBefore(LocalDateTime.now()))
                .forEach(token -> {
                    tokenService.deleteByToken(token);
                    userService.delete(token.getUser().getId());
                });
    }

    @Async
    @Scheduled(cron = "0 0 3 * * *",zone = "Europe/Istanbul")
    @Autowired
    public void resetDailyPasswordToken(TokenService tokenService){
        tokenService.getAllByPasswordToken().stream()
                .filter(token -> token.getExDate().isBefore(LocalDateTime.now()))
                .forEach(token -> tokenService.deleteByToken(token));
    }

}
