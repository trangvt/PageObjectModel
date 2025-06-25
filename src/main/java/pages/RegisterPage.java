package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;
import utils.LoggerUtil;

import java.time.Duration;

public class RegisterPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Elements
    private By firstNameTextBox = By.id("firstname");
    private By lastNameTextBox = By.id("lastname");
    private By emailTextBox = By.id("email_address"); // ID cho email ở trang đăng ký có thể khác
    private By passwordTextBox = By.id("password");
    private By confirmPasswordTextBox = By.id("password-confirmation");
    private By createAccountButton = By.cssSelector("button[title='Create an Account']");
    private By successMessage = By.cssSelector(".message-success.success div");
    private By errorMessage = By.cssSelector(".message-error.error div");
    private By validationMessage = By.cssSelector(".mage-error"); // Common class for field validation errors

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void navigateToRegisterPage() {
        driver.get("https://magento-demo.mageplaza.com/customer/account/create/");
    }

    public void enterFirstName(String firstName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameTextBox)).sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameTextBox)).sendKeys(lastName);
    }

    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailTextBox)).sendKeys(email);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordTextBox)).sendKeys(password);
    }

    public void enterConfirmPassword(String confirmPassword) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPasswordTextBox)).sendKeys(confirmPassword);
    }

    public void clickCreateAccountButton() {
        wait.until(ExpectedConditions.elementToBeClickable(createAccountButton)).click();
    }

    public String getSuccessMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).getText();
    }

    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText();
    }

    public String getValidationMessage(By fieldLocator) {
        // Tìm thông báo lỗi ngay dưới trường input cụ thể
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(fieldLocator));
        return field.findElement(By.xpath("./following-sibling::div[@class='mage-error']")).getText();
    }

    public boolean isErrorMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}