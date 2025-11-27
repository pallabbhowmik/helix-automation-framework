package com.helix.automation.framework.core;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver = DriverManager.getDriver();
    protected WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    protected WebElement $(By locator) { return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)); }
    protected void click(By locator) { $(locator).click(); }
    protected void type(By locator, String input) { $(locator).sendKeys(input); }
    public boolean isVisible(By locator) {
        try {
            return $(locator).isDisplayed();
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    protected String getText(By locator) { return $(locator).getText(); }
}
