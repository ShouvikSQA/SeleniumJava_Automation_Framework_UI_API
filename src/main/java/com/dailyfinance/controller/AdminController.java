package com.dailyfinance.controller;

import com.dailyfinance.models.UserModel;
import com.dailyfinance.utilities.APIUtility;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class AdminController {

    private Properties properties;

    public AdminController(Properties properties) {
        this.properties = properties;
        RestAssured.baseURI = properties.getProperty("urlApi");
    }

    public Response login(UserModel userModel) {

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return APIUtility.sendPostRequest("/api/auth/login", headers, userModel);
    }

    public Response userList() {

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + properties.getProperty("adminToken"));

        return APIUtility.sendGetRequest("/api/user/users", headers);
    }

    public Response searchUser(String userID) {

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + properties.getProperty("adminToken"));

        return APIUtility.sendGetRequest("/api/user/" + userID, headers);
    }

    public Response updateUser(UserModel userModel) {

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + properties.getProperty("adminToken"));

        return APIUtility.sendPutRequest("/api/user/" + properties.getProperty("userID"), headers, userModel);
    }
}

