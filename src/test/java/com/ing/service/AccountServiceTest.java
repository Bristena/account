package com.ing.service;

import com.ing.dao.AccountDao;
import com.ing.dao.UserDao;
import com.ing.domain.Account;
import com.ing.domain.User;
import com.ing.dto.AccountDTO;
import com.ing.exception.PreconditionFailedException;
import com.ing.mapper.AccountMapper;
import com.ing.utils.DateTimeUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AccountServiceTest {
    @Mock
    private AccountDao accountDao;
    @Mock
    private DateTimeUtils dateTimeUtils;
    @Mock
    private AccountMapper accountMapper;
    @Mock
    private UserDao userDao;
    private AccountService accountService;
    private User user;
    private Account account;
    private AccountDTO accountDTO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        accountService = new AccountService(accountDao, dateTimeUtils, userDao, accountMapper);
        user = User.builder()
                .id(1)
                .name("Donald Duck")
                .details("details").build();
        account = Account.builder()
                .user(user)
                .iban("iban")
                .name("Savings account")
                .build();
        accountDTO = AccountDTO.builder()
                .userId(1)
                .iban("iban")
                .name("Savings account")
                .build();
    }

    @Test
    public void createAccount() {
        //given
        LocalDateTime weekLocalDateTime = LocalDateTime.of(2020, 8, 20, 11, 5, 56);
        when(accountDao.findByUserId(1)).thenReturn(Optional.empty());
        when(dateTimeUtils.nowUTC()).thenReturn(weekLocalDateTime);
        when(accountDao.save(account)).thenReturn(account);
        when(userDao.findById(1)).thenReturn(Optional.of(user));
        when(accountMapper.dtoToDomain(accountDTO)).thenReturn(account);

        //when
        Account savedAccount = accountService.createAccount(accountDTO);

        //then
        Assertions.assertNotNull(savedAccount);
        verify(accountDao).findByUserId(1);
        verify(accountDao).save(account);
        verify(userDao).findById(1);
    }

    @Test(expected = PreconditionFailedException.class)
    public void createAccountForUserThatAlreadyHasAnAccount() {
        //given
        LocalDateTime weekLocalDateTime = LocalDateTime.of(2020, 8, 20, 11, 5, 56);
        when(accountDao.findByUserId(1)).thenReturn(Optional.of(account));
        when(dateTimeUtils.nowUTC()).thenReturn(weekLocalDateTime);
        when(userDao.findById(1)).thenReturn(Optional.of(user));
        when(accountMapper.dtoToDomain(accountDTO)).thenReturn(account);

        //when
        Account savedAccount = accountService.createAccount(accountDTO);

        //then
        Assertions.assertNull(savedAccount);
        verify(accountDao).findByUserId(1);
        verify(userDao).findById(1);
    }

    @Test(expected = PreconditionFailedException.class)
    public void createAccountOnWeekdayAfterHours() {
        //given
        LocalDateTime weekLocalDateTime = LocalDateTime.of(2020, 8, 20, 20, 5, 56);
        when(accountDao.findByUserId(1)).thenReturn(Optional.empty());
        when(dateTimeUtils.nowUTC()).thenReturn(weekLocalDateTime);
        when(accountDao.save(account)).thenReturn(account);
        when(userDao.findById(1)).thenReturn(Optional.of(user));
        when(accountMapper.dtoToDomain(accountDTO)).thenReturn(account);

        //when
        Account savedAccount = accountService.createAccount(accountDTO);

        //then
        Assertions.assertNull(savedAccount);
        verify(accountDao).findByUserId(1);
        verify(userDao).findById(1);
    }

    @Test(expected = PreconditionFailedException.class)
    public void createAccountOnWeekdayBeforeHours() {
        //given
        LocalDateTime weekLocalDateTime = LocalDateTime.of(2020, 8, 20, 8, 59, 56);
        when(accountDao.findByUserId(1)).thenReturn(Optional.empty());
        when(dateTimeUtils.nowUTC()).thenReturn(weekLocalDateTime);
        when(accountDao.save(account)).thenReturn(account);
        when(userDao.findById(1)).thenReturn(Optional.of(user));
        when(accountMapper.dtoToDomain(accountDTO)).thenReturn(account);

        //when
        Account savedAccount = accountService.createAccount(accountDTO);

        //then
        Assertions.assertNull(savedAccount);
        verify(accountDao).findByUserId(1);
        verify(userDao).findById(1);
    }

    @Test(expected = PreconditionFailedException.class)
    public void createAccountOnWeekend() {
        //given
        LocalDateTime weekLocalDateTime = LocalDateTime.of(2020, 8, 23, 10, 5, 56);
        when(accountDao.findByUserId(1)).thenReturn(Optional.empty());
        when(dateTimeUtils.nowUTC()).thenReturn(weekLocalDateTime);
        when(accountDao.save(account)).thenReturn(account);
        when(userDao.findById(1)).thenReturn(Optional.of(user));
        when(accountMapper.dtoToDomain(accountDTO)).thenReturn(account);

        //when
        Account savedAccount = accountService.createAccount(accountDTO);

        //then
        Assertions.assertNull(savedAccount);
        verify(accountDao).findByUserId(1);
        verify(userDao).findById(1);
    }

    @Test(expected = PreconditionFailedException.class)
    public void createAccountNotFoundUser() {
        //given
        LocalDateTime weekLocalDateTime = LocalDateTime.of(2020, 8, 23, 10, 5, 56);
        when(accountDao.findByUserId(1)).thenReturn(Optional.empty());
        when(dateTimeUtils.nowUTC()).thenReturn(weekLocalDateTime);
        when(accountDao.save(account)).thenReturn(account);
        when(userDao.findById(1)).thenReturn(Optional.empty());
        when(accountMapper.dtoToDomain(accountDTO)).thenReturn(account);

        //when
        Account savedAccount = accountService.createAccount(accountDTO);

        //then
        Assertions.assertNull(savedAccount);
        verify(accountDao).findByUserId(1);
        verify(userDao).findById(1);
    }

}