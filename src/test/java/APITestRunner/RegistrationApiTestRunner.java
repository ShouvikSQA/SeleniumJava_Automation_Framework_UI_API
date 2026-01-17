package APITestRunner;


import com.dailyfinance.actiondriver.ActionDriver;
import com.dailyfinance.base.APIBaseClass;
import com.dailyfinance.controller.RegistrationController;
import com.dailyfinance.models.UserModel;
import com.dailyfinance.utilities.ExtentManager;
import com.dailyfinance.utilities.RandomDataUtility;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import javax.naming.ConfigurationException;

public class RegistrationApiTestRunner extends APIBaseClass {

    @Test(priority = 1, description = "New user registration with Invalid Email")
    public void registerWithInvalidEmail() {

        ExtentManager.logStep("Preparing invalid email registration payload");
        RegistrationController registrationController = new RegistrationController(props);

        com.github.javafaker.Faker faker = new com.github.javafaker.Faker();
        UserModel userModel = new UserModel();

        userModel.setFirstName(faker.name().firstName());
        userModel.setLastName(faker.name().lastName());
        userModel.setEmail(faker.name().firstName().toLowerCase() + RandomDataUtility.generateRandomNumber(1000, 9000));
        userModel.setPassword("1234");
        userModel.setPhoneNumber("0170" + RandomDataUtility.generateRandomNumber(1000000, 9999999));
        userModel.setAddress(faker.address().fullAddress());
        userModel.setGender("Male");
        userModel.setTermsAccepted(true);

        ExtentManager.logStep("Calling register API");
        Response response = registrationController.register(userModel);

        ExtentManager.logStep("Validating status code");
        ActionDriver.assertIntEqualityWithoutSS(400, response.getStatusCode());

        ExtentManager.logStep("Validation Completed");
    }

    @Test(priority = 2, description = "New user registration with Invalid Phone Number")
    public void registerWithInvalidPhoneNumber() {

        ExtentManager.logStep("Preparing invalid phone registration payload");
        RegistrationController registrationController = new RegistrationController(props);

        com.github.javafaker.Faker faker = new com.github.javafaker.Faker();
        UserModel userModel = new UserModel();

        userModel.setFirstName(faker.name().firstName());
        userModel.setLastName(faker.name().lastName());
        userModel.setEmail(faker.name().firstName().toLowerCase() + RandomDataUtility.generateRandomNumber(1000, 9000) + "@gmail.com");
        userModel.setPassword("1234");
        userModel.setPhoneNumber("yhs$#!" + RandomDataUtility.generateRandomNumber(10000, 99999));
        userModel.setAddress(faker.address().fullAddress());
        userModel.setGender("Male");
        userModel.setTermsAccepted(true);

        ExtentManager.logStep("Calling register API");
        Response response = registrationController.register(userModel);

        ExtentManager.logStep("Validating status code");
        ActionDriver.assertIntEqualityWithoutSS(400, response.getStatusCode());

        ExtentManager.logStep("Validation Completed");
    }

    @Test(priority = 3, description = "New user registration with only mandatory data")
    public void registrationUsingMandatoryFields() {

        ExtentManager.logStep("Preparing mandatory-only registration payload");
        RegistrationController registrationController = new RegistrationController(props);

        com.github.javafaker.Faker faker = new com.github.javafaker.Faker();
        UserModel userModel = new UserModel();

        userModel.setFirstName(faker.name().firstName());
        userModel.setLastName("");
        userModel.setEmail(faker.name().firstName().toLowerCase() + RandomDataUtility.generateRandomNumber(1000, 9000) + "@gmail.com");
        userModel.setPassword("1234");
        userModel.setPhoneNumber("0170" + RandomDataUtility.generateRandomNumber(1000000, 9999999));
        userModel.setAddress("");
        userModel.setGender("Male");
        userModel.setTermsAccepted(true);

        ExtentManager.logStep("Calling register API");
        Response response = registrationController.register(userModel);

        ExtentManager.logStep("Validating status code");
        ActionDriver.assertIntEqualityWithoutSS(201, response.getStatusCode());

        ExtentManager.logStep("Validation Completed");
    }

    @Test(priority = 4, description = "New user registration with all data")
    public void newUserRegistration() throws ConfigurationException, org.apache.commons.configuration.ConfigurationException {

        ExtentManager.logStep("Preparing full registration payload");
        RegistrationController registrationController = new RegistrationController(props);

        com.github.javafaker.Faker faker = new com.github.javafaker.Faker();
        UserModel userModel = new UserModel();

        userModel.setFirstName(faker.name().firstName());
        userModel.setLastName(faker.name().lastName());
        userModel.setEmail(faker.name().firstName().toLowerCase() + RandomDataUtility.generateRandomNumber(1000, 9000) + "@gmail.com");
        userModel.setPassword("1234");
        userModel.setPhoneNumber("0170" + RandomDataUtility.generateRandomNumber(1000000, 9999999));
        userModel.setAddress(faker.address().fullAddress());
        userModel.setGender("Male");
        userModel.setTermsAccepted(true);

        ExtentManager.logStep("Calling register API");
        Response response = registrationController.register(userModel);

        ExtentManager.logStep("Validating status code");
        ActionDriver.assertIntEqualityWithoutSS(201, response.getStatusCode());

        ExtentManager.logStep("Extracting userID and userEmail");
        JsonPath jsonPath = response.jsonPath();

        String userID = jsonPath.getString("_id");
        String userEmail = jsonPath.getString("email");

        ExtentManager.logStep("Saving userID and userEmail to config/environment");
        ActionDriver.setEnvironmentVariable("userID", userID);
        ActionDriver.setEnvironmentVariable("userEmail", userEmail);

        ExtentManager.logStep("Validation Completed");
    }

    @Test(priority = 5, description = "New user registration with duplicate Email")
    public void registrationWithDuplicateEmail() {

        ExtentManager.logStep("Preparing duplicate email registration payload");
        RegistrationController registrationController = new RegistrationController(props);

        com.github.javafaker.Faker faker = new com.github.javafaker.Faker();
        UserModel userModel = new UserModel();

        userModel.setFirstName(faker.name().firstName());
        userModel.setLastName(faker.name().lastName());
        userModel.setEmail(props.getProperty("userEmail"));
        userModel.setPassword("1234");
        userModel.setPhoneNumber("0170" + RandomDataUtility.generateRandomNumber(1000000, 9999999));
        userModel.setAddress(faker.address().fullAddress());
        userModel.setGender("Male");
        userModel.setTermsAccepted(true);

        ExtentManager.logStep("Calling register API");
        Response response = registrationController.register(userModel);

        ExtentManager.logStep("Validating status code");
        ActionDriver.assertIntEqualityWithoutSS(400, response.getStatusCode());

        JsonPath jsonPath = response.jsonPath();
        String errorMsg = jsonPath.getString("message");

        ExtentManager.logStep("Validating error message contains expected text");
        ActionDriver.assertContainsWithoutSS("User already exists with this email address", errorMsg);

        ExtentManager.logStep("Validation Completed");
    }
}

