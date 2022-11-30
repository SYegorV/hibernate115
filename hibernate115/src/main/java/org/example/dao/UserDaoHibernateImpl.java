package org.example.dao;

import org.example.model.User;
import org.example.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import javax.persistence.Query;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final Session session = Util.getSessionFactory().openSession(); // соединение с бд

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS table_u (id INT NOT NULL AUTO_INCREMENT," +
                    "name CHAR(255) not NULL, lastName CHAR(255) not NULL, age smallint not NULL, PRIMARY KEY (id)," +
                    "UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE)").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.getStackTrace();
            session.getTransaction().rollback();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("drop table if exists table_u").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.getStackTrace();
            session.getTransaction().rollback();
        }
    }


    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.getStackTrace();
            session.getTransaction().rollback();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.getStackTrace();
            session.getTransaction().rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createSQLQuery("SELECT ID, NAME, LASTNAME, AGE FROM table_u").addEntity(User.class);
            List<User> usersList = query.getResultList();
            session.getTransaction().commit();
            return usersList;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("delete from table_u").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.getStackTrace();
            session.getTransaction().rollback();
        }
    }
}
