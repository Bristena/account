package com.ing.service;


import com.ing.dao.AccountDao;
import com.ing.dao.UserDao;
import com.ing.domain.Account;
import com.ing.domain.User;
import com.ing.dto.AccountDTO;
import com.ing.exception.PreconditionFailedException;
import com.ing.mapper.AccountMapper;
import com.ing.utils.DateTimeUtils;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountDao accountDao;
    private final UserDao userDao;
    private final DateTimeUtils dateTimeUtils;
    private final AccountMapper accountMapper;

    public AccountService(AccountDao accountDao, DateTimeUtils dateTimeUtils, UserDao userDao, AccountMapper accountMapper) {
        this.accountDao = accountDao;
        this.userDao = userDao;
        this.dateTimeUtils = dateTimeUtils;
        this.accountMapper = accountMapper;
    }

    public Account createAccount(AccountDTO accountDTO) {
        Optional<User> user = userDao.findById(accountDTO.getUserId());
        if (user.isEmpty()) {
            throw new PreconditionFailedException("The user does not exist");
        } else {
            Optional<Account> existingAccount = accountDao.findByUserId(accountDTO.getUserId());
            if (existingAccount.isPresent()) {
                throw new PreconditionFailedException("The current user already has a savings account");
            } else {
                Account account = accountMapper.dtoToDomain(accountDTO);
                account.setCreatedAt(dateTimeUtils.nowUTC());
                account.setUser(user.get());
                if (checkSchedule(account.getCreatedAt())) {
                    return accountDao.save(account);
                } else {
                    throw new PreconditionFailedException("The account can be created only Monday-Friday within working hours");
                }
            }
        }
    }

    private boolean checkSchedule(LocalDateTime accountCreatedDate) {
        return checkWorkingHours(accountCreatedDate) && checkWorkingDays(accountCreatedDate);
    }

    private boolean checkWorkingDays(LocalDateTime accountCreatedDate) {
        int dayOfWeek = accountCreatedDate.getDayOfWeek().getValue();
        return (dayOfWeek >= DayOfWeek.MONDAY.getValue()) && (dayOfWeek <= DayOfWeek.FRIDAY.getValue());
    }

    private boolean checkWorkingHours(LocalDateTime accountCreatedDate) {
        LocalTime startTime = LocalTime.of(9, 0, 0, 0);
        LocalTime endTime = LocalTime.of(18, 0, 0, 0);
        LocalTime createdTime = accountCreatedDate.toLocalTime();
        return createdTime.isAfter(startTime) && createdTime.isBefore(endTime);
    }
}
