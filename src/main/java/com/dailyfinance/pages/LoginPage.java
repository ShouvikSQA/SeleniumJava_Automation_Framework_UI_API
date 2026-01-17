package com.dailyfinance.pages;

import com.dailyfinance.actiondriver.ActionDriver;
import com.dailyfinance.base.UIBaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private ActionDriver actionDriver;

    public LoginPage(WebDriver driver) {
        this.actionDriver = UIBaseClass.getActionDriver();
    }

    private By txtEmail = By.id("email");

    private By txtPassword = By.id("password");

    private By btnLogin = By.cssSelector("button[type='submit']");

    private By btnProfileIcon = By.cssSelector("[data-testid='AccountCircleIcon']");

    private By btnProfileMenuItemLogout = By.xpath("(//*[@role='menuitem'])[2]");

    private By dashboardMsg = By.tagName("h2");

    // Method to perform login
    public void login(String email, String password) {
        actionDriver.enterText(txtEmail, email);
        actionDriver.enterText(txtPassword, password );
        actionDriver.click(btnLogin);
    }

    public void clearLoginCreds() {

        actionDriver.clearFieldByKeyboard(txtEmail);
        actionDriver.clearFieldByKeyboard(txtPassword);


    }

    public void logout() {
        actionDriver.click(btnProfileIcon);
        actionDriver.waitForElementToBeClickable(btnProfileMenuItemLogout);
        actionDriver.click(btnProfileMenuItemLogout);

    }


}
