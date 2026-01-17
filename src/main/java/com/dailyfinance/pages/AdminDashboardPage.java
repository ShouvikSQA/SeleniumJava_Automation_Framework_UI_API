package com.dailyfinance.pages;

import com.dailyfinance.actiondriver.ActionDriver;
import com.dailyfinance.base.UIBaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminDashboardPage {

    private ActionDriver actionDriver;

    public AdminDashboardPage(WebDriver driver) {
        this.actionDriver = UIBaseClass.getActionDriver();
    }

    // Locators (converted to By like your LoginPage)

    private By txtSearchBox = By.className("search-box");


    private By firstRowEmailCell = By.xpath("//tbody/tr[1]/td[3]");

    public void tableDataSearching(String email) throws InterruptedException {


        Thread.sleep(3000);

        actionDriver.enterText(txtSearchBox, email);

        String actualEmail = actionDriver.getText(firstRowEmailCell);

        actionDriver.assertEquality(email, actualEmail);
    }
}
