package com.helix.automation.tests.ui.tests;

import com.helix.automation.framework.core.*;
import com.helix.automation.framework.pages.LoginPage;
import org.testng.annotations.*;
import org.testng.Assert;

public class LoginTests {

    @BeforeMethod
    public void setup() {
        DriverManager.setDriver(WebDriverFactory.create());
    }

    @AfterMethod
    public void teardown() {
        DriverManager.quitDriver();
    }

    @Test
    public void testLoginPageLoads() {
        new LoginPage().open();
        String actualTitle = DriverManager.getDriver().getTitle();
        System.out.println("Page title: " + actualTitle);
        Assert.assertTrue(actualTitle.contains("Pass The Note"), "Expected title to contain 'Pass The Note', but was: " + actualTitle);
    }
}
