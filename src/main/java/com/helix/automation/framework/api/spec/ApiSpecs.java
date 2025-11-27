package com.helix.automation.framework.api.spec;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

/**
 * API request spec helper.
 * Supports an optional bearer token that tests or setup code can set with setAuthToken().
 */

public class ApiSpecs {
    private static volatile String authToken = null;

    public static synchronized void setAuthToken(String token) { authToken = token; }

    public static RequestSpecification base() {
        RequestSpecification req = RestAssured.given().baseUri(System.getProperty("api.baseUrl", "https://www.passthenote.com"));
        if (authToken != null && !authToken.isEmpty()) req = req.header("Authorization", "Bearer " + authToken);
        return req;
    }
}
