package com.ing.mapper;

import com.ing.domain.Account;
import com.ing.dto.AccountDTO;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

   public AccountDTO domainToDTO(Account account){
        return AccountDTO
                .builder()
                .name(account.getName())
                .iban(account.getIban())
                .userId(account.getUser().getId())
                .build();
    }

   public Account dtoToDomain(AccountDTO dto) {
       return Account.builder()
               .iban(dto.getIban())
               .name(dto.getName())
               .build();
   }
}
