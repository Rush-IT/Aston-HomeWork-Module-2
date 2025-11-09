package com.userservice;

import com.userservice.ui.ConsoleUI;
import com.userservice.util.HibernateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        log.info("Запуск приложения User Service");

        try {
            // Инициализация Hibernate (SessionFactory)
            HibernateUtil.getSessionFactory();
            log.info("Hibernate инициализирован");

            // Запуск консольного пользовательского интерфейса
            ConsoleUI consoleUI = new ConsoleUI();
            consoleUI.start();

        } catch (Exception e) {
            log.error("Ошибка при запуске приложения", e);
            System.err.println("Критическая ошибка при запуске приложения: " + e.getMessage());
        } finally {
            // Корректное завершение работы Hibernate
            HibernateUtil.shutdown();
            log.info("Приложение завершено");
        }
    }
}
