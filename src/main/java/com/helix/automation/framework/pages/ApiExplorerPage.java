package com.helix.automation.framework.pages;

import com.helix.automation.framework.core.BasePage;
import com.helix.automation.framework.config.ConfigManager;
import org.openqa.selenium.By;

public class ApiExplorerPage extends BasePage {
    private final By header = By.cssSelector("[data-test='api-header'], h1, .api-header");
    private final By baseUrl = By.cssSelector("[data-test='api-base-url'], .api-base-url");
    private final By endpointList = By.cssSelector("[data-test='endpoint-list'], .endpoint-list");
    private final By codePanel = By.cssSelector("[data-test='code-panel'], .code-sample");
    private final By sendButton = By.cssSelector("[data-test='send-request'], button:contains('Send')");
    private final By responseStatus = By.cssSelector("[data-test='response-status'], .response-status");
    private final By responseBody = By.cssSelector("[data-test='response-body'], .response-body");
    private final By quickAuth = By.cssSelector("[data-test='quick-auth']");

    public ApiExplorerPage open() { driver.get(ConfigManager.getApiBaseUrl() + "/explorer"); return this; }
    public boolean isHeaderVisible() { return isVisible(header); }
    public boolean isBaseUrlVisible() { return isVisible(baseUrl); }
    public boolean isEndpointGroupVisible() { return isVisible(endpointList); }
    public boolean isCodeSamplePanelVisible() { return isVisible(codePanel); }
    public void selectEndpoint(String endpoint) { click(By.xpath(String.format("//div[contains(@class,'endpoint') and contains(text(), '%s')", endpoint))); }
    public void clickSendRequest() { click(sendButton); }
    public String getResponseStatus() { return isVisible(responseStatus) ? getText(responseStatus) : ""; }
    public String getResponseBody() { return isVisible(responseBody) ? getText(responseBody) : ""; }
    public void setQuickAuthToTestUser() { click(quickAuth); }
}
