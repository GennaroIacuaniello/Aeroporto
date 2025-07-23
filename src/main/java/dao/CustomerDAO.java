package dao;

import java.sql.SQLException;
import java.util.List;

/**
 * The interface Customer dao.
 */
public interface CustomerDAO {

    /**
     * Search user by username.
     *
     * @param userID   the user id
     * @param username the username
     * @param mail     the mail
     * @param password the password
     * @throws SQLException the sql exception
     */
    void searchUserByUsername(List<Integer> userID, String username, List<String> mail, String password) throws SQLException;

    /**
     * Search user by mail.
     *
     * @param userID   the user id
     * @param username the username
     * @param mail     the mail
     * @param password the password
     * @throws SQLException the sql exception
     */
    void searchUserByMail(List<Integer> userID, List<String> username, String mail, String password) throws SQLException;

    /**
     * Insert new customer.
     *
     * @param mail     the mail
     * @param username the username
     * @param password the password
     * @throws SQLException the sql exception
     */
    void insertNewCustomer(String mail, String username, String password) throws SQLException;

    /**
     * Update customer.
     *
     * @param userID   the user id
     * @param mail     the mail
     * @param username the username
     * @param password the password
     * @throws SQLException the sql exception
     */
    public void updateCustomer(Integer userID, String mail, String username, String password) throws SQLException;

    /**
     * Delete customer.
     *
     * @param userID the user id
     * @throws SQLException the sql exception
     */
    public void deleteCustomer(Integer userID) throws SQLException;

}
