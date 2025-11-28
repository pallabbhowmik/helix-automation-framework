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
        String base = System.getProperty("api.baseUrl");
        if (base == null || base.isEmpty()) base = com.helix.automation.framework.config.ConfigManager.getApiBaseUrl();
        if (base == null || base.isEmpty()) base = "https://www.passthenote.com/api/v1";
            RequestSpecification req = RestAssured.given().baseUri(System.getProperty("api.baseUrl", "https://www.passthenote.com/api/v1"));
        if (authToken != null && !authToken.isEmpty()) req = req.header("Authorization", "Bearer " + authToken);
        return req;
    }
}
