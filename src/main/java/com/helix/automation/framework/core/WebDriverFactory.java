package com.helix.automation.framework.core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.time.Duration;
import org.openqa.selenium.WebDriver;

public class WebDriverFactory {
    public static WebDriver create() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions opt = new ChromeOptions();
        if (Boolean.parseBoolean(System.getProperty("headless", "false"))) opt.addArguments("--headless=new");
        WebDriver driver = new ChromeDriver(opt);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        return driver;
    }
}
