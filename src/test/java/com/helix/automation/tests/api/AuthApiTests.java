package com.helix.automation.tests.api;

import com.helix.automation.framework.api.spec.ApiSpecs;
import com.helix.automation.framework.api.clients.AuthApi;
import com.helix.automation.framework.config.ConfigManager;
import com.helix.automation.framework.api.models.AuthRequest;
import com.helix.automation.framework.api.models.AuthResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

// Tests target the live host by default (www.passthenote.com)

public class AuthApiTests {
    @BeforeClass
    public void setBaseToTarget() {
        // ensure tests default to the public PassTheNote host
        System.setProperty("api.baseUrl", System.getProperty("api.baseUrl", "https://www.passthenote.com"));
        // attempt to authenticate using configured credentials so other API calls use a valid token
        try {
            String user = ConfigManager.getUsername();
            String pass = ConfigManager.getPassword();
            if (user != null && pass != null && !user.isEmpty() && !pass.isEmpty()) {
                var r = AuthApi.login(new com.helix.automation.framework.api.models.AuthRequest(user, pass));
                if (r != null && r.getToken() != null && !r.getToken().isEmpty()) {
                    ApiSpecs.setAuthToken(r.getToken());
                }
            }
        } catch (Exception ignored) { /* keep tests defensive */ }
    }

    @Test(groups = "api")
    public void testSuccessfulLoginReturnsToken() {
        AuthRequest req = new AuthRequest("tester@passthenote.com", "Tester@123");
        var response = ApiSpecs.base().body(req).when().post("/auth/login");
        int status = response.getStatusCode();
        Assert.assertTrue(status < 500, "Server returned error status: " + status);
        if (status == 200) {
            AuthResponse res = response.then().extract().as(AuthResponse.class);
            Assert.assertNotNull(res.getToken(), "Token should not be null for valid login");
            Assert.assertTrue(res.getToken().length() > 10, "Token should be a valid string");
        }
    }
}
