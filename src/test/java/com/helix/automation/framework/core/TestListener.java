package com.helix.automation.framework.core;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestListener implements ITestListener {
    private Path screenshotsDir = Path.of(System.getProperty("user.dir"), "build", "screenshots");

    @Override
    public void onTestFailure(ITestResult result) {
        try {
            if (!Files.exists(screenshotsDir)) Files.createDirectories(screenshotsDir);
            if (DriverManager.getDriver() instanceof TakesScreenshot) {
                TakesScreenshot ts = (TakesScreenshot) DriverManager.getDriver();
                File out = ts.getScreenshotAs(OutputType.FILE);
                String name = String.format("%s_%s.png", result.getName(), new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
                Path dest = screenshotsDir.resolve(name);
                Files.copy(out.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Saved screenshot: " + dest.toString());
            }
        } catch (Exception e) {
            System.err.println("Failed taking screenshot: " + e.getMessage());
        }
    }

    @Override public void onTestStart(ITestResult result) {}
    @Override public void onTestSuccess(ITestResult result) {}
    @Override public void onTestSkipped(ITestResult result) {}
    @Override public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}
    @Override public void onStart(ITestContext context) {}
    @Override public void onFinish(ITestContext context) {}
}
