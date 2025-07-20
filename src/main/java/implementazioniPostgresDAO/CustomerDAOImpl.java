package implementazioniPostgresDAO;

import dao.CustomerDAO;
import dao.UserNotFoundException;
import database.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public void searchUserByUsername(List<Integer> userID, String username, List<String> mail, String password) throws SQLException, UserNotFoundException {
        try(Connection connection = ConnessioneDatabase.getInstance().getConnection()){
            String query = "SELECT id_customer, mail " +
                            "FROM Customer " +
                            "WHERE username = ? AND hashed_password = ? AND is_deleted = false";

            PreparedStatement preparedQuery = connection.prepareStatement(query);
            preparedQuery.setString(1, username);
            preparedQuery.setString(2, password);

            ResultSet rs = preparedQuery.executeQuery();

            while(rs.next()){
                userID.add(rs.getInt("id_customer"));
                mail.add(rs.getString("mail"));
            }

            rs.close();
        }

        if(userID.isEmpty()){
            throw new UserNotFoundException("User non esiste nella tabella Customer");
        }
    }

    @Override
    public void searchUserByMail(List<Integer> userID, List<String> username, String mail, String password) throws SQLException, UserNotFoundException {

        try(Connection connection = ConnessioneDatabase.getInstance().getConnection()){
            String query = "SELECT id_customer, username " +
                            "FROM Customer " +
                            "WHERE mail = ? AND hashed_password = ? AND is_deleted = false";

            PreparedStatement preparedQuery = connection.prepareStatement(query);
            preparedQuery.setString(1, mail);
            preparedQuery.setString(2, password);

            ResultSet rs = preparedQuery.executeQuery();

            while(rs.next()){
                userID.add(rs.getInt("id_customer"));
                username.add(rs.getString("username"));
            }

            rs.close();
        }

        if(userID.isEmpty()){
            throw new UserNotFoundException("User non esiste nella tabella Customer");
        }
    }
}
