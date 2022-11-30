package org.example.service;

import org.example.dao.UserDao;
import org.example.dao.UserDaoHibernateImpl;
import org.example.model.User;
import java.util.List;
import java.sql.SQLException;
import org.example.dao.UserDaoJDBCImpl;

public class UserServiceImpl implements UserService {

//    UserDao userDao = new UserDaoHibernateImpl(); // hibernate
    UserDao userDao = new UserDaoJDBCImpl();


    public void createUsersTable() throws SQLException { //
        userDao.createUsersTable();
    }

    public void dropUsersTable() throws SQLException { //
        userDao.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException { //
        userDao.saveUser(name, lastName, age);
        System.out.println("User with name – " + name + " add in database");
    }

    public void removeUserById(long id) throws SQLException { //
        userDao.removeUserById(id);
    }

    public List<User> getAllUsers() {
        List<User> users =  userDao.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
        return users;

    }

    public void cleanUsersTable() throws SQLException { //
        userDao.cleanUsersTable();
    }
}
