package implementazioniPostgresDAO;

import dao.AdminDAO;
import database.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.SQLException;

public class AdminDAOImpl implements AdminDAO {

    private Connection connection;

    public AdminDAOImpl() throws SQLException {

        try {

            connection = ConnessioneDatabase.getInstance().getConnection();
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}
