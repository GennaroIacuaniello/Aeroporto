package gui;

/**
 * RuntimeException thrown when password hashing operations encounter cryptographic errors in the airport management system.
 * <p>
 * This exception is specifically designed to handle cryptographic failures and password processing errors
 * within the {@link PasswordHandler} component's password hashing functionality. It serves as a dedicated
 * exception type for password operations, providing clear error identification and debugging support
 * for password security failures and cryptographic algorithm issues.
 * </p>
 * <p>
 * The PasswordException is primarily used for:
 * </p>
 * <ul>
 *   <li><strong>Cryptographic Algorithm Failures:</strong> Thrown when SHA-256 or other hashing algorithms are unavailable</li>
 *   <li><strong>Password Processing Errors:</strong> Thrown when password hashing operations fail due to system issues</li>
 *   <li><strong>Security System Failures:</strong> Thrown when cryptographic subsystems encounter unexpected errors</li>
 *   <li><strong>System Integrity:</strong> Ensures proper error handling for critical password security operations</li>
 * </ul>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see RuntimeException
 * @see PasswordHandler
 * @see java.security.NoSuchAlgorithmException
 * @see java.security.MessageDigest
 */
public class PasswordException extends RuntimeException {
    
    /**
     * Constructs a new PasswordException with the specified detail message.
     *
     * @param message the detail message explaining the specific password processing or cryptographic failure.
     */
    public PasswordException(String message) {
        super(message);
    }
}