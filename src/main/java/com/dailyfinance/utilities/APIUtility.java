package com.dailyfinance.utilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.poi.ss.formula.functions.T;

import java.util.Map;


public class APIUtility {
    //Method to send the GET Request
    public static Response sendGetRequest(String endPoint, Map<String, String> headers) {

        return RestAssured
                .given()
                .headers(headers)
                .when()
                .get(endPoint);
    }

    //Method to send the POST Request
    public static Response sendPostRequest(
            String endPoint,
            Map<String, String> headers,
            Object payload
    ) {

        return RestAssured
                .given()
                .headers(headers)
                .body(payload)
                .when()
                .post(endPoint);
    }

    // Method to send the PUT Request
    public static Response sendPutRequest(
            String endPoint,
            Map<String, String> headers,
            Object payload
    ) {

        return RestAssured
                .given()
                .headers(headers)
                .body(payload)
                .when()
                .put(endPoint);
    }

    // Method to send the DELETE Request
    public static Response sendDeleteRequest(
            String endPoint,
            Map<String, String> headers
    ) {

        return RestAssured
                .given()
                .headers(headers)
                .when()
                .delete(endPoint);
    }




}
