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
 * PostgreSQL implementation of the AdminDAO interface for managing administrator user operations.
 * <p>
 * This class provides concrete implementations for all administrator-related database operations
 * defined in the {@link AdminDAO} interface. It handles all administrator account management
 *  functions, including authentication, account creation, updates, and deletion operations
 * using PostgreSQL database connectivity.
 * </p>
 * <p>
 * The implementation provides comprehensive administrator management capabilities including:
 * </p>
 * <ul>
 *   <li>Administrator authentication with username/password and email/password combinations</li>
 *   <li>Secure administrator account creation with duplicate validation</li>
 *   <li>Administrator profile updates with cross-table uniqueness validation</li>
 *   <li>Soft deletion of administrator accounts through status flags</li>
 *   <li>Cross-table validation to prevent username/email conflicts with customer accounts</li>
 * </ul>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see AdminDAO
 * @see model.Admin
 * @see UserAlreadyExistsException
 * @see UserNotFoundException
 * @see ConnessioneDatabase
 */
public class AdminDAOImpl implements AdminDAO {

    /**
     * {@inheritDoc}
     * <p>
     * This implementation executes a SQL query against the Admin table to find an administrator
     * with the specified username and password combination. The query filters out deleted accounts
     * by checking the is_deleted flag and only considers active administrator accounts.
     * </p>
     * <p>
     * The method uses prepared statements for secure parameter binding and proper resource
     * management through try-with-resources. Upon successful authentication, it populates
     * the provided lists with the administrator's ID and email address for session management.
     * </p>
     * <p>
     * Authentication failure occurs when:
     * </p>
     * <ul>
     *   <li>No administrator exists with the specified username</li>
     *   <li>The provided password doesn't match the stored hashed password</li>
     *   <li>The administrator account has been marked as deleted</li>
     * </ul>
     *
     * @param userID list to be populated with the administrator's unique identifier
     * @param username the administrator's username to search for, must not be null
     * @param mail list to be populated with the administrator's email address
     * @param password the hashed password to verify against the stored password
     * @throws SQLException if a database access error occurs during the operation
     * @throws UserNotFoundException if no administrator is found with the specified username and password combination
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
     * {@inheritDoc}
     * <p>
     * This implementation executes a SQL query against the Admin table to find an administrator
     * with the specified email address and password combination. The query filters out deleted
     * accounts by checking the is_deleted flag and only considers active administrator accounts.
     * </p>
     * <p>
     * The method uses prepared statements for secure parameter binding and proper resource
     * management through try-with-resources. Upon successful authentication, it populates
     * the provided lists with the administrator's ID and username for session management.
     * </p>
     * <p>
     * Authentication failure occurs when:
     * </p>
     * <ul>
     *   <li>No administrator exists with the specified email address</li>
     *   <li>The provided password doesn't match the stored hashed password</li>
     *   <li>The administrator account has been marked as deleted</li>
     * </ul>
     *
     * @param userID list to be populated with the administrator's unique identifier
     * @param username list to be populated with the administrator's username
     * @param mail the administrator's email address to search for, must not be null
     * @param password the hashed password to verify against the stored password
     * @throws SQLException if a database access error occurs during the operation
     * @throws UserNotFoundException if no administrator is found with the specified email and password combination
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
     * {@inheritDoc}
     * <p>
     * This implementation performs a two-step operation: first validating that the username
     * and email address are unique across both Admin and Customer tables, then inserting
     * the new administrator account if validation passes.
     * </p>
     * <p>
     * The validation query uses UNION ALL to check for existing usernames and email addresses
     * in both user tables, ensuring system-wide uniqueness. Only active (non-deleted) accounts
     * are considered during the validation process to allow reuse of credentials from deleted accounts.
     * </p>
     * <p>
     * The insertion process creates a new administrator record with:
     * </p>
     * <ul>
     *   <li>Unique username across all user types</li>
     *   <li>Unique email address across all user types</li>
     *   <li>Hashed password for security</li>
     *   <li>Active status (is_deleted = false) by default</li>
     *   <li>Auto-generated unique administrator ID</li>
     * </ul>
     * <p>
     * The method uses prepared statements for both validation and insertion operations,
     * ensuring secure parameter binding and SQL injection prevention.
     * </p>
     *
     * @param mail the email address for the new administrator account, must not be null and must be unique
     * @param username the username for the new administrator account, must not be null and must be unique
     * @param password the hashed password for the new administrator account, must not be null
     * @throws SQLException if a database access error occurs during the operation
     * @throws UserAlreadyExistsException if the username or email address is already in use by another user
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
     * {@inheritDoc}
     * <p>
     * This implementation performs a two-step operation: first validating that the new username
     * is unique across both Admin and Customer tables (excluding the current administrator),
     * then updating the administrator account if validation passes.
     * </p>
     * <p>
     * The validation query uses UNION ALL to check for existing usernames in both user tables
     * while excluding the current administrator account from the Admin table check. This allows
     * administrators to keep their existing username while preventing conflicts with other users.
     * Only active (non-deleted) accounts are considered during validation.
     * </p>
     * <p>
     * The update process modifies the administrator record with:
     * </p>
     * <ul>
     *   <li>New username (validated for uniqueness across all user types)</li>
     *   <li>New hashed password for security</li>
     *   <li>Preserved email address (email updates not supported for administrators)</li>
     *   <li>Maintained account status and creation timestamp</li>
     * </ul>
     * <p>
     * The method uses prepared statements for both validation and update operations,
     * ensuring secure parameter binding and SQL injection prevention.
     * </p>
     *
     * @param userID the unique identifier of the administrator account to update
     * @param username the new username for the administrator account, must be unique
     * @param password the new hashed password for the administrator account
     * @throws SQLException if a database access error occurs during the operation
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
     * {@inheritDoc}
     * <p>
     * This implementation performs a soft deletion by setting the is_deleted flag to true
     * rather than physically removing the administrator record from the database. This
     * approach preserves data integrity and maintains audit trails for administrative
     * actions while preventing the account from being used for authentication.
     * </p>
     * <p>
     * The soft deletion process:
     * </p>
     * <ul>
     *   <li>Sets is_deleted = true for the specified administrator account</li>
     *   <li>Preserves all account data for audit and historical purposes</li>
     *   <li>Prevents the account from being used for future authentication</li>
     *   <li>Allows the username and email to be reused for new accounts</li>
     *   <li>Maintains referential integrity with related database records</li>
     * </ul>
     * <p>
     * Once deleted, the administrator account will be excluded from authentication
     * queries and validation checks, effectively removing access while preserving
     * data for compliance and audit requirements.
     * </p>
     * <p>
     * The method uses prepared statements for secure parameter binding and SQL
     * injection prevention.
     * </p>
     *
     * @param userID the unique identifier of the administrator account to delete
     * @throws SQLException if a database access error occurs during the operation
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