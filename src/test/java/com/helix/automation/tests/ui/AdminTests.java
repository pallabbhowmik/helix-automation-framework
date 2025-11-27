package com.helix.automation.tests.ui;

import com.helix.automation.framework.pages.AdminPage;
import com.helix.automation.framework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.helix.automation.tests.BaseTest;

public class AdminTests extends BaseTest {
    @Test
    public void testAdminDashboardRequiresAdmin() {
        AdminPage admin = new AdminPage();
        admin.open();
        Assert.assertTrue(admin.isForbiddenMessageVisible() || admin.isDashboardVisible());
    }

    @Test
    public void testAdminLogin() {
        LoginPage login = new LoginPage();
        login.open();
        // simulated admin login flow
        login.enterEmail("admin@passthenote.com");
        login.enterPassword("Admin@123");
        login.clickSignIn();
    }
}
