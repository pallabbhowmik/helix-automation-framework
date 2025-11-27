package com.helix.automation.tests.legacy;

import com.helix.automation.framework.pages.ApiExplorerPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ApiExplorerTests extends BaseTest {

    @Test
    public void testApiExplorerPageStructure() {
        ApiExplorerPage api = new ApiExplorerPage();
        api.open();
        Assert.assertTrue(api.isHeaderVisible(), "API Explorer header should be visible");
        Assert.assertTrue(api.isBaseUrlVisible(), "Base URL text should be visible");
        Assert.assertTrue(api.isEndpointGroupVisible(), "Endpoint groups should be visible");
    }

    @Test
    public void testSelectingEndpointShowsCodeSamplePanel() {
        ApiExplorerPage api = new ApiExplorerPage();
        api.open();
        api.selectEndpoint("/notes");
        Assert.assertTrue(api.isCodeSamplePanelVisible(), "Code sample panel should be visible");
    }

    @Test
    public void testApiExplorerHealthCheckEndpoint() {
        ApiExplorerPage api = new ApiExplorerPage();
        api.open();
        api.selectEndpoint("/health");
        api.clickSendRequest();
        Assert.assertTrue(api.getResponseStatus().contains("200"), "Health check should return 200");
        Assert.assertTrue(api.getResponseBody().contains("status"), "Response body should contain health status");
    }

    @Test
    public void testUnauthorizedVsAuthorizedBehaviorWithUserId() {
        ApiExplorerPage api = new ApiExplorerPage();
        api.open();
        api.selectEndpoint("/notes");
        api.clickSendRequest();
        Assert.assertTrue(api.getResponseStatus().contains("401") || api.getResponseStatus().contains("403"), "Should get unauthorized error");
        api.setQuickAuthToTestUser();
        api.clickSendRequest();
        Assert.assertTrue(api.getResponseStatus().contains("200") || api.getResponseStatus().contains("201"), "Should get success with Test User auth");
    }
}
