package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private final Connection connection;

    private final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(45), last_name VARCHAR(45), age INT)";
    private final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS users";
    private final String SQL_SAVE_USER = "INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)";
    private final String SQL_REMOVE_USER_ID = "DELETE FROM users WHERE id = ?";
    private final String SQL_GET_ALL_USERS = "SELECT * FROM users";
    private final String SQL_CLEAN_TABLE = "TRUNCATE TABLE users";

    public UserDaoJDBCImpl() {
        connection = Util.getConnected();
    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(SQL_CREATE_TABLE);
            System.out.println("Таблица создана");
        } catch (SQLException e) {
            System.out.println("При создании таблицы призошла ошибка: " + e.getMessage());
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(SQL_DROP_TABLE);
            System.out.println("Таблица удалена");
        } catch (SQLException e) {
            System.out.println("При удалении таблицы произошла ошибка: " + e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SAVE_USER)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("Пользователь Имя: " + name + " Фамилия: " + lastName + " Возраст : " + age + " внёсён в таблицу");
        } catch (SQLException e) {
            System.out.println("При сохранении пользователя произошла ошибка: " + e.getMessage());
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_REMOVE_USER_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Пользователь с id = " + id + " удалён из таблицы");
        } catch (SQLException e) {
            System.out.println("При удалении пользователя с id = " + id + " произошла ошибка: " + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (ResultSet resultSet = connection.createStatement().executeQuery(SQL_GET_ALL_USERS)) {
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"), resultSet.getString("last_name"), resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
                System.out.println(user.toString());
            }
        } catch (SQLException e) {
            System.out.println("При получении всех пользователей произошла ошибка: " + e.getMessage());
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(SQL_CLEAN_TABLE);
            System.out.println("Таблица очищена");
        } catch (SQLException e) {
            System.out.println("При очистке таблице произошла ошибка: " + e.getMessage());
        }
    }

}
