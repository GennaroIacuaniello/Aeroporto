package dao;

import java.sql.SQLException;

public class UserNotFoundException extends SQLException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
