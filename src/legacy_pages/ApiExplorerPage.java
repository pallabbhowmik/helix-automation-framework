package com.passthenote.pages.legacy;

// NOTE: legacy page object kept for reference during refactor. Use com.passthenote.framework.pages.ApiExplorerPage instead.

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ApiExplorerPage extends BasePage {
    private final By header = By.cssSelector("h1, [data-test='api-explorer-header']");
    private final By baseUrlText = By.cssSelector("[data-test='api-base-url']");
    private final By endpointGroup = By.cssSelector("[data-test='endpoint-group']");
    private final By endpoint = By.cssSelector("[data-test='endpoint']");
    private final By codeSamplePanel = By.cssSelector("[data-test='code-sample-panel']");
    private final By restAssuredTab = By.cssSelector("[data-test='tab-rest-assured']");
    private final By curlTab = By.cssSelector("[data-test='tab-curl']");
    private final By sendRequestBtn = By.cssSelector("[data-test='send-request']");
    private final By responseStatus = By.cssSelector("[data-test='response-status']");
    private final By responseBody = By.cssSelector("[data-test='response-body']");
    private final By quickAuthDropdown = By.cssSelector("[data-test='quick-auth']");
    private final By userIdPreset = By.cssSelector("[data-test='preset-test-user']");

    public ApiExplorerPage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get("https://www.passthenote.com/app/api-explorer");
    }

    public boolean isHeaderVisible() {
        return isVisible(header);
    }

    public boolean isBaseUrlVisible() {
        return isVisible(baseUrlText);
    }

    public boolean isEndpointGroupVisible() {
        return isVisible(endpointGroup);
    }

    public void selectEndpoint(String endpointName) {
        click(By.xpath("//div[contains(@data-test,'endpoint') and contains(text(),'" + endpointName + "')]") );
    }

    public boolean isCodeSamplePanelVisible() {
        return isVisible(codeSamplePanel);
    }

    public void selectRestAssuredTab() {
        click(restAssuredTab);
    }

    public void clickSendRequest() {
        click(sendRequestBtn);
    }

    public String getResponseStatus() {
        return getText(responseStatus);
    }

    public String getResponseBody() {
        return getText(responseBody);
    }

    public void setQuickAuthToTestUser() {
        click(quickAuthDropdown);
        click(userIdPreset);
    }
}
