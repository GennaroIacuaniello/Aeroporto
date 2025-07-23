package dao;

import java.sql.SQLException;
import java.util.List;

/**
 * The interface Admin dao.
 */
public interface AdminDAO {

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
     * Insert new admin.
     *
     * @param mail     the mail
     * @param username the username
     * @param password the password
     * @throws SQLException the sql exception
     */
    void insertNewAdmin(String mail, String username, String password) throws SQLException;

    /**
     * Update admin.
     *
     * @param userID   the user id
     * @param username the username
     * @param password the password
     * @throws SQLException the sql exception
     */
    void updateAdmin(Integer userID, String username, String password) throws SQLException;

    /**
     * Delete admin.
     *
     * @param userID the user id
     * @throws SQLException the sql exception
     */
    void deleteAdmin(Integer userID) throws SQLException;
}
