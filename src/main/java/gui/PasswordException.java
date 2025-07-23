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
 * <p>
 * The exception extends {@link RuntimeException}, making it an unchecked exception that does
 * not require explicit handling by calling code. This design choice allows the password system
 * to fail fast when cryptographic operations encounter irrecoverable errors, while not burdening
 * calling code with mandatory exception handling for system-level cryptographic failures.
 * </p>
 * <p>
 * Common scenarios that trigger PasswordException include:
 * </p>
 * <ul>
 *   <li><strong>Missing Cryptographic Algorithms:</strong> When SHA-256 or other required hashing algorithms are not available in the JVM</li>
 *   <li><strong>Security Provider Issues:</strong> When cryptographic service providers encounter configuration or operational problems</li>
 *   <li><strong>System Resource Constraints:</strong> When password hashing operations fail due to insufficient system resources</li>
 *   <li><strong>Platform Compatibility Issues:</strong> When cryptographic operations fail due to platform-specific limitations</li>
 * </ul>
 * <p>
 * The exception provides descriptive error messages that aid in debugging and system maintenance
 * by clearly identifying the specific cryptographic failure and providing context for resolution.
 * Error messages typically include details about the underlying cryptographic exception that
 * triggered the password processing failure.
 * </p>
 * <p>
 * Integration with the password handling system ensures that cryptographic failures are properly
 * escalated and handled at appropriate system levels. The exception enables proper error propagation
 * from low-level cryptographic operations to higher-level password management functionality,
 * maintaining system integrity and security throughout password processing workflows.
 * </p>
 * <p>
 * Security considerations include ensuring that cryptographic failures do not compromise system
 * security or expose sensitive information. The exception provides sufficient detail for debugging
 * while avoiding exposure of cryptographic implementation details that might compromise security.
 * </p>
 * <p>
 * The exception is typically caught and handled by authentication systems, user registration
 * components, and password management interfaces to provide appropriate user feedback when
 * password operations fail due to system-level issues. It ensures that password security
 * operations maintain integrity and fail safely when cryptographic errors occur.
 * </p>
 * <p>
 * Error recovery strategies typically involve system administration intervention to resolve
 * cryptographic configuration issues, security provider problems, or platform compatibility
 * concerns that prevent proper password hashing operations.
 * </p>
 * <p>
 * The class integrates seamlessly with the broader airport management system through consistent
 * usage within password security components including {@link PasswordHandler}, authentication
 * interfaces, and user management systems while maintaining clean separation of concerns
 * and proper error handling patterns.
 * </p>
 * <p>
 * Logging and monitoring systems can leverage this exception to track cryptographic system
 * health and identify potential security subsystem issues that require administrative attention
 * or system configuration updates to maintain proper password security operations.
 * </p>
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
     * <p>
     * This constructor creates an exception instance with a descriptive error message
     * that explains the specific nature of the password processing or cryptographic failure.
     * The message provides detailed information about the underlying error that caused
     * the password operation to fail, enabling effective debugging and system maintenance.
     * </p>
     * <p>
     * The message parameter should clearly describe the cryptographic failure scenario
     * to help system administrators and developers identify and resolve password security
     * issues. Common message formats include details about missing cryptographic algorithms,
     * security provider failures, or system resource constraints that prevent proper
     * password hashing operations.
     * </p>
     * <p>
     * Error messages are designed to be informative for technical personnel while avoiding
     * exposure of sensitive cryptographic implementation details that might compromise
     * system security. The messages provide sufficient context for problem resolution
     * without revealing internal security mechanisms or cryptographic configurations.
     * </p>
     * <p>
     * The constructor delegates to the parent RuntimeException constructor, ensuring
     * proper exception initialization and integration with standard Java exception
     * handling mechanisms. This provides compatibility with logging frameworks and
     * exception handling patterns throughout the airport management system.
     * </p>
     * <p>
     * The message is preserved for later retrieval through the {@link #getMessage()}
     * method, enabling exception handlers and logging systems to access detailed
     * error information for debugging, monitoring, and system administration purposes.
     * </p>
     * <p>
     * Usage scenarios include wrapping underlying cryptographic exceptions such as
     * {@link java.security.NoSuchAlgorithmException} to provide consistent error
     * handling throughout the password management system while preserving essential
     * error details for troubleshooting and system maintenance.
     * </p>
     *
     * @param message the detail message explaining the specific password processing or cryptographic failure.
     *               The message is saved for later retrieval by the {@link #getMessage()} method.
     */
    public PasswordException(String message) {
        super(message);
    }
}