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
 * <p>
 * The validation system is designed with security and user experience optimization, providing:
 * </p>
 * <ul>
 *   <li><strong>Precise Error Identification:</strong> Specific validation failure reasons for targeted user guidance</li>
 *   <li><strong>Internationalization Support:</strong> Error code structure that supports localized error message generation</li>
 *   <li><strong>Comprehensive Coverage:</strong> Complete validation rule coverage for robust password security</li>
 *   <li><strong>Consistent Implementation:</strong> Standardized validation codes across all password input components</li>
 *   <li><strong>Developer Clarity:</strong> Clear, descriptive enum values that enhance code readability and maintenance</li>
 * </ul>
 * <p>
 * Integration architecture enables seamless use across multiple password validation contexts including
 * user registration ({@link RegisterScreen}), account modification ({@link ModifyAccount}), and
 * authentication ({@link LogInScreen}) while maintaining consistent validation behavior and
 * error reporting throughout the airport management system.
 * </p>
 * <p>
 * Password security enforcement follows industry best practices through comprehensive validation
 * rules that ensure strong password creation while providing clear user feedback for validation
 * failures. The system balances security requirements with user experience considerations to
 * promote adoption of secure password practices.
 * </p>
 * <p>
 * The enumeration integrates with the {@link PasswordHandler} component to provide real-time
 * validation feedback and error message generation. Each validation code corresponds to specific
 * error conditions that can be translated into user-friendly messages with appropriate guidance
 * for password improvement.
 * </p>
 * <p>
 * Validation workflow utilizes these codes to track validation state throughout password input,
 * enabling immediate user feedback and preventing submission of invalid passwords. The codes
 * support both positive validation confirmation and detailed error reporting for validation
 * failures.
 * </p>
 * <p>
 * Error message generation leverages these codes to produce localized, context-appropriate
 * error messages through the {@link PasswordHandler#showInvalidPasswordMessage} method,
 * ensuring consistent user communication and clear guidance for password improvement across
 * all password input scenarios.
 * </p>
 * <p>
 * The enumeration supports extensibility for future password validation requirements while
 * maintaining backward compatibility with existing validation logic. Additional validation
 * codes can be added to support enhanced security requirements or specialized validation
 * scenarios as system requirements evolve.
 * </p>
 * <p>
 * Security considerations include comprehensive coverage of common password weaknesses while
 * avoiding overly restrictive rules that might compromise user experience. The validation
 * codes enable implementation of balanced security policies that promote strong passwords
 * without creating excessive barriers to legitimate system access.
 * </p>
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
     * This code confirms that the password meets all validation criteria including:
     * length requirements, character composition requirements, and character set
     * restrictions. Passwords receiving this code are considered secure and
     * suitable for system use.
     * </p>
     * <p>
     * Usage scenarios include:
     * </p>
     * <ul>
     *   <li>Successful password validation during user registration</li>
     *   <li>Valid password confirmation during account modification</li>
     *   <li>Password strength verification for security compliance</li>
     *   <li>Pre-submission validation for password input fields</li>
     * </ul>
     */
    VALID_PASSWORD,

    /**
     * Indicates password validation failure due to insufficient character count.
     * <p>
     * This code is assigned when the password length falls below the minimum
     * required character count as defined by {@link PasswordHandler#MINIMUM_PASSWORD_LENGTH}.
     * The validation system enforces minimum length requirements to ensure
     * adequate password security against brute force attacks.
     * </p>
     * <p>
     * Error handling includes:
     * </p>
     * <ul>
     *   <li>Display of minimum character count requirement</li>
     *   <li>Current character count indication for user guidance</li>
     *   <li>Suggestion to add additional characters to meet requirements</li>
     *   <li>Prevention of password submission until requirement is met</li>
     * </ul>
     */
    TOO_SHORT,

    /**
     * Indicates password validation failure due to excessive character count.
     * <p>
     * This code is assigned when the password length exceeds the maximum
     * allowed character count as defined by {@link PasswordHandler#MAXIMUM_PASSWORD_LENGTH}.
     * The validation system enforces maximum length limits to ensure system
     * compatibility and prevent potential security vulnerabilities from
     * excessively long passwords.
     * </p>
     * <p>
     * Error handling includes:
     * </p>
     * <ul>
     *   <li>Display of maximum character count limitation</li>
     *   <li>Current character count indication showing excess</li>
     *   <li>Suggestion to reduce password length to meet requirements</li>
     *   <li>Prevention of password submission until requirement is met</li>
     * </ul>
     */
    TOO_LONG,

    /**
     * Indicates password validation failure due to absence of required lowercase characters.
     * <p>
     * This code is assigned when the password does not contain at least one
     * lowercase letter (a-z). The validation system requires lowercase characters
     * as part of comprehensive character composition requirements that enhance
     * password security through character diversity.
     * </p>
     * <p>
     * Security rationale includes:
     * </p>
     * <ul>
     *   <li>Increased password complexity through mixed case requirements</li>
     *   <li>Enhanced resistance to dictionary-based attacks</li>
     *   <li>Compliance with industry standard password security practices</li>
     *   <li>Balanced requirement that maintains usability while improving security</li>
     * </ul>
     */
    MUST_CONTAIN_LOWERCASE,

    /**
     * Indicates password validation failure due to absence of required uppercase characters.
     * <p>
     * This code is assigned when the password does not contain at least one
     * uppercase letter (A-Z). The validation system requires uppercase characters
     * as part of comprehensive character composition requirements that enhance
     * password security through character diversity and case variation.
     * </p>
     * <p>
     * Security benefits include:
     * </p>
     * <ul>
     *   <li>Increased password entropy through case variation</li>
     *   <li>Enhanced protection against automated password cracking</li>
     *   <li>Compliance with enterprise security standards</li>
     *   <li>Improved resistance to common password attack vectors</li>
     * </ul>
     */
    MUST_CONTAIN_UPPERCASE,

    /**
     * Indicates password validation failure due to absence of required numeric characters.
     * <p>
     * This code is assigned when the password does not contain at least one
     * numeric digit (0-9). The validation system requires numeric characters
     * as part of comprehensive character composition requirements that enhance
     * password security through character type diversity.
     * </p>
     * <p>
     * Validation benefits include:
     * </p>
     * <ul>
     *   <li>Increased character space for password generation</li>
     *   <li>Enhanced protection against linguistic pattern attacks</li>
     *   <li>Improved password strength through numeric integration</li>
     *   <li>Compliance with multi-character-type security requirements</li>
     * </ul>
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
     * <p>
     * Security advantages include:
     * </p>
     * <ul>
     *   <li>Maximum password entropy through special character inclusion</li>
     *   <li>Enhanced resistance to pattern-based attack methods</li>
     *   <li>Compliance with advanced security policy requirements</li>
     *   <li>Improved protection against automated password generation attacks</li>
     * </ul>
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
     * <p>
     * Character restrictions include:
     * </p>
     * <ul>
     *   <li>Prohibition of space characters to prevent parsing and display issues</li>
     *   <li>Restriction to predefined safe character set for system compatibility</li>
     *   <li>Prevention of control characters that might cause system errors</li>
     *   <li>Exclusion of characters that might interfere with database storage or transmission</li>
     * </ul>
     */
    CHARACTER_NOT_ALLOWED
}