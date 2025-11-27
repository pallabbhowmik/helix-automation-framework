package com.passthenote.pages.legacy;

// NOTE: legacy page object kept for reference during refactor. Use com.passthenote.framework.pages.SignupPage instead.

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignupPage extends BasePage {
    private final By fullName = By.cssSelector("[data-test='signup-fullname'], input[name='fullName']");
    private final By email = By.cssSelector("[data-test='signup-email'], input[name='email']");
    private final By password = By.cssSelector("[data-test='signup-password'], input[name='password']");
    private final By confirmPassword = By.cssSelector("[data-test='signup-confirm-password'], input[name='confirmPassword']");
    private final By continueBtn = By.cssSelector("[data-test='signup-submit'], button[type='submit']");
    private final By errorMsg = By.cssSelector("[data-test='signup-error'], .error-message, .MuiAlert-message");
    private final By fieldError = By.cssSelector(".MuiFormHelperText-root, .field-error");

    public SignupPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get("https://www.passthenote.com/auth/signup");
    }

    public void enterFullName(String name) {
        type(fullName, name);
    }

    public void enterEmail(String emailStr) {
        type(email, emailStr);
    }

    public void enterPassword(String pwd) {
        type(password, pwd);
    }

    public void enterConfirmPassword(String pwd) {
        type(confirmPassword, pwd);
    }

    public void clickContinue() {
        click(continueBtn);
    }

    public boolean isErrorVisible() {
        return isVisible(errorMsg);
    }

    public String getErrorText() {
        return getText(errorMsg);
    }

    public boolean isFieldErrorVisible() {
        return isVisible(fieldError);
    }

    public String getFieldErrorText() {
        return getText(fieldError);
    }
}
