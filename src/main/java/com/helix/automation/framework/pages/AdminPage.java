package com.helix.automation.framework.pages;

import com.helix.automation.framework.core.BasePage;
import com.helix.automation.framework.config.ConfigManager;
import org.openqa.selenium.By;

public class AdminPage extends BasePage {
    private final By adminPanel = By.cssSelector("[data-test='admin-panel']");
    private final By userList = By.cssSelector("[data-test='admin-users']");
    private final By forbiddenMessage = By.cssSelector("[data-test='admin-forbidden'], .forbidden, .error-403");

    public AdminPage open() { driver.get(ConfigManager.getBaseUrl() + "/admin"); return this; }
    public boolean isAdminPanelVisible() { return isVisible(adminPanel); }
    public boolean isUserListVisible() { return isVisible(userList); }
    public boolean isForbiddenMessageVisible() { return isVisible(forbiddenMessage); }
    public boolean isDashboardVisible() { return isAdminPanelVisible() && isUserListVisible(); }
}
