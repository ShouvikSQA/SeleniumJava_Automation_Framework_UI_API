package UITestRunner;

import com.dailyfinance.base.UIBaseClass;
import com.dailyfinance.pages.AdminDashboardPage;
import com.dailyfinance.pages.LoginPage;
import com.dailyfinance.utilities.ExtentManager;
import com.dailyfinance.utilities.JSONUtility;
import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import java.io.IOException;

public class AdminDashboardTestRunner extends UIBaseClass {

    private LoginPage loginPage;
    private AdminDashboardPage adminDashboardPage;

    @BeforeClass
    public void setupPages() {
        loginPage = new LoginPage(UIBaseClass.getDriver());
        adminDashboardPage = new AdminDashboardPage(UIBaseClass.getDriver());
    }

    @Test( priority = 1 , description = "Logged in as Admin ")
    public void doLogin() throws ParseException, IOException, InterruptedException {

        ExtentManager.logStep("Logging in as Admin");

        if (System.getProperty("username") != null && System.getProperty("password") != null) {
            loginPage.login(System.getProperty("username"), System.getProperty("password"));
        } else {
            loginPage.login("admin@test.com", "admin123");
        }

        Thread.sleep(1500);
    }

    @Test( priority = 2, description = "Search by The newly updated email on the DashBoard")
    public void userValueMatching() throws IOException, ParseException, InterruptedException {

        String email = JSONUtility.getLatestUserProperty("email" , "./src/test/resources/testdata/UserData.json" );

//        ExtentManager.logStep("Navigating to Admin Dashboard page");
//        BaseClass.getDriver().get("https://dailyfinance.roadtocareer.net/admin");

        ExtentManager.logStep("Searching and matching email from table");
        adminDashboardPage.tableDataSearching(email);

        ExtentManager.logStep("Validation Completed");
        Thread.sleep(1500);
    }
}

