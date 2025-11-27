package com.helix.automation.framework.core;

import org.testng.Assert;
import org.testng.annotations.Test;

public class RetryAnalyzerTest {
    private static int counter = 0;

    @Test
    public void flakyThenPass() {
        counter++;
        // This test intentionally fails twice and then succeeds on the 3rd attempt. With MAX_RETRIES=2 it should pass.
        System.out.println("Attempt #" + counter);
        Assert.assertTrue(counter >= 3, "Should pass only on the 3rd attempt or later");
    }
}
