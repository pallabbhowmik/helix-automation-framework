package com.helix.automation.framework.api.spec;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class ApiSpecs {
    public static RequestSpecification base() {
        return RestAssured.given().baseUri(System.getProperty("api.baseUrl", "http://localhost:8080"));
    }
}
