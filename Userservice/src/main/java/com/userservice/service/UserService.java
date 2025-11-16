package com.userservice.service;

import com.userservice.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {

    User createUser(String name, String email, Integer age);

    Optional<User> getUserById(Long id);

    Optional<User> getUserByEmail(String email);

    List<User> getAllUsers();

    User updateUser(Integer id, String name, String email, Integer age);

    boolean deleteUser(Integer id);

    long getUserCount();
}
