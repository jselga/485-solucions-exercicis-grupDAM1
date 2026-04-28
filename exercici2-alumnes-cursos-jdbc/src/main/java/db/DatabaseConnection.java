package db;

import exception.ConfigurationException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    private static final String FILE_NAME = "db.properties";
    private static final Properties PROPERTIES = loadProperties();

    private DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException {
        String url = PROPERTIES.getProperty("db.url");
        String user = PROPERTIES.getProperty("db.user");
        String password = PROPERTIES.getProperty("db.password");

        validarConfiguracio(url, user);
        return DriverManager.getConnection(url, user, password);
    }

    private static void validarConfiguracio(String url, String user) {
        if (url == null || url.isBlank()) {
            throw new ConfigurationException("La propietat db.url no està configurada correctament.");
        }

        if (user == null || user.isBlank()) {
            throw new ConfigurationException("La propietat db.user no està configurada correctament.");
        }
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();

        try (InputStream inputStream = DatabaseConnection.class.getClassLoader().getResourceAsStream(FILE_NAME)) {
            if (inputStream == null) {
                throw new ConfigurationException("No s'ha trobat el fitxer " + FILE_NAME + " a resources.");
            }

            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            throw new ConfigurationException("No s'ha pogut llegir el fitxer " + FILE_NAME + ".", e);
        }
    }
}
