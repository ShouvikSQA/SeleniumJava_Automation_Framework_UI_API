package com.dailyfinance.controller;

import com.dailyfinance.models.CostModel;
import com.dailyfinance.models.UserModel;
import com.dailyfinance.utilities.APIUtility;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class UserController {

    private Properties properties;

    public UserController(Properties properties) {
        this.properties = properties;
        RestAssured.baseURI = properties.getProperty("urlApi");
    }

    public Response login(UserModel userModel) {

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return APIUtility.sendPostRequest("/api/auth/login", headers, userModel);
    }

    public Response addItem(CostModel costModel) {

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + properties.getProperty("userToken"));

        return APIUtility.sendPostRequest("/api/costs", headers, costModel);
    }

    public Response getItemList() {

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + properties.getProperty("userToken"));

        return APIUtility.sendGetRequest("/api/costs", headers);
    }

    public Response editItemName(CostModel costModel) {

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + properties.getProperty("userToken"));

        return APIUtility.sendPutRequest("/api/costs/" + properties.getProperty("itemID"), headers, costModel);
    }

    public Response deleteItem() {

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + properties.getProperty("userToken"));

        return APIUtility.sendDeleteRequest("/api/costs/" + properties.getProperty("itemID"), headers);
    }
}
