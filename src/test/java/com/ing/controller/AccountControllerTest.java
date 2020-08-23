package com.ing.controller;

import com.ing.domain.Account;
import com.ing.domain.User;
import com.ing.dto.AccountDTO;
import com.ing.mapper.AccountMapper;
import com.ing.service.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AccountControllerTest {

        @Mock
        private AccountService accountService;
        @Mock
        private AccountMapper accountMapper;
        private AccountController accountController;

        @Before
        public void setUp() {
            MockitoAnnotations.initMocks(this);
            accountController = new AccountController(accountService, accountMapper);
        }

        @Test
        public void createAccount() {
            //given
            User user = User.builder()
                    .name("Minnie Mouse")
                    .details("details")
                    .id(1)
                    .build();
            AccountDTO accountDTO = AccountDTO.builder()
                    .iban("iban")
                    .name("name")
                    .userId(1)
                    .build();
            Account account = Account.builder()
                    .name("name")
                    .iban("iban")
                    .user(user)
                    .build();
            when(accountMapper.domainToDTO(account)).thenReturn(accountDTO);
            when(accountService.createAccount(accountDTO)).thenReturn(account);

            //when
            AccountDTO savedAccountDTO = accountController.createAccount(accountDTO);

            //then
            assertNotNull(savedAccountDTO);
            verify(accountMapper).domainToDTO(account);
            verify(accountService).createAccount(accountDTO);
        }
}
