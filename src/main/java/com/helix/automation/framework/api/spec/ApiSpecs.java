package com.helix.automation.framework.api.spec;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class ApiSpecs {
    public static RequestSpecification base() {
        // default to the target application host as requested
        return RestAssured.given().baseUri(System.getProperty("api.baseUrl", "https://www.passthenote.com"));
    }
}
