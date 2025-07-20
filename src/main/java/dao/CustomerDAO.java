package dao;

import java.sql.SQLException;
import java.util.List;

public interface CustomerDAO {

    void searchUserByUsername(List<Integer> userID, String username, List<String> mail, String password) throws SQLException, UserNotFoundException;

    void searchUserByMail(List<Integer> userID, List<String> username, String mail, String password) throws SQLException, UserNotFoundException;

}
