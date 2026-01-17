package UITestRunner;

import com.dailyfinance.base.UIBaseClass;
import com.dailyfinance.utilities.ExtentManager;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.dailyfinance.pages.LoginPage;

import java.io.IOException;

public class AdminLoginTestRunner extends UIBaseClass {

    private LoginPage loginPage;

    @BeforeClass
    public void setupPages() {
        loginPage = new LoginPage(UIBaseClass.getDriver());

    }

    @Test(priority = 1, description = "Admin can not login with wrong Password")
    public void verifyLoginWrongPass() throws InterruptedException {
        // ExtentManager.startTest("Valid Login Test"); --This has been implemented in TestListener
        ExtentManager.logStep("Navigating to Login Page entering username and password");
        loginPage.login("admin@test.com", "wrongpass");
        String errorMessageActual = UIBaseClass.getDriver().findElement(By.tagName("p")).getText();
        String errorMessageExpected = "Invalid";
        ExtentManager.logStep("Verify Showing Proper Invalid Message or not");
        Thread.sleep(1000);
        getActionDriver().assertContains( errorMessageExpected , errorMessageActual  );
       // Assert.assertTrue(errorMessageActual.contains(errorMessageExpected));
        ExtentManager.logStep("Validation Completed");
        loginPage.clearLoginCreds();

        Thread.sleep(2000);
    }

    @Test(priority = 2, description = "Admin can not login with wrong Email")
    public void verifyLoginWrongEmail() throws InterruptedException {

        ExtentManager.logStep("Navigating to Login Page entering wrong email and password");
        loginPage.login("admin1234@test.com", "admin123");

        String errorMessageActual = UIBaseClass.getDriver()
                .findElement(By.tagName("p")).getText();
        String errorMessageExpected = "Invalid";

        Thread.sleep(1000);

        ExtentManager.logStep("Verify Showing Proper Invalid Message or not");
        getActionDriver().assertContains( errorMessageExpected , errorMessageActual  );

       // Assert.assertTrue(errorMessageActual.contains(errorMessageExpected));

        ExtentManager.logStep("Validation Completed");
        loginPage.clearLoginCreds();
        Thread.sleep(2000);

    }

    @Test(priority = 3, description = "Admin can not login with wrong Credentials")
    public void verifyLoginWrongCreds() throws InterruptedException {

        ExtentManager.logStep("Navigating to Login Page entering wrong credentials");
        loginPage.login("admin1234@test.com","wrongpass");

        String errorMessageActual = UIBaseClass.getDriver()
                .findElement(By.tagName("p")).getText();
        String errorMessageExpected = "Invalid";

        Thread.sleep(1000);

        ExtentManager.logStep("Verify Showing Proper Invalid Message or not");
        getActionDriver().assertContains( errorMessageExpected , errorMessageActual  );
       // Assert.assertTrue(errorMessageActual.contains(errorMessageExpected));

        ExtentManager.logStep("Validation Completed");
        loginPage.clearLoginCreds();
        Thread.sleep(2000);
    }

    @Test(priority = 4, description = "Admin login with correct credentials")
    public void verifyAdminLogin() throws IOException, InterruptedException {

        ExtentManager.logStep("Navigating to Login Page entering valid credentials");

        if(System.getProperty("username") != null && System.getProperty("password") != null){
            loginPage.login(System.getProperty("username"), System.getProperty("password"));
        } else {
            loginPage.login("admin@test.com","admin123");
        }

        String headerActual = UIBaseClass.getDriver()
                .findElement(By.tagName("h2")).getText();
        String headerExpected = "Admin Dashboard";

        ExtentManager.logStep("Verify Admin Dashboard is displayed");
        getActionDriver().assertContains( headerExpected , headerActual );

       // Assert.assertTrue(headerActual.contains(headerExpected));

        ExtentManager.logStep("Validation Successful");
        Thread.sleep(2000);
    }

}