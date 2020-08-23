package com.ing.dao;

import com.ing.domain.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountDao extends CrudRepository<Account, Integer> {

    @Override
    Account save(Account account);

    @Override
    Optional<Account> findById(Integer id);

    Optional<Account> findByUserId(Integer id);
}
