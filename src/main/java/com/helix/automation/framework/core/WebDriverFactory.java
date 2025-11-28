package com.helix.automation.framework.core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import com.helix.automation.framework.config.ConfigManager;

public class WebDriverFactory {
    public static WebDriver create() {
        String browser = ConfigManager.getBrowser();
        if (browser == null)
            browser = "chrome";

        WebDriver driver;
        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new org.openqa.selenium.firefox.FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new org.openqa.selenium.edge.EdgeDriver();
                break;
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions opt = new ChromeOptions();
                if (Boolean.parseBoolean(System.getProperty("headless", "false")))
                    opt.addArguments("--headless=new");
                driver = new ChromeDriver(opt);
                break;
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigManager.getTimeout()));
        driver.manage().window().maximize();
        return driver;
    }
}
