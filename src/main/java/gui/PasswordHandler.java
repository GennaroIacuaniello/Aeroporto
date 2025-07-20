package gui;

import javax.swing.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class PasswordHandler extends JPasswordField {


    public static final int  minimumPasswordLength = 8;
    public static final int  maximumPasswordLength = 20;

    public static final String allowedSpecialCharacters = "!#$%&'()*+,-./:;<=>?@[]^_`{|}~";
    public static final String  allowedCharacterSet = "abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ 0123456789 " + allowedSpecialCharacters;

    String hashingAlgorithm = "SHA-256";
    String hashedPassword = null;

    public PasswordHandler(){
        super();
    }

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
            hashedPassword = hexString.toString();
            return hashedPassword;

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

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

    public PasswordCode isValidPassword(char[] inputPassword){
        boolean upper = false, lower = false, digit = false, special = false;

        if(inputPassword.length < minimumPasswordLength){
            return PasswordCode.tooShort;
        } else if (inputPassword.length > maximumPasswordLength) {
            return PasswordCode.tooLong;
        }

        for(char c : inputPassword){
            if(allowedCharacterSet.indexOf(c) == -1 || c == ' '){
                return PasswordCode.characterNotAllowed;
            }
            if(Character.isUpperCase(c)){
                upper = true;
            } else if (Character.isLowerCase(c)) {
                lower = true;
            } else if (Character.isDigit(c)) {
                digit = true;
            } else if (allowedSpecialCharacters.indexOf(c) != -1) {
                special = true;
            }
        }

        if(!upper){ return PasswordCode.mustContainUppercase; }
        if(!lower){ return PasswordCode.mustContainLowercase; }
        if(!digit){ return PasswordCode.mustContainDigit; }
        if(!special){ return PasswordCode.mustContainSpecial; }

        return PasswordCode.validPassword;
    }

    public boolean verifyUserPassword(String username){
        return true;
        //TODO: use DAO to verify that the password hash matches
    }
}
