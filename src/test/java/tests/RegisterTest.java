package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.RegisterPage;
import utils.DriverFactory;

import java.util.UUID;

public class RegisterTest {
    private WebDriver driver;
    private RegisterPage registerPage;

    @BeforeMethod
    @Parameters("browser") // Tên tham số phải khớp với tên trong testng.xml
    public void setup(String browserName) {
        driver = DriverFactory.getDriver(browserName); // Truyền tên trình duyệt vào getDriver()
        registerPage = new RegisterPage(driver);
        registerPage.navigateToRegisterPage();
    }

    @Test(priority = 1, description = "Successful registration with valid details")
    public void testRegisterSuccess() {
        String randomEmail = "testuser_" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";
        registerPage.enterFirstName("Test");
        registerPage.enterLastName("User");
        registerPage.enterEmail(randomEmail);
        registerPage.enterPassword("Password@123");
        registerPage.enterConfirmPassword("Password@123");
        registerPage.clickCreateAccountButton();
        Assert.assertTrue(registerPage.getSuccessMessage().contains("Thank you for registering with Main Website Store."), "Success message not displayed or incorrect.");
    }

    @Test(priority = 2, description = "Registration with existing email")
    public void testRegisterExistingEmail() {
        // Sử dụng một email đã được đăng ký trước đó (hoặc đăng ký thành công ở test case khác)
        registerPage.enterFirstName("Test");
        registerPage.enterLastName("User");
        registerPage.enterEmail("existinguser@example.com"); // Thay bằng email đã tồn tại
        registerPage.enterPassword("Password@123");
        registerPage.enterConfirmPassword("Password@123");
        registerPage.clickCreateAccountButton();
        Assert.assertTrue(registerPage.isErrorMessageDisplayed(), "Error message should be displayed for existing email.");
        Assert.assertTrue(registerPage.getErrorMessage().contains("There is already an account with this email address."), "Incorrect error message for existing email.");
    }

    @Test(priority = 3, description = "Registration with mismatching passwords")
    public void testRegisterMismatchPassword() {
        String randomEmail = "mismatch_" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";
        registerPage.enterFirstName("Test");
        registerPage.enterLastName("User");
        registerPage.enterEmail(randomEmail);
        registerPage.enterPassword("Password@123");
        registerPage.enterConfirmPassword("Password@Wrong"); // Mật khẩu không khớp
        registerPage.clickCreateAccountButton();
        Assert.assertTrue(driver.findElement(By.id("password-confirmation-error")).isDisplayed(), "Password confirmation error message should be displayed.");
        Assert.assertTrue(driver.findElement(By.id("password-confirmation-error")).getText().contains("Please enter the same value again."), "Incorrect error message for mismatching passwords.");
    }

    @Test(priority = 4, description = "Registration with weak password")
    public void testRegisterWeakPassword() {
        String randomEmail = "weakpass_" + UUID.randomUUID().toString().substring(0, 8) + "@example.com";
        registerPage.enterFirstName("Test");
        registerPage.enterLastName("User");
        registerPage.enterEmail(randomEmail);
        registerPage.enterPassword("123"); // Mật khẩu yếu
        registerPage.enterConfirmPassword("123");
        registerPage.clickCreateAccountButton();
        Assert.assertTrue(driver.findElement(By.id("password-error")).isDisplayed(), "Password error message should be displayed for weak password.");
        Assert.assertTrue(driver.findElement(By.id("password-error")).getText().contains("Minimum length of this field must be equal or greater than 8 symbols."), "Incorrect error message for weak password.");
    }

    @Test(priority = 5, description = "Registration with empty mandatory fields")
    public void testRegisterEmptyFields() {
        registerPage.clickCreateAccountButton(); // Click without entering anything
        Assert.assertTrue(driver.findElement(By.id("firstname-error")).isDisplayed(), "First name error message should be displayed.");
        Assert.assertTrue(driver.findElement(By.id("lastname-error")).isDisplayed(), "Last name error message should be displayed.");
        Assert.assertTrue(driver.findElement(By.id("email_address-error")).isDisplayed(), "Email error message should be displayed.");
        Assert.assertTrue(driver.findElement(By.id("password-error")).isDisplayed(), "Password error message should be displayed.");
        Assert.assertTrue(driver.findElement(By.id("password-confirmation-error")).isDisplayed(), "Confirm password error message should be displayed.");
    }


    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
