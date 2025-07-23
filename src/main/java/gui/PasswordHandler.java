package gui;

import javax.swing.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * The type Password handler.
 */
public class PasswordHandler extends JPasswordField {


    /**
     * The constant MINIMUM_PASSWORD_LENGTH.
     */
    public static final int MINIMUM_PASSWORD_LENGTH = 8;
    /**
     * The constant MAXIMUM_PASSWORD_LENGTH.
     */
    public static final int MAXIMUM_PASSWORD_LENGTH = 20;

    /**
     * The constant ALLOWED_SPECIAL_CHARACTERS.
     */
    public static final String ALLOWED_SPECIAL_CHARACTERS = "!#$%&'()*+,-./:;<=>?@[]^_`{|}~";
    /**
     * The constant ALLOWED_CHARACTER_SET.
     */
    public static final String ALLOWED_CHARACTER_SET = "abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ 0123456789 " + ALLOWED_SPECIAL_CHARACTERS;

    /**
     * The Hashing algorithm.
     */
    String hashingAlgorithm = "SHA-256";
    /**
     * The Password validity code.
     */
    PasswordCode passwordValidityCode;

    /**
     * Instantiates a new Password handler.
     */
    public PasswordHandler(){
        super();
    }

    /**
     * Instantiates a new Password handler.
     *
     * @param columns the columns
     */
    public PasswordHandler(int columns){
        super(columns);
    }

    /**
     * Is empty boolean.
     *
     * @return the boolean
     */
    public boolean isEmpty(){
        return this.getPassword().length == 0;
    }

    /**
     * Hash password string.
     *
     * @return the string
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
     * Get hashed password string.
     *
     * @return the string
     */
    public String getHashedPassword(){
        return hashPassword();
    }

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
     * Is valid password boolean.
     *
     * @return the boolean
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
     * Show invalid password message.
     *
     * @param button the button
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
