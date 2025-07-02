package tests;

import base.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.LoggerUtil;
import utils.ScreenshotUtil;

@Listeners(ScreenshotUtil.class)

public class Search extends BaseTest {
    private static final String LOGIN_PATH = "default/customer/account/login/";

    private LoginPage loginPage;

    @BeforeMethod
    public void setup() {
        LoggerUtil.info("LoginTest - setup");

        //navigate to Customer Login page
        driver.get(baseUrl + LOGIN_PATH);
        loginPage = new LoginPage(driver);
    }

    public void performActionThatFails() {
        throw new AssertionError("Test thất bại để chụp ảnh màn hình");
    }

    @Test
    public void testWithFailure() {
        this.performActionThatFails(); // Gây lỗi để kích hoạt listener
    }
}
