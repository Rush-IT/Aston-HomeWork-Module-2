package com.userservice.dao;

import com.userservice.entity.User;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserDAOImplIntegrationTest {

    private static final PostgreSQLContainer<?> POSTGRES_CONTAINER = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    private UserDAO userDAO;

    @BeforeAll
    void setUpContainer() {
        POSTGRES_CONTAINER.start();

        // Конфигурируем Hibernate для тестовой БД
        HibernateUtilTest.configureHibernate(POSTGRES_CONTAINER.getJdbcUrl(),
                POSTGRES_CONTAINER.getUsername(),
                POSTGRES_CONTAINER.getPassword());

        userDAO = new UserDAOImpl();
    }

    @AfterAll
    void tearDownContainer() {
        POSTGRES_CONTAINER.stop();
    }

    @BeforeEach
    void cleanDB() {
        List<User> users = userDAO.findAll();
        users.forEach(u -> userDAO.deleteById(u.getId()));
    }

    @Test
    void testSaveAndFind() {
        User user = new User("Test User", "test@example.com", 23);

        User saved = userDAO.save(user);
        assertNotNull(saved.getId());

        Optional<User> fetched = userDAO.findById(saved.getId());
        assertTrue(fetched.isPresent());
        assertEquals("Test User", fetched.get().getName());
    }

    @Test
    void testUpdate() {
        User user = userDAO.save(new User("Old Name", "upd@example.com", 30));
        user.setName("New Name");

        User updated = userDAO.update(user);
        assertEquals("New Name", updated.getName());
    }

    @Test
    void testDelete() {
        User user = userDAO.save(new User("To Delete", "del@example.com", 40));
        boolean deleted = userDAO.deleteById(user.getId());

        assertTrue(deleted);
        assertFalse(userDAO.findById(user.getId()).isPresent());
    }
}
