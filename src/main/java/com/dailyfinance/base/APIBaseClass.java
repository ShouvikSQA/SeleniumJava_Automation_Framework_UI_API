package com.dailyfinance.base;

import com.dailyfinance.actiondriver.ActionDriver;
import com.dailyfinance.utilities.ExtentManager;
import com.dailyfinance.utilities.LoggerManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class APIBaseClass {


    public static Properties props;
    public FileInputStream file;
    public static final Logger logger = LoggerManager.getLogger(APIBaseClass.class);


    @BeforeTest
    public void loadConfig() throws IOException {
        props = new Properties();
        file = new FileInputStream("./src/main/resources/config.properties");
        props.load(file);
        logger.info("config.properties file loaded");
    }


    @AfterMethod
    public void refresh() throws IOException {
        props = new Properties();
        file = new FileInputStream("./src/main/resources/config.properties");
        props.load(file);
        logger.info("config.properties file Refreshed");

    }


    @AfterTest
    public void tearDown() {
        ExtentManager.endTest();
    }




}
