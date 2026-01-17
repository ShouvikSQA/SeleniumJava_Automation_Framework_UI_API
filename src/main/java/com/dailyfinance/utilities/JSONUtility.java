package com.dailyfinance.utilities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JSONUtility {

    public static void fileWriteProcess( JSONArray jsonArray , String filePath ) throws IOException {
        FileWriter fileWriter=new FileWriter(filePath);
        fileWriter.write(jsonArray.toJSONString());
        fileWriter.flush();
        fileWriter.close();
    }



    public static void saveDataJSONArray( JSONObject jsonObject , String filePath) throws IOException, ParseException {
        JSONParser jsonParser=new JSONParser();
        JSONArray jsonArray= (JSONArray) jsonParser.parse(new FileReader(filePath));
        jsonArray.add(jsonObject);
        fileWriteProcess(jsonArray,filePath);
    }

    public static String getLatestUserProperty(String key,String filePath) throws IOException, ParseException {

        JSONParser parser=new JSONParser();
        JSONArray jsonArray= (JSONArray) parser.parse(new FileReader(filePath));
        JSONObject jsonObject = (JSONObject) jsonArray.get(jsonArray.size() -1);

        String value = (String) jsonObject.get(key);
        return  value;

    }

    public static void updateCreds(String field, String newData, String filePath) throws IOException, ParseException {
        JSONParser jsonParser=new JSONParser();
        JSONArray jsonArray= (JSONArray) jsonParser.parse(new FileReader(filePath));
        JSONObject updatedUserObj = (JSONObject) jsonArray.get( jsonArray.size() -1 );
        updatedUserObj.put(field,newData);

        fileWriteProcess(jsonArray,filePath);



    }
}
