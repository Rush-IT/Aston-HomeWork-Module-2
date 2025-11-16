package com.userservice.dao;

import com.userservice.entity.User;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;
import java.util.Optional;

public interface UserDAO {

    User save(User user);

    Optional<User> findById(Integer id);

    Optional<User> findByEmail(String email);

    List<User> findAll();

    User update(User user);

    boolean deleteById(Integer id);

    void delete(User user);

    boolean existsById(Integer id);

    long count();
}