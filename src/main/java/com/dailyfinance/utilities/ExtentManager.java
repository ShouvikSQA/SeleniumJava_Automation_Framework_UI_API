package com.dailyfinance.utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

    private static ExtentReports extent;
    private static ExtentTest test;
    private static WebDriver driver;

    // Initialize the Extent Report
    public  static ExtentReports getReporter() {
        if (extent == null) {
            String reportPath = System.getProperty("user.dir") + "/src/test/resources/extentreports/ExtentReport.html";
            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setReportName("Automation Test Report");
            spark.config().setDocumentTitle("Daily Finance Report");
            spark.config().setTheme(Theme.DARK);

            extent = new ExtentReports();
            extent.attachReporter(spark);

            extent.setSystemInfo("Operating System", System.getProperty("os.name"));
            extent.setSystemInfo("Java Version", System.getProperty("java.version"));
            extent.setSystemInfo("User Name", System.getProperty("user.name"));
        }
        return extent;
    }

    // Set single driver
      public static void setDriver(WebDriver webDriver) {
        driver = webDriver;
    }

    // Start the Test
    public static ExtentTest startTest(String testName) {
        test = getReporter().createTest(testName);
        return test;
    }

    // End a Test
    public  static void endTest() {
        getReporter().flush();
    }

    // Get current test
    public static ExtentTest getTest() {
        return test;
    }

    // Get current test name
    public static String getTestName() {
        ExtentTest currentTest = getTest();
        if (currentTest != null) {
            return currentTest.getModel().getName();
        } else {
            return "No test is currently active";
        }
    }

    // Log a step
    public static void logStep(String logMessage) {
        getTest().info(logMessage);
    }

    // Log a step validation with screenshot
    public static void logStepWithScreenshot(String logMessage, String screenShotMessage) {
        getTest().pass(logMessage);
        attachScreenshot(screenShotMessage);
    }

    // Log a step validation for API
    public static void logStepValidationForAPI(String logMessage) {
        getTest().pass(logMessage);
    }

    // Log a Failure
    public static void logFailure(String logMessage, String screenShotMessage) {
        String colorMessage = String.format("<span style='color:red;'>%s</span>", logMessage);
        getTest().fail(colorMessage);
        attachScreenshot(screenShotMessage);
    }

    // Log a Failure for API
    public static void logFailureAPI(String logMessage) {
        String colorMessage = String.format("<span style='color:red;'>%s</span>", logMessage);
        getTest().fail(colorMessage);
    }

    // Log a skip
    public static void logSkip(String logMessage) {
        String colorMessage = String.format("<span style='color:orange;'>%s</span>", logMessage);
        getTest().skip(colorMessage);
    }

    // Log a PASS step with screenshot
    public static void logPassStepWithScreenshot(String logMessage, String screenShotMessage) {
        try {
            getTest().pass(logMessage);
            attachScreenshot(screenShotMessage);
        } catch (Exception e) {
            getTest().fail("Failed to log PASS step with screenshot: " + logMessage);
            e.printStackTrace();
        }
    }

    // Log a FAIL step with screenshot
    public static void logFailStepWithScreenshot(String logMessage, String screenShotMessage) {
        try {
            String colorMessage = String.format(
                    "<span style='color:red;'>%s</span>",
                    logMessage
            );
            getTest().fail(colorMessage);
            attachScreenshot(screenShotMessage);
        } catch (Exception e) {
            getTest().fail("Failed to log FAIL step with screenshot: " + logMessage);
            e.printStackTrace();
        }
    }



    // Take a screenshot with date and time in the file
    public static String takeScreenshot(String screenshotName) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);

        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String destPath = System.getProperty("user.dir") + "/src/test/resources/screenshots/" + screenshotName + "_" + timeStamp + ".png";

        File finalPath = new File(destPath);
        try {
            FileUtils.copyFile(src, finalPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return convertToBase64(src);
    }

    // Convert screenshot to Base64 format
    public static String convertToBase64(File screenShotFile) {
        String base64Format = "";
        try {
            byte[] fileContent = FileUtils.readFileToByteArray(screenShotFile);
            base64Format = Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64Format;
    }

    // Attach screenshot to report using Base64
    public static void attachScreenshot(String message) {
        try {
            String screenShotBase64 = takeScreenshot(getTestName());
            getTest().info(
                    message,
                    com.aventstack.extentreports.MediaEntityBuilder
                            .createScreenCaptureFromBase64String(screenShotBase64)
                            .build()
            );
        } catch (Exception e) {
            getTest().fail("Failed to attach screenshot:" + message);
            e.printStackTrace();
        }
    }
}
