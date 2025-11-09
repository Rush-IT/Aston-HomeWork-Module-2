package com.userservice.dao;

import com.userservice.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {

    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    List<User> findAll();

    User update(User user);

    boolean deleteById(Long id);

    void delete(User user);

    boolean existsById(Long id);

    long count();
}