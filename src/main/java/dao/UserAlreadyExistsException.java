package dao;

import java.sql.SQLException;

/**
 * The type User already exists exception.
 */
public class UserAlreadyExistsException extends SQLException {
    /**
     * Instantiates a new User already exists exception.
     *
     * @param message the message
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
