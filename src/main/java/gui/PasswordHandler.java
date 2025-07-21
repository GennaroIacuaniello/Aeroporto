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
    PasswordCode passwordValidityCode;

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
            return hexString.toString();

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

    public boolean isValidPassword(){
        boolean upper = false, lower = false, digit = false, special = false;

        if(this.getPassword().length < minimumPasswordLength){
            this.passwordValidityCode = PasswordCode.tooShort;
            return false;
        } else if (this.getPassword().length > maximumPasswordLength) {
            this.passwordValidityCode = PasswordCode.tooLong;
            return false;
        }

        for(char c : this.getPassword()){
            if(allowedCharacterSet.indexOf(c) == -1 || c == ' '){
                this.passwordValidityCode = PasswordCode.characterNotAllowed;
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

        if(!upper){
            this.passwordValidityCode = PasswordCode.mustContainUppercase;
            return false;
        }
        if(!lower){
            this.passwordValidityCode = PasswordCode.mustContainLowercase;
            return false;
        }
        if(!digit){
            this.passwordValidityCode = PasswordCode.mustContainDigit;
            return false;
        }
        if(!special){
            this.passwordValidityCode = PasswordCode.mustContainSpecial;
            return false;
        }

        this.passwordValidityCode = PasswordCode.validPassword;
        return true;
    }

    public boolean verifyUserPassword(String username){
        return true;
        //TODO: use DAO to verify that the password hash matches
    }
}
