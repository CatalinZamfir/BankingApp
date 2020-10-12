package org.sda.BankingApp.config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.sda.BankingApp.types.Account;
import org.sda.BankingApp.types.ClientData;
import org.sda.BankingApp.types.Transactions;
import java.util.Properties;

import static java.util.logging.Level.SEVERE;

public class HibernateUtil {

    public static SessionFactory getSessionFactory() {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(SEVERE);
        Configuration configuration = createConfig();
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
            .applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private static Configuration createConfig() {
        Configuration configuration = new Configuration();
        Properties settings = new Properties();
        settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        settings.put(Environment.URL, "jdbc:mysql://localhost:3306/bank_data?serverTimezone=UTC");
        settings.put(Environment.USER, "root");
        settings.put(Environment.PASS, "password");
        settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
        settings.put(Environment.SHOW_SQL, "false");
        settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        configuration.setProperties(settings);

        configuration.addAnnotatedClass(Account.class);
        configuration.addAnnotatedClass(ClientData.class);
        configuration.addAnnotatedClass(Transactions.class);

        return configuration;
    }
}
