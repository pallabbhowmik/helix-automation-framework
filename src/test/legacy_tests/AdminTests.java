package com.helix.automation.tests.legacy;

import com.helix.automation.framework.pages.AdminPage;
import com.helix.automation.framework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AdminTests extends BaseTest {

    @Test
    public void testAdminAccessRequiresAuthentication() {
        AdminPage admin = new AdminPage();
        admin.open();
        Assert.assertTrue(admin.isForbiddenMessageVisible() || !admin.isDashboardVisible(), "Admin area should be protected for unauthenticated users");
    }

    @Test
    public void testAdminUserCanAccessAdminDashboard() {
        LoginPage login = new LoginPage();
        login.open();
        login.enterEmail("admin@passthenote.com");
        login.enterPassword("Admin@123");
        login.clickSignIn();
        AdminPage admin = new AdminPage();
        admin.open();
        Assert.assertTrue(admin.isDashboardVisible(), "Admin dashboard should be visible for admin user");
    }
}
