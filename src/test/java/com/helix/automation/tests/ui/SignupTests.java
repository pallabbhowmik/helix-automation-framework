package com.helix.automation.tests.ui;

import com.helix.automation.framework.pages.SignupPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.helix.automation.tests.BaseTest;

public class SignupTests extends BaseTest {

    @Test
    public void testSuccessfulAccountCreation() {
        SignupPage signup = new SignupPage();
        signup.open();
        signup.enterFullName("Automation Tester");
        String uniqueEmail = "automation+" + System.currentTimeMillis() + "@passthenote.com";
        signup.enterEmail(uniqueEmail);
        signup.enterPassword("StrongPwd@12345");
        signup.enterConfirmPassword("StrongPwd@12345");
        signup.clickContinue();
        // After signup, should redirect to dashboard or show success toast
        Assert.assertFalse(signup.isErrorVisible(), "No error should be visible after signup");
    }

    @Test
    public void testSignupPasswordMismatchValidation() {
        SignupPage signup = new SignupPage();
        signup.open();
        signup.enterFullName("Automation Tester");
        signup.enterEmail("automation+" + System.currentTimeMillis() + "@example.com");
        signup.enterPassword("StrongPwd@123");
        signup.enterConfirmPassword("DifferentPwd@123");
        signup.clickContinue();
        Assert.assertTrue(signup.isFieldErrorVisible(), "Password mismatch error should be visible");
        Assert.assertTrue(signup.getFieldErrorText().toLowerCase().contains("match"), "Error should mention passwords do not match");
    }

    @Test
    public void testSignupRequiredFieldsValidation() {
        SignupPage signup = new SignupPage();
        signup.open();
        signup.clickContinue();
        Assert.assertTrue(signup.isFieldErrorVisible(), "Required field errors should be visible");
    }
}
