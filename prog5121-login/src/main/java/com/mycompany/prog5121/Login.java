package com.mycompany.prog5121;

public class Login {
    private String firstName;
    private String lastName;
    private String storedUsername;
    private String storedPassword;

    public boolean checkUserName(String username) {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    public boolean checkPasswordComplexity(String password) {
        return password != null
                && password.length() >= 8
                && password.matches(".*[A-Z].*")
                && password.matches(".*[0-9].*")
                && password.matches(".*[!@#$%^&*()_+=<>?/].*");
    }

    public boolean checkCellPhoneNumber(String cell) {
        return cell != null && cell.matches("^\\+27[0-9]{9}$");
    }

    public String registerUser(String firstName, String lastName, String username,
                               String password, String cell) {
        this.firstName = firstName;
        this.lastName = lastName;

        if (!checkUserName(username)) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number and a special character.";
        }
        if (!checkCellPhoneNumber(cell)) {
            return "Cell phone number incorrectly formatted or does not contain an international code, please correct the number and try again.";
        }

        this.storedUsername = username;
        this.storedPassword = password;
        return "The two above conditions have been met and the user has been registered successfully.";
    }

    public boolean loginUser(String username, String password) {
        return username != null
                && password != null
                && username.equals(storedUsername)
                && password.equals(storedPassword);
    }

    public String returnLoginStatus(String username, String password) {
        if (loginUser(username, password)) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }
}