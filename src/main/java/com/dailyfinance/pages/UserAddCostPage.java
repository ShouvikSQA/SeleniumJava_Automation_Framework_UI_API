package com.dailyfinance.pages;

import com.dailyfinance.actiondriver.ActionDriver;
import com.dailyfinance.base.UIBaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UserAddCostPage {

    private ActionDriver actionDriver;
    private WebDriver driver;

    public UserAddCostPage(WebDriver driver) {
        this.driver = driver;
        this.actionDriver = UIBaseClass.getActionDriver();
    }

    // Locators
    private By btnIncreaseQuantity = By.xpath("(//button[@type='button'])[3]");
    private By txtItemName = By.id("itemName");
    private By txtAmount = By.id("amount");
    private By dateElement = By.id("purchaseDate");
    private By txtRemark = By.id("remarks");
    private By dropDownMonths = By.id("month");
    private By btnSubmit = By.cssSelector("[type='submit']");
    private By btnAddCost = By.className("add-cost-button");

    // Actions
    public void clickAddCost() {
        actionDriver.click(btnAddCost);
    }

    public void addCost(String name, String amount, int quantity, String date, String month, String remark) {
        actionDriver.enterText(txtItemName, name);


        for (int i = 2; i <= quantity; i++) {
            actionDriver.click(btnIncreaseQuantity);
        }

        actionDriver.enterText(txtAmount, amount);

        // purchase date
        actionDriver.enterText(dateElement, date);

        // month dropdown

        actionDriver.selectByValue(dropDownMonths,month);

        actionDriver.enterText(txtRemark, remark);

        actionDriver.click(btnSubmit);

        actionDriver.acceptAlert();
    }
}
