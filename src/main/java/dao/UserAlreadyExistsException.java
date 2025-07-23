package dao;

import java.sql.SQLException;

public class UserAlreadyExistsException extends SQLException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
