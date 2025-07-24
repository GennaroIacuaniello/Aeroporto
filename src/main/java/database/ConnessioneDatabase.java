package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Singleton class that manages database connections for the airport management system.
 * <p>
 * This class implements the Singleton design pattern to ensure that only one database
 * connection instance exists throughout the application lifecycle. It provides
 * connection management for PostgreSQL database operations, handling connection creation,
 * retrieval, and cleanup operations.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see Connection
 * @see DriverManager
 * @see SQLException
 */
public class ConnessioneDatabase {

    /**
     * Logger instance for recording database connection events and errors.
     */
    private static final Logger LOGGER = Logger.getLogger(ConnessioneDatabase.class.getName());
    
    /**
     * The singleton instance of the ConnessioneDatabase class.
     * <p>
     * This static field holds the single instance of the class following
     * the Singleton pattern.
     * </p>
     */
    private static ConnessioneDatabase instance;
    
    /**
     * The active database connection instance.
     * <p>
     * This field holds the actual JDBC connection to the PostgreSQL database.
     * It is initialized during object construction and used for all database
     * operations.
     * </p>
     */
    private Connection connection = null;

    /**
     * Private constructor that establishes the database connection.
     * <p>
     * This constructor is private to enforce the Singleton pattern and prevent
     * direct instantiation. It initializes the PostgreSQL database connection
     * using hardcoded connection parameters for the local "Aeroporto" database.
     * </p>
     * <p>
     * Connection parameters:
     * </p>
     * <ul>
     *   <li>Driver: org.postgresql.Driver</li>
     *   <li>URL: jdbc:postgresql://localhost:5432/Aeroporto</li>
     *   <li>Username: postgres</li>
     *   <li>Password: ciao9999</li>
     * </ul>
     *
     * @throws SQLException if a database access error occurs during a connection establishment
     */
    private ConnessioneDatabase() throws SQLException {
        try {
            String driver = "org.postgresql.Driver";
            Class.forName(driver);
            String nome = "postgres";
            String password = "ciao9999";
            String url = "jdbc:postgresql://localhost:5432/Aeroporto";
            connection = DriverManager.getConnection(url, nome, password);

        } catch (ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }

    }

    /**
     * Returns the singleton instance of ConnessioneDatabase.
     * <p>
     * This method implements the Singleton pattern by ensuring that only one
     * instance of the class exists. If no instance exists or if the current
     * instance's connection is closed, a new instance is created. This provides
     * automatic connection recovery in case of database disconnections.
     * </p>
     *
     * @return the singleton ConnessioneDatabase instance with an active connection
     * @throws SQLException if a database access error occurs during instance creation
     *                     or connection establishment
     */
    public static ConnessioneDatabase getInstance() throws SQLException {
        if (instance == null || instance.connection.isClosed()) {
            instance = new ConnessioneDatabase();
        }
        return instance;
    }

    /**
     * Returns the active database connection.
     *
     * @return the active database connection, may be null if initialization failed
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Closes the database connection and releases associated resources.
     */
    public void closeConnection() {

        try {

            connection.close();
        } catch (SQLException ex) {

            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
    }
}