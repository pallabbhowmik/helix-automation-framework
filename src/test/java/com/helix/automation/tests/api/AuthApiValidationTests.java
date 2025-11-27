package com.helix.automation.tests.api;

import com.helix.automation.framework.api.models.AuthRequest;
import com.helix.automation.framework.api.spec.ApiSpecs;
import com.helix.automation.framework.api.models.AuthResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AuthApiValidationTests {
    @Test(groups = "api")
    public void testSuccessfulLoginReturnsToken_nonNull() {
        AuthRequest req = new AuthRequest("tester@passthenote.com", "Tester@123");
        AuthResponse res = ApiSpecs.base()
                .body(req)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract().as(AuthResponse.class);

        Assert.assertNotNull(res);
        Assert.assertNotNull(res.getToken());
    }
}
