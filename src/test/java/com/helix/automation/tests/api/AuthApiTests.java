package com.helix.automation.tests.api;

import com.helix.automation.framework.api.clients.AuthApi;
import com.helix.automation.framework.api.models.AuthRequest;
import com.helix.automation.framework.api.models.AuthResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AuthApiTests {
    @Test(groups = "api")
    public void testSuccessfulLoginReturnsToken() {
        AuthRequest req = new AuthRequest("tester@passthenote.com", "Tester@123");
        AuthResponse res = AuthApi.login(req);
        Assert.assertNotNull(res.getToken(), "Token should not be null for valid login");
        Assert.assertTrue(res.getToken().length() > 10, "Token should be a valid string");
    }
}
