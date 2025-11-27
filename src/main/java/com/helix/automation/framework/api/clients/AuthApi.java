package com.helix.automation.framework.api.clients;

import com.helix.automation.framework.api.models.AuthRequest;
import com.helix.automation.framework.api.models.AuthResponse;
import com.helix.automation.framework.api.spec.ApiSpecs;

public class AuthApi {
    public static AuthResponse login(AuthRequest req) {
        return ApiSpecs.base().body(req).when().post("/auth/login").then().statusCode(200).extract().as(AuthResponse.class);
    }
}
