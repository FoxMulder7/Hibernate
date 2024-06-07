package jm.task.core.jdbc.util;
import java.sql.Connection;
import java.util.Properties;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import jm.task.core.jdbc.model.User;

public class Util {



    private static final String HOST = "jdbc:mysql://localhost:3306/schema_name";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";


    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public static Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(HOST, USERNAME, PASSWORD);
            connection.setAutoCommit(false);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }


    private static SessionFactory sessionFactory;
    public static Session getSessionFactory() {
        if (sessionFactory == null) {
            try {

                Configuration configuration = new Configuration();
                Properties settings = new Properties();

                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/schema_name");
                settings.put(Environment.USER, "root");
                settings.put(Environment.PASS, "root");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory.getCurrentSession();
    }
}