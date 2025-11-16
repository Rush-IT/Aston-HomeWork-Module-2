package com.userservice.ui;

import com.userservice.entity.User;
import com.userservice.service.UserService;
import com.userservice.service.UserServiceImpl;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ConsoleUI {

    private final UserService userService;
    private final Scanner scanner = new Scanner(System.in);
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public ConsoleUI() {
        this.userService = new UserServiceImpl();
    }

    public void start() {
        boolean running = true;

        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> createUser();
                    case "2" -> findUserById();
                    case "3" -> findUserByEmail();
                    case "4" -> listAllUsers();
                    case "5" -> updateUser();
                    case "6" -> deleteUser();
                    case "7" -> showUserCount();
                    case "0" -> running = false;
                    default -> System.out.println("❌ Неверный выбор. Попробуйте снова.");
                }
            } catch (Exception e) {
                System.out.println("❌ Ошибка: " + e.getMessage());
            }

            if (running) {
                System.out.println("\nНажмите Enter для продолжения...");
                scanner.nextLine();
            }
        }

        scanner.close();
    }

    private void printMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("--------МЕНЮ УПРАВЛЕНИЯ ПОЛЬЗОВАТЕЛЯМИ--------");
        System.out.println("=".repeat(50));
        System.out.println("1. Создать пользователя");
        System.out.println("2. Найти пользователя по ID");
        System.out.println("3. Найти пользователя по Email");
        System.out.println("4. Показать всех пользователей");
        System.out.println("5. Обновить пользователя");
        System.out.println("6. Удалить пользователя");
        System.out.println("7. Показать количество пользователей");
        System.out.println("0. Выход");
        System.out.print("Выберите действие: ");
    }

    private void createUser() {
        System.out.println("\n--------Создание пользователя--------");
        System.out.print("Имя: ");
        String name = scanner.nextLine().trim();

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Возраст (или Enter чтобы пропустить): ");
        String ageStr = scanner.nextLine().trim();
        Integer age = ageStr.isEmpty() ? null : Integer.parseInt(ageStr);

        User user = userService.createUser(name, email, age);
        System.out.println("✅ Пользователь создан:\n" + user);
    }

    private void findUserById() {
        System.out.print("\nВведите ID: ");
        Long id = Long.parseLong(scanner.nextLine().trim());

        Optional<User> userOpt = userService.getUserById(id);
        if (userOpt.isPresent()) {
            System.out.println("✅ Найден пользователь:\n" + userOpt.get());
        } else {
            System.out.println("❌ Пользователь не найден");
        }
    }

    private void findUserByEmail() {
        System.out.print("\nВведите email: ");
        String email = scanner.nextLine().trim();

        Optional<User> userOpt = userService.getUserByEmail(email);
        if (userOpt.isPresent()) {
            System.out.println("✅ Найден пользователь:\n" + userOpt.get());
        } else {
            System.out.println("❌ Пользователь не найден");
        }
    }

    private void listAllUsers() {
        List<User> users = userService.getAllUsers();
        System.out.println("\nВсего пользователей: " + users.size());
        for (User u : users) {
            System.out.println(u);
            System.out.println("-".repeat(50));
        }
    }

    private void updateUser() {
        System.out.print("\nВведите ID для обновления: ");
        Long id = Long.parseLong(scanner.nextLine().trim());

        Optional<User> existing = userService.getUserById(id);
        if (existing.isEmpty()) {
            System.out.println("❌ Пользователь не найден");
            return;
        }

        User current = existing.get();

        System.out.print("Новое имя (" + current.getName() + "): ");
        String name = scanner.nextLine().trim();
        name = name.isEmpty() ? current.getName() : name;

        System.out.print("Новый email (" + current.getEmail() + "): ");
        String email = scanner.nextLine().trim();
        email = email.isEmpty() ? current.getEmail() : email;

        System.out.print("Новый возраст (" + (current.getAge() != null ? current.getAge() : "не установлен") + "): ");
        String ageStr = scanner.nextLine().trim();
        Integer age = ageStr.isEmpty() ? current.getAge() : Integer.parseInt(ageStr);

        User updated = userService.updateUser(id, name, email, age);
        System.out.println("Пользователь обновлен:\n" + updated);
    }

    private void deleteUser() {
        System.out.print("\nВведите ID для удаления: ");
        Integer id = Integer.parseInt(scanner.nextLine().trim());

        boolean deleted = userService.deleteUser(id);
        if (deleted) {
            System.out.println("Пользователь удалён");
        } else {
            System.out.println("Пользователь не найден");
        }
    }

    private void showUserCount() {
        long count = userService.getUserCount();
        System.out.println("\nОбщее количество пользователей: " + count);
    }
}