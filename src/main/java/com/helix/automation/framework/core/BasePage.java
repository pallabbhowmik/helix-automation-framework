package com.helix.automation.framework.core;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import com.helix.automation.framework.config.ConfigManager;

import java.time.Duration;
import java.util.function.Supplier;

/**
 * Enhanced BasePage with improved wait mechanisms and retry logic
 */
public class BasePage {
    protected WebDriver driver = DriverManager.getDriver();
    protected WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigManager.getTimeout()));
    private static final int MAX_STALE_RETRY = 3;

    /**
     * Wait for element to be visible and return it
     */
    protected WebElement $(By locator) {
        return waitForVisible(locator);
    }

    /**
     * Wait for element to be visible
     */
    protected WebElement waitForVisible(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            throw new TimeoutException(
                    "Element not visible after " + ConfigManager.getTimeout() + " seconds: " + locator);
        }
    }

    /**
     * Wait for element to be clickable
     */
    protected WebElement waitForClickable(By locator) {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (TimeoutException e) {
            throw new TimeoutException(
                    "Element not clickable after " + ConfigManager.getTimeout() + " seconds: " + locator);
        }
    }

    /**
     * Wait for element to be invisible
     */
    protected boolean waitForInvisible(By locator) {
        try {
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Wait for element to be present in DOM
     */
    protected WebElement waitForPresent(By locator) {
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (TimeoutException e) {
            throw new TimeoutException(
                    "Element not present after " + ConfigManager.getTimeout() + " seconds: " + locator);
        }
    }

    /**
     * Click with retry logic for stale element exceptions
     */
    protected void click(By locator) {
        retryOnStale(() -> {
            waitForClickable(locator).click();
            return null;
        });
    }

    /**
     * Type text with retry logic for stale element exceptions
     */
    protected void type(By locator, String input) {
        retryOnStale(() -> {
            WebElement element = waitForVisible(locator);
            element.clear();
            element.sendKeys(input);
            return null;
        });
    }

    /**
     * Get text with retry logic
     */
    protected String getText(By locator) {
        return retryOnStale(() -> waitForVisible(locator).getText());
    }

    /**
     * Check if element is visible
     */
    public boolean isVisible(By locator) {
        try {
            return waitForVisible(locator).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    /**
     * Check if element is present in DOM
     */
    protected boolean isPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Retry logic for handling StaleElementReferenceException
     */
    private <T> T retryOnStale(Supplier<T> action) {
        int attempts = 0;
        while (attempts < MAX_STALE_RETRY) {
            try {
                return action.get();
            } catch (StaleElementReferenceException e) {
                attempts++;
                if (attempts >= MAX_STALE_RETRY) {
                    throw e;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        return null;
    }
}
