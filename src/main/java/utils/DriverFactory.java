package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {
    // Sử dụng ThreadLocal để hỗ trợ chạy song song
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();


    // Phương thức này nhận browserName làm tham số
    public static WebDriver getDriver(String browserName) {
        if (driver.get() == null) {
            initializeDriver(browserName);
        }
        return driver.get();
    }

    private static void initializeDriver(String browserName) {
        switch (browserName.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver.set(new ChromeDriver());
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver.set(new FirefoxDriver());
                break;
            case "edge": // Hỗ trợ Edge
                WebDriverManager.edgedriver().setup();
                driver.set(new EdgeDriver());
                break;
            default:
                throw new IllegalArgumentException("Browser " + browserName + " is not supported. Please choose 'chrome', 'firefox', or 'edge'.");
        }
        driver.get().manage().window().maximize();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove(); // Xóa WebDriver khỏi ThreadLocal
        }
    }
}
