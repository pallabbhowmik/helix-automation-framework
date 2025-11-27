package com.helix.automation.tests.ui;

import com.helix.automation.framework.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.helix.automation.tests.BaseTest;

public class HomeNavigationTests extends BaseTest {
    @Test
    public void testCTALinks() {
        HomePage home = new HomePage();
        home.open();
        home.clickStartPracticing();
        Assert.assertTrue(driver.getCurrentUrl().contains("/auth/signup") || driver.getCurrentUrl().contains("/app"), "Start Practicing should navigate to signup or app area");
        driver.navigate().back();
        home.clickExploreApiSandbox();
        Assert.assertTrue(driver.getCurrentUrl().contains("/app/api-explorer"), "Explore API Sandbox should navigate to API Explorer");
        driver.navigate().back();
        home.clickAutomationChallenge();
        Assert.assertTrue(driver.getCurrentUrl().contains("challenge") || driver.getCurrentUrl().contains("practice"), "Automation Challenge should navigate to challenge/practice flow");
    }

    @Test
    public void testAuthTestLink() {
        HomePage home = new HomePage();
        home.open();
        home.clickTestAuthFlows();
        Assert.assertTrue(driver.getCurrentUrl().contains("auth"), "Test auth flows should navigate to auth module");
    }
}
