package com.dailyfinance.controller;

import com.dailyfinance.models.UserModel;
import com.dailyfinance.utilities.APIUtility;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class RegistrationController {

    private Properties properties;

    public RegistrationController(Properties properties) {
        this.properties = properties;
        RestAssured.baseURI = properties.getProperty("urlApi");
    }

    public Response register(UserModel userModel) {

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return APIUtility.sendPostRequest("/api/auth/register", headers, userModel);
    }
}
