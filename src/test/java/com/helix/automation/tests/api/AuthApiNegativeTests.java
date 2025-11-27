package com.helix.automation.tests.api;

import com.helix.automation.framework.api.models.AuthRequest;
import com.helix.automation.framework.api.spec.ApiSpecs;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class AuthApiNegativeTests {
    @Test(groups = "api")
    public void testInvalidCredentialsReturn401() {
        AuthRequest req = new AuthRequest("baduser@example.com", "wrongpassword");
        ApiSpecs.base()
                .body(req)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(401);
    }

    @Test(groups = "api")
    public void testMissingPasswordReturnsBadRequest() {
        // send a payload with missing password -> expecting 400 / validation error
        given()
            .baseUri(System.getProperty("api.baseUrl", "http://localhost:8080"))
            .body("{ \"email\": \"user@example.com\" }")
        .when()
            .post("/auth/login")
        .then()
            .statusCode(400);
    }
}
