package com.helix.automation.framework.pages;

import com.helix.automation.framework.core.BasePage;
import com.helix.automation.framework.config.ConfigManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;

public class ApiExplorerPage extends BasePage {
    // Helper to try multiple locator strategies in order: id, name, css, xpath
    private WebElement findElement(String id, String name, String css, String xpath) {
        try { return driver.findElement(By.id(id)); } catch (Exception ignored) {}
        try { return driver.findElement(By.name(name)); } catch (Exception ignored) {}
        try { return driver.findElement(By.cssSelector(css)); } catch (Exception ignored) {}
        try { return driver.findElement(By.xpath(xpath)); } catch (Exception ignored) {}
        throw new NoSuchElementException("Element not found: " + id + ", " + name + ", " + css + ", " + xpath);
    }

    public ApiExplorerPage open() { driver.get(ConfigManager.getBaseUrl() + "/app/api-explorer"); return this; }

    public boolean isHeaderVisible() {
        try {
            WebElement el = findElement("api-header", "", "[data-test='api-header'], h1, .api-header", "//h1[contains(.,'API Explorer')]" );
            return el.isDisplayed();
        } catch (Exception e) { return false; }
    }

    public boolean isBaseUrlVisible() {
        try {
            WebElement el = findElement("api-base-url", "", "[data-test='api-base-url'], .api-base-url", "//*[contains(text(),'Base URL')]" );
            return el.isDisplayed();
        } catch (Exception e) { return false; }
    }

    public boolean isEndpointGroupVisible() {
        try {
            WebElement el = findElement("endpoint-list", "", "[data-test='endpoint-list'], .endpoint-list", "//div[contains(@class,'endpoint-list')]" );
            return el.isDisplayed();
        } catch (Exception e) { return false; }
    }

    public boolean isCodeSamplePanelVisible() {
        try {
            WebElement el = findElement("code-panel", "", "[data-test='code-panel'], .code-sample", "//div[contains(@class,'code-sample')]" );
            return el.isDisplayed();
        } catch (Exception e) { return false; }
    }

    public void selectEndpoint(String endpoint) {
        WebElement el = findElement("", "", "", String.format("//div[contains(@class,'endpoint') and contains(text(), '%s')]", endpoint));
        el.click();
    }

    public void clickSendRequest() {
        WebElement el = findElement("send-request", "", "[data-test='send-request'], button:contains('Send')", "//button[contains(.,'Send')]" );
        el.click();
    }

    public String getResponseStatus() {
        try {
            WebElement el = findElement("response-status", "", "[data-test='response-status'], .response-status", "//*[contains(@class,'response-status')]" );
            return el.getText();
        } catch (Exception e) { return ""; }
    }

    public String getResponseBody() {
        try {
            WebElement el = findElement("response-body", "", "[data-test='response-body'], .response-body", "//*[contains(@class,'response-body')]" );
            return el.getText();
        } catch (Exception e) { return ""; }
    }

    public void setQuickAuthToTestUser() {
        WebElement el = findElement("quick-auth", "", "[data-test='quick-auth']", "//button[contains(.,'Test User')]" );
        el.click();
    }
}
