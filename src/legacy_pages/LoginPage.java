package com.passthenote.pages.legacy;

// NOTE: legacy page object kept for reference during refactor. Use com.passthenote.framework.pages.LoginPage instead.

import com.passthenote.framework.core.BasePage;
import com.passthenote.framework.config.ConfigManager;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {
    private final By heading = By.cssSelector("[data-test='auth-heading'], h1");
    private final By email = By.cssSelector("[data-test='auth-email'], input[name='email']");
    private final By password = By.cssSelector("[data-test='auth-password'], input[name='password']");
    private final By signInBtn = By.cssSelector("[data-test='auth-login-submit'], button[type='submit']");
    private final By errorMsg = By.cssSelector("[data-test='auth-error'], .error-message, .MuiAlert-message");
    private final By forgotPasswordLink = By.cssSelector("[data-test='forgot-password'], a[href*='forgot']");

    public LoginPage open() {
        driver.get(ConfigManager.getBaseUrl() + "/auth/login");
        return this;
    }

    public boolean isWelcomeBackVisible() {
        return isVisible(heading) && getText(heading).toLowerCase().contains("welcome back");
    }

    public void enterEmail(String emailStr) {
        type(email, emailStr);
    }

    public void enterPassword(String pwd) {
        type(password, pwd);
    }

    public void clickSignIn() { click(signInBtn); }

    public boolean isErrorVisible() {
        return isVisible(errorMsg);
    }

    public String getErrorText() {
        return getText(errorMsg);
    }

    public void clickForgotPassword() {
        click(forgotPasswordLink);
    }
}
