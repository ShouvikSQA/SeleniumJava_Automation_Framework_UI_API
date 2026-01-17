package com.dailyfinance.base;

import com.dailyfinance.actiondriver.ActionDriver;
import com.dailyfinance.utilities.ExtentManager;
import com.dailyfinance.utilities.LoggerManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class UIBaseClass {

    public static Properties props;
    public FileInputStream file;
    private static WebDriver driver;
    private static ActionDriver actionDriver;
    public static final Logger logger = LoggerManager.getLogger(UIBaseClass.class);



    @BeforeTest
    public void loadConfig() throws IOException {

        props = new Properties();
        file = new FileInputStream("./src/main/resources/config.properties");
        props.load(file);

        logger.info("config.properties file loaded");

        // Start the Extent Report
        // ExtentManager.getReporter(); --This has been implemented in TestListener


    }

    @BeforeTest
    public void setup() throws IOException {
        launchBrowser();
        configureBrowser();
        logger.info("WebDriver Initialized and Browser Maximized");
        ExtentManager.setDriver(driver);


        //Initialize the actionDriver only once
        if (actionDriver == null) {
            actionDriver = new ActionDriver(driver);
            logger.info("ActionDriver instance is created.");
        }


    }

    private void launchBrowser(){
        String browser = props.getProperty("browser");

        if (browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
            logger.info("ChromeDriver Instance is created.");
        }
        else if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
            logger.info("FirefoxDriver Instance is created.");
        }
        else if (browser.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
            logger.info("EdgeDriver Instance is created.");
        }
        else {
            throw new IllegalArgumentException("Browser Not Supported:" + browser);
        }

    }

    private void configureBrowser(){

        //Implicit Wait
        int implicitWait = Integer.parseInt(props.getProperty("implicitWait"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

        //maximize the browser
        driver.manage().window().maximize();
//        ActionDriver actionDriver1 = new ActionDriver(driver);
//        actionDriver1.maximizeWindow();

        //Navigate to URL
        try {
            driver.get(props.getProperty("url"));
        } catch (Exception e) {
            System.out.println("Failed to navigate to url "+e.getMessage());
        }

    }



    @AfterTest
    public void tearDown() {
        if (driver != null) {

            try {
                driver.quit();
            }

            catch (Exception e) {
                System.out.println("Failed to quit driver "+e.getMessage());

            }
        }


        logger.info("WebDriver instance is closed.");
        driver = null;
        actionDriver = null;

        ExtentManager.endTest();

    }

    // Getter method for WebDriver
    public static WebDriver getDriver() {

        if (driver == null) {
            System.out.println("WebDriver is not initialized");
            throw new IllegalStateException("WebDriver is not initialized");
        }
        return driver;
    }

    // Getter Method for ActionDriver
    public static ActionDriver getActionDriver() {

        if (actionDriver == null) {
            System.out.println("ActionDriver is not initialized");
            throw new IllegalStateException("ActionDriver is not initialized");
        }
        return actionDriver;
    }



}
