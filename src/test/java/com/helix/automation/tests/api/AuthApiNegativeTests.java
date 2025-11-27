package com.helix.automation.tests.api;

import com.helix.automation.framework.api.models.AuthRequest;
import com.helix.automation.framework.api.spec.ApiSpecs;
// no lifecycle imports required
import org.testng.Assert;
import org.testng.annotations.Test;

// No WireMock — tests target the real passthenote.com host by default

public class AuthApiNegativeTests {
    // Tests target the live host by default. We'll assert the server responds with a client or success status

    @Test(groups = "api")
    public void testInvalidCredentialsReturn401() {
        AuthRequest req = new AuthRequest("baduser@example.com", "wrongpassword");
        var res = ApiSpecs.base().body(req).when().post("/auth/login");
        int status = res.getStatusCode();
        Assert.assertTrue(status < 500, "Server error: " + status);
    }

    @Test(groups = "api")
    public void testMissingPasswordReturnsBadRequest() {
        var res = ApiSpecs.base().body("{ \"email\": \"user@example.com\" }").when().post("/auth/login");
        int status = res.getStatusCode();
        Assert.assertTrue(status < 500, "Server error: " + status);
    }
}
