package com.helix.automation.framework.core;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Retry analyzer for handling flaky tests
 * Implements TestNG IRetryAnalyzer to automatically retry failed tests
 */
public class RetryAnalyzer implements IRetryAnalyzer {
    private static final Logger logger = LoggerFactory.getLogger(RetryAnalyzer.class);
    private int retryCount = 0;
    private static final int MAX_RETRY_COUNT = getMaxRetryFromConfig();

    private static int getMaxRetryFromConfig() {
        String maxRetry = System.getProperty("test.retry.count", "2");
        try {
            return Integer.parseInt(maxRetry);
        } catch (NumberFormatException e) {
            return 2; // default to 2 retries
        }
    }

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < MAX_RETRY_COUNT) {
            retryCount++;
            String testName = result.getMethod().getMethodName();
            logger.warn("Retrying test '{}' - Attempt {} of {}", testName, retryCount, MAX_RETRY_COUNT);
            return true;
        }
        return false;
    }
}
