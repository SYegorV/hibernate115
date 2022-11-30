package org.example.service;

import org.example.model.User;
import java.sql.SQLException;
import java.util.List;

public interface UserService {
    void createUsersTable() throws SQLException; //

    void dropUsersTable() throws SQLException; //

    void saveUser(String name, String lastName, byte age) throws SQLException; //

    void removeUserById(long id) throws SQLException; //

    List<User> getAllUsers();

    void cleanUsersTable() throws SQLException; //
}
