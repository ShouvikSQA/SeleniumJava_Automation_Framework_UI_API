package APITestRunner;

import com.dailyfinance.actiondriver.ActionDriver;
import com.dailyfinance.base.APIBaseClass;
import com.dailyfinance.controller.AdminController;
import com.dailyfinance.models.UserModel;
import com.dailyfinance.utilities.ExtentManager;
import com.dailyfinance.utilities.RandomDataUtility;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import javax.naming.ConfigurationException;
import java.io.IOException;

public class AdminApiTestRunner extends APIBaseClass {

    @Test(priority = 1, description = "Admin login with incorrect email")
    public void adminLoginIncorrectEmail() {

        ExtentManager.logStep("Creating AdminController and preparing invalid email payload");
        AdminController adminController = new AdminController(props);

        UserModel userModel = new UserModel();
        userModel.setEmail("admin12345@test.com");
        userModel.setPassword("admin123");

        ExtentManager.logStep("Sending login request");
        Response response = adminController.login(userModel);
        ExtentManager.logStep( "Actual Response is : "+ response );

        ExtentManager.logStep("Validating status code");

        ActionDriver.assertIntEqualityWithoutSS( 401, response.getStatusCode() );

        JsonPath jsonPath = response.jsonPath();
        String msgActual = jsonPath.getString("message");
        String msgExpected = "Invalid email or password";

        ExtentManager.logStep("Validating error message");
        ActionDriver.assertEqualityWithoutSS(msgExpected, msgActual);

        ExtentManager.logStep("Validation Completed");
    }

    @Test(priority = 2, description = "Admin login with incorrect password")
    public void adminLoginIncorrectPass() {

        ExtentManager.logStep("Creating AdminController and preparing invalid password payload");
        AdminController adminController = new AdminController(props);

        UserModel userModel = new UserModel();
        userModel.setEmail("admin@test.com");
        userModel.setPassword("admin12345");

        ExtentManager.logStep("Sending login request");
        Response response = adminController.login(userModel);

        ExtentManager.logStep("Validating status code");
        ActionDriver.assertIntEqualityWithoutSS(401, response.getStatusCode());

        JsonPath jsonPath = response.jsonPath();
        String msgActual = jsonPath.getString("message");
        String msgExpected = "Invalid email or password";

        ExtentManager.logStep("Validating error message");
        ActionDriver.assertEqualityWithoutSS(msgExpected, msgActual);

        ExtentManager.logStep("Validation Completed");
    }

    @Test(priority = 3, description = "Admin login with valid creds")
    public void adminLogin() throws ConfigurationException, org.apache.commons.configuration.ConfigurationException {

        ExtentManager.logStep("Trying to login with valid login payload");
        AdminController adminController = new AdminController(props);

        UserModel userModel = new UserModel();
        userModel.setEmail("admin@test.com");
        userModel.setPassword("admin123");

        ExtentManager.logStep("Sending login request");
        Response response = adminController.login(userModel);

        ExtentManager.logStep("Validating status code");
        ActionDriver.assertIntEqualityWithoutSS(200, response.getStatusCode());

        JsonPath jsonPath = response.jsonPath();
        String roleActual = jsonPath.getString("role");

        ExtentManager.logStep("Validating role is admin");
        ActionDriver.assertEqualityWithoutSS("admin", roleActual);

        String token = jsonPath.getString("token");

        ExtentManager.logStep("Saving adminToken to config/environment");
        ActionDriver.setEnvironmentVariable("adminToken", token);

        ExtentManager.logStep("Validation Completed");
    }

    @Test(priority = 4, description = "Admin can get the list of all users")
    public void getUserList() {

        ExtentManager.logStep("Calling Admin userList API");
        AdminController adminController = new AdminController(props);

        Response response = adminController.userList();

        ExtentManager.logStep("Validating status code");
        ActionDriver.assertIntEqualityWithoutSS(200, response.getStatusCode());

        ExtentManager.logStep("Validation Completed");
    }

    @Test(priority = 5, description = "Admin can search user by user id")
    public void searchUserByID() {

        ExtentManager.logStep("Calling Admin searchUser API with valid userID");
        AdminController adminController = new AdminController(props);

        Response response = adminController.searchUser(props.getProperty("userID"));

        ExtentManager.logStep("Validating status code");
        ActionDriver.assertIntEqualityWithoutSS(200, response.getStatusCode());

        ExtentManager.logStep("Validation Completed");
    }

    @Test(priority = 6, description = "Search user by invalid id")
    public void searchInvalidUserByID() {

        ExtentManager.logStep("Calling Admin searchUser API with invalid userID");
        AdminController adminController = new AdminController(props);

        Response response = adminController.searchUser("dgbweyweqdfwef123323256");

        ExtentManager.logStep("Validating status code");
        ActionDriver.assertIntEqualityWithoutSS(404, response.getStatusCode());

        JsonPath jsonPath = response.jsonPath();
        String msgActual = jsonPath.getString("message");

        ExtentManager.logStep("Validating error message contains 'User not found'");
        ActionDriver.assertContainsWithoutSS("User not found", msgActual);

        ExtentManager.logStep("Validation Completed");
    }

    @Test(priority = 7, description = "Admin can update user info")
    public void updateUser() throws IOException, InterruptedException {

        ExtentManager.logStep("Preparing user update payload");
        AdminController adminController = new AdminController(props);

        com.github.javafaker.Faker faker = new com.github.javafaker.Faker();
        UserModel userModel = new UserModel();

        userModel.setFirstName(faker.name().firstName());
        userModel.setLastName(faker.name().lastName());
        userModel.setEmail(props.getProperty("userEmail"));
        userModel.setPhoneNumber("0170" + RandomDataUtility.generateRandomNumber(1000000, 9999999));
        userModel.setAddress("Alaska");
        userModel.setGender("Male");

        ExtentManager.logStep("Calling updateUser API");
        Response response = adminController.updateUser(userModel);

        ExtentManager.logStep("Validating status code");
        ActionDriver.assertIntEqualityWithoutSS(200, response.getStatusCode());

        ExtentManager.logStep("Validation Completed");
    }
}
