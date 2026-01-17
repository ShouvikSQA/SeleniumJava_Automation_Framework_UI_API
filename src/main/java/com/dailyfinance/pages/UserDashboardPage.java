package com.dailyfinance.pages;

import com.dailyfinance.actiondriver.ActionDriver;
import com.dailyfinance.base.UIBaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class UserDashboardPage {

    private ActionDriver actionDriver;
    private WebDriver driver;

    public UserDashboardPage(WebDriver driver) {
        this.driver = driver;
        this.actionDriver = UIBaseClass.getActionDriver();
    }

    // Locators
    private By rowData = By.xpath("(//div[@class='summary']/span)[1]");
    private By table = By.tagName("tbody");
    private By txtCost = By.xpath("(//div[@class='summary']/span)[2]");
    private By txtSearch = By.className("search-input");
    private By tableBody = By.tagName("tbody");
    private By btnAddCost = By.className("add-cost-button");
    private By expectedTotalPriceTxt = By.xpath("(//tbody//tr)[1]//td[3]");



    public void itemAssertion() {
        actionDriver.waitForElementToBeVisible(table);
        String str = actionDriver.getAttributeValue(rowData , "innerText" );
        String actualTotalRow = str.replaceAll("\\D", "");

        String expectedTotalRow = "5";
        actionDriver.assertEquality(expectedTotalRow,actualTotalRow);
    }

    public void assertTotalCost(int totalSum) {
        actionDriver.waitForElementToBeVisible(table);
        String str = actionDriver.getAttributeValue(txtCost , "innerText" );
        str = str.replaceAll("\\D", "");

        int actualPrice = Integer.parseInt(str);
        actionDriver.assertIntEquality( totalSum , actualPrice);
    }

    public void searchItem() {
        actionDriver.enterText(txtSearch, "Pizza");

        String expectedTotalPrice = actionDriver.getText(expectedTotalPriceTxt) ;

        // txtCost.get(1)
        String str = actionDriver.getAttributeValue( txtCost , "innerText" );
        String actualTotalPrice = str.replaceAll("\\D", "");

        actionDriver.assertEquality( expectedTotalPrice , actualTotalPrice  );
    }

    public void clickAddCost() {
        actionDriver.click(btnAddCost);
    }
}
