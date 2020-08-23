package com.ing.dao;

import com.ing.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDao extends CrudRepository<User, Integer> {
    @Override
    List<User> findAll();
    @Override
    Optional<User> findById(Integer id);
}
