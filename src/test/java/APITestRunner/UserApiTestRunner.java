package APITestRunner;

import com.dailyfinance.actiondriver.ActionDriver;
import com.dailyfinance.base.APIBaseClass;
import com.dailyfinance.controller.UserController;
import com.dailyfinance.models.CostModel;
import com.dailyfinance.models.UserModel;
import com.dailyfinance.utilities.ExtentManager;
import com.dailyfinance.utilities.RandomDataUtility;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import javax.naming.ConfigurationException;

public class UserApiTestRunner extends APIBaseClass {

    @Test(priority = 1, description = "User can login with valid creds")
    public void login() throws ConfigurationException, org.apache.commons.configuration.ConfigurationException {

        ExtentManager.logStep("Preparing user login payload");
        UserController userController = new UserController(props);

        UserModel userModel = new UserModel();
        userModel.setEmail(props.getProperty("userEmail"));
        userModel.setPassword("1234");

        ExtentManager.logStep("Calling user login API");
        Response response = userController.login(userModel);

        ExtentManager.logStep("Validating status code");
        ActionDriver.assertIntEqualityWithoutSS(200, response.getStatusCode());

        JsonPath jsonPath = response.jsonPath();
        String userToken = jsonPath.getString("token");

        ExtentManager.logStep("Saving userToken to config/environment");
        ActionDriver.setEnvironmentVariable("userToken", userToken);

        ExtentManager.logStep("Validation Completed");
    }

    @Test(priority = 2, description = "User can add items")
    public void addItem() throws ConfigurationException, org.apache.commons.configuration.ConfigurationException {

        ExtentManager.logStep("Preparing add item payload");
        UserController userController = new UserController(props);

        CostModel costModel = new CostModel();
        costModel.setItemName("Laptop");
        costModel.setQuantity(1);
        costModel.setAmount(RandomDataUtility.generateRandomNumber(3000, 8000) + "");
        costModel.setPurchaseDate("2025-04-25");
        costModel.setMonth("April");
        costModel.setRemarks("Very Good Product");

        ExtentManager.logStep("Calling addItem API");
        Response response = userController.addItem(costModel);

        ExtentManager.logStep("Validating status code");
        ActionDriver.assertIntEqualityWithoutSS(201, response.getStatusCode());

        JsonPath jsonPath = response.jsonPath();
        String itemID = jsonPath.getString("_id");

        ExtentManager.logStep("Saving itemID to config/environment");
        ActionDriver.setEnvironmentVariable("itemID", itemID);

        ExtentManager.logStep("Validation Completed");
    }

    @Test(priority = 3, description = "User can check item list")
    public void getItemList() {

        ExtentManager.logStep("Calling getItemList API");
        UserController userController = new UserController(props);

        Response response = userController.getItemList();

        ExtentManager.logStep("Validating status code");
        ActionDriver.assertIntEqualityWithoutSS(200, response.getStatusCode());

        ExtentManager.logStep("Validation Completed");
    }

    @Test(priority = 4, description = "User can edit item name")
    public void editItemName() {

        ExtentManager.logStep("Preparing edit item payload");
        UserController userController = new UserController(props);

        CostModel costModel = new CostModel();
        costModel.setItemName("Mobile Phone");
        costModel.setQuantity(1);
        costModel.setAmount("100");
        costModel.setPurchaseDate("2025-04-19");
        costModel.setMonth("April");
        costModel.setRemarks("Apple");

        ExtentManager.logStep("Calling editItemName API");
        Response response = userController.editItemName(costModel);

        ExtentManager.logStep("Validating status code");
        ActionDriver.assertIntEqualityWithoutSS(200, response.getStatusCode());

        ExtentManager.logStep("Validation Completed");
    }

    @Test(priority = 5, description = "User can delete item")
    public void deleteItem() {

        ExtentManager.logStep("Calling deleteItem API");
        UserController userController = new UserController(props);

        Response response = userController.deleteItem();

        ExtentManager.logStep("Validating status code");
        ActionDriver.assertIntEqualityWithoutSS(200, response.getStatusCode());

        JsonPath jsonPath = response.jsonPath();
        String messageActual = jsonPath.getString("message");

        ExtentManager.logStep("Validating success message contains expected text");
        ActionDriver.assertContainsWithoutSS("Cost deleted successfully", messageActual);

        ExtentManager.logStep("Validation Completed");
    }
}
