# Project Summary :
I have Created A **Hybrid Framework for Both UI and API Automation** using Selenium Java. It is an project where we have automated Different scenarios of DailyFinance Website Both 
in UI and API Level. I have followed the Page Object Model (POM) an for designing and developing the project. Also Added Extent Report with Screenshot and Logging Functionality

## Technology used:
- Language: Java
- Build System: Gradle
- Automation tool and framework: Selenium and TestNG, Rest Assured
- Data manipulation: Simple JSON and MS Excel
- Report : Extent Report
- Logging : Log4j2

## Project Description -

### Complete Framework Explanation :
  [ Click Here to see the Details Description and Workflow of the Framework ](https://drive.google.com/file/d/1MXTFM4sR5XUj0DRgs1bwvu6b4B3jOJkg/view?usp=sharing) 

### UI Automation :
1. Visit the site https://dailyfinance.roadtocareer.net/. Register a new user. ANd also check the confirmation email is received or not.
2. Do Reset password using the link you will get in your gmail
3. Now login with the new password to ensure login successful and login with old password fails.
4. Update the user's profile image and also update gmail of the user.
5. Add 5 costs from 5 rows in Excel File  
6. Print the total cost and assert it against your expected total sum of the amounts.
7. Search for an item by name from the list and assert that the total cost matches the item's price.
8. Log in as admin (pass admin credentials from the terminal)
9. Search by the updated gmail and Assert that updated user email credentials are is showing on admin dashboard.
#### Output :
 <img width="1790" height="746" alt="image" src="https://github.com/user-attachments/assets/0b54cb8f-7278-4d96-a49e-8478469dd83a" />

#### Excel Test Case Report
- [Click Here To See The Test Case Excel Report](https://docs.google.com/spreadsheets/d/1r-2_TqiQ_RXdBcFvSuvhAYKNsxrNkuxZmJHiVHEvxko/edit?usp=sharing)

#### Output Video Link :
-  [Click Here To see the output video of Smoke Suite Automation Script ](https://drive.google.com/file/d/1qx7AfyI90dmzj1aOx5ZN89UAE5x2Xl1n/view?usp=sharing)

### API Automation :
1. Visit the url https://dailyfinance.roadtocareer.net/

2. Create a Postman collection by inspecting the API requests for the following features from the Network tab:

    - Register a new user  
    - Login by admin  
    - Get user list  
    - Search the new user by user ID  
    - Edit the user info (e.g., `firstname`, `phonenumber`)  
    - Login by any user  
    - Get item list  
    - Add any item  
    - Edit any item name  
    - Delete any item from the item list
#### API Collection Documentation
  [Click Here To See the API Collection Documentation ](https://documenter.getpostman.com/view/28923318/2sB2j7c8q3)
#### Test Cases For API
   [Click Here to see the Test Cases for API Collection](https://docs.google.com/spreadsheets/d/1RUCVZOHitbYrhqRl6NeQlEx0172XidJ3/edit?usp=sharing&ouid=108139447743460312613&rtpof=true&sd=true)
#### Output :
<img width="1738" height="753" alt="image" src="https://github.com/user-attachments/assets/9fa51bd4-0fb4-47c7-b9c3-0001484bc889" />

    



### How to run this project
- Clone the project
- Open the project from IntellIJ; File>Open>Select and expand folder>Open as project
- Hit this command: `gradle clean test -PsuiteName="UITestSuite.xml"` to run the UI Automation suite or `gradle clean test -PsuiteName="APITestSuite.xml"` to run the API Automation suite.







