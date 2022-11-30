package org.example.util;

import org.example.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import java.sql.*;


import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
//import java.sql.SQLException;
import java.util.Properties;


public class Util {
    private static SessionFactory sessionFactory;
    private static final String URL = "jdbc:mysql://localhost:3306/dblucky";
    private static final String USERNAME = "root0617";
    private static final String PASS = "root0617";
    private static Connection connection = null;
    private static Util instance = null;

    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration(); // Configuration
                configuration.setProperty("hibernate.current_session_context_class", "thread");
                configuration.setProperty(Environment.URL, URL);
                configuration.setProperty(Environment.USER, USERNAME);
                configuration.setProperty(Environment.PASS, PASS);
                configuration.setProperty("hibernate.connection.release_mode", "auto");
                configuration.setProperty("hibernate.show_sql", "true");
                configuration.addAnnotatedClass(User.class); // передаем конфигурации класс который является сущностью

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (HibernateException hibernateException) {
                hibernateException.getStackTrace();
                throw new RuntimeException();
            }
        } return sessionFactory;
    }

//    public static Connection getConnection() throws ClassNotFoundException, SQLException {
//        Connection connection;
//        Driver driver = new com.mysql.cj.jdbc.Driver();
//        DriverManager.registerDriver(driver);
//        connection = DriverManager.getConnection(URL, USERNAME, PASS);
//
//        return connection;
//    }






    // --- //
    private Util() {
        try {
            if (null == connection || connection.isClosed()) {
                Properties props = getProps();
                connection = DriverManager
                        .getConnection(props.getProperty("db.url"), props.getProperty("db.username"), props.getProperty("db.password"));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    public static Util getInstance() {
        if (null == instance) {
            instance = new Util();
        }
        return instance;
    }

    public static Connection getConnection() {
        return connection;
    }

    private static Properties getProps() throws IOException {
        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get(Util.class.getResource("/application.properties").toURI()))) {
            props.load(in);
            return props;
        } catch (IOException | URISyntaxException e) {
            throw new IOException("Database config file not found", e);
        }
    }
}
