package dao;

import java.sql.SQLException;

/**
 * The type User not found exception.
 */
public class UserNotFoundException extends SQLException {
    /**
     * Instantiates a new User not found exception.
     *
     * @param message the message
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}
