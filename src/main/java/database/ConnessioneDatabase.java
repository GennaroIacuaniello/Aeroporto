package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Connessione database.
 */
public class ConnessioneDatabase {

	private static final Logger LOGGER = Logger.getLogger(ConnessioneDatabase.class.getName());
	// ATTRIBUTI
	private static ConnessioneDatabase instance;
	private Connection connection = null;

    // COSTRUTTORE
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
     * Gets instance.
     *
     * @return the instance
     * @throws SQLException the sql exception
     */
    public static ConnessioneDatabase getInstance() throws SQLException {
		if (instance == null || instance.connection.isClosed()) {
			instance = new ConnessioneDatabase();
		}
		return instance;
	}

    /**
     * Gets connection.
     *
     * @return the connection
     */
    public Connection getConnection() {
		return connection;
	}

    /**
     * Close connection.
     */
    public void closeConnection() {

		try {

			connection.close();
		} catch (SQLException ex) {

			LOGGER.log(Level.SEVERE, ex.getMessage());
		}
	}
}