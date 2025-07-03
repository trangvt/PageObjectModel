package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.RegisterPage;
import utils.LoggerUtil;
import utils.ScreenshotUtil;

@Listeners(ScreenshotUtil.class)
@Epic("Web Testing")
@Feature("Register Functionality")
public class RegisterTest extends BaseTest {
    private static final String PAGE_PATH = "default/customer/account/create/";
    private static final String errorRequiredField = "This is a required field.";
    private static final String errorInvalidEmailFormat = "Please enter a valid email address (Ex: johndoe@domain.com).";
    private static final String errorPasswordLength = "Minimum length of this field must be equal or greater than 8 symbols. Leading and trailing spaces will be ignored.";
    private static final String errorWeakPassword = "Minimum of different classes of characters in password is 3. Classes of characters: Lower Case, Upper Case, Digits, Special Characters.";
    private static final String errorMismatchPassword = "Please enter the same value again.";

    private RegisterPage registerPage;

    @BeforeMethod
    public void setup() {
        LoggerUtil.info("RegisterPage - setup");

        driver.get(baseUrl + PAGE_PATH);
        registerPage = new RegisterPage(driver);
    }

    /*
    Scenario: Verify error message for empty required fields
    Given I am on the registration page
    When I leave all required fields empty
    And I click the Create an Account button
    Then I should see "This is a required field" error message for each required field
      | Field            |
      | First Name       |
      | Last Name        |
      | Email            |
      | Password         |
      | Confirm Password |
     */
    @Test(priority = 1, description = "Verify error message for empty required fields")
    @Story("User Register")
    @Description("Verify error message for empty required fields")
    public void testAllEmptyFields() throws InterruptedException {
        registerPage.submitEmptyForm();

        Assert.assertEquals(registerPage.getFirstNameErrorEmpty(), errorRequiredField, "Wrong error message");
        Assert.assertEquals(registerPage.getLastNameErrorEmpty(), errorRequiredField, "Wrong error message");
        Assert.assertEquals(registerPage.getEmailError(), errorRequiredField, "Wrong error message");
        Assert.assertEquals(registerPage.getPasswordError(), errorRequiredField, "Wrong error message");
        Assert.assertEquals(registerPage.getConfirmPasswordError(), errorRequiredField, "Wrong error message");
    }

    /*
    Scenario: Verify error message for invalid email format
    Given I am on the registration page
    When I enter invalid email format "invalidemail"
    And I enter valid data in other required fields
      | Field            | Value          |
      | First Name       | John           |
      | Last Name        | Doe            |
      | Password         | Password123!   |
      | Confirm Password | Password123!   |
    And I click the Create an Account button
    Then I should see an error message for invalid email format
     */
    @Test(priority = 2, description = "Verify error message for invalid email format")
    @Story("User Register")
    @Description("Verify error message for invalid email format")
    public void testInvalidEmailFormat() {
        registerPage.enterFirstName("John");
        registerPage.enterLastName("Doe");
        registerPage.enterEmail("testemail");
        registerPage.enterPassword("Password123");
        registerPage.enterConfirmPassword("Password123");
        registerPage.clickCreateAccountButton();

        Assert.assertEquals(registerPage.getEmailError(), errorInvalidEmailFormat, "Wrong error message");
    }

    /*
    Scenario: Verify error message for short password
    Given I am on the registration page
    When I enter a weak password "weak"
    And I enter valid data in other required fields
      | Field            | Value          |
      | First Name       | John           |
      | Last Name        | Doe            |
      | Email            | john.doe@example.com |
      | Password         | short           |
    And I click the Create an Account button
    Then I should see "Minimum length of this field must be equal or greater than 8 symbols. Leading and trailing spaces will be ignored."
     */
    @Test(priority = 3, description = "Verify error message for short password")
    @Story("User Register")
    @Description("Verify error message for short password")
    public void testShortPassword() {
        registerPage.enterFirstName("John");
        registerPage.enterLastName("Doe");
        registerPage.enterEmail("john.doe@example.com");
        registerPage.enterPassword("short");
        registerPage.enterConfirmPassword("short");
        registerPage.clickCreateAccountButton();

        Assert.assertEquals(registerPage.getPasswordError(), errorPasswordLength, "Wrong error message");
    }

    @Test(priority = 4, description = "Verify error message for weak password - has Lower Case, Upper Case")
    @Story("User Register")
    @Description("Verify error message for weak password - has Lower Case, Upper Case")
    public void testWeakPassword() {
        registerPage.enterFirstName("John");
        registerPage.enterLastName("Doe");
        registerPage.enterEmail("john.doe@example.com");
        registerPage.enterPassword("Weakpasswork");
        registerPage.enterConfirmPassword("Weakpasswork");
        registerPage.clickCreateAccountButton();

        Assert.assertEquals(registerPage.getPasswordError(), errorWeakPassword, "Wrong error message");
    }

    /*
    Scenario: Verify error message for password mismatch
    Given I am on the registration page
    When I enter mismatched passwords
      | Field            | Value          |
      | First Name       | John           |
      | Last Name        | Doe            |
      | Email            | john.doe@example.com |
      | Password         | Password123!   |
      | Confirm Password | Password456!   |
    And I click the Create an Account button
    Then I should see an error message for password mismatch
     */
    @Test(priority = 5, description = "Verify error message for password mismatch")
    @Story("User Register")
    @Description("Verify error message for password mismatch")
    public void testMismatchPassword() {
        registerPage.enterFirstName("John");
        registerPage.enterLastName("Doe");
        registerPage.enterEmail("john.doe@example.com");
        registerPage.enterPassword("Password123!");
        registerPage.enterConfirmPassword("Password456!");
        registerPage.clickCreateAccountButton();

        Assert.assertEquals(registerPage.getConfirmPasswordError(), errorMismatchPassword, "Wrong error message");
    }
}
