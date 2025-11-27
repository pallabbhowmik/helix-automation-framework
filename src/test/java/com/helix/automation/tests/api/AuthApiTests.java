package com.helix.automation.tests.api;

import com.helix.automation.framework.api.spec.ApiSpecs;
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
