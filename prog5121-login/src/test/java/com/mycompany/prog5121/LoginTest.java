package com.mycompany.prog5121;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginTest {
    private Login login;

    @BeforeEach
    void setUp() {
        login = new Login();
        login.registerUser(
                "Kyle",
                "Khan",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976");
    }

    @Test
    void checkUsernameCorrectlyFormatted() {
        assertTrue(login.checkUserName("kyl_1"));
    }

    @Test
    void checkUsernameIncorrectlyFormatted() {
        assertFalse(login.checkUserName("kyle!!!!!!!"));
    }

    @Test
    void checkPasswordCorrectlyFormatted() {
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));
    }

    @Test
    void checkPasswordIncorrectlyFormatted() {
        assertFalse(login.checkPasswordComplexity("password"));
    }

    @Test
    void checkCellPhoneCorrectlyFormatted() {
        assertTrue(login.checkCellPhoneNumber("+27838968976"));
    }

    @Test
    void checkCellPhoneIncorrectlyFormatted() {
        assertFalse(login.checkCellPhoneNumber("08966553"));
    }

    @Test
    void loginSuccessful() {
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));
    }

    @Test
    void loginFailed() {
        assertFalse(login.loginUser("kyl_1", "wrongPassword"));
    }
}