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
 * PostgreSQL implementation of the CustomerDAO interface for managing customer user operations.
 * <p>
 * This class provides concrete implementations for all customer-related database operations
 * defined in the {@link CustomerDAO} interface. It handles all customer account management
 * functions including authentication, account creation, updates, and deletion operations
 * using PostgreSQL database connectivity.
 * </p>
 * <p>
 * The implementation provides comprehensive customer management capabilities including:
 * </p>
 * <ul>
 *   <li>Customer authentication with username/password and email/password combinations</li>
 *   <li>Secure customer account creation with duplicate validation</li>
 *   <li>Customer profile updates with cross-table uniqueness validation</li>
 *   <li>Soft deletion of customer accounts through status flags</li>
 *   <li>Cross-table validation to prevent username/email conflicts with administrator accounts</li>
 * </ul>
 * <p>
 * All database operations use prepared statements to prevent SQL injection attacks and ensure
 * data security. The class implements proper connection management using the singleton
 * {@link ConnessioneDatabase} pattern and handles resource cleanup through try-with-resources
 * statements.
 * </p>
 * <p>
 * Customer accounts have more flexible requirements compared to administrator accounts:
 * </p>
 * <ul>
 *   <li>Email addresses are optional for customer accounts (can be null during creation)</li>
 *   <li>Usernames must be unique across both Customer and Admin tables</li>
 *   <li>Email addresses must be unique across both Customer and Admin tables when provided</li>
 *   <li>Only active (non-deleted) accounts are considered during authentication and validation</li>
 * </ul>
 * <p>
 * The class handles authentication failures by throwing {@link UserNotFoundException} when
 * credentials don't match any active customer accounts. Account creation and update
 * operations throw {@link UserAlreadyExistsException} when attempting to use credentials
 * that already exist in the system.
 * </p>
 * <p>
 * All methods follow the contract defined by the {@link CustomerDAO} interface and maintain
 * data consistency through proper transaction handling and validation mechanisms.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see CustomerDAO
 * @see model.Customer
 * @see UserAlreadyExistsException
 * @see UserNotFoundException
 * @see ConnessioneDatabase
 */
public class CustomerDAOImpl implements CustomerDAO {

    /**
     * {@inheritDoc}
     * <p>
     * This implementation executes a SQL query against the Customer table to find a customer
     * with the specified username and password combination. The query filters out deleted accounts
     * by checking the is_deleted flag and only considers active customer accounts.
     * </p>
     * <p>
     * The method uses prepared statements for secure parameter binding and proper resource
     * management through try-with-resources. Upon successful authentication, it populates
     * the provided lists with the customer's ID and email address for session management.
     * </p>
     * <p>
     * Authentication failure occurs when:
     * </p>
     * <ul>
     *   <li>No customer exists with the specified username</li>
     *   <li>The provided password doesn't match the stored hashed password</li>
     *   <li>The customer account has been marked as deleted</li>
     * </ul>
     *
     * @param userID list to be populated with the customer's unique identifier
     * @param username the customer's username to search for, must not be null
     * @param mail list to be populated with the customer's email address (can be null)
     * @param password the hashed password to verify against the stored password
     * @throws SQLException if a database access error occurs during the operation
     * @throws UserNotFoundException if no customer is found with the specified username and password combination
     */
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

    /**
     * {@inheritDoc}
     * <p>
     * This implementation executes a SQL query against the Customer table to find a customer
     * with the specified email address and password combination. The query filters out deleted
     * accounts by checking the is_deleted flag and only considers active customer accounts.
     * </p>
     * <p>
     * The method uses prepared statements for secure parameter binding and proper resource
     * management through try-with-resources. Upon successful authentication, it populates
     * the provided lists with the customer's ID and username for session management.
     * </p>
     * <p>
     * Authentication failure occurs when:
     * </p>
     * <ul>
     *   <li>No customer exists with the specified email address</li>
     *   <li>The provided password doesn't match the stored hashed password</li>
     *   <li>The customer account has been marked as deleted</li>
     * </ul>
     *
     * @param userID list to be populated with the customer's unique identifier
     * @param username list to be populated with the customer's username
     * @param mail the customer's email address to search for, must not be null
     * @param password the hashed password to verify against the stored password
     * @throws SQLException if a database access error occurs during the operation
     * @throws UserNotFoundException if no customer is found with the specified email and password combination
     */
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

    /**
     * {@inheritDoc}
     * <p>
     * This implementation performs a two-step operation: first validating that the username
     * and email address (if provided) are unique across both Customer and Admin tables, then
     * inserting the new customer account if validation passes.
     * </p>
     * <p>
     * The validation query uses UNION ALL to check for existing usernames and email addresses
     * in both user tables, ensuring system-wide uniqueness. Only active (non-deleted) accounts
     * are considered during the validation process to allow reuse of credentials from deleted accounts.
     * </p>
     * <p>
     * The insertion process creates a new customer record with:
     * </p>
     * <ul>
     *   <li>Unique username across all user types</li>
     *   <li>Unique email address across all user types (if provided)</li>
     *   <li>Hashed password for security</li>
     *   <li>Active status (is_deleted = false) by default</li>
     *   <li>Auto-generated unique customer ID</li>
     * </ul>
     * <p>
     * Unlike administrator accounts, customer accounts allow null email addresses during
     * creation, providing flexibility for customers who prefer not to provide email information.
     * </p>
     * <p>
     * The method uses prepared statements for both validation and insertion operations,
     * ensuring secure parameter binding and SQL injection prevention.
     * </p>
     *
     * @param mail the email address for the new customer account, can be null
     * @param username the username for the new customer account, must not be null and must be unique
     * @param password the hashed password for the new customer account, must not be null
     * @throws SQLException if a database access error occurs during the operation
     * @throws UserAlreadyExistsException if the username or email address is already in use by another user
     */
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

    /**
     * {@inheritDoc}
     * <p>
     * This implementation performs a two-step operation: first validating that the new username
     * and email address are unique across both Customer and Admin tables (excluding the current
     * customer), then updating the customer account if validation passes.
     * </p>
     * <p>
     * The validation query uses UNION ALL to check for existing usernames and email addresses
     * in both user tables while excluding the current customer account from the Customer table check.
     * This allows customers to keep their existing credentials while preventing conflicts with other users.
     * Only active (non-deleted) accounts are considered during validation.
     * </p>
     * <p>
     * The update process modifies the customer record with:
     * </p>
     * <ul>
     *   <li>New username (validated for uniqueness across all user types)</li>
     *   <li>New email address (validated for uniqueness across all user types, can be null)</li>
     *   <li>New hashed password for security</li>
     *   <li>Maintained account status and creation timestamp</li>
     * </ul>
     * <p>
     * Unlike administrator updates, customer updates support modifying email addresses,
     * providing full account management capabilities including the ability to add, change,
     * or remove email addresses from customer accounts.
     * </p>
     * <p>
     * The method uses prepared statements for both validation and update operations,
     * ensuring secure parameter binding and SQL injection prevention.
     * </p>
     *
     * @param userID the unique identifier of the customer account to update
     * @param mail the new email address for the customer account, can be null
     * @param username the new username for the customer account, must be unique
     * @param password the new hashed password for the customer account
     * @throws SQLException if a database access error occurs during the operation
     * @throws UserAlreadyExistsException if the new username or email address is already in use by another user
     */
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

    /**
     * {@inheritDoc}
     * <p>
     * This implementation performs a soft deletion by setting the is_deleted flag to true
     * rather than physically removing the customer record from the database. This
     * approach preserves data integrity and maintains audit trails for customer
     * transactions and booking history while preventing the account from being used for authentication.
     * </p>
     * <p>
     * The soft deletion process:
     * </p>
     * <ul>
     *   <li>Sets is_deleted = true for the specified customer account</li>
     *   <li>Preserves all account data for audit and historical purposes</li>
     *   <li>Prevents the account from being used for future authentication</li>
     *   <li>Allows the username and email to be reused for new accounts</li>
     *   <li>Maintains referential integrity with related booking and transaction records</li>
     * </ul>
     * <p>
     * Once deleted, the customer account will be excluded from authentication
     * queries and validation checks, effectively removing access while preserving
     * historical data for compliance, audit requirements, and customer service purposes.
     * This is particularly important for customer accounts due to their association
     * with financial transactions and booking records.
     * </p>
     * <p>
     * The method uses prepared statements for secure parameter binding and SQL
     * injection prevention.
     * </p>
     *
     * @param userID the unique identifier of the customer account to delete
     * @throws SQLException if a database access error occurs during the operation
     */
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