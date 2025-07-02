package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;
import utils.LoggerUtil;

import java.time.Duration;

public class RegisterPage{
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By firstNameTextBox = By.id("firstname");
    private final By lastNameTextBox = By.id("lastname");
    private final By isSubscribedCheckBox = By.id("is_subscribed");
    private final By emailTextBox = By.id("email_address");
    private final By passwordTextBox = By.id("password");
    private final By confirmPasswordTextBox = By.id("password-confirmation");
    private final By createAccountButton = By.id("send2");

    private final By firstNameErrorEmpty = By.id("firstname-error");
    private final By lastNameErrorEmpty = By.id("lastname-error");
    private final By emailError = By.id("email_address-error");
    private final By passwordError = By.id("password-error");
    private final By confirmPasswordError = By.id("password-confirmation-error");

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
        /*
        explicit wait, wait 5 seconds
         */
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public void enterFirstName(String firstName) {
        driver.findElement(firstNameTextBox).sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        driver.findElement(lastNameTextBox).sendKeys(lastName);
    }

    public void enterEmail(String email) {
        driver.findElement(emailTextBox).sendKeys(email);
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

    public void submitEmptyForm() {
        clickCreateAccountButton();
    }

    public String getFirstNameErrorEmpty() {
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameErrorEmpty));
        return error.getText();
    }
    public String getLastNameErrorEmpty() {
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameErrorEmpty));
        return error.getText();
    }
    public String getEmailError() {
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(emailError));
        return error.getText();
    }
    public String getPasswordError() {
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordError));
        return error.getText();
    }
    public String getConfirmPasswordError() {
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPasswordError));
        return error.getText();
    }
}