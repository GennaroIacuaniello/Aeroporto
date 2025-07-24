package gui;

/**
 * Enumeration defining comprehensive password validation result codes for the airport management system.
 * <p>
 * This enum provides standardized validation status codes used throughout the password validation
 * system to indicate specific validation outcomes and error conditions. The codes enable precise
 * error reporting, user feedback generation, and validation state management across all password
 * input components within the airport management system.
 * </p>
 * <p>
 * The PasswordCode enumeration supports comprehensive password validation including:
 * </p>
 * <ul>
 *   <li><strong>Length Validation:</strong> Minimum and maximum character count enforcement</li>
 *   <li><strong>Character Composition:</strong> Required character type validation including uppercase, lowercase, digits, and special characters</li>
 *   <li><strong>Character Set Enforcement:</strong> Allowed character validation and prohibited character detection</li>
 *   <li><strong>Success Indication:</strong> Valid password confirmation for successful validation</li>
 *   <li><strong>Error Classification:</strong> Specific error categorization for targeted user feedback</li>
 * </ul>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see PasswordHandler
 * @see RegisterScreen
 * @see ModifyAccount
 * @see LogInScreen
 * @see PasswordException
 */
public enum PasswordCode {

    /**
     * Indicates successful password validation with all security requirements satisfied.
     * <p>
     * This code confirms that the password meets all validation criteria, including
     * length requirements, character composition requirements, and character set
     * restrictions. Passwords receiving this code are considered secure and
     * suitable for system use.
     * </p>
     */
    VALID_PASSWORD,

    /**
     * Indicates password validation failure due to insufficient character count.
     */
    TOO_SHORT,

    /**
     * Indicates password validation failure due to excessive character count.
     */
    TOO_LONG,

    /**
     * Indicates password validation failure due to the absence of required lowercase characters.
     */
    MUST_CONTAIN_LOWERCASE,

    /**
     * Indicates password validation failure due to absence of required uppercase characters.
     */
    MUST_CONTAIN_UPPERCASE,

    /**
     * Indicates password validation failure due to absence of required numeric characters.
     */
    MUST_CONTAIN_DIGIT,

    /**
     * Indicates password validation failure due to absence of required special characters.
     * <p>
     * This code is assigned when the password does not contain at least one
     * special character from the allowed set defined by
     * {@link PasswordHandler#ALLOWED_SPECIAL_CHARACTERS}. The validation system
     * requires special characters to maximize password complexity and security
     * through comprehensive character type requirements.
     * </p>
     */
    MUST_CONTAIN_SPECIAL,

    /**
     * Indicates password validation failure due to presence of prohibited characters.
     * <p>
     * This code is assigned when the password contains characters that are not
     * included in the allowed character set defined by
     * {@link PasswordHandler#ALLOWED_CHARACTER_SET} or contains space characters
     * which are specifically prohibited. The validation system enforces character
     * set restrictions to ensure system compatibility and prevent potential
     * security vulnerabilities from unsupported characters.
     * </p>
     */
    CHARACTER_NOT_ALLOWED
}