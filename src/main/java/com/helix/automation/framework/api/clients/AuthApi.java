package com.helix.automation.framework.api.clients;

import com.helix.automation.framework.api.models.AuthRequest;
import com.helix.automation.framework.api.models.AuthResponse;
import com.helix.automation.framework.api.spec.ApiSpecs;

public class AuthApi {
    public static AuthResponse login(AuthRequest req) {
        var response = ApiSpecs.base().body(req).when().post("/auth/login");
        if (response.getStatusCode() == 200) {
            try { return response.then().extract().as(AuthResponse.class); } catch (Exception e) { return null; }
        }
        return null;
    }
}
