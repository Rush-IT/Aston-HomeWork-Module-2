package com.userservice.service;

import com.userservice.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {

    User createUser(String name, String email, Integer age);

    Optional<User> getUserById(Long id);

    Optional<User> getUserByEmail(String email);

    List<User> getAllUsers();

    User updateUser(Long id, String name, String email, Integer age);

    boolean deleteUser(Long id);

    long getUserCount();
}
