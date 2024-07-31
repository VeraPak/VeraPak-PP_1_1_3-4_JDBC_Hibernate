package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {
    public static Logger logger = Logger.getLogger("Logger_UserDaoJDBCImpl");
    private static Connection connection;

    static {
        try {
            connection = Util.getConnection();
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Ошибка при попытке установить соединение с БД");

            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {
                logger.log(Level.WARNING, "Ошибка при попытки закрыть соединение");
            }
        }
    }

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users ("
                + "id BIGINT AUTO_INCREMENT PRIMARY KEY, "
                + "name VARCHAR(50), "
                + "lastName VARCHAR(50), "
                + "age TINYINT)";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Ошибка при создании таблицы: " + e);
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Ошибка при удалении таблицы: " + e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Ошибка при сохранении пользователя: " + e);
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Ошибка при удалении пользователя по ID: " + e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM users";
        try (Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");

                User user = new User(name, lastName, age);
                user.setId(id);

                users.add(user);
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Ошибка при получении пользователей: " + e);
        }
        return users;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Ошибка при очистке таблицы: " + e);
        }
    }
}
