package com.userservice.service;

import com.userservice.dao.UserDAO;
import com.userservice.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplUnitTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testCreateUserSuccess() {
        when(userDAO.findByEmail("email@test.com")).thenReturn(Optional.empty());
        when(userDAO.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        User user = userService.createUser("Name", "email@test.com", 25);

        assertNotNull(user);
        verify(userDAO).findByEmail("email@test.com");
        verify(userDAO).save(any());
    }

    @Test
    void testCreateUserEmailExists() {
        when(userDAO.findByEmail("email@test.com")).thenReturn(Optional.of(new User()));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser("Name", "email@test.com", 25);
        });

        assertEquals("Пользователь с таким email уже существует", exception.getMessage());
    }

    @Test
    void testGetUserById() {
        User user = new User("Name", "a@b.com", 20);
        when(userDAO.findById(1)).thenReturn(Optional.of(user));

        Optional<User> found = userService.getUserById(1L);

        assertTrue(found.isPresent());
        assertEquals("Name", found.get().getName());
    }

    @Test
    void testUpdateUserSuccess() {
        User existing = new User("Old", "old@test.com", 30);
        existing.setId(1);

        when(userDAO.findById(1)).thenReturn(Optional.of(existing));
        when(userDAO.findByEmail("new@test.com")).thenReturn(Optional.empty());
        when(userDAO.update(existing)).thenAnswer(i -> i.getArgument(0));

        User updated = userService.updateUser(1, "New", "new@test.com", 35);
        assertEquals("New", updated.getName());
        assertEquals("new@test.com", updated.getEmail());
        assertEquals(35, updated.getAge());
    }

    @Test
    void testDeleteSuccess() {
        when(userDAO.deleteById(1)).thenReturn(true);
        boolean deleted = userService.deleteUser(1);
        assertTrue(deleted);
    }

    @Test
    void testDeleteNotFound() {
        when(userDAO.deleteById(1)).thenReturn(false);
        boolean deleted = userService.deleteUser(1);
        assertFalse(deleted);
    }
}
