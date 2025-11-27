package com.helix.automation.framework.pages;

import com.helix.automation.framework.core.BasePage;
import com.helix.automation.framework.config.ConfigManager;
import org.openqa.selenium.By;

public class SignupPage extends BasePage {
    private final By fullName = By.cssSelector("[data-test='signup-fullname'], input[name='fullName']");
    private final By email = By.cssSelector("[data-test='signup-email'], input[name='email']");
    private final By password = By.cssSelector("[data-test='signup-password'], input[name='password']");
    private final By confirmPassword = By.cssSelector("[data-test='signup-confirm-password'], input[name='confirmPassword']");
    private final By continueBtn = By.cssSelector("[data-test='signup-submit'], button[type='submit']");
    private final By errorMsg = By.cssSelector("[data-test='signup-error'], .error-message, .MuiAlert-message");
    private final By fieldError = By.cssSelector(".MuiFormHelperText-root, .field-error");

    public SignupPage open() { driver.get(ConfigManager.getBaseUrl() + "/auth/signup"); return this; }
    public SignupPage enterFullName(String name) { type(fullName, name); return this; }
    public SignupPage enterEmail(String emailStr) { type(email, emailStr); return this; }
    public SignupPage enterPassword(String pwd) { type(password, pwd); return this; }
    public SignupPage enterConfirmPassword(String pwd) { type(confirmPassword, pwd); return this; }
    public void clickContinue() { click(continueBtn); }
    public boolean isErrorVisible() { return isVisible(errorMsg); }
    public String getErrorText() { return getText(errorMsg); }
    public boolean isFieldErrorVisible() { return isVisible(fieldError); }
    public String getFieldErrorText() { return getText(fieldError); }
}
