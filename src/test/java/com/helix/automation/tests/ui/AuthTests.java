package com.helix.automation.tests.ui;

import com.helix.automation.framework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.helix.automation.tests.BaseTest;

public class AuthTests extends BaseTest {
    @Test
    public void testLoginPageOpens() {
        LoginPage login = new LoginPage();
        login.open();
        Assert.assertTrue(login.isWelcomeBackVisible(), "Welcome back header should be visible");
    }

    @Test
    public void testInvalidLoginShowsError() {
        LoginPage login = new LoginPage();
        login.open();
        login.enterEmail("bad@example.com");
        login.enterPassword("badpwd");
        login.clickSignIn();
        Assert.assertTrue(login.isErrorVisible(), "Invalid login should show error");
    }

    @Test
    public void testForgotPasswordNavigation() {
        LoginPage login = new LoginPage();
        login.open();
        login.clickForgotPassword();
        Assert.assertTrue(driver.getCurrentUrl().contains("/auth/forgot-password"), "Should navigate to forgot password page");
    }
}
