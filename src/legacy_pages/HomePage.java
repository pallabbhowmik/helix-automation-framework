package com.passthenote.pages.legacy;

// NOTE: legacy page object kept for reference during refactor. Use com.passthenote.framework.pages.HomePage instead.

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    private final By startPracticingBtn = By.cssSelector("[data-test='cta-practice'], button:contains('Start Practicing Free')");
    private final By exploreApiBtn = By.cssSelector("[data-test='cta-api'], button:contains('Explore API Sandbox')");
    private final By automationChallengeBtn = By.cssSelector("[data-test='cta-challenge'], button:contains('Start Your First Automation Challenge')");
    private final By testAuthFlowsLink = By.cssSelector("[data-test='cta-auth'], a:contains('Test auth flows')");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void open() {
        driver.get("https://www.passthenote.com/");
    }

    public void clickStartPracticing() {
        click(startPracticingBtn);
    }

    public void clickExploreApiSandbox() {
        click(exploreApiBtn);
    }

    public void clickAutomationChallenge() {
        click(automationChallengeBtn);
    }

    public void clickTestAuthFlows() {
        click(testAuthFlowsLink);
    }
}
