package com.dailyfinance.utilities;

import com.dailyfinance.actiondriver.ActionDriver;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.configuration.ConfigurationException;
import org.testng.Assert;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class GmailReaderUtility {

    public static Properties props;
    public static FileInputStream file;


    public static   String getEmailList() throws IOException, ConfigurationException, org.apache.commons.configuration.ConfigurationException {

        props = new Properties();
        file = new FileInputStream("./src/main/resources/config.properties");
        props.load(file);

        RestAssured.baseURI="https://gmail.googleapis.com";
        Response res=given().contentType("application/json")
                .header("Authorization","Bearer "+props.getProperty("google_access_token"))
                .when().get("/gmail/v1/users/me/messages");
        JsonPath jsonPath=res.jsonPath();
        return jsonPath.get("messages[0].id");
    }

    public static String readLatestMail() throws IOException, ConfigurationException, org.apache.commons.configuration.ConfigurationException {
        String messageId= getEmailList();
        RestAssured.baseURI="https://gmail.googleapis.com";
        Response res=given().contentType("application/json")
                .header("Authorization","Bearer "+props.getProperty("google_access_token"))
                .when().get("/gmail/v1/users/me/messages/"+messageId);

        JsonPath jsonPath=res.jsonPath();
        String message= jsonPath.get("snippet");

        return message;

    }

    public static void assertRegistrationEmail(String fname) throws ConfigurationException, org.apache.commons.configuration.ConfigurationException, IOException {

        String confirmationEmailActual = readLatestMail();
        String confirmationEmailExpected = "Dear "+ fname  + ", Welcome to our platform!";
        System.out.println(confirmationEmailActual);
        ActionDriver.assertContainsWithoutSS( confirmationEmailExpected , confirmationEmailActual );
    }


}
