package com.helix.automation.tests.ui;

import com.helix.automation.framework.pages.ApiExplorerPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.helix.automation.tests.BaseTest;

public class ApiExplorerTests extends BaseTest {
    @org.testng.annotations.BeforeClass
    public void setupClass() {
        login();
    }

    @Test
    public void testApiExplorerLoads() {
        ApiExplorerPage api = new ApiExplorerPage();
        api.open();
        Assert.assertTrue(api.isHeaderVisible(), "API Explorer header should be visible");
    }

    @Test
    public void testSendRequestButton() {
        ApiExplorerPage api = new ApiExplorerPage();
        api.open();
        api.setQuickAuthToTestUser();
        api.selectEndpoint("/auth/login");
        api.clickSendRequest();
        Assert.assertTrue(api.getResponseStatus().length() > 0);
    }
}
