package com.helix.automation.framework.flows;

import com.helix.automation.framework.pages.LoginPage;
import com.helix.automation.framework.pages.SignupPage;

public class AuthFlow {
    public static void loginAsTestUser() {
        LoginPage p = new LoginPage();
        p.open();
        p.enterEmail("tester@passthenote.com");
        p.enterPassword("Tester@123");
        p.clickSignIn();
    }

    public static void signupNewAccount(String fullName, String email, String password) {
        SignupPage s = new SignupPage();
        s.open();
        s.enterFullName(fullName);
        s.enterEmail(email);
        s.enterPassword(password);
        s.enterConfirmPassword(password);
        s.clickContinue();
    }
}
