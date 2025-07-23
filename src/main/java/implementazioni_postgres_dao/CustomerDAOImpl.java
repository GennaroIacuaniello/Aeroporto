package implementazioni_postgres_dao;

import dao.CustomerDAO;
import dao.UserAlreadyExistsException;
import dao.UserNotFoundException;
import database.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * The type Customer dao.
 */
public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public void searchUserByUsername(List<Integer> userID, String username, List<String> mail, String password) throws SQLException{

        String query = "SELECT id_customer, mail " +
                "FROM Customer " +
                "WHERE username = ? AND hashed_password = ? AND is_deleted = false";

        try(Connection connection = ConnessioneDatabase.getInstance().getConnection();
            PreparedStatement preparedQuery = connection.prepareStatement(query)){

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
    public void searchUserByMail(List<Integer> userID, List<String> username, String mail, String password) throws SQLException {

        String query = "SELECT id_customer, username " +
                "FROM Customer " +
                "WHERE mail = ? AND hashed_password = ? AND is_deleted = false";

        try(Connection connection = ConnessioneDatabase.getInstance().getConnection();
            PreparedStatement preparedQuery = connection.prepareStatement(query)){

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

    @Override
    public void insertNewCustomer(String mail, String username, String password) throws SQLException {

        String checkExistenceQuery = "SELECT username, mail " +
                "FROM Admin " +
                "WHERE (username = ? OR mail = ?) AND is_deleted = false " +
                "UNION ALL " +
                "SELECT username, mail " +
                "FROM Customer " +
                "WHERE (username = ? OR mail = ?) AND is_deleted = false";

        String insertCustomer = "INSERT INTO Customer(username, mail, hashed_password) " +
                "VALUES(?, ?, ?)";

        try(Connection connection = ConnessioneDatabase.getInstance().getConnection();

            PreparedStatement checkExistenceStatement = connection.prepareStatement(checkExistenceQuery);
            PreparedStatement insertStatement = connection.prepareStatement(insertCustomer)){

            checkExistenceStatement.setString(1, username);
            checkExistenceStatement.setString(2, mail);
            checkExistenceStatement.setString(3, username);
            checkExistenceStatement.setString(4, mail);

            ResultSet rs = checkExistenceStatement.executeQuery();

            if(!rs.next()){ //if username/mail are not already used there will be nothing in the result set
                rs.close();
                insertStatement.setString(1, username);
                insertStatement.setString(2, mail);
                insertStatement.setString(3, password);

                insertStatement.execute();
            }else{
                rs.close();
                throw new UserAlreadyExistsException("Mail o Username già in uso");
            }
        }
    }

    @Override
    public void updateCustomer(Integer userID, String mail, String username, String password) throws SQLException {

        String checkExistenceQuery = "SELECT username " +
                "FROM Admin " +
                "WHERE (username = ? OR mail = ?) AND is_deleted = false " +
                "UNION ALL " +
                "SELECT username " +
                "FROM Customer " +
                "WHERE (username = ? OR mail = ?) AND is_deleted = false AND id_customer <> ? ";

        String updateQuery = "UPDATE Customer " +
                "SET username = ?, mail = ?, hashed_password = ? " +
                "WHERE id_customer = ?";

        try(Connection connection = ConnessioneDatabase.getInstance().getConnection();
            PreparedStatement checkExistenceStatement = connection.prepareStatement(checkExistenceQuery);
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);) {

            checkExistenceStatement.setString(1, username);
            checkExistenceStatement.setString(2, mail);
            checkExistenceStatement.setString(3, username);
            checkExistenceStatement.setString(4, mail);
            checkExistenceStatement.setInt(5, userID);

            ResultSet rs = checkExistenceStatement.executeQuery();

            if(!rs.next()) { //is username/mail are not already used rs.next gives false
                rs.close();

                updateStatement.setString(1, username);
                updateStatement.setString(2, mail);
                updateStatement.setString(3, password);
                updateStatement.setInt(4, userID);

                updateStatement.executeUpdate();
            } else{
                rs.close();
                throw new UserAlreadyExistsException("Mail o Username già in uso");
            }

        }
    }

    @Override
    public void deleteCustomer(Integer userID) throws SQLException {

        String deleteQuery = "UPDATE Customer " +
                "SET is_deleted = true " +
                "WHERE id_customer = ?";

        try(Connection connection = ConnessioneDatabase.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)){

            preparedStatement.setInt(1, userID);

            preparedStatement.executeUpdate();
        }
    }
}
