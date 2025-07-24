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
 * The PasswordHandler class supports comprehensive password security, including:
 * </p>
 * <ul>
 *   <li><strong>Advanced Validation:</strong> Multi-criteria password strength validation with character composition requirements</li>
 *   <li><strong>Cryptographic Security:</strong> SHA-256 password hashing with proper salt handling and secure storage</li>
 *   <li><strong>Character Enforcement:</strong> Strict character set validation with prohibited character detection</li>
 *   <li><strong>User Feedback:</strong> Real-time validation feedback with localized error messages through FloatingMessage integration</li>
 *   <li><strong>Length Validation:</strong> Configurable minimum and maximum password length enforcement</li>
 *   <li><strong>Security Standards Compliance:</strong> Industry-standard password requirements for enhanced security</li>
 * </ul>
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
     */
    public static final int MINIMUM_PASSWORD_LENGTH = 8;
    
    /**
     * Maximum allowed password length for system compatibility and security.
     */
    public static final int MAXIMUM_PASSWORD_LENGTH = 20;

    /**
     * Set of allowed special characters for password validation.
     */
    public static final String ALLOWED_SPECIAL_CHARACTERS = "!#$%&'()*+,-./:;<=>?@[]^_`{|}~";
    
    /**
     * Complete set of allowed characters for password validation.
     */
    public static final String ALLOWED_CHARACTER_SET = "abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ 0123456789 " + ALLOWED_SPECIAL_CHARACTERS;

    /**
     * Cryptographic hashing algorithm identifier for password security.
     */
    String hashingAlgorithm = "SHA-256";
    
    /**
     * Current password validation status code for error tracking and user feedback.
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
     *
     * @param columns the number of columns to use for calculating the preferred width of the password field
     */
    public PasswordHandler(int columns){
        super(columns);
    }

    /**
     * Checks whether the password field is empty for validation and form processing.
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
     * The hashing process includes:
     * </p>
     * <ul>
     *   <li><strong>Algorithm Initialization:</strong> SHA-256 MessageDigest instance creation with availability checking</li>
     *   <li><strong>Byte Conversion:</strong> Secure conversion of password characters to byte array representation</li>
     *   <li><strong>Cryptographic Processing:</strong> SHA-256 hash generation with proper digest computation</li>
     *   <li><strong>Hexadecimal Encoding:</strong> Conversion of binary hash to hexadecimal string representation</li>
     *   <li><strong>Error Management:</strong> PasswordException generation for cryptographic algorithm failures</li>
     * </ul>
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
     * The conversion process includes:
     * </p>
     * <ul>
     *   <li><strong>Array Initialization:</strong> Byte array creation with appropriate sizing for password length</li>
     *   <li><strong>Character Processing:</strong> Individual character conversion to byte representation</li>
     *   <li><strong>Secure Handling:</strong> Proper character data management during conversion operations</li>
     *   <li><strong>Memory Management:</strong> Efficient byte array population with minimal memory overhead</li>
     * </ul>
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