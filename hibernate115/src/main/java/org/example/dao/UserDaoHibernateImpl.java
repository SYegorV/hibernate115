package org.example.dao;

import org.example.model.User;
import org.example.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final Session session = Util.getSessionFactory().openSession(); // соединение с бд

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (id INT NOT NULL AUTO_INCREMENT," +
                    "name VARCHAR(45) NULL,lastName VARCHAR(45)" +
                    " NULL, age VARCHAR(45) NULL, PRIMARY KEY (id)," +
                    " UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE)").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.getStackTrace();
            session.close();
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users;").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.getStackTrace();
            session.close();
        }
    }


    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.getStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            session.beginTransaction();
            session.delete(session.get(User.class,id));
            session.createQuery("DELETE users WHERE id = :id");
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.getStackTrace();
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List users = null;

        try {
            users = session.createQuery("FROM users").list();
        } catch (HibernateException e) {
            e.getStackTrace();
            session.close();
        }

        return users;
    }

    @Override
    public void cleanUsersTable() {
        try {
            session.beginTransaction();
            session.createQuery("delete users").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.getStackTrace();
            session.close();
        }
    }
}
