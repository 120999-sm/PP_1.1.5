package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {

    public static SessionFactory sessionFactory;
    private static final String URL = "jdbc:mysql://localhost:3306/new_schema";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root1";

    public static Connection getConnected() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Database connected!");
        } catch (SQLException e) {
            System.out.println("Database not connected");
        }
        return connection;
    }

    public static SessionFactory getConnectedHibernate() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();

                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, URL);
                settings.put(Environment.USER, USERNAME);
                settings.put(Environment.PASS, PASSWORD);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "create-drop");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                System.out.println("Database connected!");
            } catch (Exception e) {
                System.out.println("Database dont connected");
                throw new RuntimeException();
                //todo: после того, как сказали "Database dont connected",
                //      нужно уронить приложение.. уже все плохо. ->  throw new RuntimeException(), например
            }
        }
        return sessionFactory;
    }
}
