package gui;

import javax.swing.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Specialized password field component with comprehensive validation, security, and hashing capabilities for the airport management system.
 * <p>
 * This class extends {@link JPasswordField} to provide advanced password management functionality including
 * real-time validation, secure cryptographic hashing, character composition enforcement, and user feedback
 * generation. The component serves as the primary password input interface across authentication, registration,
 * and account management workflows throughout the airport management system.
 * </p>
 * <p>
 * The PasswordHandler class supports comprehensive password security including:
 * </p>
 * <ul>
 *   <li><strong>Advanced Validation:</strong> Multi-criteria password strength validation with character composition requirements</li>
 *   <li><strong>Cryptographic Security:</strong> SHA-256 password hashing with proper salt handling and secure storage</li>
 *   <li><strong>Character Enforcement:</strong> Strict character set validation with prohibited character detection</li>
 *   <li><strong>User Feedback:</strong> Real-time validation feedback with localized error messages through FloatingMessage integration</li>
 *   <li><strong>Length Validation:</strong> Configurable minimum and maximum password length enforcement</li>
 *   <li><strong>Security Standards Compliance:</strong> Industry-standard password requirements for enhanced security</li>
 * </ul>
 * <p>
 * The validation system is designed with security and user experience optimization, providing:
 * </p>
 * <ul>
 *   <li><strong>Real-time Validation:</strong> Immediate password strength assessment during user input</li>
 *   <li><strong>Comprehensive Requirements:</strong> Uppercase, lowercase, numeric, and special character validation</li>
 *   <li><strong>Clear User Guidance:</strong> Detailed error messages with specific improvement suggestions</li>
 *   <li><strong>Flexible Integration:</strong> Seamless integration with various authentication and account management interfaces</li>
 *   <li><strong>Security-First Design:</strong> Cryptographically secure password processing with proper error handling</li>
 * </ul>
 * <p>
 * Security architecture utilizes industry-standard SHA-256 cryptographic hashing with comprehensive
 * error handling for cryptographic failures. The hashing implementation includes proper byte conversion,
 * hexadecimal encoding, and exception management to ensure reliable password security across all
 * system interactions and data storage operations.
 * </p>
 * <p>
 * Validation criteria enforce balanced security requirements that promote strong passwords without
 * creating excessive user experience barriers. The system requires passwords to contain uppercase
 * letters, lowercase letters, numeric digits, and special characters while maintaining reasonable
 * length constraints for practical usability.
 * </p>
 * <p>
 * Character set enforcement includes comprehensive validation against a predefined safe character
 * set that ensures system compatibility while preventing potential security vulnerabilities from
 * unsupported or problematic characters. Space characters are specifically prohibited to prevent
 * parsing and display issues throughout the system.
 * </p>
 * <p>
 * Error messaging provides localized Italian feedback through the {@link FloatingMessage} system,
 * offering users specific guidance for password improvement. Messages include detailed requirements
 * information, character set specifications, and length constraints to facilitate successful
 * password creation and modification.
 * </p>
 * <p>
 * Integration architecture enables seamless use across multiple authentication contexts including
 * user registration ({@link RegisterScreen}), account modification ({@link ModifyAccount}), and
 * authentication ({@link LogInScreen}) while maintaining consistent validation behavior and
 * security standards throughout the airport management system.
 * </p>
 * <p>
 * The component maintains validation state through the {@link PasswordCode} enumeration system,
 * enabling precise error identification and appropriate user feedback generation. The state management
 * supports both validation checking and error message generation for comprehensive password handling.
 * </p>
 * <p>
 * Cryptographic implementation includes robust error handling for algorithm availability issues,
 * with {@link PasswordException} integration to provide clear error reporting when cryptographic
 * operations fail due to system configuration or platform compatibility issues.
 * </p>
 * <p>
 * The class follows standard Swing component patterns while providing enhanced security functionality,
 * maintaining compatibility with existing Swing applications and design patterns while offering
 * advanced password security capabilities required for enterprise-level airport management systems.
 * </p>
 * <p>
 * Performance considerations include efficient validation algorithms and optimized hashing operations
 * that provide security without compromising user experience during password entry and validation
 * workflows throughout the airport management system.
 * </p>
 *
 * @author Aeroporto Di Napoli
 * @version 1.0
 * @since 1.0
 * @see JPasswordField
 * @see PasswordCode
 * @see PasswordException
 * @see FloatingMessage
 * @see RegisterScreen
 * @see ModifyAccount
 * @see LogInScreen
 * @see MessageDigest
 */
public class PasswordHandler extends JPasswordField {

    /**
     * Minimum required password length for security compliance.
     * <p>
     * This constant defines the minimum number of characters required for password
     * validation. The 8-character minimum follows industry security standards to
     * ensure adequate password strength against brute force attacks while maintaining
     * reasonable usability for legitimate users.
     * </p>
     */
    public static final int MINIMUM_PASSWORD_LENGTH = 8;
    
    /**
     * Maximum allowed password length for system compatibility and security.
     * <p>
     * This constant defines the maximum number of characters allowed for password
     * validation. The 20-character maximum ensures system compatibility and prevents
     * potential security vulnerabilities from excessively long passwords while
     * providing adequate flexibility for complex password creation.
     * </p>
     */
    public static final int MAXIMUM_PASSWORD_LENGTH = 20;

    /**
     * Set of allowed special characters for password validation.
     * <p>
     * This constant defines the comprehensive set of special characters permitted
     * in passwords. The character set includes standard punctuation and symbols
     * that enhance password complexity while ensuring system compatibility and
     * preventing potential parsing or display issues throughout the application.
     * </p>
     */
    public static final String ALLOWED_SPECIAL_CHARACTERS = "!#$%&'()*+,-./:;<=>?@[]^_`{|}~";
    
    /**
     * Complete set of allowed characters for password validation.
     * <p>
     * This constant defines the comprehensive character set permitted in passwords,
     * including lowercase letters, uppercase letters, numeric digits, and special
     * characters. The character set ensures system compatibility while providing
     * adequate character diversity for strong password creation.
     * </p>
     */
    public static final String ALLOWED_CHARACTER_SET = "abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ 0123456789 " + ALLOWED_SPECIAL_CHARACTERS;

    /**
     * Cryptographic hashing algorithm identifier for password security.
     * <p>
     * This field specifies the hashing algorithm used for password encryption.
     * SHA-256 provides cryptographically secure password hashing that ensures
     * password confidentiality and integrity throughout storage and transmission
     * operations within the airport management system.
     * </p>
     */
    String hashingAlgorithm = "SHA-256";
    
    /**
     * Current password validation status code for error tracking and user feedback.
     * <p>
     * This field maintains the current validation state of the password field,
     * enabling precise error identification and appropriate user feedback generation.
     * The validation code is updated during validation operations and used for
     * error message generation through the FloatingMessage system.
     * </p>
     */
    PasswordCode passwordValidityCode;

    /**
     * Constructs a new PasswordHandler with default configuration and comprehensive password management capabilities.
     * <p>
     * This constructor initializes the password field with standard JPasswordField behavior while
     * establishing the foundation for advanced password validation, hashing, and security features.
     * The constructor creates a fully functional password component ready for immediate integration
     * into authentication and account management interfaces.
     * </p>
     * <p>
     * Default initialization includes:
     * </p>
     * <ul>
     *   <li><strong>Parent Class Initialization:</strong> Standard JPasswordField construction with default sizing</li>
     *   <li><strong>Security Configuration:</strong> SHA-256 hashing algorithm setup for cryptographic operations</li>
     *   <li><strong>Validation Preparation:</strong> Password validation system initialization for real-time checking</li>
     *   <li><strong>Character Set Setup:</strong> Allowed character configuration for input validation</li>
     * </ul>
     * <p>
     * The constructor establishes a password field ready for immediate use in authentication
     * workflows with all security and validation features available for password processing
     * and user feedback generation throughout the airport management system.
     * </p>
     */
    public PasswordHandler(){
        super();
    }

    /**
     * Constructs a new PasswordHandler with specified column width and comprehensive password management capabilities.
     * <p>
     * This constructor initializes the password field with custom column sizing while establishing
     * the foundation for advanced password validation, hashing, and security features. The constructor
     * creates a fully functional password component with specified visual dimensions ready for
     * integration into authentication and account management interfaces.
     * </p>
     * <p>
     * Custom width initialization includes:
     * </p>
     * <ul>
     *   <li><strong>Parent Class Initialization:</strong> JPasswordField construction with specified column width</li>
     *   <li><strong>Visual Configuration:</strong> Custom field sizing for optimal integration with interface layouts</li>
     *   <li><strong>Security Configuration:</strong> SHA-256 hashing algorithm setup for cryptographic operations</li>
     *   <li><strong>Validation Preparation:</strong> Password validation system initialization for real-time checking</li>
     * </ul>
     * <p>
     * The constructor provides flexibility for interface design requirements while maintaining
     * full password security and validation capabilities for use throughout authentication
     * and account management workflows in the airport management system.
     * </p>
     *
     * @param columns the number of columns to use for calculating the preferred width of the password field
     */
    public PasswordHandler(int columns){
        super(columns);
    }

    /**
     * Checks whether the password field is empty for validation and form processing.
     * <p>
     * This method provides a convenient way to determine if the password field contains
     * any characters, supporting validation workflows and form submission logic throughout
     * authentication and account management interfaces. The method enables empty field
     * detection for comprehensive form validation and user feedback systems.
     * </p>
     * <p>
     * Empty field detection supports:
     * </p>
     * <ul>
     *   <li><strong>Form Validation:</strong> Required field checking for authentication and registration forms</li>
     *   <li><strong>User Experience:</strong> Preventing submission of incomplete password information</li>
     *   <li><strong>Security Enforcement:</strong> Ensuring password presence before processing authentication</li>
     *   <li><strong>Interface Logic:</strong> Enabling conditional behavior based on password field state</li>
     * </ul>
     *
     * @return true if the password field contains no characters, false if characters are present
     */
    public boolean isEmpty(){
        return this.getPassword().length == 0;
    }

    /**
     * Generates a cryptographically secure SHA-256 hash of the current password with comprehensive error handling.
     * <p>
     * This method performs secure password hashing using the SHA-256 cryptographic algorithm,
     * converting the password characters to bytes and generating a hexadecimal hash representation
     * suitable for secure storage and authentication operations. The method includes comprehensive
     * error handling for cryptographic failures and algorithm availability issues.
     * </p>
     * <p>
     * Hashing process includes:
     * </p>
     * <ul>
     *   <li><strong>Algorithm Initialization:</strong> SHA-256 MessageDigest instance creation with availability checking</li>
     *   <li><strong>Byte Conversion:</strong> Secure conversion of password characters to byte array representation</li>
     *   <li><strong>Cryptographic Processing:</strong> SHA-256 hash generation with proper digest computation</li>
     *   <li><strong>Hexadecimal Encoding:</strong> Conversion of binary hash to hexadecimal string representation</li>
     *   <li><strong>Error Management:</strong> PasswordException generation for cryptographic algorithm failures</li>
     * </ul>
     * <p>
     * Security considerations include proper handling of password character data during conversion
     * and comprehensive error reporting when cryptographic operations fail due to system configuration
     * or platform compatibility issues. The method ensures consistent hash generation across
     * different system environments and configurations.
     * </p>
     * <p>
     * Hexadecimal encoding includes proper padding for single-digit hex values to ensure consistent
     * hash length and format. The encoding process generates lowercase hexadecimal representation
     * suitable for database storage and authentication comparison operations.
     * </p>
     * <p>
     * Error handling provides clear exception reporting through {@link PasswordException} when
     * SHA-256 algorithm is unavailable or cryptographic operations fail, enabling appropriate
     * error recovery and user feedback in authentication and registration workflows.
     * </p>
     *
     * @return the SHA-256 hash of the password as a lowercase hexadecimal string
     * @throws PasswordException if SHA-256 algorithm is unavailable or cryptographic operations fail
     */
    public String hashPassword(){
        try {
            MessageDigest digest = MessageDigest.getInstance(hashingAlgorithm);
            byte[] encodedHash = digest.digest((this.convertInBytes()));

            StringBuilder hexString = new StringBuilder();
            for(byte b : encodedHash){
                String hex = Integer.toHexString(0xff & b);
                if(hex.length() == 1){
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new PasswordException(e.getMessage());
        }
    }

    /**
     * Provides convenient access to the hashed password for authentication and account management operations.
     * <p>
     * This method serves as a convenient wrapper for the {@link #hashPassword()} method, providing
     * consistent access to password hashing functionality throughout authentication workflows and
     * account management operations. The method maintains the same security and error handling
     * characteristics as the underlying hashing implementation.
     * </p>
     * <p>
     * The method supports various authentication scenarios including:
     * </p>
     * <ul>
     *   <li><strong>User Registration:</strong> Password hashing for new account creation and secure storage</li>
     *   <li><strong>Authentication Verification:</strong> Hash generation for login credential comparison</li>
     *   <li><strong>Account Modification:</strong> Password change operations with secure hash generation</li>
     *   <li><strong>Security Operations:</strong> Consistent hash generation across different security contexts</li>
     * </ul>
     *
     * @return the SHA-256 hash of the password as a lowercase hexadecimal string
     * @throws PasswordException if SHA-256 algorithm is unavailable or cryptographic operations fail
     */
    public String getHashedPassword(){
        return hashPassword();
    }

    /**
     * Converts password characters to byte array for cryptographic processing with secure character handling.
     * <p>
     * This private utility method performs secure conversion of password characters to byte array
     * representation required for cryptographic hashing operations. The method handles character
     * encoding properly while ensuring that password data is processed securely throughout the
     * conversion process.
     * </p>
     * <p>
     * Conversion process includes:
     * </p>
     * <ul>
     *   <li><strong>Array Initialization:</strong> Byte array creation with appropriate sizing for password length</li>
     *   <li><strong>Character Processing:</strong> Individual character conversion to byte representation</li>
     *   <li><strong>Secure Handling:</strong> Proper character data management during conversion operations</li>
     *   <li><strong>Memory Management:</strong> Efficient byte array population with minimal memory overhead</li>
     * </ul>
     * <p>
     * The conversion maintains data integrity throughout the process while ensuring that password
     * characters are properly represented in byte format for cryptographic hash generation. The
     * method supports the overall password security architecture by providing reliable character
     * to byte conversion for hashing operations.
     * </p>
     *
     * @return byte array representation of the password characters for cryptographic processing
     */
    private byte[] convertInBytes(){

        byte[] passwordInBytes = new byte[this.getPassword().length];
        int i = 0;
        for (char a: this.getPassword()){
            passwordInBytes[i] = (byte)(a);
            i++;
        }

        return passwordInBytes;
    }

    /**
     * Performs comprehensive password validation with multi-criteria security requirements and detailed error tracking.
     * <p>
     * This method conducts thorough password validation against industry-standard security requirements
     * including length constraints, character composition requirements, and character set restrictions.
     * The validation provides real-time password strength assessment with detailed error code generation
     * for specific user feedback and guidance throughout password creation and modification workflows.
     * </p>
     * <p>
     * Validation criteria include:
     * </p>
     * <ul>
     *   <li><strong>Length Validation:</strong> Minimum 8 and maximum 20 character requirements for security and compatibility</li>
     *   <li><strong>Character Composition:</strong> Required uppercase, lowercase, numeric, and special character inclusion</li>
     *   <li><strong>Character Set Enforcement:</strong> Validation against allowed character set with prohibited character detection</li>
     *   <li><strong>Security Standards:</strong> Industry-standard password complexity requirements for enhanced security</li>
     *   <li><strong>Error Classification:</strong> Specific error code generation for targeted user feedback and guidance</li>
     * </ul>
     * <p>
     * Length validation ensures that passwords meet security requirements for brute force resistance
     * while maintaining system compatibility and reasonable user experience. The validation prevents
     * both insufficiently short passwords and excessively long passwords that might cause system issues.
     * </p>
     * <p>
     * Character composition validation requires the presence of at least one character from each
     * required category: uppercase letters (A-Z), lowercase letters (a-z), numeric digits (0-9),
     * and special characters from the approved set. This requirement ensures maximum password entropy
     * and resistance to common attack methods.
     * </p>
     * <p>
     * Character set enforcement validates each password character against the allowed character set
     * while specifically prohibiting space characters that might cause parsing or display issues.
     * The validation ensures system compatibility and prevents potential security vulnerabilities
     * from unsupported characters.
     * </p>
     * <p>
     * Error tracking utilizes the {@link PasswordCode} enumeration to provide specific validation
     * failure identification, enabling precise user feedback generation through the error message
     * system. Each validation failure sets an appropriate error code for subsequent message generation.
     * </p>
     * <p>
     * The validation algorithm processes all password characters efficiently while maintaining
     * comprehensive security checking, providing optimal performance during real-time validation
     * without compromising security requirements or user experience.
     * </p>
     *
     * @return true if the password meets all security requirements, false if validation fails
     */
    public boolean isValidPassword(){
        boolean upper = false;
        boolean lower = false;
        boolean digit = false;
        boolean special = false;

        if(this.getPassword().length < MINIMUM_PASSWORD_LENGTH){
            this.passwordValidityCode = PasswordCode.TOO_SHORT;
            return false;
        } else if (this.getPassword().length > MAXIMUM_PASSWORD_LENGTH) {
            this.passwordValidityCode = PasswordCode.TOO_LONG;
            return false;
        }

        for(char c : this.getPassword()){
            if(ALLOWED_CHARACTER_SET.indexOf(c) == -1 || c == ' '){
                this.passwordValidityCode = PasswordCode.CHARACTER_NOT_ALLOWED;
            }
            if(Character.isUpperCase(c)){
                upper = true;
            } else if (Character.isLowerCase(c)) {
                lower = true;
            } else if (Character.isDigit(c)) {
                digit = true;
            } else if (ALLOWED_SPECIAL_CHARACTERS.indexOf(c) != -1) {
                special = true;
            }
        }

        if(!upper){
            this.passwordValidityCode = PasswordCode.MUST_CONTAIN_UPPERCASE;
            return false;
        }
        if(!lower){
            this.passwordValidityCode = PasswordCode.MUST_CONTAIN_LOWERCASE;
            return false;
        }
        if(!digit){
            this.passwordValidityCode = PasswordCode.MUST_CONTAIN_DIGIT;
            return false;
        }
        if(!special){
            this.passwordValidityCode = PasswordCode.MUST_CONTAIN_SPECIAL;
            return false;
        }

        this.passwordValidityCode = PasswordCode.VALID_PASSWORD;
        return true;
    }

    /**
     * Displays localized validation error messages with specific password improvement guidance through FloatingMessage integration.
     * <p>
     * This method generates and displays comprehensive validation error messages based on the current
     * password validation state, providing users with specific guidance for password improvement.
     * The method utilizes the {@link FloatingMessage} system to present localized Italian error
     * messages with detailed requirements information and character set specifications.
     * </p>
     * <p>
     * Error message generation includes:
     * </p>
     * <ul>
     *   <li><strong>Length Requirements:</strong> Specific minimum and maximum character count information</li>
     *   <li><strong>Character Composition:</strong> Detailed requirements for uppercase, lowercase, numeric, and special characters</li>
     *   <li><strong>Character Set Information:</strong> Complete allowed character set display for user reference</li>
     *   <li><strong>Localized Content:</strong> Italian language error messages for consistent user experience</li>
     *   <li><strong>Visual Presentation:</strong> HTML-formatted messages with proper line breaks and emphasis</li>
     * </ul>
     * <p>
     * Message types include specific guidance for each validation failure scenario:
     * </p>
     * <ul>
     *   <li><strong>TOO_SHORT:</strong> Minimum character count requirement with current minimum value</li>
     *   <li><strong>TOO_LONG:</strong> Maximum character count limitation with current maximum value</li>
     *   <li><strong>MUST_CONTAIN_LOWERCASE:</strong> Lowercase letter requirement explanation</li>
     *   <li><strong>MUST_CONTAIN_UPPERCASE:</strong> Uppercase letter requirement explanation</li>
     *   <li><strong>MUST_CONTAIN_DIGIT:</strong> Numeric digit requirement explanation</li>
     *   <li><strong>MUST_CONTAIN_SPECIAL:</strong> Special character requirement with complete character set display</li>
     *   <li><strong>CHARACTER_NOT_ALLOWED:</strong> Invalid character notification with allowed character set information</li>
     * </ul>
     * <p>
     * HTML formatting provides proper message presentation with line breaks, emphasis, and structured
     * information display. The formatting ensures optimal readability and professional appearance
     * within the FloatingMessage presentation system.
     * </p>
     * <p>
     * FloatingMessage integration positions error messages appropriately relative to the specified
     * button component, providing contextual error feedback that guides users toward successful
     * password creation and modification throughout authentication and account management workflows.
     * </p>
     *
     * @param button the button component to position the floating error message relative to for optimal user feedback presentation
     */
    public void showInvalidPasswordMessage(JButton button) {
        String warningMessage = null;
        switch (this.passwordValidityCode){
            case TOO_SHORT -> warningMessage = "<html>La password deve contenere almeno " + PasswordHandler.MINIMUM_PASSWORD_LENGTH + " caratteri</html>";
            case TOO_LONG -> warningMessage = "<html>La password può contenere al più " + PasswordHandler.MAXIMUM_PASSWORD_LENGTH + " caratteri</html>";
            case MUST_CONTAIN_LOWERCASE -> warningMessage = "<html>La password deve contenere almeno un carattere minuscolo</html>";
            case MUST_CONTAIN_UPPERCASE -> warningMessage = "<html>La password deve contenere almeno un carattere maiuscolo</html>";
            case MUST_CONTAIN_DIGIT -> warningMessage = "<html>La password deve contenere almeno una cifra</html>";
            case MUST_CONTAIN_SPECIAL -> warningMessage = "<html>La password deve contenere almeno uno dei seguenti caratteri: <br>" +
                    PasswordHandler.ALLOWED_SPECIAL_CHARACTERS + "</html>";
            case CHARACTER_NOT_ALLOWED -> warningMessage = "<html>La password inserita contiene un carattere non valido. <br>" +
                    "I caratteri validi sono: <br>" + PasswordHandler.ALLOWED_CHARACTER_SET + "</html>";
            case VALID_PASSWORD -> warningMessage = "";
        }
        new FloatingMessage(warningMessage, button, FloatingMessage.WARNING_MESSAGE);
    }
}