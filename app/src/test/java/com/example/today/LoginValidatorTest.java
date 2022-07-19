package com.example.today;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.today.validation.LoginValidator;
import org.junit.Test;

public class LoginValidatorTest {

    @Test
    public void password_should_not_be_null() {
        // ARRANGE
        LoginValidator loginValidator = new LoginValidator();
        String Password1 = null;
        String Password2 = null;

        // ACT
        boolean result = loginValidator.passwordRegistrationValidation(Password1, Password2);

        // ASSERT
        assertFalse(result);
    }

    @Test
    public void passwords_should_be_the_same() {
        // ARRANGE
        LoginValidator loginValidator = new LoginValidator();
        String firstPassword = "H3lloTester!";
        String secondPassword = "1etsTotaal4nderS!";

        // ACT
        boolean result = loginValidator.passwordRegistrationValidation(firstPassword, secondPassword);

        // ASSERT
        assertFalse(result);
    }

    @Test
    public void password_should_not_be_empty() {
        // ARRANGE
        LoginValidator loginValidator = new LoginValidator();
        String emptyPassword1 = "";
        String emptyPassword2 = "";

        // ACT
        boolean result = loginValidator.passwordRegistrationValidation(emptyPassword1, emptyPassword2);

        // ASSERT
        assertFalse(result);
    }

    @Test
    public void password_should_be_complete() {
        // ARRANGE
        LoginValidator loginValidator = new LoginValidator();
        String tooShortPassword = "7chars#"; // 7 characters (8 needed),  demands attained.
        String tooShortPassword2 = "7chars#"; // 7 characters (8 needed), demands attained.

        // ACT
        boolean result = loginValidator.passwordRegistrationValidation(tooShortPassword, tooShortPassword2);

        // ASSERT
        assertFalse(result);
    }

    @Test
    public void password_should_contain_upperCase() {
        // ARRANGE
        LoginValidator loginValidator = new LoginValidator();
        String password1 = "th1sisalllowercase";
        String password2 = "th1sisalllowercase";

        // ACT
        boolean result = loginValidator.passwordRegistrationValidation(password1, password2);

        // ASSERT
        assertFalse(result);
    }

    @Test
    public void password_should_contain_lowerCase() {
        // ARRANGE
        LoginValidator loginValidator = new LoginValidator();
        String password1 = "TH1SISALLUPPERCASE";
        String password2 = "TH1SISALLUPPERCASE";

        // ACT
        boolean result = loginValidator.passwordRegistrationValidation(password1, password2);

        // ASSERT
        assertFalse(result);
    }

    @Test
    public void password_should_contain_digit() {
        // ARRANGE
        LoginValidator loginValidator = new LoginValidator();
        String password1 = "NoDigitsAround";
        String password2 = "NoDigitsAround";

        // ACT
        boolean result = loginValidator.passwordRegistrationValidation(password1, password2);

        // ASSERT
        assertFalse(result);
    }

    @Test
    public void password_should_be_max20_chars() {
        // ARRANGE
        LoginValidator loginValidator = new LoginValidator();
        String password1 = "Th1sIsAVeryLongP4ssword";
        String password2 = "Th1sIsAVeryLongP4ssword";

        // ACT
        boolean result = loginValidator.passwordRegistrationValidation(password1, password2);

        // ASSERT
        assertFalse(result);
    }

    @Test
    public void complient_password_should_work() {
        // ARRANGE
        LoginValidator loginValidator = new LoginValidator();
        String perfectPassword = "P3rf3ct!@"; // demands attained.
        String copyperfectPassword = "P3rf3ct!@"; // demands attained.

        // ACT
        boolean result = loginValidator.passwordRegistrationValidation(perfectPassword, copyperfectPassword);

        // ASSERT
        assertTrue(result);
    }

    @Test
    public void registering_username_should_not_be_null() {
        // ARRANGE
        LoginValidator loginValidator = new LoginValidator();
        String nullUser = null;

        // ACT
        boolean result = loginValidator.userRegistrationValidation(nullUser);

        // ASSERT
        assertFalse(result);
    }

    @Test
    public void registering_username_should_not_be_empty() {
        // ARRANGE
        LoginValidator loginValidator = new LoginValidator();
        String emptyUser = "";

        // ACT
        boolean result = loginValidator.userRegistrationValidation(emptyUser);

        // ASSERT
        assertFalse(result);
    }

    @Test
    public void registering_compliant_username_should_be_valid() {
        // ARRANGE
        LoginValidator loginValidator = new LoginValidator();
        String username = "markrutte1";

        // ACT
        boolean result = loginValidator.userRegistrationValidation(username);

        // ASSERT
        assertTrue(result);
    }
}