package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    private static UserServiceImpl userService = new UserServiceImpl();
    private static User user1 = new User("Petr", "Petrov", (byte) 34);
    private static User user2 = new User("Nikolai", "Nikolayev", (byte) 25);
    private static User user3 = new User("Alina", "Alinova", (byte) 22);
    private static User user4 = new User("Maha", "Mahovna", (byte) 18);

    public static void main(String[] args) {

        // Создание таблицы User(ов)
        userService.createUsersTable();

        // Добавление 4 User(ов) в таблицу с данными.
        // После каждого добавления вывод в консоль
        userService.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        System.out.println(String.format("User с именем — %s добавлен в базу данных", user1.getName()));

        userService.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        System.out.println(String.format("User с именем — %s добавлен в базу данных", user2.getName()));

        userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        System.out.println(String.format("User с именем — %s добавлен в базу данных", user3.getName()));

        userService.saveUser(user4.getName(), user4.getLastName(), user4.getAge());
        System.out.println(String.format("User с именем — %s добавлен в базу данных", user4.getName()));

        // Получение всех User из базы и вывод в консоль
        System.out.println(userService.getAllUsers());

        // Очистка таблицы User(ов)
        userService.cleanUsersTable();

        // Удаление таблицы
        userService.dropUsersTable();
    }
}
