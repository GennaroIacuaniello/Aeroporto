package dao;

import java.sql.SQLException;
import java.util.List;

public interface AdminDAO {

    void searchUserByUsername(List<Integer> userID, String username, List<String> mail, String password) throws SQLException, UserNotFoundException;

    void searchUserByMail(List<Integer> userID, List<String> username, String mail, String password) throws SQLException, UserNotFoundException;

    void insertNewAdmin(String mail, String username, String password) throws SQLException, UserAlreadyExistsException;
}
