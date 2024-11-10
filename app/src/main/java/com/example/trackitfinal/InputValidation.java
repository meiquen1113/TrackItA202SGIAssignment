package com.example.trackitfinal;

// Creation a input validation class
public class InputValidation {

    // check if input string is alphanumeric. eg No special character etc
    // return true if alphanumeric.
    public boolean isAlphanumeric(String str) {
        for (int i=0; i<str.length(); i++) {
            char c = str.charAt(i);
            if (c < 0x30 || (c >= 0x3a && c <= 0x40) || (c > 0x5a && c <= 0x60) || c > 0x7a)
                return false;
        }
        return true;
    }

    // check if input string is alpha. eg a-z, A-Z. & space
    // return true if alpha.
    public boolean isAlpha(String str) {
        for (int i=0; i<str.length(); i++) {
            char c = str.charAt(i);
            if (c < 0x41 || c > 0x7a){
                if (c != 0x20)
                    return false;
            }
        }
        return true;
    }

    // check if input string is numeric. eg number 0 to 9 only.
    // return true if numeric.
    public boolean isNumeric(String str) {
        for (int i=0; i<str.length(); i++) {
            char c = str.charAt(i);
            if (c < 0x30 || (c >= 0x3a) )
                return false;
        }
        return true;
    }

    // return the char position of @
    // return -1 if not found.
    public int posOfAlias(String str) {
        for (int i=0; i<str.length(); i++) {
            char c = str.charAt(i);
            if (c == 0x40)
                return i;
        }
        return -1;
    }

    // validate the username data entry to meet requirements (max/min char, no special char etc)
    // return null if valid, otherwise specific error message string will be return.
    public String validUsername(String username) {
        if (username.length() < 3){
            return ("Username length is too short! Must be between 3 to 9 chars length.");
        } else if (username.length() > 9) {
            return ("Username length is too long! Must be between 3 to 9 chars length.");
        }
        if (!isAlphanumeric(username)){
            return ("Username must be Alphanumeric only. No special char!");
        }
        if (isNumeric(username)){
            return ("Username must not be all numbers");
        }
        return (null);
    }

    // validate the password data entry to meet requirements (max/min char, no special char etc)
    // return null if valid, otherwise specific error message string will be return.
    public String validPassword(String password) {
        if (password.length() < 6){
            return ("Password length is too short! Must be between 6 to 12 chars length.");
        } else if (password.length() > 12) {
            return ("Password length is too long! Must be between 6 to 12 chars length.");
        }
        if (!isAlphanumeric(password)){
            return ("Password must be Alphanumeric only. No special char!");
        }
        return (null);
    }

    // validate the email data entry to meet requirements (xxx@yyy.zz)
    // return null if valid, otherwise specific error message string will be return.
    public String validFullname(String name) {
        if (name.length() < 6 ) {
            return ("Fullname length is too short! Must be between 8 to 16 chars length.");
        }
        if (name.length() > 16 ) {
            return ("Fullname length is too long! Must be between 8 to 16 chars length.");
        }
        if (!isAlpha(name)){
            return ("Fullname must is Alpha only.");
        }
        return (null);
    }
}