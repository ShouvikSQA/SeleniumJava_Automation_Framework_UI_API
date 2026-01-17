package com.dailyfinance.utilities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class RandomDataUtility {

    public static int generateRandomNumber(int min, int max){
        double randomId= Math.random()*(max-min)+min;
        return (int) randomId;
    }

    public static String geneateRandomEmail() throws IOException {


        int num = generateRandomNumber(10000,99999999);
        String originalEmail = "shouvik9292+" + num  + "@gmail.com";
        return originalEmail;
    }




}
