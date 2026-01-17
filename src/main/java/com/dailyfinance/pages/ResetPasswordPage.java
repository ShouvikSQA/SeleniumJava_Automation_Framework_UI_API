package com.dailyfinance.pages;

import com.dailyfinance.actiondriver.ActionDriver;
import com.dailyfinance.base.UIBaseClass;
import com.dailyfinance.utilities.GmailReaderUtility;
import com.dailyfinance.utilities.JSONUtility;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import javax.naming.ConfigurationException;
import java.io.IOException;

public class ResetPasswordPage {

    private ActionDriver actionDriver;

    public ResetPasswordPage(WebDriver driver) {
        this.actionDriver = UIBaseClass.getActionDriver();
    }

    //  Locators
    private By txtEmail = By.id(":r1:");
    private By btnSendReset = By.cssSelector("button[type='submit']");
    private By txtInformation = By.tagName("p");

    private By txtNewPassword = By.xpath("(//input)[1]");
    private By txtConfirmPassword = By.xpath("(//input)[2]");

    private By btnResetPass = By.xpath("(//button)[1]");

    private By linkResetPassword = By.cssSelector("a[href='/forgot-password']");

    //  Actions

    public String getResetPass(String userEmail)
            throws ConfigurationException, org.apache.commons.configuration.ConfigurationException,
            IOException, InterruptedException, ParseException {

        actionDriver.enterText(txtEmail, userEmail);
        actionDriver.click(btnSendReset);
        actionDriver.isDisplayed(txtInformation);

        // wait for email to arrive
        Thread.sleep(8000);

        String email = GmailReaderUtility.readLatestMail();
        String resetPassLink = email.split(": ")[1].trim();

        UIBaseClass.getDriver().get(resetPassLink);

        String newPass = "4321";

        actionDriver.enterText(txtNewPassword, newPass);
        actionDriver.enterText(txtConfirmPassword, newPass);
        actionDriver.click(btnResetPass);

        String confirmationMsgActual = actionDriver.getText(txtInformation);
        String confirmationMsgExpected = "Password reset successfully";
        actionDriver.assertContains(confirmationMsgExpected, confirmationMsgActual);

        JSONUtility.updateCreds("password", newPass ,"./src/test/resources/testdata/UserData.json" );

        return newPass;
    }

    public void sendUnregisteredEmail(String userEmail) {

        actionDriver.enterText(txtEmail, userEmail);
        actionDriver.click(btnSendReset);

        String msgActual = actionDriver.getText(txtInformation);
        String msgExpected = "Your email is not registered";
        actionDriver.assertContains(msgExpected, msgActual);
    }

    public void resetPassOlderLink(String userEmail)
            throws ConfigurationException, org.apache.commons.configuration.ConfigurationException, IOException {

        String email = GmailReaderUtility.readLatestMail();
        String resetPassLink = email.split(": ")[1].trim();

        UIBaseClass.getDriver().get(resetPassLink);

        String newPass = "654321";

        actionDriver.enterText(txtNewPassword, newPass);
        actionDriver.enterText(txtConfirmPassword, newPass);
        actionDriver.click(btnResetPass);

        String confirmationMsgActual = actionDriver.getText(txtInformation);
        String confirmationMsgExpected = "Error resetting password";
        actionDriver.assertContains(confirmationMsgExpected, confirmationMsgActual);
    }

    public void resetPassMismatch(String userEmail)
            throws ConfigurationException, org.apache.commons.configuration.ConfigurationException,
            IOException, InterruptedException {

        actionDriver.enterText(txtEmail, userEmail);
        actionDriver.click(btnSendReset);
        actionDriver.isDisplayed(txtInformation);

        Thread.sleep(3000);

        String email = GmailReaderUtility.readLatestMail();
        String resetPassLink = email.split(": ")[1].trim();

        UIBaseClass.getDriver().get(resetPassLink);

        actionDriver.enterText(txtNewPassword, "12345");
        actionDriver.enterText(txtConfirmPassword, "54321");
        actionDriver.click(btnResetPass);

        String confirmationMsgActual = actionDriver.getText(txtInformation);
        String confirmationMsgExpected = "Passwords do not match";
        actionDriver.assertContains(confirmationMsgExpected, confirmationMsgActual);
    }
}
