
package com.helix.automation.framework.pages;

import com.helix.automation.framework.core.BasePage;
import com.helix.automation.framework.config.ConfigManager;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {
    // Locators
    private final By heading = By.xpath("//h1[contains(.,'Welcome Back') or contains(.,'Sign In')]");
    private final By emailInput = By.xpath("//input[@type='email' or @name='email']");
    private final By passwordInput = By.xpath("//input[@type='password' or @name='password']");
    private final By signInButton = By.xpath("//button[contains(.,'Sign In') or @type='submit']");
    private final By errorMessage = By.xpath("//*[contains(@class,'error') or contains(@class,'MuiAlert')]");
    private final By forgotPasswordLink = By.xpath("//a[contains(@href,'forgot') or contains(.,'Forgot')]");

    public LoginPage open() {
        driver.get(ConfigManager.getBaseUrl() + "/auth/login");
        return this;
    }

    public boolean isWelcomeBackVisible() {
        try {
            return waitForVisible(heading).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void enterEmail(String emailStr) {
        type(emailInput, emailStr);
    }

    public void enterPassword(String pwd) {
        type(passwordInput, pwd);
    }

    public void clickSignIn() {
        click(signInButton);
    }

    public boolean isErrorVisible() {
        try {
            return waitForVisible(errorMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorText() {
        try {
            return getText(errorMessage);
        } catch (Exception e) {
            return "";
        }
    }

    public void clickForgotPassword() {
        click(forgotPasswordLink);
    }
}
