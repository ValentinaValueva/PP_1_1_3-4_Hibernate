package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;

import org.hibernate.cfg.Environment;

import org.hibernate.cfg.Configuration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class Util {

    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private static volatile Util instance;
    private static SessionFactory sessionFactory;

    public static Util getInstance() {
        Util localInstance = instance;
        if (localInstance == null) {
            synchronized (Util.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Util();
                }
            }
        }
        return localInstance;
    }

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            configuration.setProperty(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            configuration.setProperty(Environment.URL, "jdbc:mysql://localhost:3306/mydb?useSSL=false");
            configuration.setProperty(Environment.USER, "root");
            configuration.setProperty(Environment.PASS, "root");
            configuration.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
            configuration.setProperty(Environment.SHOW_SQL, "true");
            configuration.setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            configuration.addAnnotatedClass(User.class);
            sessionFactory = configuration.buildSessionFactory();
        }
        return sessionFactory;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}