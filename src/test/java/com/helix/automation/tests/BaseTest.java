package com.helix.automation.tests;

import org.openqa.selenium.WebDriver;
import com.helix.automation.framework.core.DriverManager;
import com.helix.automation.framework.core.WebDriverFactory;
import org.testng.annotations.*;

public abstract class BaseTest {
    protected WebDriver driver;

    @BeforeClass
    public void setUp() {
        DriverManager.setDriver(WebDriverFactory.create());
        driver = DriverManager.getDriver();
    }

    @AfterClass
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
