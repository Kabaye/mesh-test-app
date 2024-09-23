package com.test.service;

import com.test.entity.EmailData;
import com.test.entity.User;
import com.test.repository.AccountRepository;
import com.test.repository.EmailDataRepository;
import com.test.repository.UserRepository;
import com.test.security.jwt.JwtUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;

class UserServiceTest {
    private static final String TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE3MjY3Mjk5ODksImV4cCI6MTcyNjgxNjM4OSwiVVNFUl9JRCI6ImtpYXJhbi5jYWJyZXJhQGhvdG1haWwuY29tIn0.6CBeQusC_9-_s4pXUWXJR6UAGfvPwVSZgeSFlL2Xn1iOK2WfT_I82hkc22KKTMNluIiJIxUAAg-c13ISvssGpA";

    UserService userService;
    UserRepository userRepository;
    EmailDataRepository emailDataRepository;
    AccountRepository accountRepository;
    JwtUtils jwtUtils;

    @BeforeEach
    public void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        emailDataRepository = Mockito.mock(EmailDataRepository.class);
        accountRepository = Mockito.mock(AccountRepository.class);
        jwtUtils = Mockito.mock(JwtUtils.class);
        userService = new UserService(userRepository, emailDataRepository, accountRepository, jwtUtils);
    }

    @Test
    void givenInvalidEmail_whenTransferMoney_thenThrowException() {
        try (MockedStatic<RequestContextHolder> mockStatic = Mockito.mockStatic(RequestContextHolder.class)) {
            ServletRequestAttributes attributes = Mockito.mock(ServletRequestAttributes.class);
            HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
            Mockito.when(request.getHeader("Authorization")).thenReturn("Bearer " + TOKEN);
            Mockito.when(attributes.getRequest()).thenReturn(request);
            mockStatic.when(RequestContextHolder::currentRequestAttributes).thenReturn(attributes);

            Mockito.when(jwtUtils.getUsernameClaimFromToken(TOKEN)).thenReturn("kiaran.cabrera@hotmail.com");

            Mockito.when(emailDataRepository.findByEmail("kiaran.cabrera@hotmail.com")).thenThrow(IllegalStateException.class);

            Assertions.assertThrows(IllegalStateException.class, () -> userService.transferMoney("test@dot.com", BigDecimal.TEN));
        }
    }

    @Test
    void givenSameUser_whenTransferMoney_thenThrowException() {
        try (MockedStatic<RequestContextHolder> mockStatic = Mockito.mockStatic(RequestContextHolder.class)) {
            ServletRequestAttributes attributes = Mockito.mock(ServletRequestAttributes.class);
            HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
            Mockito.when(request.getHeader("Authorization")).thenReturn("Bearer " + TOKEN);
            Mockito.when(attributes.getRequest()).thenReturn(request);
            mockStatic.when(RequestContextHolder::currentRequestAttributes).thenReturn(attributes);

            Mockito.when(jwtUtils.getUsernameClaimFromToken(TOKEN)).thenReturn("kiaran.cabrera@hotmail.com");

            Mockito.when(emailDataRepository.findByEmail("kiaran.cabrera@hotmail.com")).thenReturn(Optional.of(new EmailData()
                    .setEmail("kiaran.cabrera@hotmail.com")
                    .setUser(new User()
                            .setId(1L))
            ));

            Assertions.assertThrows(IllegalStateException.class, () -> userService.transferMoney("kiaran.cabrera@hotmail.com", BigDecimal.TEN));
        }
    }

    @Test
    void givenOKData_whenTransferMoney_thenVerifyTransfer() {
        try (MockedStatic<RequestContextHolder> mockStatic = Mockito.mockStatic(RequestContextHolder.class)) {
            ServletRequestAttributes attributes = Mockito.mock(ServletRequestAttributes.class);
            HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
            Mockito.when(request.getHeader("Authorization")).thenReturn("Bearer " + TOKEN);
            Mockito.when(attributes.getRequest()).thenReturn(request);
            mockStatic.when(RequestContextHolder::currentRequestAttributes).thenReturn(attributes);

            Mockito.when(emailDataRepository.findByEmail(anyString()))
                    .thenReturn(Optional.of(new EmailData()
                            .setEmail("kiaran.cabrera@hotmail.com")
                            .setUser(new User()
                                    .setId(1L))
                    ), Optional.of(new EmailData()
                            .setEmail("test@hotmail.com")
                            .setUser(new User()
                                    .setId(2L))
                    ));

            Mockito.when(jwtUtils.getUsernameClaimFromToken(TOKEN)).thenReturn("kiaran.cabrera@hotmail.com");

            Mockito.doNothing().when(accountRepository).transferMoney(1L, 2L, BigDecimal.TEN);

            userService.transferMoney("test@hotmail.com", BigDecimal.TEN);

            Mockito.verify(accountRepository, Mockito.times(1)).transferMoney(1L, 2L, BigDecimal.TEN);
            Mockito.verify(emailDataRepository, Mockito.times(2)).findByEmail(anyString());
        }
    }


}
