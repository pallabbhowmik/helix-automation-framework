package com.helix.automation.tests;

import org.openqa.selenium.WebDriver;
import com.helix.automation.framework.core.DriverManager;
import com.helix.automation.framework.core.WebDriverFactory;
import org.testng.annotations.*;
import com.helix.automation.framework.config.ConfigManager;
import com.helix.automation.framework.pages.LoginPage;

public abstract class BaseTest {
    protected WebDriver driver;

    @BeforeClass
    public void setUp() {
        DriverManager.setDriver(WebDriverFactory.create());
        driver = DriverManager.getDriver();

        // perform login for UI tests automatically using configured credentials unless
        // this class is the AuthTests which validate login behavior
        try {
            String cls = this.getClass().getSimpleName();
            if (!cls.toLowerCase().contains("authtests")) {
                String user = ConfigManager.getUsername();
                String pwd = ConfigManager.getPassword();
                if (user != null && pwd != null && !user.isEmpty() && !pwd.isEmpty()) {
                    LoginPage login = new LoginPage();
                    login.open();
                    login.enterEmail(user);
                    login.enterPassword(pwd);
                    login.clickSignIn();
                }
            }
        } catch (Exception ignored) { }
    }

    @AfterClass
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
