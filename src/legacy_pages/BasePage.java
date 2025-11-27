package com.passthenote.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
    }

    protected WebElement $(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void click(By locator) {
        $(locator).click();
    }

    protected void type(By locator, String text) {
        WebElement el = $(locator);
        el.clear();
        el.sendKeys(text);
    }

    public boolean isVisible(By locator) {
        try {
            return $(locator).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected String getText(By locator) {
        return $(locator).getText();
    }
}
