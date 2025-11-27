
package com.helix.automation.framework.pages;

import com.helix.automation.framework.core.BasePage;
import com.helix.automation.framework.config.ConfigManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;

public class LoginPage extends BasePage {
    // Helper to try multiple locator strategies in order: id, name, css, xpath
    private WebElement findElement(String id, String name, String css, String xpath) {
        try { return driver.findElement(By.id(id)); } catch (Exception ignored) {}
        try { return driver.findElement(By.name(name)); } catch (Exception ignored) {}
        try { return driver.findElement(By.cssSelector(css)); } catch (Exception ignored) {}
        try { return driver.findElement(By.xpath(xpath)); } catch (Exception ignored) {}
        throw new NoSuchElementException("Element not found: " + id + ", " + name + ", " + css + ", " + xpath);
    }

    public LoginPage open() { driver.get(ConfigManager.getBaseUrl() + "/auth/login"); return this; }

    public boolean isWelcomeBackVisible() {
        try {
            WebElement el = findElement(
                "auth-heading", "", "[data-test='auth-heading'], h1", "//h1[contains(.,'Welcome Back')]"
            );
            return el.isDisplayed() && el.getText().toLowerCase().contains("welcome back");
        } catch (Exception e) { return false; }
    }

    public void enterEmail(String emailStr) {
        WebElement el = findElement(
            "auth-email", "email", "[data-test='auth-email'], input[name='email']", "//input[@type='email']"
        );
        el.clear();
        el.sendKeys(emailStr);
    }

    public void enterPassword(String pwd) {
        WebElement el = findElement(
            "auth-password", "password", "[data-test='auth-password'], input[name='password']", "//input[@type='password']"
        );
        el.clear();
        el.sendKeys(pwd);
    }

    public void clickSignIn() {
        WebElement el = findElement(
            "auth-login-submit", "", "[data-test='auth-login-submit'], button[type='submit']", "//button[contains(.,'Sign In') or @type='submit']"
        );
        el.click();
    }

    public boolean isErrorVisible() {
        try {
            WebElement el = findElement(
                "auth-error", "", "[data-test='auth-error'], .error-message, .MuiAlert-message", "//*[contains(@class,'error') or contains(@class,'MuiAlert-message')]"
            );
            return el.isDisplayed();
        } catch (Exception e) { return false; }
    }

    public String getErrorText() {
        try {
            WebElement el = findElement(
                "auth-error", "", "[data-test='auth-error'], .error-message, .MuiAlert-message", "//*[contains(@class,'error') or contains(@class,'MuiAlert-message')]"
            );
            return el.getText();
        } catch (Exception e) { return ""; }
    }

    public void clickForgotPassword() {
        WebElement el = findElement(
            "forgot-password", "", "[data-test='forgot-password'], a[href*='forgot']", "//a[contains(@href,'forgot')]"
        );
        el.click();
    }
}
