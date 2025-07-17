package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnessioneDatabase {

	// ATTRIBUTI
	private static ConnessioneDatabase instance;
	private Connection connection = null;
	private String nome = "postgres";
	private String password = "ciao9999";
	private String url = "jdbc:postgresql://localhost:5432/Aeroporto";
	private String driver = "org.postgresql.Driver";

	// COSTRUTTORE
	private ConnessioneDatabase() throws SQLException {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, nome, password);

		} catch (ClassNotFoundException ex) {
			System.out.println("Database Connection Creation Failed : " + ex.getMessage());
			ex.printStackTrace();
		}

	}


	public static ConnessioneDatabase getInstance() throws SQLException {
		if (instance == null) {
			instance = new ConnessioneDatabase();
		} else if (instance.connection.isClosed()) {
			instance = new ConnessioneDatabase();
		}
		return instance;
	}

	public Connection getConnection() {
		return connection;
	}

	public void closeConnection() {

		try {

			connection.close();
		} catch (SQLException ex) {

			System.out.println("Database Connection Close Failed : " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}