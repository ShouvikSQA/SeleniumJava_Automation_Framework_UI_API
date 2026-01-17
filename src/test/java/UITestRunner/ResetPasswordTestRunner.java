package UITestRunner;

import com.dailyfinance.base.UIBaseClass;
import com.dailyfinance.pages.ResetPasswordPage;
import com.dailyfinance.utilities.ExtentManager;
import com.dailyfinance.utilities.JSONUtility;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.naming.ConfigurationException;
import java.io.FileReader;
import java.io.IOException;

public class ResetPasswordTestRunner extends UIBaseClass {

    public static String newPass;
    public static String userEmail;

    private ResetPasswordPage resetPass;

    @BeforeClass
    public void setupPages() {
        resetPass = new ResetPasswordPage(UIBaseClass.getDriver());
    }

    @Test(priority = 1, description = "Reset New Password")
    public void resetPassword() throws IOException, ParseException, ConfigurationException,
            org.apache.commons.configuration.ConfigurationException, InterruptedException {

        ExtentManager.logStep("Navigating to Forgot Password page");
        UIBaseClass.getDriver().findElement(By.cssSelector("a[href='/forgot-password']")).click();

        ExtentManager.logStep("Reading latest user email from json File");

        userEmail =  JSONUtility.getLatestUserProperty( "email" , "./src/test/resources/testData/UserData.json"  );

        ExtentManager.logStep("Using email: " + userEmail);

        ExtentManager.logStep("Performing reset password flow");
        newPass = resetPass.getResetPass(userEmail);

        ExtentManager.logStep("Password reset completed. New password saved.");
    }

    @Test(priority = 2, description = "Reset New Password With Older Link")
    public void resetByOlderLink() throws ConfigurationException,
            org.apache.commons.configuration.ConfigurationException, IOException {

        ExtentManager.logStep("Trying to reset password using older link");
        resetPass.resetPassOlderLink(userEmail);

        ExtentManager.logStep("Validation Completed");
    }

    @Test(priority = 3, description = "Reset New Password With Unregistered Email")
    public void resetPassUnregisteredEmail() throws IOException, ParseException, ConfigurationException,
            org.apache.commons.configuration.ConfigurationException, InterruptedException {

        ExtentManager.logStep("Navigating to Login page");
        UIBaseClass.getDriver().get("https://dailyfinance.roadtocareer.net/login");

        ExtentManager.logStep("Navigating to Forgot Password page");
        UIBaseClass.getDriver().findElement(By.cssSelector("a[href='/forgot-password']")).click();

        ExtentManager.logStep("Sending unregistered email and validating message");
        resetPass.sendUnregisteredEmail("shouvik9292+50000@gmail.com");

        ExtentManager.logStep("Validation Completed");
    }

    @Test(priority = 4, description = "Reset New Password by different password and confirm Password")
    public void resetMismatchPass() throws IOException, ParseException, ConfigurationException,
            org.apache.commons.configuration.ConfigurationException, InterruptedException {

        ExtentManager.logStep("Navigating to Login page");
        UIBaseClass.getDriver().get("https://dailyfinance.roadtocareer.net/login");

        ExtentManager.logStep("Navigating to Forgot Password page");
        UIBaseClass.getDriver().findElement(By.cssSelector("a[href='/forgot-password']")).click();

        ExtentManager.logStep("Trying reset password with mismatched password/confirm password");
        resetPass.resetPassMismatch(userEmail);

        ExtentManager.logStep("Validation Completed");
    }
}
