package com.userservice.service;

import com.userservice.dao.UserDAO;
import com.userservice.dao.UserDAOImpl;
import com.userservice.entity.User;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    public UserServiceImpl() {
        this.userDAO = new UserDAOImpl();
    }

    @Override
    public User createUser(String name, String email, Integer age) {
        validateUserData(name, email, age);

        if (userDAO.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Пользователь с таким email уже существует");
        }

        User user = new User(name, email, age);
        return userDAO.save(user);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userDAO.findById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    @Override
    public User updateUser(Long id, String name, String email, Integer age) {
        validateUserData(name, email, age);

        User user = userDAO.findById(id).orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

        if (!user.getEmail().equals(email) && userDAO.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email уже используется");
        }

        user.setName(name);
        user.setEmail(email);
        user.setAge(age);

        return userDAO.update(user);
    }

    @Override
    public boolean deleteUser(Long id) {
        return userDAO.deleteById(id);
    }

    @Override
    public long getUserCount() {
        return userDAO.count();
    }

    private void validateUserData(String name, String email, Integer age) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя не может быть пустым");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email не может быть пустым");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            throw new IllegalArgumentException("Некорректный формат email");
        }
        if (age != null && (age < 0 || age > 150)) {
            throw new IllegalArgumentException("Возраст должен быть от 0 до 150");
        }
    }
}
