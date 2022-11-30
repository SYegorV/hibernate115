package org.example.dao;

import org.example.model.User;
import org.example.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final Connection connection = Util.getInstance().getConnection();

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() throws SQLException { //
        connection.setAutoCommit(false); //
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("create table if not exists table_users " +
                    "(id bigint primary key auto_increment, name char(255), last_name char(255), age int)");
            connection.commit(); //
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback(); //
        }
        connection.setAutoCommit(true); //
    }

    public void dropUsersTable() throws SQLException { //
        connection.setAutoCommit(false); //
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("drop table if exists table_users");
            connection.commit(); //
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback(); //
        }
        connection.setAutoCommit(true); //
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        connection.setAutoCommit(false); //
        try (PreparedStatement preparedStatement = Util.getConnection().
                prepareStatement("insert into table_users (name, last_name, age) values (?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit(); //
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback(); //
        }
        connection.setAutoCommit(true); //
    }

    public void removeUserById(long id) throws SQLException {
        connection.setAutoCommit(false); //
        try (PreparedStatement preparedStatement = Util.getConnection().
                prepareStatement("delete from table_users where id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit(); //
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback(); //
        }
        connection.setAutoCommit(true); //
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (ResultSet resultSet = Util.getConnection().createStatement().executeQuery("select * from table_users")) {
            while(resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("last_name"), resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
            } //
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() throws SQLException {
        connection.setAutoCommit(false); //
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.executeUpdate("truncate table table_users");
            connection.commit(); //
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback(); //
        }
        connection.setAutoCommit(true); //
    }
}
