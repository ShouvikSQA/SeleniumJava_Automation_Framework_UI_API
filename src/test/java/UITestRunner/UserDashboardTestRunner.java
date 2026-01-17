package UITestRunner;

import com.dailyfinance.base.UIBaseClass;
import com.dailyfinance.pages.LoginPage;
import com.dailyfinance.pages.UserAddCostPage;
import com.dailyfinance.pages.UserDashboardPage;
import com.dailyfinance.utilities.ExtentManager;
import com.dailyfinance.utilities.JSONUtility;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.dailyfinance.utilities.DataProviders;

import java.io.IOException;

public class UserDashboardTestRunner extends UIBaseClass {

    private LoginPage loginPage;
    private UserAddCostPage addCostPage;
    private UserDashboardPage dashboardPage;

    private int totalSum = 0;

    // Indexed / single locators used in test (avoid driver.findElement directly)
    private By btnAddCost = By.className("add-cost-button");

    @BeforeClass
    public void setupPages() {
        loginPage = new LoginPage(UIBaseClass.getDriver());
        addCostPage = new UserAddCostPage(UIBaseClass.getDriver());
        dashboardPage = new UserDashboardPage(UIBaseClass.getDriver());
    }

    @Test(priority = 1, description = "User Logged In successfully after Email Updatation")
    public void userLogin() throws ParseException, IOException, InterruptedException {

        ExtentManager.logStep("Fetching latest user credentials from properties");
        String email = JSONUtility.getLatestUserProperty("email", "./src/test/resources/testdata/UserData.json");
        String password = JSONUtility.getLatestUserProperty("password", "./src/test/resources/testdata/UserData.json");

        ExtentManager.logStep("Logging in with latest user credentials");
        loginPage.login(email, password);

        String expectedMsg = "User Daily Costs";
        String actualMsg = getActionDriver().getText( By.tagName("h2") );

        ExtentManager.logStep("Verify User Dashboard header is displayed");
        getActionDriver().assertContains(expectedMsg, actualMsg);
    }

    @Test(
            priority = 2,
            dataProvider = "AddCostSheet",
            dataProviderClass = DataProviders.class,
            description = "Adding Data From Excel File to User"

    )
    public void addCost(String name, String amount, String Quantity, String date, String month, String remark) {

        ExtentManager.logStep("Clicking Add Cost button");

        int quantity = Integer.parseInt(Quantity);
        addCostPage.clickAddCost();

        ExtentManager.logStep("Adding cost data from dataset");
        addCostPage.addCost(name, amount, quantity, date, month, remark);

        totalSum += Integer.parseInt(amount);
        ExtentManager.logStep("Updated running totalSum = " + totalSum);
    }

    @Test(priority = 3, description = "Assert The newly Added Products")
    public void productAssertion() {

        ExtentManager.logStep("Asserting newly added products row count");
        dashboardPage.itemAssertion();
    }

    @Test(priority = 4, description = "Compare the Expected Total Cost and Actual Total Cost")
    public void costAssertion() {

        ExtentManager.logStep("Asserting total cost matches dataset sum");
        dashboardPage.assertTotalCost(totalSum);
    }

    @Test(priority = 5, description = "Search Item and Assert the price")
    public void priceAssertion() {

        ExtentManager.logStep("Searching item and asserting price");
        dashboardPage.searchItem();
    }
}
