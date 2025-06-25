package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.ConfigReader;
import utils.LoggerUtil;

/*
chứa logic kiểm thử, bao gồm cả việc điều hướng đến trang
 */
public class LoginTest extends BaseTest{
    private static final String LOGIN_PATH = "default/customer/account/login/";
    private static final String ERROR_MESSAGE_GENERAL = "The account sign-in was incorrect or your account is disabled temporarily. Please wait and try again later.";

    private LoginPage loginPage;

    @BeforeMethod
    public void setup() {
        LoggerUtil.info("LoginTest - setup");

        //navigate to Customer Login page
        driver.get(baseUrl + LOGIN_PATH);
        loginPage = new LoginPage(driver);
    }

    @DataProvider(name = "invalidLoginData")
    public Object[][] getInvalidLoginData() {
        return new Object[][] {
            {"", "", "This is a required field.", "This is a required field.", ""},     // Cả hai trống
            {"", "anypassword", "This is a required field.", "", ""},                   // Email trống
            {"test@example.com", "", "", "This is a required field.", ""},              // Password trống
            {ConfigReader.getProperty("login_valid_username"), "wrongpass", "", "", ERROR_MESSAGE_GENERAL},     // Email đúng, password sai
            {"wrong@example.com", "wrongpass", "", "", ERROR_MESSAGE_GENERAL},          // Sai cả hai
            // Thêm các trường hợp khác như: email không đúng định dạng, tài khoản bị khóa, ...
        };
    }

    @Test(priority = 1, dataProvider = "invalidLoginData", description = "Verify login with various invalid credentials")
    public void testLoginWithInvalidCredentials(
            String email,
            String password,
            String expectedEmailError,
            String expectedPasswordError,
            String expectedGenericError) throws InterruptedException {
        LoggerUtil.info("LoginTest - testLoginWithInvalidCredentials - Start for: " + email + "/" + password);

        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        // Thread.sleep(1000);
        loginPage.clickSignInButton();

        // Kiểm tra thông báo lỗi cụ thể cho email/password trống
        if (!expectedEmailError.isEmpty()) {
            Assert.assertEquals(loginPage.getEmailRequiredErrorMessage(), expectedEmailError, "Wrong error message - email error");
        }
        if (!expectedPasswordError.isEmpty()) {
            Assert.assertEquals(loginPage.getPasswordRequiredErrorMessage(), expectedPasswordError, "Wrong error message - password error");
        }

        // Kiểm tra thông báo lỗi chung
        if (!expectedGenericError.isEmpty()) {
            Assert.assertEquals(loginPage.getGenericErrorMessage(), expectedGenericError, "Wrong error message - general message");
        }

        LoggerUtil.info("LoginTest - testLoginWithInvalidCredentials - End");
    }

    @Test(priority = 2, description = "Verify successful login with valid credentials")
    public void testLoginSuccess() throws InterruptedException {
        LoggerUtil.info("LoginTest - testLoginSuccess - start");
        LoggerUtil.info("Current Url: " + driver.getCurrentUrl());
        Assert.assertEquals(driver.getTitle(), "Customer Login", "This is not Customer Login Page");

        loginPage.enterEmail(ConfigReader.getProperty("login_valid_username"));
        loginPage.enterPassword(ConfigReader.getProperty("login_valid_password"));
        loginPage.clickSignInButton();

        // Kiểm tra kết quả đăng nhập thành công
        Assert.assertEquals(driver.getTitle(), "My Account", "Customer can not login");
    }

}