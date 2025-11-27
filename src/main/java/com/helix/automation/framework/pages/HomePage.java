package com.helix.automation.framework.pages;

import com.helix.automation.framework.core.BasePage;
import com.helix.automation.framework.config.ConfigManager;
import org.openqa.selenium.By;

public class HomePage extends BasePage {
    private final By hero = By.cssSelector("[data-test='home-hero'], .hero");
    private final By authArea = By.cssSelector("[data-test='auth-area'], .auth-area");
    private final By cta = By.cssSelector("[data-test='cta'], .cta");
    private final By startPracticing = By.cssSelector("[data-test='cta-start'], a[href*='signup']");
    private final By exploreApi = By.cssSelector("[data-test='cta-explore-api'], a[href*='api-explorer']");
    private final By automationChallenge = By.cssSelector("[data-test='cta-challenge'], a[href*='challenge']");
    private final By testAuthFlows = By.cssSelector("[data-test='cta-test-auth'], a[href*='auth']");

    public HomePage open() { driver.get(ConfigManager.getBaseUrl()); return this; }
    public boolean isHeroVisible() { return isVisible(hero); }
    public boolean isAuthAreaVisible() { return isVisible(authArea); }
    public void clickCTA() { click(cta); }
    public void clickStartPracticing() { click(startPracticing); }
    public void clickExploreApiSandbox() { click(exploreApi); }
    public void clickAutomationChallenge() { click(automationChallenge); }
    public void clickTestAuthFlows() { click(testAuthFlows); }
}
