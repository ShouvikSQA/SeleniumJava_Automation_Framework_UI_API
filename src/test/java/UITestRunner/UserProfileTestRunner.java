package UITestRunner;

import com.dailyfinance.base.UIBaseClass;
import com.dailyfinance.pages.LoginPage;
import com.dailyfinance.pages.UserProfilePage;
import com.dailyfinance.utilities.ExtentManager;
import com.dailyfinance.utilities.JSONUtility;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class UserProfileTestRunner extends UIBaseClass {

    private LoginPage loginPage;
    private UserProfilePage userProfilePage;

    private String oldEmail;
    private String filePath = "./src/test/resources/testdata/UserData.json";




    private By btnLogin = By.cssSelector("button[type='submit']");



    @BeforeClass
    public void setupPages() {
        loginPage = new LoginPage(UIBaseClass.getDriver());
        userProfilePage = new UserProfilePage(UIBaseClass.getDriver());
    }



    @Test(priority = 1, description = "User can not Login With Old pass" )
    public void userLoginInvalidPass() throws ParseException, IOException, InterruptedException {

        ExtentManager.logStep("Reading latest user email from users.json and trying wrong password");

        String email = JSONUtility.getLatestUserProperty("email", filePath );
        String password = "1234";

           oldEmail = email;

        loginPage.login(email, password);

        String errorMessageActual = getActionDriver().getText(By.tagName("p"));
        String errorMessageExpected = "Invalid";

        ExtentManager.logStep("Verify showing proper invalid message or not");
        getActionDriver().assertContains(errorMessageExpected, errorMessageActual);

        ExtentManager.logStep("Clearing login creds");
        loginPage.clearLoginCreds();

        Thread.sleep(1500);
    }

    @Test(priority = 2, description = "User can Login With updated Pass")
    public void userLoginValidPass() throws ParseException, IOException, InterruptedException {

        ExtentManager.logStep("Reading latest user credentials from users.json and login");

        String email = JSONUtility.getLatestUserProperty("email", filePath );
        String password = JSONUtility.getLatestUserProperty("password", filePath );

        oldEmail = email;

        loginPage.login(email, password);

        String expectedMsg = "User Daily Costs";
        String actualMsg = getActionDriver().getText(By.tagName("h2"));

        ExtentManager.logStep("Verify user dashboard header is displayed");
        getActionDriver().assertContains(expectedMsg, actualMsg);

        Thread.sleep(1500);
    }

    @Test(priority = 3, description = "Update User photo")
    public void updateUserPhoto() throws InterruptedException {

        ExtentManager.logStep("Navigating to profile page from user menu");
        getActionDriver().click( By.cssSelector("[data-testid='AccountCircleIcon']") );
        getActionDriver().click( By.xpath("(//*[@role='menuitem'])[1]") );

        ExtentManager.logStep("Uploading user profile picture");
        userProfilePage.uploadPicture();

        Thread.sleep(1500);
    }

    @Test(priority = 4, description = "Update User gmail")
    public void updateUserEmail() throws InterruptedException, IOException, ParseException {

        ExtentManager.logStep("Updating user email from profile page");
        userProfilePage.updateEmail();

        ExtentManager.logStep("Logging out after email update");
        loginPage.logout();

        ExtentManager.logStep("Waiting for login button to be clickable");
        getActionDriver().waitForElementToBeClickable( By.cssSelector("button[type='submit']") );

        Thread.sleep(1500);
    }

    @Test(priority = 5, description = "User can not login with Older email")
    public void userLoginFail() throws InterruptedException, IOException, ParseException {

        ExtentManager.logStep("Trying to login using old email after update (should fail)");
        String password = JSONUtility.getLatestUserProperty("password", filePath );
        loginPage.login(oldEmail, password);

        String errorMessageActual = getActionDriver().getText( By.tagName("p") );
        String errorMessageExpected = "Invalid";

        ExtentManager.logStep("Verify showing proper invalid message or not");
        getActionDriver().assertContains(errorMessageExpected, errorMessageActual);

        ExtentManager.logStep("Clearing login creds");
        loginPage.clearLoginCreds();

        Thread.sleep(1500);
    }
}
