package UITestRunner;

import com.dailyfinance.base.UIBaseClass;
import com.dailyfinance.models.UserModel;
import com.dailyfinance.pages.RegistrationPage;
import com.dailyfinance.utilities.ExtentManager;
import com.dailyfinance.utilities.GmailReaderUtility;
import com.dailyfinance.utilities.JSONUtility;
import com.dailyfinance.utilities.RandomDataUtility;
import com.github.javafaker.Faker;
import org.apache.commons.configuration.ConfigurationException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;

public class RegistrationTestRunner extends UIBaseClass {

    private RegistrationPage registrationPage;

    @BeforeClass
    public void setupPages() {
        registrationPage = new RegistrationPage(UIBaseClass.getDriver());

    }

    @Test(priority = 4, description = "User can register successfully with valid data")
    public void verifyUserRegistration() throws InterruptedException, IOException, ParseException, ConfigurationException {

        ExtentManager.logStep("Navigating to Registration Page");
        UIBaseClass.getDriver().findElement(By.cssSelector("a[href='/register']")).click();

        ExtentManager.logStep("Entering registration information and submitting form");
        Faker faker=new Faker();

        String firstname=faker.name().firstName();
        String lastname=faker.name().lastName();
        String email = RandomDataUtility.geneateRandomEmail();
        String password="1234";
        String phonenumber= "01505"+ RandomDataUtility.generateRandomNumber(100000,999999);
        String address=faker.address().fullAddress();



        UserModel userModel = new UserModel();
        userModel.setFirstName(firstname);
        userModel.setLastName(lastname);
        userModel.setEmail(email);
        userModel.setPassword(password);
        userModel.setPhoneNumber(phonenumber);
        userModel.setAddress(address);

        registrationPage.doRegistration(userModel);

        ExtentManager.logStep("Verifying registration success message");

       registrationPage.doRegAssertion("successfully");
       Thread.sleep(8000);
        GmailReaderUtility.assertRegistrationEmail(firstname);

        ExtentManager.logStep("Registration  Validation Successful");

        JSONObject userObj=new JSONObject();
        userObj.put("firstName",firstname);
        userObj.put("email",email);
        userObj.put("password",password);
        userObj.put("phoneNumber",phonenumber);

        JSONUtility.saveDataJSONArray(userObj,"./src/test/resources/testdata/UserData.json");

        ExtentManager.logStep("Data Saved into JSON File Successfully");

        // Waiting for register test Link to be loaded and clickable properly
        // Thread.sleep(3000);

        getActionDriver().waitForElementToBeClickable( By.cssSelector("a[href='/register']") );
    }

    @Test(priority = 5, description = "User can register successfully with only mandatory data")
    public void verifyUserRegistrationMandatoryData() throws InterruptedException, IOException, ParseException, ConfigurationException {

        ExtentManager.logStep("Navigating to Registration Page");
        UIBaseClass.getDriver().findElement(By.cssSelector("a[href='/register']")).click();

        ExtentManager.logStep("Entering registration information and submitting form");
        Faker faker=new Faker();

        String firstname=faker.name().firstName();
        String email = RandomDataUtility.geneateRandomEmail();
        String password="1234";
        String phonenumber= "01505"+ RandomDataUtility.generateRandomNumber(100000,999999);



        UserModel userModel = new UserModel();
        userModel.setFirstName(firstname);
        userModel.setEmail(email);
        userModel.setPassword(password);
        userModel.setPhoneNumber(phonenumber);

        registrationPage.doRegistration(userModel);


        ExtentManager.logStep("Verifying registration success message");

        registrationPage.doRegAssertion("successfully");
        Thread.sleep(2000);
        GmailReaderUtility.assertRegistrationEmail(firstname);

        ExtentManager.logStep("Registration  Validation Successful");

        JSONObject userObj=new JSONObject();
        userObj.put("firstName",firstname);
        userObj.put("email",email);
        userObj.put("password",password);
        userObj.put("phoneNumber",phonenumber);

        JSONUtility.saveDataJSONArray(userObj,"./src/test/resources/testdata/UserData.json");

        ExtentManager.logStep("Data Saved into JSON File Successfully");

        // Waiting for register test Link to be loaded and clickable properly
        // Thread.sleep(3000);

        getActionDriver().waitForElementToBeClickable( By.cssSelector("a[href='/register']") );
    }

    @Test(priority = 1, description = "User can not register by missing any one mandatory info")
    public void verifyUserRegistrationMissingMandatoryData() throws IOException, ParseException, InterruptedException {

        ExtentManager.logStep("Navigating to Registration Page");
        UIBaseClass.getDriver().findElement(By.cssSelector("a[href='/register']")).click();

        ExtentManager.logStep("Entering registration information with missing mandatory field");

        Faker faker = new Faker();

        String firstname = faker.name().firstName();
        String email = RandomDataUtility.geneateRandomEmail();
        String password = ""; // Missing mandatory field
        String phonenumber = "01505" + RandomDataUtility.generateRandomNumber(100000, 999999);

        UserModel userModel = new UserModel();
        userModel.setFirstName(firstname);
        userModel.setEmail(email);
        userModel.setPassword(password);
        userModel.setPhoneNumber(phonenumber);

        registrationPage.doRegistration(userModel);

        ExtentManager.logStep("Verifying validation message for missing password field");

        Thread.sleep(4000);

        registrationPage.invalidRegAssertion("password", "Please fill out this field");

        ExtentManager.logStep("Validation message for missing mandatory verified successfully");

        ExtentManager.logStep("Navigating back to Registration Page");
        UIBaseClass.getDriver().findElement(By.tagName("a")).click();

        // Waiting for register link to be loaded and clickable properly
        getActionDriver().waitForElementToBeClickable(
                By.cssSelector("a[href='/register']")
        );
    }


    @Test(priority = 2, description = "User can not register by Invalid EMail")
    public void verifyUserRegistrationWithInvalidEmail() throws IOException, ParseException, InterruptedException {

        ExtentManager.logStep("Navigating to Registration Page");
        UIBaseClass.getDriver().findElement(By.cssSelector("a[href='/register']")).click();

        ExtentManager.logStep("Entering registration information with invalid email and submitting form");
        Faker faker = new Faker();

        String firstname = faker.name().firstName();
        String email = "abcdefg"; // Invalid email
        String password = "1234@#";
        String phonenumber = "01505" + RandomDataUtility.generateRandomNumber(100000, 999999);

        UserModel userModel = new UserModel();
        userModel.setFirstName(firstname);
        userModel.setEmail(email);
        userModel.setPassword(password);
        userModel.setPhoneNumber(phonenumber);

        registrationPage.doRegistration(userModel);

        Thread.sleep(4000);

        ExtentManager.logStep("Verifying validation message for invalid email field");
        registrationPage.invalidRegAssertion("email", "Please include an '@' in the email address");

        ExtentManager.logStep("Validation message verified successfully");

        ExtentManager.logStep("Navigating back to Registration Page");
        UIBaseClass.getDriver().findElement(By.tagName("a")).click();

        // Waiting for register link to be loaded and clickable properly
        getActionDriver().waitForElementToBeClickable(By.cssSelector("a[href='/register']"));
    }

    @Test(priority = 3, description = "User can not register by Invalid Phone Number")
    public void verifyUserRegistrationWithInvalidPhoneNumber() throws IOException, ParseException, InterruptedException {

        ExtentManager.logStep("Navigating to Registration Page");
        UIBaseClass.getDriver().findElement(By.cssSelector("a[href='/register']")).click();

        ExtentManager.logStep("Entering registration information with invalid phone number and submitting form");
        Faker faker = new Faker();

        String firstname = faker.name().firstName();
        String email = "xyzab" + RandomDataUtility.generateRandomNumber(1000, 9999) + "@gmail.com";
        String password = "1234";
        String phonenumber = "abcde" + RandomDataUtility.generateRandomNumber(100000, 999999); // Invalid phone

        UserModel userModel = new UserModel();
        userModel.setFirstName(firstname);
        userModel.setEmail(email);
        userModel.setPassword(password);
        userModel.setPhoneNumber(phonenumber);

        registrationPage.doRegistration(userModel);
        Thread.sleep(2000);


        ExtentManager.logStep("Verifying validation message for invalid phone number field");
        registrationPage.invalidRegAssertion("phoneNumber", "Phone Number is Invalid");

        ExtentManager.logStep("Validation message verified successfully");

        // Waiting for register link to be loaded and clickable properly
        getActionDriver().waitForElementToBeClickable(
                By.cssSelector("a[href='/register']")
        );
    }

    @Test(priority = 6, description = "User can not register by using already registered email")
    public void verifyUserRegistrationWithUsedEmail()
            throws InterruptedException, IOException, ParseException{

        ExtentManager.logStep("Navigating to Registration Page");
        UIBaseClass.getDriver().findElement(By.cssSelector("a[href='/register']")).click();

        ExtentManager.logStep("Entering registration information with already registered email");
        Faker faker = new Faker();

        String firstname = faker.name().firstName();
        String lastname  = faker.name().lastName();
        String email     = JSONUtility.getLatestUserProperty("email", "./src/test/resources/testdata/UserData.json"); // Already registered email
        String password  = "1234#@";
        String phonenumber = "01505" + RandomDataUtility.generateRandomNumber(100000, 999999);
        String address   = faker.address().fullAddress();

        UserModel userModel = new UserModel();
        userModel.setFirstName(firstname);
        userModel.setLastName(lastname);
        userModel.setEmail(email);
        userModel.setPassword(password);
        userModel.setPhoneNumber(phonenumber);
        userModel.setAddress(address);

        registrationPage.doRegistration(userModel);


        ExtentManager.logStep("Verifying validation message for already registered email");
        registrationPage.doRegAssertion("already exists");

        ExtentManager.logStep("Duplicate email validation verified successfully");


    }





}
