package org.example;

import org.example.service.UserService;
import org.example.service.UserServiceImpl;
import java.sql.SQLException;

public class Main {

    private final static UserService userService = new UserServiceImpl();

    public static void main(String[] args) throws  SQLException {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("catherine", "bell", (byte) 24);
        userService.saveUser("alexandra", "stone", (byte) 28);
        userService.saveUser("amanda", "green", (byte) 26);
        userService.saveUser("ruby", "kelly", (byte) 29);
        userService.saveUser("anastasia", "perry", (byte) 28);
        userService.removeUserById(2);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}