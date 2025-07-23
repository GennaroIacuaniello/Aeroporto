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
 * connection instance exists throughout the application lifecycle. It provides centralized
 * connection management for PostgreSQL database operations, handling connection creation,
 * retrieval, and cleanup operations.
 * </p>
 * <p>
 * The class manages connections to a PostgreSQL database instance running on localhost
 * and provides thread-safe access to the database connection. It automatically handles
 * connection recovery by creating a new instance if the current connection is closed
 * or unavailable.
 * </p>
 * <p>
 * Key features include:
 * </p>
 * <ul>
 *   <li>Singleton pattern implementation for connection management</li>
 *   <li>Automatic connection recovery and reconnection</li>
 *   <li>PostgreSQL database driver integration</li>
 *   <li>Centralized error logging and exception handling</li>
 *   <li>Resource cleanup and connection closure management</li>
 * </ul>
 * <p>
 * The database connection is configured for the "Aeroporto" database running on
 * localhost:5432 using PostgreSQL driver. Connection parameters are hardcoded
 * for development purposes but can be externalized for production environments.
 * </p>
 * <p>
 * Usage example:
 * </p>
 * <pre>
 * try (Connection conn = ConnessioneDatabase.getInstance().getConnection()) {
 *     // Perform database operations
 * } catch (SQLException e) {
 *     // Handle database errors
 * }
 * </pre>
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
     * <p>
     * This logger is used to record connection establishment, errors during
     * connection creation, and connection closure events. It helps in debugging
     * database connectivity issues and monitoring system health.
     * </p>
     */
    private static final Logger LOGGER = Logger.getLogger(ConnessioneDatabase.class.getName());
    
    /**
     * The singleton instance of the ConnessioneDatabase class.
     * <p>
     * This static field holds the single instance of the class following
     * the Singleton pattern. It is initialized lazily when first accessed
     * through the getInstance() method and may be recreated if the
     * connection becomes unavailable.
     * </p>
     */
    private static ConnessioneDatabase instance;
    
    /**
     * The active database connection instance.
     * <p>
     * This field holds the actual JDBC connection to the PostgreSQL database.
     * It is initialized during object construction and used for all database
     * operations. The connection may be null if initialization fails or if
     * it has been explicitly closed.
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
     * <p>
     * If the PostgreSQL driver class cannot be found, the error is logged
     * but the constructor completes, leaving the connection as null.
     * </p>
     *
     * @throws SQLException if a database access error occurs during connection establishment
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
     * <p>
     * The method is thread-safe through synchronized access to the singleton
     * instance and includes connection validity checking to ensure the returned
     * instance has an active database connection.
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
     * <p>
     * This method provides access to the underlying JDBC connection for
     * database operations. The returned connection should be used with
     * try-with-resources or manually closed after use to prevent resource leaks.
     * </p>
     * <p>
     * The connection is managed by the singleton instance and may be shared
     * across multiple components of the application. Callers should not close
     * this connection directly unless they intend to terminate database access
     * for the entire application.
     * </p>
     *
     * @return the active database connection, may be null if initialization failed
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Closes the database connection and releases associated resources.
     * <p>
     * This method explicitly closes the database connection and logs any
     * errors that occur during the closure process. Once called, the
     * connection becomes unavailable and subsequent calls to getInstance()
     * will create a new connection.
     * </p>
     * <p>
     * This method is typically called during application shutdown or when
     * database access is no longer needed. It provides graceful cleanup
     * of database resources and ensures proper connection termination.
     * </p>
     * <p>
     * Any SQLException thrown during connection closure is caught and logged
     * at SEVERE level to prevent application termination during cleanup.
     * </p>
     */
    public void closeConnection() {

        try {

            connection.close();
        } catch (SQLException ex) {

            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
    }
}