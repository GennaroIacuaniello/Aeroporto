package implementazioni_postgres_dao;

import dao.AdminDAO;
import dao.UserAlreadyExistsException;
import dao.UserNotFoundException;
import database.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * The AdminDAOImpl class provides PostgreSQL database implementation for the AdminDAO interface.
 * This class handles all database operations related to administrator users, including:
 * - Authentication (verifying username/email and password)
 * - Creating new admin accounts
 * - Updating admin information
 * - Soft-deleting admin accounts (marking as deleted rather than removing)
 * 
 * All database operations use prepared statements to prevent SQL injection attacks.
 */
public class AdminDAOImpl implements AdminDAO {


    /**
     * Searches for an admin user by username and password.
     * This method is primarily used for authentication when a user logs in with a username.
     * It populates the provided lists with the user's ID and email if authentication is successful.
     *
     * @param userID   output parameter - list to be populated with the admin's ID if found
     * @param username the username to search for
     * @param mail     output parameter - list to be populated with the admin's email if found
     * @param password the hashed password to verify
     * @throws SQLException if a database access error occurs
     * @throws UserNotFoundException if no admin with the given username and password is found
     *                              or if the account has been marked as deleted
     */
    @Override
    public void searchUserByUsername(List<Integer> userID, String username, List<String> mail, String password) throws SQLException {

        String query = "SELECT id_admin, mail " +
                        "FROM Admin " +
                        "WHERE username = ? AND hashed_password = ? AND is_deleted = false";

        try (Connection connection = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement preparedQuery = connection.prepareStatement(query)){

            preparedQuery.setString(1, username);
            preparedQuery.setString(2, password);

            ResultSet rs = preparedQuery.executeQuery();

            while(rs.next()){
                userID.add(rs.getInt("id_admin"));
                mail.add(rs.getString("mail"));
            }

            rs.close();
        }

        if(userID.isEmpty()){
            throw new UserNotFoundException("User non esiste nella tabella Admin");
        }
    }

    /**
     * Searches for an admin user by email and password.
     * This method is primarily used for authentication when a user logs in with an email address.
     * It populates the provided lists with the user's ID and username if authentication is successful.
     *
     * @param userID   output parameter - list to be populated with the admin's ID if found
     * @param username output parameter - list to be populated with the admin's username if found
     * @param mail     the email address to search for
     * @param password the hashed password to verify
     * @throws SQLException if a database access error occurs
     * @throws UserNotFoundException if no admin with the given email and password is found
     *                              or if the account has been marked as deleted
     */
    @Override
    public void searchUserByMail(List<Integer> userID, List<String> username, String mail, String password) throws SQLException {

        String query = "SELECT id_admin, username " +
                        "FROM Admin " +
                        "WHERE mail = ? AND hashed_password = ? AND is_deleted = false";

        try(Connection connection = ConnessioneDatabase.getInstance().getConnection();
            PreparedStatement preparedQuery = connection.prepareStatement(query)){

            preparedQuery.setString(1, mail);
            preparedQuery.setString(2, password);

            ResultSet rs = preparedQuery.executeQuery();

            while(rs.next()){
                userID.add(rs.getInt("id_admin"));
                username.add(rs.getString("username"));
            }

            rs.close();
        }

        if(userID.isEmpty()){
            throw new UserNotFoundException("User non esiste nella tabella Admin");
        }
    }

    /**
     * Creates a new administrator account in the database.
     * This method first checks if the username or email is already in use by any admin or customer,
     * then creates the new admin account if the credentials are available.
     *
     * @param mail     the email address for the new admin
     * @param username the username for the new admin
     * @param password the hashed password for the new admin
     * @throws SQLException if a database access error occurs
     * @throws UserAlreadyExistsException if the username or email is already in use by another user
     */
    @Override
    public void insertNewAdmin(String mail, String username, String password) throws SQLException {

        String checkExistenceQuery = "SELECT username, mail " +
                                     "FROM Admin " +
                                     "WHERE (username = ? OR mail = ?) AND is_deleted = false " +
                                     "UNION ALL " +
                                     "SELECT username, mail " +
                                     "FROM Customer " +
                                     "WHERE (username = ? OR mail = ?) AND is_deleted = false";

        String insertAdmin = "INSERT INTO Admin(username, mail, hashed_password) " +
                             "VALUES(?, ?, ?)";

        try(Connection connection = ConnessioneDatabase.getInstance().getConnection();
            PreparedStatement checkExistenceStatement = connection.prepareStatement(checkExistenceQuery);
            PreparedStatement insertStatement = connection.prepareStatement(insertAdmin);){

            checkExistenceStatement.setString(1, username);
            checkExistenceStatement.setString(2, mail);
            checkExistenceStatement.setString(3, username);
            checkExistenceStatement.setString(4, mail);

            ResultSet rs = checkExistenceStatement.executeQuery();

            if(!rs.next()){ //is username/mail are not already used rs.next gives false
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

    /**
     * Updates an existing administrator's information in the database.
     * This method first checks if the new username is already in use by any other admin or customer,
     * then updates the admin's information if the username is available.
     * Note that this method only updates the username and password, not the email address.
     *
     * @param userID   the unique identifier of the admin to update
     * @param username the new username for the admin
     * @param password the new hashed password for the admin
     * @throws SQLException if a database access error occurs
     * @throws UserAlreadyExistsException if the new username is already in use by another user
     */
    @Override
    public void updateAdmin(Integer userID, String username, String password) throws SQLException {

        String checkExistenceQuery = "SELECT username " +
                "FROM Admin " +
                "WHERE username = ? AND is_deleted = false AND id_admin <> ? " +
                "UNION ALL " +
                "SELECT username " +
                "FROM Customer " +
                "WHERE username = ?  AND is_deleted = false";

        String updateQuery = "UPDATE Admin " +
                "SET username = ?, hashed_password = ? " +
                "WHERE id_admin = ?";

        try(Connection connection = ConnessioneDatabase.getInstance().getConnection();
            PreparedStatement checkExistenceStatement = connection.prepareStatement(checkExistenceQuery);
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);) {

            checkExistenceStatement.setString(1, username);
            checkExistenceStatement.setInt(2, userID);
            checkExistenceStatement.setString(3, username);

            ResultSet rs = checkExistenceStatement.executeQuery();

            if(!rs.next()) { //is username/mail are not already used rs.next gives false
                rs.close();

                updateStatement.setString(1, username);
                updateStatement.setString(2, password);
                updateStatement.setInt(3, userID);

                updateStatement.executeUpdate();
            } else{
                rs.close();
                throw new UserAlreadyExistsException("Mail o Username già in uso");
            }

        }

    }

    /**
     * Soft-deletes an administrator account from the database.
     * This method doesn't actually remove the record from the database but marks it as deleted
     * by setting the is_deleted flag to true. This approach preserves historical data while
     * preventing the account from being used for login.
     *
     * @param userID the unique identifier of the admin to delete
     * @throws SQLException if a database access error occurs
     */
    @Override
    public void deleteAdmin(Integer userID) throws SQLException {
        String deleteQuery = "UPDATE Admin " +
                "SET is_deleted = true " +
                "WHERE id_admin = ?";

        try(Connection connection = ConnessioneDatabase.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)){

            preparedStatement.setInt(1, userID);

            preparedStatement.executeUpdate();
        }
    }
}
