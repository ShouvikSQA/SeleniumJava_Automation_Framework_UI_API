package com.dailyfinance.pages;

import com.dailyfinance.actiondriver.ActionDriver;
import com.dailyfinance.models.UserModel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class RegistrationPage {

    private ActionDriver actionDriver;

    public RegistrationPage(WebDriver driver) {
        this.actionDriver = new ActionDriver(driver);
    }

    private By btnRegister = By.cssSelector("a[href='/register']");

    private By txtFirstname = By.id("firstName");

    private By txtLastname = By.id("lastName");

    private By txtEmail = By.id("email");

    private By txtPassword = By.id("password");

    private By txtPhoneNumber = By.id("phoneNumber");

    private By txtAddress = By.id("address");

    private By rbGender = By.cssSelector("[type='radio']");

    private By chkAcceptTerms = By.cssSelector("[type='checkbox']");

    private By btnSubmitReg = By.id("register");


    public void doRegistration(UserModel userModel) {

        actionDriver.enterText(txtFirstname, userModel.getFirstName());
        actionDriver.enterText(txtLastname, userModel.getLastName() != null ? userModel.getLastName() : "");
        actionDriver.enterText(txtEmail, userModel.getEmail());
        actionDriver.enterText(txtPassword, userModel.getPassword());
        actionDriver.enterText(txtPhoneNumber, userModel.getPhoneNumber());
        actionDriver.enterText(txtAddress, userModel.getAddress() != null ? userModel.getAddress() : "");

        // rbGender.get(0).click();  -> click first radio button (male/female)
        actionDriver.click(rbGender);

        actionDriver.click(chkAcceptTerms);
        actionDriver.click(btnSubmitReg);
    }

    public void doRegAssertion(String expectedMsg) throws InterruptedException {
        Thread.sleep(2500);
        By by = By.className("Toastify__toast");
        String successMessageActual= actionDriver.getText(by);
        String successMessageExpected=expectedMsg;
        System.out.println(successMessageActual);
        actionDriver.assertContains( successMessageExpected , successMessageActual );
       // Assert.assertTrue(successMessageActual.contains(successMessageExpected));
    }

    public void invalidRegAssertion(String id, String msg) throws InterruptedException {

        actionDriver.waitForElementToBeVisible(By.id(id));
        String errorMessageActual = actionDriver.getAttributeValue( By.id(id) , "validationMessage" );
        String errorMessageExpected = msg;
        System.out.println(errorMessageActual);

        actionDriver.assertContains( errorMessageExpected , errorMessageActual );



    }



}
