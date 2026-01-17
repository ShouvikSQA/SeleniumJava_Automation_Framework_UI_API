package com.dailyfinance.pages;

import com.dailyfinance.actiondriver.ActionDriver;
import com.dailyfinance.base.UIBaseClass;
import com.dailyfinance.utilities.JSONUtility;
import com.dailyfinance.utilities.RandomDataUtility;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

public class UserProfilePage {

    private WebDriver driver;
    private ActionDriver actionDriver;

    public UserProfilePage(WebDriver driver) {
        this.driver = driver;
        this.actionDriver = UIBaseClass.getActionDriver();
    }

    //  Locators
    private By btnEdit = By.xpath("(//button)[2]");
    private By btnThirdButton = By.xpath("(//button)[3]");
    private By txtEmail = By.cssSelector("input[name='email']");
    private By imgFileSelect = By.className("upload-input");
    private By profileImage = By.className("profile-image");

    // Actions
    public void updateEmail() throws InterruptedException, IOException, ParseException {

        actionDriver.scrollToElement( btnEdit );

        // Click Edit button
        actionDriver.click(btnEdit);

        // Generate and type new email
        String newEmail = RandomDataUtility.geneateRandomEmail();
        System.out.println(newEmail);
        actionDriver.clearFieldByKeyboard(txtEmail);
        actionDriver.enterText(txtEmail, newEmail);

        // Click Update button
        actionDriver.click(btnThirdButton);

        // Alert validation + accept
        String userUpdateMsgExpected = "User updated successfully!";
        String userUpdateMsgActual = actionDriver.getAlertText();
        actionDriver.assertContains(userUpdateMsgExpected, userUpdateMsgActual);
        actionDriver.acceptAlert();

        // Update stored creds
        JSONUtility.updateCreds("email", newEmail, "./src/test/resources/testdata/UserData.json");

    }

    public void uploadPicture() throws InterruptedException {

        // Click Edit button
        actionDriver.click(btnEdit);

        // Upload file
        String filePath = System.getProperty("user.dir") + "./src/test/resources/testdata/WorldCup.jpeg";
        actionDriver.uploadFile(imgFileSelect, filePath);

        // Click Upload Image Button
        actionDriver.click(btnEdit);

        // Alert validation + accept
        String imgUploadMsgExpected = "Image uploaded successfully!";
        String imgUploadMsgActual = actionDriver.getAlertText();
        ActionDriver.assertContainsWithoutSS(imgUploadMsgExpected, imgUploadMsgActual);
        actionDriver.acceptAlert();

        // Wait until image is visible
        actionDriver.waitForElementToBeVisible(profileImage);

        // Click Update button
        actionDriver.click(btnThirdButton);

        // Profile update alert validation + accept
        String userUpdateMsgExpected = "User updated successfully!";
        String userUpdateMsgActual = actionDriver.getAlertText();
        ActionDriver.assertContainsWithoutSS(userUpdateMsgExpected, userUpdateMsgActual);
        Thread.sleep(1000);
        actionDriver.acceptAlert();
    }
}
