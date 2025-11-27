
package com.helix.automation.tests.legacy;

import com.helix.automation.framework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.By;

public class AuthTests extends BaseTest {

    @Test
    public void testSuccessfulLoginWithTestUser() {
        LoginPage login = new LoginPage();
        login.open();
        Assert.assertTrue(login.isWelcomeBackVisible(), "Welcome Back heading should be visible");
        login.enterEmail("tester@passthenote.com");
        login.enterPassword("Tester@123");
        login.clickSignIn();
        // Wait for redirect or user menu
        Assert.assertFalse(login.isVisible(By.cssSelector("[data-test='auth-email'], input[name='email']")), "Login form should not be visible after successful login");
        // Add more assertions for authenticated area as needed
    }

    @Test
    public void testLoginWithInvalidCredentialsShowsError() {
        LoginPage login = new LoginPage();
        login.open();
        login.enterEmail("tester@passthenote.com");
        login.enterPassword("WrongPassword123!");
        login.clickSignIn();
        Assert.assertTrue(login.isErrorVisible(), "Error message should be visible");
        Assert.assertTrue(login.getErrorText().toLowerCase().contains("invalid"), "Error should mention invalid credentials");
    }

    @Test
    public void testLockedPersonaShowsForbiddenMessage() {
        LoginPage login = new LoginPage();
        login.open();
        login.enterEmail("locked@passthenote.com");
        login.enterPassword("Locked@123");
        login.clickSignIn();
        Assert.assertTrue(login.isErrorVisible(), "Error message should be visible");
        Assert.assertTrue(login.getErrorText().toLowerCase().contains("locked") || login.getErrorText().contains("403"), "Error should mention locked or forbidden");
    }

    @Test
    public void testGlitchPersonaShowsFeedback() {
        LoginPage login = new LoginPage();
        login.open();
        login.enterEmail("glitch@passthenote.com");
        login.enterPassword("Glitch@123");
        login.clickSignIn();
        // Assert spinner or disabled button during request
        // (You may need to add a method for spinner detection in LoginPage)
        // Assert error or success message after response
        Assert.assertTrue(login.isErrorVisible() || !login.isVisible(By.cssSelector("[data-test='auth-email'], input[name='email']")), "UI should show feedback or login should succeed/fail gracefully");
    }

    @Test
    public void testForgotPasswordNavigation() {
        LoginPage login = new LoginPage();
        login.open();
        login.clickForgotPassword();
        Assert.assertTrue(driver.getCurrentUrl().contains("/auth/forgot-password"), "Should navigate to forgot password page");
        // Add assertions for email field and reset button
    }
}
