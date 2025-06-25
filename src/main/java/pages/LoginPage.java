package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.LoggerUtil;

import java.time.Duration;
import java.util.Objects;

/*
Chỉ chứa các hành động và element liên quan đến login page
 */
public class LoginPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final By usernameTextBox = By.id("email");
    private final By passwordTextBox = By.id("pass");
    private final By signInButton = By.id("send2");
    private final By emailRequiredError = By.id("email-error");
    private final By passwordRequiredError = By.id("pass-error");
    private final By genericErrorMessage = By.xpath("(//div[@data-bind='html: $parent.prepareMessageForHtml(message.text)'])[1]");
    private final By forgotPasswordLink = By.xpath("(//a[@class='action remind'])[1]");

    /*
    This is constructor() vì trùng với className
     */
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void enterEmail(String username) {
        //driver.findElement(usernameTextBox).clear();
        driver.findElement(usernameTextBox).sendKeys(username);
    }

    public void enterPassword(String password) {
        //driver.findElement(passwordTextBox).clear();
        driver.findElement(passwordTextBox).sendKeys(password);
    }

    public void clickSignInButton() {
        driver.findElement(signInButton).click();
    }

    // Lấy thông báo lỗi chung
    public String getGenericErrorMessage() {
        return driver.findElement(genericErrorMessage).getText();
    }

    // Lấy thông báo lỗi "This is a required field." cho Email
    public String getEmailRequiredErrorMessage() {
        return driver.findElement(emailRequiredError).getText();
    }

    // Lấy thông báo lỗi "This is a required field." cho Password
    public String getPasswordRequiredErrorMessage() {
        return driver.findElement(passwordRequiredError).getText();
    }

    public void clickForgotPasswordLink() {
        wait.until(ExpectedConditions.elementToBeClickable(forgotPasswordLink)).click();
    }

    public boolean isSignInButtonEnabled() {
        try {
            // Chờ cho nút hiển thị và có thể click được (enable)
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(signInButton));
            return button.isEnabled(); // Kiểm tra thuộc tính enabled
        } catch (org.openqa.selenium.TimeoutException | org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }
}
