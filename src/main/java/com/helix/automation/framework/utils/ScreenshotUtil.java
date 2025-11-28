package com.helix.automation.framework.utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Utility class for capturing screenshots and attaching to Allure reports
 */
public class ScreenshotUtil {

    /**
     * Capture screenshot and attach to Allure report
     * 
     * @param driver WebDriver instance
     * @param name   Screenshot name
     */
    public static void captureScreenshot(WebDriver driver, String name) {
        if (driver == null) {
            return;
        }

        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(name, "image/png", new ByteArrayInputStream(screenshot), "png");
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
        }
    }

    /**
     * Capture full page screenshot using AShot
     * 
     * @param driver WebDriver instance
     * @param name   Screenshot name
     */
    public static void captureFullPageScreenshot(WebDriver driver, String name) {
        if (driver == null) {
            return;
        }

        try {
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(100))
                    .takeScreenshot(driver);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(screenshot.getImage(), "PNG", baos);
            byte[] bytes = baos.toByteArray();

            Allure.addAttachment(name, "image/png", new ByteArrayInputStream(bytes), "png");
        } catch (IOException e) {
            System.err.println("Failed to capture full page screenshot: " + e.getMessage());
        }
    }

    /**
     * Capture screenshot as byte array
     * 
     * @param driver WebDriver instance
     * @return Screenshot as byte array
     */
    public static byte[] getScreenshotAsBytes(WebDriver driver) {
        if (driver == null) {
            return new byte[0];
        }

        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            System.err.println("Failed to get screenshot bytes: " + e.getMessage());
            return new byte[0];
        }
    }
}
